package nist.module.mhxt.controller;

import nist.module.mhxt.entity.DicItemEntity;
import nist.module.mhxt.service.DicItemService;
import nist.module.mhxt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dicItem")
@ResponseBody
public class DicItemController {
    @Autowired
    private DicItemService dicItemService;

    //1-1.分页查询
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public  String query(@RequestBody DicItemEntity entity){
        Map<String,Object> result = dicItemService.getDataList(entity);
        return ResponseUtil.writer("0","success",result.get("dataList"),(Long) result.get("count"));
    }

    //1-2.根据jlbh查询
    @RequestMapping(value = "/queryById",method = {RequestMethod.GET, RequestMethod.POST})
    public String queryById(@RequestBody DicItemEntity entity){
        return ResponseUtil.writer("0","success",dicItemService.queryById(entity));
    }

    //1-2.根据name查询
    @RequestMapping(value = "/queryByName", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryByName(@RequestBody DicItemEntity entity){
        List<DicItemEntity> dataList = dicItemService.queryByName(entity);
        return ResponseUtil.writer("0","success",dataList);
    }

    //2.save
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    public  String save(@RequestBody DicItemEntity entity){
        DicItemEntity entity1 = dicItemService.save(entity);
        return ResponseUtil.writer("0","success",entity1);
    }

    //3.批量删除
    @RequestMapping(value = "/del", method = {RequestMethod.GET, RequestMethod.POST})
    public String del(@RequestBody DicItemEntity entity){
        dicItemService.del(entity);
        return ResponseUtil.writer("0","success");
    }

}
