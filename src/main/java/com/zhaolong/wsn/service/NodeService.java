package com.zhaolong.wsn.service;

import java.util.List;

import com.zhaolong.wsn.entity.Node;

public interface NodeService {
	Long addNode(Node node);
	List<Node> nodeList();
}
