package nist.module.mhxt.service.po;

import nist.module.mhxt.entity.ModuleEntity;
import nist.module.mhxt.entity.po.ModuleRoleEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleRoleService {
    @PersistenceContext
    private EntityManager entityManager;

    //1.query方法
    public Map<String,Object> getDataList(ModuleEntity moduleEntity){
        Map<String,Object> sFhz = new HashMap<String,Object>();
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select rownum as sNum,t.jlbh,t.roleid,v.name,t.moduleid from s_role_module t,s_role v where t.roleid = v.jlbh");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from s_role_module t,s_role v where t.roleid = v.jlbh");
        if(moduleEntity.getJlbh()!=null&& !moduleEntity.getJlbh().equals("")){
            sCondition.append(" and roleId = '" + moduleEntity.getJlbh() + "'");
            sConditionCount.append(" and roleId = '" + moduleEntity.getJlbh() + "'");
        }
        //分页处理
        Integer iStart = (moduleEntity.getPage()-1)*moduleEntity.getLimit() + 1; //开始
        Integer iEnd = moduleEntity.getPage() * moduleEntity.getLimit(); //结束
        //sql语句准备
        String SQL = "select * From (" + sCondition.toString() + ") where sNum between " + String.valueOf(iStart) + " and " + String.valueOf(iEnd);
        String SQLCount = sConditionCount.toString();

        //2.语句执行
        Query query = entityManager.createNativeQuery(SQL, ModuleRoleEntity.class);
        Query queryCount = entityManager.createNativeQuery(SQLCount);
        List<ModuleEntity> dataList = query.getResultList(); //查询结果
        Object obj = queryCount.getSingleResult(); //总数结果
        Long count = Long.valueOf(String.valueOf(obj));

        //3.组织返回结果
        sFhz.put("dataList",dataList);
        sFhz.put("count",count);
        return sFhz;
    }
}
