package nist.module.mhxt.service;

import nist.module.mhxt.entity.RoleEntity;
import nist.module.mhxt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServcie {
    @Autowired
    private RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    //1.query方法
    public Map<String,Object> getDataList(RoleEntity roleEntity){
        Map<String,Object> sFhz = new HashMap<String,Object>();
        //1.获得条件
        StringBuilder sCondition = new StringBuilder("select * from s_role where 1 = 1");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from s_role where 1 = 1");
/*        //2.条件处理
        if(roleEntity.getType() != null && !roleEntity.getType().equals("")){
            sCondition.append(" and type = " + roleEntity.getType());
            sConditionCount.append(" and type = " + roleEntity.getType());
        }
        if(roleEntity.getName() != null && !roleEntity.getName().equals("")){
            sCondition.append(" and name like '%" + roleEntity.getName() + "%'");
            sConditionCount.append((" and name like '%" + roleEntity.getName() + "%'"));
        }*/
        //3.分页处理
/*        Integer iEnd = roleEntity.getLimit(); //结束
        Integer iStart = (roleEntity.getPage()-1)*iEnd; //开始
        sCondition.append(" limit " + String.valueOf(iStart) + ", " + String.valueOf(iEnd));*/
        //4.语句执行
        Query query = entityManager.createNativeQuery(sCondition.toString(),RoleEntity.class);
        Query queryCount = entityManager.createNativeQuery(sConditionCount.toString());
        List<RoleEntity> dataList = query.getResultList(); //查询结果
        Object obj = queryCount.getSingleResult();
        Long count = Long.valueOf(String.valueOf(obj));

        //5.组织返回结果
        sFhz.put("dataList",dataList);
        sFhz.put("count",count);
        return sFhz;
    }

    //1-2.(根据jlbh查询)
    public Object getDataListById(RoleEntity roleEntity){
        //1.条件判断
        StringBuilder sCondition = new StringBuilder("select * from s_role where 1 = 1");
        String jlbh = roleEntity.getJlbh();
        if(jlbh != null && !"".equals(jlbh)){
            sCondition.append(" and jlbh = '" + jlbh + "'");
        }
        String SQL = sCondition.toString();
        //2.语句执行
        Query query = entityManager.createNativeQuery(SQL, RoleEntity.class);
        Object dataList = query.getSingleResult();
        //3.结果返回
        return dataList;
    }
    //2.save方法
    public RoleEntity save(RoleEntity roleEntity){
        return roleRepository.save(roleEntity);
    }

    //3-1.delete方法(根据实体删除)
    public void delete(RoleEntity roleEntity){
        roleRepository.delete(roleEntity);
    }

    //3-2.根据主键编号删除
    public void deleteById(String roleId){
        roleRepository.deleteById(roleId);
    }
}
