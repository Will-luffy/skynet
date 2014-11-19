package com.dong.skynet.common;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dongshuwang on 14-11-19.
 */
public class DownloadFile {

    /**
     * 根据URL 和网页类型生成需要保存的网页的文件名，去除URL 中的非文件名字符
     */
    public String getFileNameByUrl(String url, String contentType) {
        //移除http:
        url = url.substring(7);
        //text/html 类型
        if (contentType.indexOf("html") != -1) {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
            return url;
        }
        //如application/pdf 类型
        else {
            return url.replaceAll("[\\?/:*|<>\"]", "_") + "." +
                    contentType.substring(contentType.lastIndexOf("/") + 1);
        }
    }

    /**
     * 保存网页字节数组到本地文件，filePath 为要保存的文件的相对地址
     */
    private void saveToLocal(byte[] data, String filePath) {
        try {
            DataOutputStream out = new DataOutputStream(new
                    FileOutputStream(new File(filePath)));
            for (int i = 0; i < data.length; i++)
                out.write(data[i]);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadFile(String url) {
        String filePath = null;
        // 1.生成HttpClinet 对象并设置参数
        HttpClient httpClient = new HttpClient();
        // 设置HTTP 连接超时5s
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(5000);
        // 2.生成GetMethod 对象并设置参数
        GetMethod getMethod = new GetMethod(url);
        // 设置get 请求超时5s
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        // 设置请求重试处理
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());
        // 3.执行HTTP GET 请求
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            // 判断访问的状态码
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
                filePath = null;
            } else {
                // 4.处理HTTP 响应内容
                byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
                // 根据网页url 生成保存时的文件名
                filePath = "D:/temp/douguo/"
                        + getFileNameByUrl(url, getMethod.getResponseHeader(
                        "Content-Type").getValue());
                saveToLocal(responseBody, filePath);
            }
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
            getMethod.releaseConnection();
        }
        return filePath;
    }
}
