package nist.module.mhxt.service.po;

import nist.module.mhxt.entity.ModuleEntity;
import nist.module.mhxt.entity.RoleEntity;
import nist.module.mhxt.entity.po.RoleModulePoEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleModulePoService {
    @PersistenceContext
    private EntityManager entityManager;

    //1-1.query(带分页)-角色关联资源
    public Map<String,Object> getDataList(RoleEntity roleEntity){
        Map<String,Object> sFhz = new HashMap<String,Object>();
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select rownum sNum,t.jlbh,t.moduleId,t.roleId,t.createTime,v.name,v.url from s_role_module t,s_module v where t.moduleId = v.jlbh and 1 = 1");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from s_role_module t,s_module v where t.moduleId = v.jlbh and 1 = 1");

        if(roleEntity.getJlbh() != null && !roleEntity.getJlbh().equals("")){
            sCondition.append(" and roleId = '" + roleEntity.getJlbh() + "'");
            sConditionCount.append(" and roleId = " + roleEntity.getJlbh());
        }

        Integer iStart = (roleEntity.getPage()-1)*roleEntity.getLimit() + 1; //开始
        Integer iEnd = roleEntity.getPage() * roleEntity.getLimit(); //结束

        String SQL = "select * From (" + sCondition.toString() + ") where sNum between " + String.valueOf(iStart) + " and " + String.valueOf(iEnd);
        String SQLCount = sConditionCount.toString();

        //2.语句执行
        Query query = entityManager.createNativeQuery(sCondition.toString(), RoleModulePoEntity.class);
        Query queryCount = entityManager.createNativeQuery(sConditionCount.toString());
        List<RoleModulePoEntity> dataList = query.getResultList(); //查询结果
        Object obj = queryCount.getSingleResult();
        Long count = Long.valueOf(String.valueOf(obj));

        //3.结果返回
        sFhz.put("dataList",dataList);
        sFhz.put("count",count);
        return sFhz;
    }

 /*   //1-2.query(带分页)-资源关联角色
    public Map<String,Object> getDataList_role(ModuleEntity moduleEntity){
        Map<String,Object> sFhz = new HashMap<String,Object>();
        //1.获得条件
        StringBuilder sCondition = new StringBuilder("select t.jlbh,t.moduleId,t.roleId,t.createTime,v.name from s_role_module t,s_role v where t.roleId = v.jlbh and 1 = 1");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from s_role_module t,s_role v where t.roleId = v.jlbh and 1 = 1");
        //2.条件处理
        if(moduleEntity.getJlbh() != null && !moduleEntity.getJlbh().equals("")){
            sCondition.append(" and moduleId = '" + moduleEntity.getJlbh() + "'");
            sConditionCount.append(" and moduleId = '" + moduleEntity.getJlbh()+ "'");
        }
        //3.分页处理
        Integer iEnd = moduleEntity.getLimit(); //结束
        Integer iStart = (moduleEntity.getPage()-1)*iEnd; //开始
        sCondition.append(" limit " + String.valueOf(iStart) + ", " + String.valueOf(iEnd));
        System.out.println(sCondition.toString());
        System.out.println(sConditionCount.toString());
        //4.语句执行
        Query query = entityManager.createNativeQuery(sCondition.toString(), RoleModulePoEntity.class);
        Query queryCount = entityManager.createNativeQuery(sConditionCount.toString());
        List<RoleModulePoEntity> dataList = query.getResultList(); //查询结果
        Object obj = queryCount.getSingleResult();
        Long count = Long.valueOf(String.valueOf(obj));

        //3.结果返回
        sFhz.put("dataList",dataList);
        sFhz.put("count",count);
        return sFhz;
    }*/
}
