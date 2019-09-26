package nist.module.mhxt.controller;

import nist.module.mhxt.entity.ModuleEntity;
import nist.module.mhxt.entity.RoleEntity;
import nist.module.mhxt.service.RoleServcie;
import nist.module.mhxt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Map;

@Controller
@RequestMapping("/role")
@ResponseBody
public class RoleController {
    @Autowired
    private RoleServcie roleServcie;

    @Autowired
    private EntityManager entityManager;

    //1-1.查询所有
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String test(@Param("name") String name){
        RoleEntity roleEntity = new RoleEntity();
        Map<String,Object> result = roleServcie.getDataList(roleEntity);
        return ResponseUtil.writer("0","success",result.get("dataList"),(Long) result.get("count"));
    }

    //1-2.根据jlbh查询
    @RequestMapping(value = "/queryById",method = {RequestMethod.GET, RequestMethod.POST})
    public String queryById(@RequestBody RoleEntity roleEntity){
        String jlbh = roleEntity.getJlbh();
        if(jlbh != null && !"".equals(jlbh)){
            Object dataList = roleServcie.getDataListById(roleEntity);
            return ResponseUtil.writer("0","success",dataList);
        }else{
            return ResponseUtil.writer("0","success");
        }
    }

    //2.保存
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String save(@RequestBody RoleEntity roleEntity){
        RoleEntity roleEntity1 = roleServcie.save(roleEntity);
        return ResponseUtil.writer("0","success",roleEntity1);
    }

    //3-1.删除(根据实体删除)
    @RequestMapping(value = "/del", method = {RequestMethod.GET, RequestMethod.POST})
    public String delete(@RequestBody RoleEntity roleEntity){
        roleServcie.delete(roleEntity);
        return ResponseUtil.writer("0","success");
    }

    //3-2.删除(根据roleId删除)
    @RequestMapping(value = "/delById", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteById(@RequestBody RoleEntity roleEntity){
        roleServcie.deleteById(roleEntity.getJlbh());
        return ResponseUtil.writer("0","success");
    }
}
