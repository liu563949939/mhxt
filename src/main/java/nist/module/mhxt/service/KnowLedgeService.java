package nist.module.mhxt.service;

import nist.module.mhxt.entity.KnowLedgeEntity;
import nist.module.mhxt.repository.KnowLedgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KnowLedgeService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private KnowLedgeRepository knowLedgeRepository;

    //1-1.查询(带分页)
    public Map<String, Object> query(KnowLedgeEntity entity){
        Map<String,Object> sFhz = new HashMap<String,Object>();
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select rownum as sNum,t.* from t_data t where 1 = 1");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from t_data where 1 = 1");

        String type = entity.getType(); //类型
        String name = entity.getName(); //名称
        String content = entity.getContent(); //内容
        if(type != null && !"".equals(type)){
            sCondition.append(" and type = '" + type + "'");
            sConditionCount.append(" and type = '" + type + "'");
        }
        if(name != null && !"".equals(name)){
            sCondition.append(" and name like '%" + name + "%'");
            sConditionCount.append(" and name like '%" + name + "%'");
        }
        if(content != null && !"".equals(content)){
            sCondition.append(" and content like '%" + content + "%'");
            sConditionCount.append(" and content like '%" + content + "%'");
        }
        //分页
        Integer iStart = (entity.getPage()-1)*entity.getLimit() + 1; //开始
        Integer iEnd = entity.getPage() * entity.getLimit(); //结束
        //语句
        String SQL = "select * From (" + sCondition.toString() + ") where sNum between " + String.valueOf(iStart) + " and " + String.valueOf(iEnd);
        String SQLCount = sConditionCount.toString();

        Query query = entityManager.createNativeQuery(SQL, KnowLedgeEntity.class);
        Query queryCount = entityManager.createNativeQuery(SQLCount);
        List<KnowLedgeEntity> dataList = query.getResultList(); //查询结果
        Object obj = queryCount.getSingleResult(); //总数结果
        Long count = Long.valueOf(String.valueOf(obj));

        //3.结果返回
        sFhz.put("dataList",dataList);
        sFhz.put("count",count);
        return sFhz;
    }

    //2.保存
    public KnowLedgeEntity save(KnowLedgeEntity entity) {
       return knowLedgeRepository.save(entity);
    }

    //3.删除
    public void delete(KnowLedgeEntity entity){
        knowLedgeRepository.delete(entity);
    }
}
