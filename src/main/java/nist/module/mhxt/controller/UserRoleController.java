package nist.module.mhxt.controller;

import nist.module.mhxt.entity.UserRoleEntity;
import nist.module.mhxt.service.UserRoleService;
import nist.module.mhxt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/userRole")
@ResponseBody
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String save(@RequestBody UserRoleEntity userRoleEntity){
        UserRoleEntity userRoleEntity1 = userRoleService.save(userRoleEntity);
        return ResponseUtil.writer("0","success",userRoleEntity1);
    }

    @RequestMapping(value = "/del", method = {RequestMethod.GET, RequestMethod.POST})
    public String del(@RequestBody UserRoleEntity userRoleEntity){
        userRoleService.del(userRoleEntity);
        return ResponseUtil.writer("0","success");
    }
}
