package com.zhaolong.wsn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.Node;
import com.zhaolong.wsn.repository.NodeRepository;
import com.zhaolong.wsn.service.NodeService;

public class NodeServiceImpl implements NodeService{
	
	@Autowired
    private NodeRepository nodeRepository;

	public Long addNode(Node node) {
		// TODO Auto-generated method stub
		return nodeRepository.save(node);
	}

	public List<Node> nodeList() {
		// TODO Auto-generated method stub
		return nodeRepository.findAll();
	}
	
	public Node nodeInfo(Long nodeId) {
		// TODO Auto-generated method stub
		return nodeRepository.get(nodeId);
	}
}
