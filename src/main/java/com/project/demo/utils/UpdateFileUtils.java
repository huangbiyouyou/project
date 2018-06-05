package com.project.demo.utils;

import com.project.demo.constant.ProjectConstant;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class UpdateFileUtils {

    public static List<String> uploadFile(HttpServletRequest request) throws Exception {
        List<String> list = new ArrayList<>();

        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {

            MultipartHttpServletRequest multipartHttpServletRequest= (MultipartHttpServletRequest) request;
            Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
            while (fileNames.hasNext()) {
                // 取得上传文件
                MultipartFile file = multipartHttpServletRequest.getFile(fileNames.next());
                if (file != null) {
                    // 取得当前上传文件的文件名称
                    String filename = file.getOriginalFilename();
                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (filename.trim() != null) {
                        String fileTyps = filename.substring(filename.lastIndexOf("."));
                        // String tempName="demo"+fileTyps;
                        String tempName = UUID.randomUUID().toString() + fileTyps;
                        // 创建文件夹
                        String folderPath = ProjectConstant.SAVEFILEPATH + File.separator + folderName();
                        File fileFolder = new File(folderPath);
                        if (!fileFolder.exists() && !fileFolder.isDirectory()) {
                            fileFolder.mkdir();
                        }
                        File uploadFile = new File(folderPath + File.separator + tempName);
                        file.transferTo(uploadFile);
                        filename = folderName() + File.separator + tempName;
                        list.add(ProjectConstant.SAVEFILEPATH + "//" + filename);

                    }
                }
            }

        }

        return list;
    }

    /**
     * 得年月日的文件夹名称
     *
     * @return
     */
    public static String getCurrentFilderName()  throws Exception{
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + "" + (now.get(Calendar.MONTH) + 1) + "" + now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 创建文件夹
     *
     * @param filderName
     */
    public static void createFilder(String filderName) throws Exception {
        File file = new File(filderName);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String extFile(String fileName)  throws Exception{
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 当前日期当文件夹名
     *
     * @return
     */
    public static String folderName() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(new Date());
        return str;
    }

}
