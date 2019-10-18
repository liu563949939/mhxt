package nist.module.mhxt.service.po;

import nist.module.mhxt.entity.ModuleEntity;
import nist.module.mhxt.entity.RoleEntity;
import nist.module.mhxt.entity.po.ModuleRoleEntity;
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
            sConditionCount.append(" and roleId = '" + roleEntity.getJlbh() + "'");
        }
        //去掉跟模块信息
        sCondition.append(" and v.parentId <> '0'");
        sConditionCount.append(" and v.parentid <> '0'");

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

    //1-2.query(带分页)-资源关联角色
    public Map<String,Object> getDataListByModuleId(ModuleEntity moduleEntity){
        Map<String,Object> sFhz = new HashMap<String,Object>();
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select rownum as sNum,t.jlbh,t.roleid,t.moduleid,v.name from s_role_module t,s_role v where t.roleid = v.jlbh");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from s_role_module t,s_role v where t.roleid = v.jlbh");
        if(moduleEntity.getJlbh() != null && !"".equals(moduleEntity.getJlbh())){
            sCondition.append(" and moduleId = '" + moduleEntity.getJlbh() + "'");
            sConditionCount.append(" and moduleId = '" + moduleEntity.getJlbh() + "'");
        }

        Integer iStart = (moduleEntity.getPage()-1)*moduleEntity.getLimit() + 1; //开始
        Integer iEnd = moduleEntity.getPage() * moduleEntity.getLimit(); //结束

        String SQL = "select * From (" + sCondition.toString() + ") where sNum between " + String.valueOf(iStart) + " and " + String.valueOf(iEnd);
        String SQLCount = sConditionCount.toString();
        //2.语句执行
        Query query = entityManager.createNativeQuery(SQL, ModuleRoleEntity.class);
        Query queryCount = entityManager.createNativeQuery(SQLCount);
        List<ModuleEntity> dataList = query.getResultList(); //查询结果
        Object obj = queryCount.getSingleResult(); //总数结果
        Long count = Long.valueOf(String.valueOf(obj));

        //3.结果返回
        sFhz.put("dataList",dataList);
        sFhz.put("count",count);
        return sFhz;
    }
}
