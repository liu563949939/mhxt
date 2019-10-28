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
        StringBuilder sCondition = new StringBuilder("select t.* from t_customer t where 1 = 1");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from t_customer where 1 = 1");

        //管理员判断(管理员可以查看已租掉的房信息)
        String type = entity.getModifyTime();
        if(!"1".equals(type)){
            sCondition.append(" and status <> '2'");
            sConditionCount.append(" and status <> '2'");
        }

        //查询条件(租售状态、装修状态、房屋状态、登记时间、楼层、厅室)
        String createUser = entity.getCreateUser(); //登记人
        String decorateStatus = entity.getDecorateStatus(); //装修状态
        String status = entity.getStatus(); //房屋状态
        String createTime = entity.getCreateTime(); //创建时间
        String phone = entity.getPhone(); //房主电话
        String cellName = entity.getCellName(); //小区名称
        String houseNum = entity.getHouseNum(); //房号
        Integer floor = entity.getFloor(); //楼层
        Integer room = entity.getRoom(); //厅室

        //租售状态
        if(createUser != null && !createUser.equals("")){
            sCondition.append(" and createUser = '" + createUser + "'");
            sConditionCount.append(" and createUser = '" + createUser + "'");
        }
        //装修状态
        if(decorateStatus != null && !decorateStatus.equals("")){
            sCondition.append(" and decorateStatus = '" + decorateStatus + "'");
            sConditionCount.append((" and decorateStatus = '" + decorateStatus + "'"));
        }
        //房屋状态
        if(status != null && !status.equals("")){
            sCondition.append(" and status = '" + status + "'");
            sConditionCount.append((" and status = '" + status + "'"));
        }
        //创建时间
        if(createTime != null && !"".equals(createTime)){
            sCondition.append(" and createTime like '" + createTime + "%'");
            sConditionCount.append(" and createTime like '" + createTime + "%'");
        }
        //房主电话
        if(phone != null && !"".equals(phone)){
            sCondition.append(" and phone like '" + phone + "%'");
            sConditionCount.append(" and phone like '" + phone + "%'");
        }
        //小区名称
        if(cellName != null && !"".equals(cellName)){
            sCondition.append(" and cellName like '%" + cellName + "%'");
            sConditionCount.append(" and cellName like '%" + cellName + "%'");
        }
        //房号
        if(houseNum != null && !"".equals(houseNum)){
            sCondition.append(" and houseNum like '%" + houseNum + "%'");
            sConditionCount.append(" and houseNum like '%" + houseNum + "%'");
        }
        //楼层
        if(floor != null && !floor.equals("")){
            sCondition.append(" and floor = " + floor + "");
            sConditionCount.append((" and floor = " + floor + ""));
        }
        //厅室
        if(room != null){
            sCondition.append(" and room = " + room + "");
            sConditionCount.append((" and room = " + room + ""));
        }

        //按照修改时间排序
        sCondition.append(" order by modifyTime desc");
        Integer iStart = (entity.getPage()-1)*entity.getLimit() + 1; //开始
        Integer iEnd = entity.getPage() * entity.getLimit(); //结束

        String SQL = "select * from (select rownum as sNum,v.* From (" + sCondition.toString() + ") v) where sNum between " + String.valueOf(iStart) + " and " + String.valueOf(iEnd);
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

    //4.判断是否已经录入过(根据小区名称+房号判断)
    public Boolean isExist(CustomerEntity entity){
        //1.条件准备
        String cellName = entity.getCellName(); //小区名称
        String houseNum = entity.getHouseNum(); //房号
        StringBuilder sConditon = new StringBuilder("select count(*) from t_customer where 1=1");
        sConditon.append(" and cellName = '" + cellName + "' and housenum = '" + houseNum + "'");
        //2.语句执行
        Query query = entityManager.createNativeQuery(sConditon.toString());
        Object obj = query.getSingleResult();
        int i = Integer.valueOf(String.valueOf(obj));
        //3.结果返回
        if(i==1){
            return true;
        }else{
            return false;
        }
    }
}
