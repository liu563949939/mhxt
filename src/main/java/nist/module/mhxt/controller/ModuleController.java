package nist.module.mhxt.controller;

import nist.module.mhxt.entity.ModuleEntity;
import nist.module.mhxt.service.ModuleService;
import nist.module.mhxt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/module")
@ResponseBody
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    //1-1.查询所有(判断子节点)
    @RequestMapping(value = "/queryAll", method = {RequestMethod.GET, RequestMethod.POST})
    public String getDataListAll(@Param("roleId") String roleId){
        List<ModuleEntity> dataList = moduleService.getDataListAll(roleId,"yes");
        return ResponseUtil.writer("0","success",dataList);
    }

    //1-2.查询所有(不判断子节点)
    @RequestMapping(value = "/queryAllNoChildren", method = {RequestMethod.GET, RequestMethod.POST})
    public String getDataListAllNoChildren(@Param("roleId") String roleId){
        List<ModuleEntity> dataList = moduleService.getDataListAll(roleId,"no");
        return ResponseUtil.writer("0","success",dataList);
    }

    //1-3.根据jlbh查询
    @RequestMapping(value = "/queryById", method = {RequestMethod.GET, RequestMethod.POST})
    public String getDataListById(@RequestBody ModuleEntity moduleEntity){
        String jlbh = moduleEntity.getJlbh();
        if(jlbh != null && !"".equals(jlbh)){
            Object dataList = moduleService.getDataListById(moduleEntity);
            return ResponseUtil.writer("0","success",dataList);
        }else{
            return ResponseUtil.writer("0","success");
        }
    }

    //1-4.根据jlbh查询子项最大序列号
    @RequestMapping(value = "/getMaxSerial",method = {RequestMethod.GET,RequestMethod.POST})
    public String getMaxSerial(@RequestBody ModuleEntity entity){
        Integer sFhz = moduleService.getSubModuleListById(entity);
        return ResponseUtil.writer("0","success",sFhz);
    }
    //2.save方法
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String save(@RequestBody ModuleEntity moduleEntity){
        ModuleEntity moduleEntity1 = moduleService.save(moduleEntity);
        return ResponseUtil.writer("0","success",moduleEntity1);
    }

    //3.delete方法
    @RequestMapping(value = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public String delete(@RequestBody ModuleEntity moduleEntity){
        moduleService.delete(moduleEntity);
        return ResponseUtil.writer("0","success");
    }
}
