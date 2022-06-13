package com.qq.eduservice.controller;


import com.qq.eduservice.entity.subject.OneSubject;
import com.qq.eduservice.service.EduSubjectService;
import com.qq.commonutils.Result;
import com.qq.eduservice.service.impl.EduSubjectServiceImpl;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/eduservice/subject")
// @CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    // 添加课程分类
    @PostMapping("/addSubject")
    public Result addSubject(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "uploadType") String uploadType,
                             @RequestParam(value = "uploadPath") String uploadPath) {
        //subjectService.addSubject(file);
        System.out.println(uploadPath);
        System.out.println(uploadType);
        try{
            if (file == null) {
                System.out.println("==>  没有上传文件。");
                return Result.error().message("没有上传文件。");
            }
            System.out.println("==>文件名: " + file.getOriginalFilename());
            uploadPath = subjectService.handlerMultipartFile(file,uploadPath);
            //return Result.success("文件上传完成。", newFileName);
            //uploadPath = createExcelConsumer.uploadExcel(file,unid);
            System.out.println("==>文件路径: " + uploadPath);
        }
        catch (Exception e){

        }
        return Result.success();
    }

    // 获取所有的课程分类
    @GetMapping("/getSubjectList")
    public Result getSubjectList() {
        List<OneSubject> lists = subjectService.getAllSubject();
        return Result.success().data("subjects", lists);
    }


    /**
     * @param path 想要下载的文件的路径
     * @param response
     * @功能描述 将输入流中的数据循环写入到响应输出流中，而不是一次性读取到内存
     */
     @PostMapping("/download")
     public Result download(String path,HttpServletResponse response)throws IOException {
         System.out.println(path);
         // 读到流中
         InputStream inputStream = new FileInputStream(path);// 文件的存放路径
         response.reset();
         response.setContentType("application/octet-stream");
         String filename = new File(path).getName();
         System.out.println(filename);
         response.addHeader("Content-Disposition", "attachment;filename=" + filename);
         System.out.println(URLEncoder.encode(filename, "UTF-8"));
         ServletOutputStream outputStream = response.getOutputStream();
         byte[] b = new byte[1024];
         int len;
         // 从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
         while ((len = inputStream.read(b)) > 0) {
             outputStream.write(b, 0, len);
         }
         inputStream.close();
         return Result.success();
     }

//    // 下载文件
//    @GetMapping("/download")
//    public Object downloadFile(String rootPath,String fileName, HttpServletResponse response) {
//        OutputStream os = null;
//        InputStream is= null;
//        //String rootPath = EduSubjectServiceImpl.class.getClassLoader().getResource("").getPath();
//        try {
//            // 取得输出流
//            os = response.getOutputStream();
//            // 清空输出流
//            response.reset();
//            response.setContentType("application/x-download;charset=utf-8");
//            //Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，
//            // 保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
//            //把文件名按UTF-8取出，并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
//            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"),"ISO8859-1"));
//            //读取流
//            File f = new File(rootPath+fileName);
//            is = new FileInputStream(f);
//            if (is == null) {
//                System.out.println("下载附件失败，请检查文件“" + fileName + "”是否存在");
//                return Result.error().message("下载附件失败，请检查文件“" + fileName + "”是否存在");
//            }
//            //复制
//            IOUtils.copy(is, response.getOutputStream());
//            response.getOutputStream().flush();
//        } catch (IOException e) {
//            return Result.error().message("下载附件失败,error:"+e.getMessage());
//        }
//        //文件的关闭放在finally中
//        finally
//        {
//            try {
//                if (is != null) {
//                    is.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (os != null) {
//                    os.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        //其实，这个返回什么都不重要
//        return " ";
//    }
}

