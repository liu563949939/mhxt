package nist.module.mhxt.controller;

import nist.module.mhxt.entity.KnowLedgeEntity;
import nist.module.mhxt.service.KnowLedgeService;
import nist.module.mhxt.util.ResponseUtil;
import org.apache.catalina.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/knowledge")
public class KnowLedgeController {
    @Autowired
    private KnowLedgeService knowLedgeService;


    //1-1.查询(带分页)
    @RequestMapping(value = "/query",method = {RequestMethod.GET, RequestMethod.POST})
    public String query(@RequestBody KnowLedgeEntity entity){
        Map<String,Object> result = knowLedgeService.query(entity);
        return ResponseUtil.writer("0","success",result.get("dataList"),(Long) result.get("count"));
    }

    //2.保存
    @RequestMapping(value = "/save",method = {RequestMethod.GET,RequestMethod.POST})
    public String save(@RequestBody KnowLedgeEntity entity){
        return ResponseUtil.writer("0","success",knowLedgeService.save(entity));
    }

    //3.删除
    @RequestMapping(value = "/del", method = {RequestMethod.GET, RequestMethod.POST})
    public String delete(@RequestBody KnowLedgeEntity entity){
        knowLedgeService.delete(entity);
        return ResponseUtil.writer("0","success");
    }

}
