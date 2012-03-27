package com.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.entity.FileEntity;

public class FileUtil {
    /**
     * 
     * @param source 源文件
     * @param fileName 目标路径
     */
    public static void copy(InputStream source, String fileName) {
        OutputStream output = null;
        File file=new File(fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            output = new FileOutputStream(fileName);
            byte[] buffer = new byte[2048];
            int length;
            while ((length = source.read(buffer)) > -1) {
                if(length>0){
                    output.write(buffer, 0, length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                source.close();
                output.flush();
                output.close();
            } catch (Exception e) {
            }
        }
    }
}
