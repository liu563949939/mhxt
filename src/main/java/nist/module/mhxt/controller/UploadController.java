package nist.module.mhxt.controller;

import nist.module.mhxt.entity.UploadEntity;
import nist.module.mhxt.service.UploadService;
import nist.module.mhxt.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@ResponseBody
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/saveFile", method = {RequestMethod.GET, RequestMethod.POST})
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("");
        Map<String, Object> obj = new HashMap<String, Object>();
        //1.文件操作
        String fileName = file.getOriginalFilename();//获取文件名(带后缀)
        if (fileName != null && !"".equals(fileName)) {
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            String fileNameNew = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;//新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = "upload/image/" + sdf.format(new Date());
            //获取文件夹路径
            File file1 = new File(path + "/" + fileAdd);
            //如果文件夹不存在则创建
            if (!file1.exists() && !file1.isDirectory()) {
                file1.mkdirs();
            }

            //2.接收文件流
            String sPath = file1.toString() + "\\" + fileNameNew;
            InputStream inputStream = (InputStream) file.getInputStream();
            FileOutputStream output = new FileOutputStream(sPath);

            //3.文件复制
            FileCopyUtils.copy(inputStream, output);
            obj.put("src",fileAdd + "/" + fileNameNew);
            obj.put("name", fileName);
        }
        //4.结果返回
        return ResponseUtil.writer("0","success",obj);
    }

    //2-1.查询(带分页)
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public String query(@RequestBody UploadEntity entity){
        Map<String, Object> sFhz = uploadService.query(entity);
        return ResponseUtil.writer("0","success",sFhz.get("dataList"),(Long)sFhz.get("count"));
    }

    //3.保存
    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    public String save(@RequestBody UploadEntity entity){
        return ResponseUtil.writer("0","success",uploadService.save(entity));
    }

    //4.删除
    @RequestMapping(value = "/del", method = {RequestMethod.GET, RequestMethod.POST})
    public String del(@RequestBody UploadEntity entity){
        uploadService.del(entity);
        return ResponseUtil.writer("0","success");
    }
}
