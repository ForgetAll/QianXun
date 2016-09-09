package com.heapot.qianxun.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 *Created by 15859 on 2016/9/7.
 * @summary Http相关
 */
public class HttpUtil {
    //换行
    private static final String CRLF = "\r\n";
    //1.第一步，分析请求
    /*
    Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,;q=0.8
    Accept-Encoding:gzip, deflate, lzma
    Accept-Language:zh-CN,zh;q=0.8
    Cache-Control:max-age=0
    Connection:keep-alive
    Content-Length:22922
    Content-Type:multipart/form-data; boundary=----WebKitFormBoundaryoiZa3QhddTPK1rkB
    Cookie:JSESSIONID=B2E03C6B0069D26C2B2C642B340013C2; __guid=111872281.3211089184477402600.1461900287263.8367
    Host:localhost:8080
    Origin:http://localhost:8080
    Referer:http://localhost:8080/ClassRoom/uploadFile.jsp
    Upgrade-Insecure-Requests:1
    User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36 OPR/37.0.2178.32

    Request Payload
    ------WebKitFormBoundaryoiZa3QhddTPK1rkB
    Content-Disposition: form-data; name="file"; filename="a2[1].jpg"
    Content-Type: image/jpeg


    ------WebKitFormBoundaryoiZa3QhddTPK1rkB--
     */

    /**
     * 发送文件到服务器
     *
     * @param path       目标路径
     * @param uploadFile 源文件
     */
    public static String postFile(String path, File uploadFile) {
        DataOutputStream dataOutputStream = null;
        HttpURLConnection conn = null;
        //创建随机边界字符串
        byte[] randomBytes = new byte[16];
        new Random().nextBytes(randomBytes);
        String boundary = "WebKitFormBoundaryoiZa3QhddTPK1rkB";
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //设置请求头
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            //允许输出和输入
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取输出流
            OutputStream out = conn.getOutputStream();
            dataOutputStream = new DataOutputStream(out);
            //根据文件，创建输入流
            FileInputStream in = new FileInputStream(uploadFile);
            //先写入正文头部信息
            dataOutputStream.writeBytes("--" + boundary + CRLF);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + "file"
                    + "\"; filename=\"" + uploadFile.getName() + "\"" + CRLF);
            // 明确指定ContentType的类型，此处表示通用文件类型
            dataOutputStream.writeBytes("Content-Type: */*" + CRLF);
            dataOutputStream.writeBytes(CRLF);
            //写入文件
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                dataOutputStream.write(buf, 0, len);
            }
            //写入尾部信息
            dataOutputStream.writeBytes(CRLF + "--" + boundary + "--" + CRLF);
            in.close();
            dataOutputStream.flush();
            //获取响应信息
            if (conn.getResponseCode() == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                char[] chars = new char[conn.getContentLength()];
                StringBuilder stringBuilder = new StringBuilder();
                int length;
                while ((length = bufferedReader.read(chars)) != -1) {
                    stringBuilder.append(chars, 0, length);
                }
                return stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 发送
     *
     * @param path 发送到的路径
     * @param data 发送的数据
     */
    public static void postString(String path, String data) {
        String result = "";
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            //设置允许输入和输出
            connection.setDoInput(true);
            //允许向服务器发送数据
            connection.setDoOutput(true);
            //获取输出流
            OutputStream outputStream = connection.getOutputStream();
            //写入数据
            outputStream.write(data.getBytes("UTF-8"));
            outputStream.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                // 定义BufferedReader输入流来读取URL的响应
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                inputStream.close();
                connection.disconnect();
                System.out.println("result:"+result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
