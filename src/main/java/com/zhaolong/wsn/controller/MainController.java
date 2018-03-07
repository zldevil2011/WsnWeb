package com.zhaolong.wsn.controller;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
public class MainController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String StartPage() {
		System.out.println("index template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "index";
	}

	@RequestMapping("/helloWorld")
	public String helloWorld(Model model) {
		String word0 = "Hello ";
		String word1 = "World!";
		//将数据添加到视图数据容器中
		model.addAttribute("word0",word0);
		model.addAttribute("word1",word1);
		return "Hello";
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		System.out.println("index template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "index";
	}

	@RequestMapping(value = "indexList", method = RequestMethod.GET)
	public String indexList() {
		System.out.println("index template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "indexList";
	}

	@RequestMapping(value = "realDataList", method = RequestMethod.GET)
	public String realDataList() {
		System.out.println("realDataList template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "realDataList";
	}

	@RequestMapping(value = "aqiInfo", method = RequestMethod.GET)
	public String aqiInfo() {
		System.out.println("aqiInfo template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "aqiInfo";
	}

	@RequestMapping(value = "dataWarning", method = RequestMethod.GET)
	public String dataWarning() {
		System.out.println("dataWarning template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "dataWarning";
	}

	@RequestMapping(value = "weather", method = RequestMethod.GET)
	public String weather() {
		System.out.println("weather template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "weather";
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register() {
		System.out.println("user register mapping!");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "register";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		System.out.println("user login mapping!");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "login";
	}

	@RequestMapping(value = "node_list", method = RequestMethod.GET)
	public String nodeList() {
		System.out.println("node_list template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "nodeList";
	}

	// 节点的详细数据
	@RequestMapping(value = "node_data/{node_id}", method = RequestMethod.GET)
	public String nodeData(@PathVariable("node_id") int node_id, ModelMap map) {
		map.put("node_id", node_id);
		return "nodeData";
	}

	// 节点的排名数据
	@RequestMapping(value = "node_rank", method = RequestMethod.GET)
	public String nodeRank() {
		return "nodeRank";
	}

	// 新闻列表数据
	@RequestMapping(value = "news_list", method = RequestMethod.GET)
	public String newsList() {
		return "newsList";
	}

	// 新增节点页面
	@RequestMapping(value = "node_add", method = RequestMethod.GET)
	public String nodeAdd() {
		return "nodeAdd";
	}
	// 数据渲染页面
	@RequestMapping(value = "airQuality", method = RequestMethod.GET)
	public String airQuality() {
		return "airQuality";
	}
	// 数据下载页面
	@RequestMapping(value = "dataExport", method = RequestMethod.GET)
	public String dataExport() {
		return "dataExport";
	}

	// 导出excel
	@RequestMapping(value = "excelExport", method = RequestMethod.GET)
	public String excelExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String columnNames[]={"ID","项目名","销售人","负责人","所用技术","备注"};//列名
		//生成一个Excel文件
		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet("sheetName");
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		for(int i=0;i<columnNames.length;i++){
			sheet.setColumnWidth((short) i, (short) (35.7 * 150));
		}
		// 创建第一行
		Row row = sheet.createRow((short) 0);
		//设置列名
		for(int i=0;i<columnNames.length;i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(columnNames[i] + "天天");
		}

		//同理可以设置数据行
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=test.xls");
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}
}
