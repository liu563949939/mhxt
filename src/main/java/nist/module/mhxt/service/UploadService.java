package nist.module.mhxt.service;

import nist.module.mhxt.entity.UploadEntity;
import nist.module.mhxt.repository.UploadRepository;
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
public class UploadService {
    @Autowired
    private UploadRepository uploadRepository;

    @Autowired
    private EntityManager entityManager;

    //1-1.查询(带分页)
    public Map<String, Object> query(UploadEntity entity){
        Map<String, Object> sFhz = new HashMap<String, Object>();
        //1.条件处理
        String jlbh = entity.getJlbh(); //业务主见编号
        StringBuilder sCondition = new StringBuilder("select t.*,rownum as sNum from t_upload t where 1=1");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from t_upload where 1=1");

        //条件
        if(jlbh != null && !"".equals(jlbh)){
            sCondition.append(" and glbh = '" + jlbh + "'");
            sConditionCount.append(" and glbh = '" + jlbh + "'");
        }else{
            sCondition.append(" and 1=0");
            sConditionCount.append(" and 1=0");
        }

        //分页
        Integer page = entity.getPage();
        Integer limit = entity.getLimit();
        String start = Integer.toString((page-1)*limit + 1);
        String end = Integer.toString(page*limit);
        String SQL = "select * from (" + sCondition.toString() + ") where sNum between " + start + " and " + end;
        String SQLCount = sConditionCount.toString();

        //2.语句执行
        Query query = entityManager.createNativeQuery(SQL, UploadEntity.class);
        Query query1 = entityManager.createNativeQuery(SQLCount);
        List<UploadEntity> dataList =  query.getResultList();
        Object obj = query1.getSingleResult();
        Long sCount = Long.valueOf(obj.toString());

        //3.结果返回
        sFhz.put("dataList",dataList);
        sFhz.put("count",sCount);
        return sFhz;
    }

    //2.保存
    public UploadEntity save(UploadEntity entity){
        return uploadRepository.save(entity);
    }

    //3.删除
    public void del(UploadEntity entity){
        uploadRepository.delete(entity);
    }
}
