package com.zjx.Controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet(urlPatterns = "/fileuploadservlet")
public class FileUpLoad extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("true","succeed");
        long startTime1 = System.currentTimeMillis(); //获取开始时间
        try {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            servletFileUpload.setHeaderEncoding("UTF-8");
            List<FileItem> list = servletFileUpload.parseRequest(req);
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {
                    String name = fileItem.getName();
                    String context = fileItem.getString();
                } else {
                    long startTime2 = System.currentTimeMillis(); //获取开始时间
                    InputStream inputStream = fileItem.getInputStream();
                    String path = req.getServletContext().getRealPath("file/" + fileItem.getName());
                    System.out.println("文件大小："+fileItem.getSize()+"byte;  文件名："+fileItem.getName()+"   "+fileItem.getFieldName());
                    OutputStream outputStream = new FileOutputStream(path);
                    int shuju = 0;
                    byte flush[]  = new byte[1024];
//                    new Thread().sleep(10000);
                    long startTime3 = System.currentTimeMillis(); //获取开始时间
                    System.out.println("程序运行时间3-2：" + (startTime3 - startTime2) + "ms"); //输出程序运行时间
                    while(0<=(shuju=inputStream.read(flush))){
                        outputStream.write(flush,0,shuju);
                    }
                    long endTime2 = System.currentTimeMillis(); //获取开始时间
                    System.out.println("程序运行时间3：" + (endTime2 - startTime3) + "ms"); //输出程序运行时间
                    System.out.println("程序运行时间2：" + (endTime2 - startTime2) + "ms"); //输出程序运行时间
                    outputStream.close();
                    inputStream.close();
                }
            }
            long endTime1 = System.currentTimeMillis(); //获取结束时间
            System.out.println("程序运行时间1：" + (endTime1 - startTime1) + "ms"); //输出程序运行时间
            resp.getWriter().write(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
