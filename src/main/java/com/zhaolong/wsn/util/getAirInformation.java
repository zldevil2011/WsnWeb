package com.zhaolong.wsn.util;

import com.zhaolong.wsn.entity.Information;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhaolong on 2018/5/5.
 */
public class getAirInformation {
    public String RegexString(String targetStr, String patternStr, int type)
    {
        // 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容 // 相当于埋好了陷阱匹配的地方就会掉下去
        Pattern pattern = Pattern.compile(patternStr);
        // 定义一个matcher用来做匹配

        Matcher matcher = pattern.matcher(targetStr);
        List<String> resList = new ArrayList<String>();
        // 如果找到了
        while (matcher.find())
        {
            // 打印出结果
            if(type == 1){
//				是图片，连接和标题

            }else{
            }
//			return matcher.group(1);
        }
        return "";
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Spider s = new Spider();
        GetInformation gf = new GetInformation();
        String url = "http://www.pm25.com/news/cat/16";
        String result = s.SendGet(url);
        String imgSrc = gf.RegexString(result, "<a class=\"list_img\" href=\"(.+?)\" title=\"(.+?)\"><img src=\"(.+?)\">", 1);

    }
}
