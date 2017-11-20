package com.zhaolong.wsn.controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLService;
import com.zhaolong.wsn.entity.Data;
import com.zhaolong.wsn.entity.Node;
import com.zhaolong.wsn.service.DataService;
import com.zhaolong.wsn.service.NodeService;
import com.zhaolong.wsn.util.ChineseCharToEn;

import sun.java2d.cmm.kcms.KcmsServiceProvider;

class NodeRank extends Node{
	public int rank;
	public double AQI;
	public double So2;
	public double Pm25;
	public double Pm10;
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
	
	@RequestMapping(value = "node_rank", method = RequestMethod.GET)
	public @ResponseBody List<NodeRank> nodeRank(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<NodeRank> rankList = new ArrayList<NodeRank>();
		List<Node> nodeList = nodeService.nodeList();
		List<Data> dataList = new ArrayList<Data>();
		System.out.println("Pre_datalist = " + dataList);
		dataList = dataService.dataList();
		System.out.println("After_datalist = " + dataList);
		int len = nodeList.size();
		int dataLen = dataList.size();
		for(int i = 0; i < len; ++i){
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
					nodeRank.setSo2(data.getSo2());
					nodeRank.setPm25(data.getPm25());
					nodeRank.setPm10(data.getPm10());
					rankList.add(nodeRank);
					break;
				}
			}
		}
		Collections.sort(rankList, new SortByAqi());
		int rankLen = rankList.size();
		for (int i = 0; i < rankLen; ++i) {
			rankList.get(i).setRank(i+1);
	    }
		return rankList;
	}
	
	@RequestMapping(value = "node_add", method = RequestMethod.GET)
	public void dataSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Node n = new Node();
		n.setInstallTime("2017-11-20 12:00:00");
		n.setOnline("0");
		n.setAddress("安徽池州");
		n.setNodeName("老干部局");
		n.setLongitude("117.491446");
		n.setLatitude("30.649073");
		nodeService.addNode(n);
		response.setStatus(200);
		PrintWriter pWriter = response.getWriter();
		pWriter.println("success");
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
}
