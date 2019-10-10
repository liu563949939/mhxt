package nist.module.mhxt.controller;

import nist.module.mhxt.entity.DicNameEntity;
import nist.module.mhxt.service.DicNameService;
import nist.module.mhxt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/dicName")
@ResponseBody
public class DicNameController {
    @Autowired
    private DicNameService dicNameService;

    //1-1.查询所有
    @RequestMapping(value = "/queryAll",method = {RequestMethod.GET, RequestMethod.POST})
    public String query(){
        List<DicNameEntity> dataList = dicNameService.queryAll();
        return ResponseUtil.writer("0","success",dataList);
    }

    //1-2.根据jlbh查询记录
    @RequestMapping(value = "/queryById",method = {RequestMethod.GET, RequestMethod.POST})
    public String queryById(@RequestBody DicNameEntity entity){
            DicNameEntity dicNameEntity = dicNameService.queryById(entity);
        return ResponseUtil.writer("0","success",dicNameEntity);
    }

    //2.新增修改
    @RequestMapping(value = "/save",method = {RequestMethod.GET, RequestMethod.POST})
    public String save(@RequestBody DicNameEntity entity){
        DicNameEntity entity1 = dicNameService.save(entity);
        return ResponseUtil.writer("0","success",entity1);
    }

    //3.删除
    @RequestMapping(value = "/delById",method = {RequestMethod.GET, RequestMethod.POST})
    public String delById(@RequestBody DicNameEntity entity){
        dicNameService.deleteById(entity);
        return ResponseUtil.writer("0","success");
    }
}
