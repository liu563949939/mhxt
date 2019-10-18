package nist.module.mhxt.controller;

import nist.module.mhxt.entity.CustomerEntity;
import nist.module.mhxt.service.CustomerService;
import nist.module.mhxt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.event.ObjectChangeListener;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/customer")
@ResponseBody
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //1-1.分页查询
    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    public String query(@RequestBody CustomerEntity entity){
        Map<String,Object> result = customerService.query(entity);
        return ResponseUtil.writer("0","success",result.get("dataList"),(Long) result.get("count"));
    }

    //2.保存
    @RequestMapping(value = "/save",method = {RequestMethod.GET, RequestMethod.POST})
    public String save(@RequestBody CustomerEntity entity){
        CustomerEntity customerEntity = customerService.save(entity);
        return ResponseUtil.writer("0","success",customerEntity);
    }

    //3.删除
    @RequestMapping(value = "/del",method = {RequestMethod.GET, RequestMethod.POST})
    public String del(@RequestBody CustomerEntity entity){
        customerService.del(entity);
        return ResponseUtil.writer("0","success");
    }

    //4.判断是否已经录入过(根据小区名称+房号判断)
    @RequestMapping(value = "/isExist",method = {RequestMethod.GET,RequestMethod.POST})
    public String isExist(@RequestBody CustomerEntity entity){
        Boolean i = customerService.isExist(entity);
        Map<String,Object> obj = new HashMap<String,Object>();
        if(i){
            obj.put("isExist",true);
        }else{
            obj.put("isExist",false);
        }
        return ResponseUtil.writer("0","success",obj);
    }
}
