package nist.module.mhxt.service;

import nist.module.mhxt.entity.DicNameEntity;
import nist.module.mhxt.repository.DicNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DicNameService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DicNameRepository dicNameRepository;

    //1-1.查询所有
    public List<DicNameEntity> queryAll(){
        //1.条件处理
        String sql  = "select * from s_dic_name where 1 = 1";

        //2.语句执行
        Query query = entityManager.createNativeQuery(sql, DicNameEntity.class);
        List<DicNameEntity> dataList = query.getResultList();

        //3.结果返回
        return dataList;
    }

    //1-2.根据jlbh查询记录
    public DicNameEntity queryById(DicNameEntity entity){
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select * from s_dic_name where 1=1");
        String jlbh = entity.getJlbh();
        if(jlbh!=null && !"".equals(jlbh)){
            sCondition.append(" and jlbh = '" + jlbh + "'");
        }
        //2.语句执行
        Query query = entityManager.createNativeQuery(sCondition.toString(), DicNameEntity.class);
        DicNameEntity dicNameEntity = (DicNameEntity)query.getSingleResult();
        //3.结果返回
        return dicNameEntity;
    }

    //2.新增修改
    public DicNameEntity save(DicNameEntity entity){
        return dicNameRepository.save(entity);
    }

    //3.删除
    public void deleteById(DicNameEntity entity){
        dicNameRepository.deleteById(entity.getJlbh());
    }
}
