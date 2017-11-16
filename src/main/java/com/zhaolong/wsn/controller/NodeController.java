package com.zhaolong.wsn.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhaolong.wsn.entity.Node;
import com.zhaolong.wsn.service.NodeService;

@Controller
public class NodeController {
	@Autowired
	private NodeService nodeService;
	
	@RequestMapping(value = "node_add", method = RequestMethod.GET)
	public void dataSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Node n = new Node();
		n.setInstallTime("2017-11-16 12:00:00");
		n.setNodeName("1号测试节点");
		n.setOnline("0");
		n.setAddress("北师大南门");
		n.setLatitude("133.34");
		n.setLongitude("34.78");
		nodeService.addNode(n);
		response.setStatus(200);
		PrintWriter pWriter = response.getWriter();
		pWriter.println("success");
	}
}
