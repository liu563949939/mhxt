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
}
