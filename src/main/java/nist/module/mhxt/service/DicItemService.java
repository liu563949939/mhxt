package nist.module.mhxt.service;

import nist.module.mhxt.entity.DicItemEntity;
import nist.module.mhxt.repository.DicItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DicItemService {
    @Autowired
    private DicItemRepository dicItemRepository;

    @Autowired
    private EntityManager entityManager;

    //1-1.分页查询
    public Map<String,Object> getDataList(DicItemEntity entity){
        Map<String,Object> sFhz = new HashMap<String,Object>();
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select rownum as sNum,t.* from s_dic_item t where 1 = 1");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from s_dic_item where 1 = 1");

        String dicName = entity.getName();
        if(dicName != null && !dicName.equals("")){
            sCondition.append(" and name = '" + dicName + "'");
        }

        Integer iStart = (entity.getPage()-1)*entity.getLimit() + 1; //开始
        Integer iEnd = entity.getPage() * entity.getLimit(); //结束
        String SQL = "select * From (" + sCondition.toString() + ") where sNum between " + String.valueOf(iStart) + " and " + String.valueOf(iEnd);
        String SQLCount = sConditionCount.toString();

        //2.语句执行
        Query query = entityManager.createNativeQuery(SQL,DicItemEntity.class);
        Query queryCount = entityManager.createNativeQuery(SQLCount);
        List<DicItemEntity> dataList = query.getResultList(); //查询结果
        Object obj = queryCount.getSingleResult(); //总数结果
        Long count = Long.valueOf(String.valueOf(obj));

        //3.结果返回
        sFhz.put("dataList",dataList);
        sFhz.put("count",count);
        return sFhz;
    }

    //1-2.根据jlbh查询
    public DicItemEntity queryById(DicItemEntity entity){
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select * from s_dic_item where 1 = 1");
        String jlbh = entity.getJlbh();
        if(jlbh != null && !"".equals(jlbh)){
            sCondition.append(" and jlbh = '" + jlbh + "'");
        }
        //2.语句执行
        Query query = entityManager.createNativeQuery(sCondition.toString(),DicItemEntity.class);
        DicItemEntity dataListSingle = (DicItemEntity)query.getSingleResult();
        //3.结果返回
        return dataListSingle;
    }

    //1-3.根据name查询
    public List<DicItemEntity> queryByName(DicItemEntity entity){
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select * from s_dic_item where 1 = 1");
        String dicName = entity.getName();
        if(dicName != null && !"".equals(dicName)){
            sCondition.append(" and name = '" + dicName + "'");
        }
        sCondition.append(" order by code");
        //2.语句执行
        Query query = entityManager.createNativeQuery(sCondition.toString(),DicItemEntity.class);
        List<DicItemEntity> dataList = query.getResultList();
        //3.结果返回
        return dataList;
    }

    //2.保存
    public DicItemEntity save(DicItemEntity entity){
        return dicItemRepository.save(entity);
    }

    //3.根据jlbh删除
    public void del(DicItemEntity entity){
        dicItemRepository.delete(entity);
    }

}
