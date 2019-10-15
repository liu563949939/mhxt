package nist.module.mhxt.service;

import nist.module.mhxt.entity.CustomerEntity;
import nist.module.mhxt.entity.KnowLedgeEntity;
import nist.module.mhxt.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CustomerService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    //1-1.分页查询
    public Map<String, Object> query(CustomerEntity entity){
        Map<String,Object> sFhz = new HashMap<String,Object>();
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select rownum as sNum,t.* from t_customer t where 1 = 1");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from t_customer where 1 = 1");

        //查询条件(租售状态、装修状态、房屋状态、楼层、厅室)
        String saleStatus = entity.getSaleStatus(); //租售状态
        String decorateStatus = entity.getDecorateStatus(); //装修状态
        String status = entity.getStatus(); //房屋状态
        Integer floor = entity.getFloor(); //楼层
        Integer room = entity.getRoom(); //厅室
        if(saleStatus != null && !saleStatus.equals("")){
            sCondition.append(" and saleStatus = '" + saleStatus + "'");
            sConditionCount.append(" and saleStatus = '" + saleStatus + "'");
        }
        if(decorateStatus != null && !decorateStatus.equals("")){
            sCondition.append(" and decorateStatus = '" + decorateStatus + "'");
            sConditionCount.append((" and decorateStatus = '" + decorateStatus + "'"));
        }
        if(status != null && !status.equals("")){
            sCondition.append(" and status = '" + status + "'");
            sConditionCount.append((" and status = '" + status + "'"));
        }
        if(floor != null && !floor.equals("")){
            sCondition.append(" and floor = " + floor + "");
            sConditionCount.append((" and floor = " + floor + ""));
        }
        if(room != null){
            sCondition.append(" and room = " + room + "");
            sConditionCount.append((" and room = " + room + ""));
        }
        Integer iStart = (entity.getPage()-1)*entity.getLimit() + 1; //开始
        Integer iEnd = entity.getPage() * entity.getLimit(); //结束

        String SQL = "select * From (" + sCondition.toString() + ") where sNum between " + String.valueOf(iStart) + " and " + String.valueOf(iEnd);
        String SQLCount = sConditionCount.toString();

        Query query = entityManager.createNativeQuery(SQL, CustomerEntity.class);
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
    public CustomerEntity save(CustomerEntity entity){
        return customerRepository.save(entity);
    }

    //3.删除
    public void del(CustomerEntity entity){
        customerRepository.delete(entity);
    }
}
