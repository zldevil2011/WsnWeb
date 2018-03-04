package com.zhaolong.wsn.controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhaolong.wsn.entity.Data;
import com.zhaolong.wsn.entity.Node;
import com.zhaolong.wsn.service.DataService;
import com.zhaolong.wsn.service.NodeService;
import com.zhaolong.wsn.util.ChineseCharToEn;

class NodeRank extends Node{
	public int rank;
	public double AQI;
	public double So2;
	public double Pm25;
	public double Pm10;
	public String DataDesc;
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public double getAQI() {
		return AQI;
	}
	public void setAQI(double aQI) {
		AQI = aQI;
	}
	public double getSo2() {
		return So2;
	}
	public void setSo2(double so2) {
		So2 = so2;
	}
	public double getPm25() {
		return Pm25;
	}
	public void setPm25(double pm25) {
		Pm25 = pm25;
	}
	public double getPm10() {
		return Pm10;
	}
	public void setPm10(double pm10) {
		Pm10 = pm10;
	}
	public String getDataDesc() {
		return DataDesc;
	}
	public void setDataDesc(String dataDesc) {
		DataDesc = dataDesc;
	}
	
}
class SortByAqi implements Comparator {
    public int compare(Object o1, Object o2) {
    	NodeRank s1 = (NodeRank) o1;
    	NodeRank s2 = (NodeRank) o2;
    	if (s1.getAQI() > s2.getAQI()){
    		return 1;
        }else if(s1.getAQI() < s2.getAQI()){
        	return -1;
        }else{
        	return 0;
        }
    }
   }

@Controller
@RequestMapping(value = "/api/*") 
public class NodeController {
	/*
	nodeList: 获取所有的节点数据
	index_node_rank： 返回所有采集站点的最新采集数据的质量排名
	node_add： 通过请求API添加节点
	node_location_list： 根据拼音首字母对安装的节点进行列表返回，返回26个列表
	node_info/{node_id}： 获取某个节点的信息
	*/
	@Autowired
	private NodeService nodeService;
	
	@Autowired
	private DataService dataService;
	
	@RequestMapping(value = "node_list", method = RequestMethod.GET)
	public @ResponseBody List<Node> nodeList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Node> nodeList = nodeService.nodeList();
		return nodeList;
	}
	
	@RequestMapping(value = "index_node_rank", method = RequestMethod.GET)
	public @ResponseBody List<NodeRank> indexNodeRank(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<NodeRank> rankList = new ArrayList<NodeRank>();
		List<Node> nodeList = nodeService.nodeList();
		List<Data> dataList = new ArrayList<Data>();
		System.out.println("Pre_datalist = " + dataList);
//		dataList = dataService.dataList();
		
		Date date = new Date();
		java.sql.Date today = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
		Calendar calendar = new GregorianCalendar();  
		calendar.setTime(today);  
		calendar.add(calendar.DATE, 1);  
		java.sql.Date tomorrow = new java.sql.Date(calendar.getTime().getTime());
		dataList = dataService.dataList(today, tomorrow);
		
		System.out.println("After_datalist = " + dataList);
		int len = nodeList.size();
		int dataLen = dataList.size();
		for(int i = 0; i < len; ++i){
			int flag = -1;
			for(int j = 0; j < dataLen; ++j){
				if(dataList.get(j).getNodeId() == nodeList.get(i).getId()){
					Node node = nodeList.get(i);
					Data data = dataList.get(j);
					NodeRank nodeRank = new NodeRank();
					nodeRank.setAddress(node.getAddress());
					nodeRank.setAQI((new Random().nextInt())%100 + 100);
					nodeRank.setCity(node.getCity());
					nodeRank.setCreated(node.getCreated());
					nodeRank.setId(node.getId());
					nodeRank.setInstallTime(node.getInstallTime());
					nodeRank.setLatitude(node.getLatitude());
					nodeRank.setLongitude(node.getLongitude());
					nodeRank.setNodeName(node.getNodeName());
					nodeRank.setOnline(node.getOnline());
					nodeRank.setProvince(node.getProvince());
					nodeRank.setSo2(data.getSo2() == null ? 0:data.getSo2());
					nodeRank.setPm25(data.getPm25() == null ? 0:data.getPm25());
					nodeRank.setPm10(data.getPm10() == null ? 0:data.getPm10());
					rankList.add(nodeRank);
					break;
				}
			}
			if(flag == -1){
				Node node = nodeList.get(i);
				NodeRank nodeRank = new NodeRank();
				nodeRank.setAddress(node.getAddress());
				nodeRank.setAQI((new Random().nextInt())%100 + 100);
				nodeRank.setCity(node.getCity());
				nodeRank.setCreated(node.getCreated());
				nodeRank.setId(node.getId());
				nodeRank.setInstallTime(node.getInstallTime());
				nodeRank.setLatitude(node.getLatitude());
				nodeRank.setLongitude(node.getLongitude());
				nodeRank.setNodeName(node.getNodeName());
				nodeRank.setOnline(node.getOnline());
				nodeRank.setProvince(node.getProvince());
				rankList.add(nodeRank);
			}
		}
		Collections.sort(rankList, new SortByAqi());
		int rankLen = rankList.size();
		for (int i = 0; i < rankLen; ++i) {
			rankList.get(i).setRank(i+1);
	    }
		return rankList;
	}
	
	@RequestMapping(value = "node_add", method = RequestMethod.POST)
	public void dataSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		System.out.println(request.getParameterNames());
		String address = request.getParameter("address");
		String installTime = request.getParameter("installTime");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String nodeName = request.getParameter("nodeName");
		String city = request.getParameter("city");
		String province = request.getParameter("province");
		System.out.println(city);
		Node n = new Node();
		n.setAddress(address);
		n.setInstallTime(installTime);
		n.setLatitude(latitude);
		n.setLongitude(longitude);
		n.setNodeName(nodeName);
		n.setCity(city);
		n.setProvince(province);
		nodeService.addNode(n);
		response.setStatus(200);
		PrintWriter pWriter = response.getWriter();
		pWriter.println("success" + n);
	}
	
	@RequestMapping(value = "node_location_list", method = RequestMethod.GET)
	public @ResponseBody List<List<Node>> nodeLocationList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Node> nodeList = nodeService.nodeList();
		List<List<Node>> resList = new ArrayList<List<Node>>();
		int len = nodeList.size();
		for(int j = 0; j < 26; ++j){
			List<Node> tList = new ArrayList<Node>();
			String tmp =String.valueOf((char)(65+j));
			for(int i = 0; i < len; ++i){
				String firstName = nodeList.get(i).getNodeName().substring(0, 1);
				String firstNameChar = new ChineseCharToEn().getFirstLetter(firstName);	
				if(firstNameChar.equals(tmp)){
					tList.add(nodeList.get(i));
				}
			}
			resList.add(tList);
		}
		return resList;
	}
	@RequestMapping(value = "node_info/{node_id}", method = RequestMethod.GET)
	public @ResponseBody Node nodeInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable("node_id") Long node_id) {
		// TODO Auto-generated method stub
		return nodeService.nodeInfo(node_id);
	}
	
}
