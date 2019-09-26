package nist.module.mhxt.controller.po;

import nist.module.mhxt.entity.RoleEntity;
import nist.module.mhxt.service.po.UserRolePoService;
import nist.module.mhxt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/userRolePo")
@ResponseBody
public class UserRolePoController{
    @Autowired
    private UserRolePoService userRolePoService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String query(@RequestBody RoleEntity roleEntity){
        Map<String,Object> result = userRolePoService.getDataList(roleEntity);
        return ResponseUtil.writer("0","success",result.get("dataList"),(Long) result.get("count"));
    }
}
