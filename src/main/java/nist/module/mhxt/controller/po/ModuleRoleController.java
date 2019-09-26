package nist.module.mhxt.controller.po;

import nist.module.mhxt.entity.ModuleEntity;
import nist.module.mhxt.service.po.ModuleRoleService;
import nist.module.mhxt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/moduleRole")
@ResponseBody
public class ModuleRoleController {
    @Autowired
    private ModuleRoleService moduleRoleService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String getDataList(@RequestBody ModuleEntity moduleEntity){
        Map<String,Object> dataList = moduleRoleService.getDataList(moduleEntity);
        return ResponseUtil.writer("0","success",dataList.get("dataList"),(Long) dataList.get("count"));
    }
}
