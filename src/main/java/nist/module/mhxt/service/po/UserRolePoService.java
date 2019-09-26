package nist.module.mhxt.service.po;

import nist.module.mhxt.entity.RoleEntity;
import nist.module.mhxt.entity.po.UserRolePoEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRolePoService {
    @PersistenceContext
    private EntityManager entityManager;

    //1.query方法
    public Map<String,Object> getDataList(RoleEntity roleEntity){
        Map<String,Object> sFhz = new HashMap<String,Object>();
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select rownum sNum,t.jlbh,t.userId,t.roleId,t.createTime,v.name From s_user_role t,s_user v where t.userId = v.jlbh and 1 = 1");
        StringBuilder sConditionCount = new StringBuilder("select count(*) From s_user_role t,s_user v where t.userId = v.jlbh and 1 = 1");

        if(roleEntity.getJlbh() != null && !roleEntity.getJlbh().equals("")){
            sCondition.append(" and roleId = '" + roleEntity.getJlbh() + "'");
            sConditionCount.append(" and roleId = " + roleEntity.getJlbh());
        }

        Integer iStart = (roleEntity.getPage()-1)*roleEntity.getLimit() + 1; //开始
        Integer iEnd = roleEntity.getPage() * roleEntity.getLimit(); //结束

        String SQL = "select * From (" + sCondition.toString() + ") where sNum between " + String.valueOf(iStart) + " and " + String.valueOf(iEnd);
        String SQLCount = sConditionCount.toString();

        //2.语句执行
        Query query = entityManager.createNativeQuery(SQL, UserRolePoEntity.class);
        Query queryCount = entityManager.createNativeQuery(SQLCount);
        List<UserRolePoEntity> dataList = query.getResultList(); //查询结果
        Object obj = queryCount.getSingleResult(); //总数结果
        Long count = Long.valueOf(String.valueOf(obj));

        //3.结果返回
        sFhz.put("dataList",dataList);
        sFhz.put("count",count);
        return sFhz;
    }

}
