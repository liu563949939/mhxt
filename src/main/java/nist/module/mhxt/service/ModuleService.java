package nist.module.mhxt.service;

import nist.module.mhxt.entity.ModuleEntity;
import nist.module.mhxt.entity.RoleModuleEntity;
import nist.module.mhxt.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    //1-1.query方法(所有)
    public List<ModuleEntity> getDataListAll(String roleId, String type) {
        //1.定义SQL
        String SQL = "select * From s_module";
        //2.语句执行
        Query query = entityManager.createNativeQuery(SQL, ModuleEntity.class);
        List<ModuleEntity> dataList = query.getResultList(); //查询结果

        //3.循环判断是否已授权给该角色
        if (type == "yes") {
            for (int i = 0; i < dataList.size(); i++) {
                String moduleId = dataList.get(i).getJlbh();
                boolean isChecked = this.isChecked(roleId, moduleId);
                if (isChecked) {
                    dataList.get(i).setCheckArr("1");
                } else {
                    dataList.get(i).setCheckArr("0");
                }
            }
        }

        //4.组织返回结果
        return dataList;
    }

    //1-2.分页查询

    //1-3.根据JLBH查询
    public Object getDataListById(ModuleEntity moduleEntity) {
        //1.条件判断
        StringBuilder sCondition = new StringBuilder("select * from s_module where 1 = 1");
        String jlbh = moduleEntity.getJlbh();
        if (jlbh != null && !"".equals(jlbh)) {
            sCondition.append(" and jlbh = '" + jlbh + "'");
        }
        String SQL = sCondition.toString();
        //2.语句执行
        Query query = entityManager.createNativeQuery(SQL, ModuleEntity.class);
        Object dataList = query.getSingleResult();
        //3.结果返回
        return dataList;
    }

    //1-4.根据jlbh查询子项最大序列号
    public Integer getSubModuleListById(ModuleEntity entity) {
        //1.条件判断
        StringBuilder sCondition = new StringBuilder("select max(serial) max from s_module where 1 = 1");
        String jlbh = entity.getJlbh();
        if (jlbh != null && !"".equals(jlbh)) {
            sCondition.append(" and parentId = '" + jlbh + "'");
        }else{
            sCondition.append(" and 1=0");
        }
        String SQL = sCondition.toString();
        //2.语句执行
        Query query = entityManager.createNativeQuery(SQL);
        Object data = query.getSingleResult();
        Integer iMaxSerial = 0;
        if(data != null){
            iMaxSerial = Integer.valueOf(data.toString()) + 1;
        }
        //3.结果返回
        return iMaxSerial;
    }

    //2.save方法
    public ModuleEntity save(ModuleEntity moduleEntity) {
        return moduleRepository.save(moduleEntity);
    }

    //3.delete方法
    public void delete(ModuleEntity moduleEntity) {
        moduleRepository.delete(moduleEntity);
    }


    //4.根据roleId和moduleId查询是否已经选中
    public boolean isChecked(String roleId, String moduleId) {
        //1.条件组织
        StringBuilder sCondition = new StringBuilder("select count(*) from s_role_module where 1 = 1");
        sCondition.append(" and roleId = '" + roleId + "'");
        sCondition.append(" and moduleId = '" + moduleId + "'");

        //2.语句处理
        Query query = entityManager.createNativeQuery(sCondition.toString());
        Object obj = query.getSingleResult();
        int i = Integer.valueOf(String.valueOf(obj));
        if (i == 1) {
            return true;
        } else {
            return false;
        }
    }

}
