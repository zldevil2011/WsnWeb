package com.zhaolong.wsn.controller.api;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhaolong.wsn.entity.Information;
import com.zhaolong.wsn.service.InformationService;

@Controller
@RequestMapping(value = "/api/*") 
public class InformationController {

	/*
	information_list： 获取新闻列表数据
	 */
	@Autowired
	private InformationService informationService;
	
	@RequestMapping(value = "information_list", method = RequestMethod.GET)
	public @ResponseBody List<Information> informationList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("news_list api");
		List<Information> data = new ArrayList<Information>();
		data = informationService.informationList();
		System.out.println(data.size());
		return data;
	}

	@RequestMapping(value = "newsDetails/{news_id}", method = RequestMethod.GET)
	public @ResponseBody Information informatioDetails(HttpServletRequest request, HttpServletResponse response, @PathVariable("news_id") Long news_id) {
		System.out.println("newsDetails api");
		Information data = informationService.getDetails(news_id);
		data.setReadCount(data.getReadCount() + 1);
		informationService.updateInformation(data);
		return data;
	}

	@RequestMapping(value = "informationAdd", method = RequestMethod.POST)
	public void InformationAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		try {
			String title = request.getParameter("title");
			String upload = request.getParameter("upload");
			String content = request.getParameter("content");

			System.out.println(title);
			System.out.println(upload);
			System.out.println(content);
			Information w = new Information();
			w.setTitle(title);
			w.setContent(content);
			if(upload.equals("true")){
				w.setIsDeleted(0);
			}else{
				w.setIsDeleted(1);
			}
			informationService.saveInformation(w);
			response.setStatus(200);
		}catch (Exception e){
			System.out.println(e);
			response.setStatus(500);
		}
		PrintWriter pWriter = response.getWriter();
		pWriter.println("success");
	}

	@RequestMapping(value = "informationDelete", method = RequestMethod.POST)
	public void InformationDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		try {
			String newsId = request.getParameter("news_id");
			System.out.println("newsId" + newsId);
			informationService.deleteInformation(Long.valueOf(newsId));
			response.setStatus(200);
		}catch (Exception e){
			System.out.println(e);
			response.setStatus(500);
		}
		PrintWriter pWriter = response.getWriter();
		pWriter.println("success");
	}
	
//	@RequestMapping(value = "information_spider", method = RequestMethod.GET)
//	public String informationSpider(HttpServletRequest request, HttpServletResponse response){
//		GetInformation gf = new GetInformation();
//		String url = "http://www.pm25.com/news/cat/16";
//		String result = gf.SendGet(url);
//		String imgSrc = gf.RegexString(result, "<a class=\"list_img\" href=\"(.+?)\" title=\"(.+?)\"><img src=\"(.+?)\">", 1);
//
//		String result2 = gf.SendGet(url);
//		String imgSrc2 = gf.RegexString(result2, "<dd>(.+?)</dd>(.*?)</dl>", 2);
//		
//		System.out.println(gf.infoAddress.size());
//		System.out.println(gf.infoImg.size());
//		System.out.println(gf.infoTitle.size());
//		System.out.println(gf.infoDesc.size());
//		
//		for(int i = 0; i < 10; ++i){
//			Information information = new Information();
//			information.setAuthor("www.pm25.com");
//			information.setCreatedTime("2017-11-22");
//			information.setDescription(gf.infoDesc.get(i));
//			information.setHeadImg("http://www.pm25.com"+gf.infoImg.get(i).substring(0, 54));
//			information.setNewsAddress(gf.infoAddress.get(i));
//			information.setTitle(gf.infoTitle.get(i));
//			informationService.saveInformation(information);
//		}
//		return "I am ok" + gf.infoAddress.size();
//	}
}

//class GetInformation {
//
//	public List<String> infoImg = new ArrayList<String>();
//	public List<String> infoTitle = new ArrayList<String>();
//	public List<String> infoDesc = new ArrayList<String>();
//	public List<String> infoAddress = new ArrayList<String>();
//
//	public String SendGet(String url)
//	{
//		// 定义一个字符串用来存储网页内容
//		String result = "";
//		// 定义一个缓冲字符输入流
//		BufferedReader in = null;
//		try
//		{
//			// 将string转成url对象
//			URL realUrl = new URL(url);
//			// 初始化一个链接到那个url的连接
//			URLConnection connection = realUrl.openConnection();
//			// 开始实际的连接
//			connection.connect();
//			// 初始化 BufferedReader输入流来读取URL的响应
//			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			// 用来临时存储抓取到的每一行的数据
//			String line;
//			while ((line = in.readLine()) != null)
//			{
//				// 遍历抓取到的每一行并将其存储到result里面
//				result += line;
//			}
//		} catch (Exception e)
//		{
//			System.out.println("发送GET请求出现异常！" + e);
//			e.printStackTrace();
//		}
//		// 使用finally来关闭输入流
//		finally
//		{
//			try
//			{
//				if (in != null)
//				{
//					in.close();
//				}
//			} catch (Exception e2)
//			{
//				e2.printStackTrace();
//			}
//		}
//		return result;
//	}
//	public String RegexString(String targetStr, String patternStr, int type)
//	{
//		// 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容 // 相当于埋好了陷阱匹配的地方就会掉下去
//		Pattern pattern = Pattern.compile(patternStr);
//		// 定义一个matcher用来做匹配
//
//		Matcher matcher = pattern.matcher(targetStr);
//		List<String> resList = new ArrayList<String>();
//		// 如果找到了
//		while (matcher.find())
//		{
//			// 打印出结果
//			if(type == 1){
////				是图片，连接和标题
//				this.infoAddress.add(matcher.group(1));
//				this.infoTitle.add(matcher.group(2));
//				this.infoImg.add(matcher.group(3));
//			}else{
//				this.infoDesc.add(matcher.group(1));
//			}
////			return matcher.group(1);
//		}
//		return "";
//	}
//}