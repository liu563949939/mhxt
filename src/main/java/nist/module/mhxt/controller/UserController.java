package nist.module.mhxt.controller;

import nist.module.mhxt.entity.ModuleEntity;
import nist.module.mhxt.entity.UserEntity;
import nist.module.mhxt.service.UserService;
import nist.module.mhxt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {
    @Autowired
    private UserService userService;

    //1-1.查询(分页)
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String query(@RequestBody UserEntity userEntity){
        Map<String,Object> result = userService.getDataList(userEntity);
        return ResponseUtil.writer("0","success",result.get("dataList"),(Long) result.get("count"));
    }

    //1-2.查询(所有)
    @RequestMapping(value = "/queryAll",method = {RequestMethod.GET, RequestMethod.POST})
    public String queryAll(@Param("roleId") String roleId){
        List<UserEntity>  dataList = userService.getDataListAll(roleId);
        return ResponseUtil.writer("0","success",dataList);
    }

    //1-3.根据moduleId查询(分页)
    @RequestMapping(value = "/queryByModuleId",method = {RequestMethod.GET,RequestMethod.POST})
    public String queryByModuleId(@RequestBody ModuleEntity entity){
        Map<String,Object> result = userService.queryByModuleId(entity);
        return ResponseUtil.writer("0","success",result.get("dataList"),(Long) result.get("count"));
    }

    //2.保存
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String save(@RequestBody UserEntity userEntity){
        UserEntity userEntity1 = userService.save(userEntity);
        return ResponseUtil.writer("0","success",userEntity1);
    }

    //3.删除
    @RequestMapping(value = "/del", method = {RequestMethod.GET, RequestMethod.POST})
    public String delete(@RequestBody UserEntity userEntity){
        userService.delete(userEntity);
        return ResponseUtil.writer("0","success");
    }

    //4.根据用户名和密码查找
    @RequestMapping(value = "/findByName",method = {RequestMethod.GET,RequestMethod.POST})
    public String findByName(@RequestBody UserEntity userEntity) {
        List<UserEntity> dataList = userService.findByName(userEntity);
        return ResponseUtil.writer("0","success",dataList);
    }

    //5.根据用户名查找对应得模块
    @RequestMapping(value = "/findModuleByUserId",method = {RequestMethod.GET,RequestMethod.POST})
    public  String findModuleByUserId(@RequestBody UserEntity userEntity){
        List<ModuleEntity> dataList = userService.findModuleByUserId(userEntity);
        return ResponseUtil.writer("0","success",dataList);
    }

}
