package com.hhxy.shops.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhxy.shops.setting.SystemSetting;
import com.hhxy.shops.utils.PwdUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * 文件上传接口
 */
@Controller
public class FileUploadController {
    @ResponseBody
    @RequestMapping(value = "/upload")
    public String fileUpload(HttpSession session, @RequestParam(value = "file") MultipartFile multipartFile) {
        JSONObject json = new JSONObject();
        try {
            String fileName = save(multipartFile, session);
            session.setAttribute("filePath", SystemSetting.UPLOAD_PATH + fileName);
            json.put("error", 0);
            json.put("url", SystemSetting.UPLOAD_PATH + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", 1);
            json.put("message", e.getMessage());
        }
        return json.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/uploads")
    public String kindEditorUpload(HttpSession session, @RequestParam(value = "imgFile") MultipartFile multipartFile) {
        return fileUpload(session, multipartFile);
    }

    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(HttpSession session, @RequestParam(value = "fileName") String fileName) throws IOException {
        InputStream in = new FileInputStream(session.getServletContext().getRealPath(fileName));
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + fileName.split("/")[fileName.split("/").length - 1]);
        headers.add("Content-Type", "multipart/form-data");
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(bytes, headers, statusCode);
        return entity;
    }

    private String save(MultipartFile multipartFile, HttpSession session) throws Exception {
        String path = session.getServletContext().getRealPath(SystemSetting.UPLOAD_PATH);
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        String suffix = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String fileName = PwdUtils.getUUID();
        FileOutputStream fos = new FileOutputStream(path + fileName + "." + suffix);
        fos.write(multipartFile.getBytes());
        return fileName + "." + suffix;
    }
}
