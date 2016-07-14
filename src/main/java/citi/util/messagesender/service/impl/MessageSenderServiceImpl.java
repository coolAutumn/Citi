package citi.util.messagesender.service.impl;

import citi.util.messagesender.service.MessageSenderService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by coolAutumn on 7/14/16.
 */
public class MessageSenderServiceImpl {

    //短信内容(最后发送时要转化成utf8urlencode编码)
    String content_part1 = "[skynet]您的注册验证码为";
    String content_part2 = ",有效时间为1小时,请勿告诉他人.";

    String urlpath   = "http://apis.baidu.com/kingtto_media/106sms/106sms";        //发送短信的接口
    String apikey    = "994adb895057b841fb6c804d8ea3d4c3";                             //百度API集市的APIKEY

    // 转码
    private String encode(String input) throws Exception {
        return URLEncoder.encode(input, "utf8");
    }

    //生成随机6位验证码
    private String getVerificationCode(){
        String vc = "";
        for(int i=0;i<6;i++){
            vc += (int) (Math.random() * 10);
        }
        return vc;
    }

    public boolean sendMessage(String phoneNumber) {
        try {
            String random = getVerificationCode();

            // 参数拼装
            String params="mobile=" + phoneNumber.trim() +
                    "&content=" + encode(content_part1+random+content_part2).trim() +
                    "&tag=2";

            //由于idea下无法输入中文的中括号,所以需要转化一下
            params = params.replace("%5B","%e3%80%90");
            params = params.replace("%5D","%e3%80%91");
            // 打开连接
            URL url = new URL(urlpath + "?" +params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("apikey",apikey);
            conn.setUseCaches(false);
            conn.connect();

            // 读取响应
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf8"));
            String line ;
            StringBuffer sb = new StringBuffer("");
            while ( (line = br.readLine()) != null){
                sb.append(line);
            }
            br.close();

            // 关闭连接
            conn.disconnect();

            System.out.println(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}