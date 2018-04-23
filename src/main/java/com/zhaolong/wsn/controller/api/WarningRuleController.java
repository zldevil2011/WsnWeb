package com.zhaolong.wsn.controller.api;

import com.zhaolong.wsn.entity.Node;
import com.zhaolong.wsn.entity.WarningRule;
import com.zhaolong.wsn.service.NodeService;
import com.zhaolong.wsn.service.WarningRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by zhaolong on 2018/4/22.
 */
@Controller
@RequestMapping(value = "/api/warningRule/*")
public class WarningRuleController {
    /*
        warningRuleList: 获取现有的预警规则的列表
        warningRuleAdd: 新建预警规则
     */
    @Autowired
    private WarningRuleService warningRuleService;
    @Autowired
    private NodeService nodeService;

    @RequestMapping(value = "warningRuleList", method = RequestMethod.GET)
    public @ResponseBody List<WarningRule> warningRuleList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO Auto-generated method stub
        try{
            request.setCharacterEncoding("utf-8");
            List<WarningRule> warningRuleList = warningRuleService.warningRuleList();
            return warningRuleList;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @RequestMapping(value = "warningRuleAdd", method = RequestMethod.POST)
    public void warningRuleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("utf-8");
        try {
            Long nodeId = Long.valueOf(request.getParameter("nodeId"));
            String parameter = request.getParameter("parameter");
            int ruleType = Integer.parseInt(request.getParameter("ruleType"));

            WarningRule w = new WarningRule();
            String nodeName = "";
            if(nodeId == 0){
                // 针对所有站点的设置
                nodeName = "所有站点";
            }else{
                Node node = nodeService.nodeInfo(nodeId);
                nodeName = node.getNodeName();
                Double ruleValue = Double.valueOf(request.getParameter("ruleValue"));
                w.setRuleValue(ruleValue);
            }
            w.setNodeId(nodeId);
            w.setNodeName(nodeName);
            w.setParameter(parameter);
            w.setRuleType(ruleType);
            warningRuleService.addWarningRule(w);
            response.setStatus(200);
        }catch (Exception e){
            System.out.println(e);
            response.setStatus(500);
        }
        PrintWriter pWriter = response.getWriter();
        pWriter.println("success");
    }
}
