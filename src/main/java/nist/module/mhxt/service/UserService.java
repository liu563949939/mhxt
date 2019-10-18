package nist.module.mhxt.service;

import nist.module.mhxt.entity.ModuleEntity;
import nist.module.mhxt.entity.UserEntity;
import nist.module.mhxt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.script.ScriptContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    //1-1.query方法(带分页)
    public Map<String,Object> getDataList(UserEntity userEntity){
        Map<String,Object> sFhz = new HashMap<String,Object>();
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select rownum as sNum,t.* from s_user t where 1 = 1");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from s_user where 1 = 1");

        if(userEntity.getName() != null && !userEntity.getName().equals("")){
            sCondition.append(" and name like '%" + userEntity.getName() + "%'");
            sConditionCount.append(" and name like '" + userEntity.getName() + "'");
        }
        if(userEntity.getUsername() != null && !userEntity.getUsername().equals("")){
            sCondition.append(" and username like '%" + userEntity.getUsername() + "%'");
            sConditionCount.append((" and username like '%" + userEntity.getUsername() + "%'"));
        }

        //去掉根菜单用户信息
        sCondition.append(" and jlbh <> '1'");

        Integer iStart = (userEntity.getPage()-1)*userEntity.getLimit() + 1; //开始
        Integer iEnd = userEntity.getPage() * userEntity.getLimit(); //结束

        String SQL = "select * From (" + sCondition.toString() + ") where sNum between " + String.valueOf(iStart) + " and " + String.valueOf(iEnd);
        String SQLCount = sConditionCount.toString();

        //2.语句执行
        Query query = entityManager.createNativeQuery(SQL,UserEntity.class);
        Query queryCount = entityManager.createNativeQuery(SQLCount);
        List<UserEntity> dataList = query.getResultList(); //查询结果
        Object obj = queryCount.getSingleResult(); //总数结果
        Long count = Long.valueOf(String.valueOf(obj));

        //3.结果返回
        sFhz.put("dataList",dataList);
        sFhz.put("count",count);
        return sFhz;
    }

    //1-2.根据moduleId查询对用的用户(带分页)
    public Map<String,Object> queryByModuleId(ModuleEntity entity){
        Map<String,Object> sFhz = new HashMap<String,Object>();
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select rownum as sNum,t.* from s_user t where 1 = 1");
        StringBuilder sConditionCount = new StringBuilder("select count(*) from s_user t where 1 = 1");

        String jlbh = entity.getJlbh();
        if(jlbh != null && !"".equals(jlbh)){
            sCondition.append(" and t.jlbh in (select distinct v.userid From s_user_role v where v.roleid in(select b.roleid from s_role a,s_role_module b where a.jlbh = b.roleid and b.moduleid = '" + jlbh + "')) ");
            sConditionCount.append(" and t.jlbh in (select distinct v.userid From s_user_role v where v.roleid in(select b.roleid from s_role a,s_role_module b where a.jlbh = b.roleid and b.moduleid = '" + jlbh + "')) ");
        }
        //去掉根菜单用户信息
        sCondition.append(" and jlbh <> '1'");

        Integer iStart = (entity.getPage()-1)*entity.getLimit() + 1; //开始
        Integer iEnd = entity.getPage() * entity.getLimit(); //结束

        String SQL = "select * From (" + sCondition.toString() + ") where sNum between " + String.valueOf(iStart) + " and " + String.valueOf(iEnd);
        String SQLCount = sConditionCount.toString();

        //2.语句执行
        Query query = entityManager.createNativeQuery(SQL,UserEntity.class);
        Query queryCount = entityManager.createNativeQuery(SQLCount);
        List<UserEntity> dataList = query.getResultList(); //查询结果
        Object obj = queryCount.getSingleResult(); //总数结果
        Long count = Long.valueOf(String.valueOf(obj));

        //3.结果返回
        sFhz.put("dataList",dataList);
        sFhz.put("count",count);
        return sFhz;
    }

    //2.save方法
    public UserEntity save(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    //3.delete方法
    public void delete(UserEntity userEntity){
        userRepository.delete(userEntity);
    }

    //4.查询所有
    public List<UserEntity> getDataListAll(String roleId){
        //1.获得条件
        StringBuilder sCondition = new StringBuilder("select * from s_user where 1 = 1");

        //2.语句执行
        Query query = entityManager.createNativeQuery(sCondition.toString(),UserEntity.class);
        List<UserEntity> dataList = query.getResultList(); //查询结果

        //3.判断是否选中(包含角色)
        for(int i=0;i<dataList.size();i++){
            String sFhz = this.isExists(dataList.get(i).getJlbh(),roleId);
            if(sFhz == "1"){
                dataList.get(i).setCheckArr("1");
            }else{
                dataList.get(i).setCheckArr("0");
            }
        }
        //4.结果返回
        return dataList;
    }

    //5.判断是否已存在角色
    public String isExists(String userId, String roleId){
        String sFhz = "0";
        StringBuilder sConditionCount = new StringBuilder("select count(*) from s_user_role where userId = '" + userId + "' and roleId = '" + roleId + "'");
        Query queryCount = entityManager.createNativeQuery(sConditionCount.toString());
        Object obj = queryCount.getSingleResult();
        int i = Integer.valueOf(String.valueOf(obj));
        if(i>0){
            sFhz = "1";
        }
        return sFhz;
    }

    //6.根据用户名和密码查找
    public List<UserEntity> findByName(UserEntity userEntity){
        return userRepository.findByName(userEntity);
    }

    //7.根据用户名查找对应得模块
    public List<ModuleEntity> findModuleByUserId(UserEntity userEntity){
        //1.条件处理
        String userId = userEntity.getJlbh();
        String publicSql = "and x.jlbh in (select distinct v.moduleid from s_role_module v where v.roleid in (select t.roleid From s_user_role t where t.userid = '" + userId + "'))"; //根据userId查找模块
        StringBuilder sCondition = new StringBuilder("select * from s_module x where 1=1 ");
        if(userId != null && !"".equals(userId)){
            sCondition.append(publicSql);
            sCondition.append(" and parentId = '1'");
        }else{
            return null;
        }
        //2.语句执行
        Query query = entityManager.createNativeQuery(sCondition.toString(),ModuleEntity.class);
        List<ModuleEntity> dataList = query.getResultList();
        for(int i=0;i<dataList.size();i++){
            String moduleId = dataList.get(i).getJlbh();
            //获取子模块
            dataList.get(i).setChildren(this.getModuleById(moduleId,publicSql));
        }
        //3.语句返回
        return dataList;
    }


    //7-1.根据父moduleId返回子模块列表
    public List<ModuleEntity> getModuleById(String moduleId,String publicSql){
        //1.条件处理
        StringBuilder sCondition = new StringBuilder("select * from s_module x where 1=1 ");
        if(!"".equals(moduleId)){
            sCondition.append(publicSql);
            sCondition.append(" and parentId = '" + moduleId + "'");
        }
        //2.语句执行
        Query query = entityManager.createNativeQuery(sCondition.toString(),ModuleEntity.class);
        List<ModuleEntity> dataList = query.getResultList();
        //3.语句返回
        return  dataList;
    }
}
