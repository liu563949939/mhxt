package nist.module.mhxt.controller.po;

import nist.module.mhxt.entity.ModuleEntity;
import nist.module.mhxt.entity.RoleEntity;
import nist.module.mhxt.service.po.RoleModulePoService;
import nist.module.mhxt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/roleModulePo")
@ResponseBody
public class RoleModulePoController {
    @Autowired
    private RoleModulePoService roleModulePoService;

    //1.根据角色找资源
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String Query(@RequestBody RoleEntity roleEntity){
        Map<String,Object> result = roleModulePoService.getDataList(roleEntity);
        return ResponseUtil.writer("0","success",result.get("dataList"),(Long) result.get("count"));
    }

    //2.根据资源找角色
    @RequestMapping(value = "/queryByModuleId", method = {RequestMethod.GET, RequestMethod.POST})
    public String Query(@RequestBody ModuleEntity moduleEntity){
        Map<String,Object> result = roleModulePoService.getDataListByModuleId(moduleEntity);
        return ResponseUtil.writer("0","success",result.get("dataList"),(Long) result.get("count"));
    }
}
