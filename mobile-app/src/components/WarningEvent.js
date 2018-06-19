import React, { Component } from 'react';
import { browserHistory } from 'react-router';
import IP from './utils'
import axios from 'axios';

import 'weui';
import 'react-weui/build/packages/react-weui.css';
import WeUI from 'react-weui';

const { Page, Preview, PreviewHeader, PreviewFooter, PreviewBody, PreviewItem, PreviewButton, Dialog} = WeUI;

class WarningEvent extends Component {
	constructor(props){
        super(props);
        this.state={
            showAddress: false,
            addressContent: '',
            warningEventList: [],
            dialogStyle: {
                buttons: [
                    {
                        label: 'Ok',
                        onClick: this.hideDialog.bind(this)
                    }
                ]
            },
        }
    }
    getDateStr(AddDayCount) { 
        var dd = new Date(); 
        dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
        var y = dd.getFullYear(); 
        var m = dd.getMonth()+1;//获取当前月份的日期
        var d = dd.getDate(); 
        return y+"-"+m+"-"+d; 
    }
    componentDidMount(){
        try{
            let userLogin = localStorage.getItem("userLogin");
            console.log(userLogin);
            if(userLogin === null){
                // 未登陆状态，跳转到登陆页面
                let a1=document.createElement('a');
                a1.setAttribute('href','/');
                a1.click();
            }else{
                // 已经登陆
                console.log(localStorage.getItem("userLogin"));
                let userId = localStorage.getItem("userLogin");
                // 获取当前用户管理节点下的所有预警事件
                // 首先获取该用户的管理节点的ID
                axios.get(`http://${IP}/WsnWeb/api/getPersonNodes/${userId}`)
                    .then(res => {
                        let userNodesInfo = res.data;
                        console.log(userNodesInfo);
                        let nodesTmp = [];
                        for(let i = 0; i < userNodesInfo.length; ++i){
                            let obj = {
                                nodeId: userNodesInfo[i].id,
                                nodeName: userNodesInfo[i].nodeName,
                                address: userNodesInfo[i].address
                            }
                            nodesTmp.push(obj);
                        }
                        // 获取完节点之后根据节点ID获取当天的预警事件
                        console.log(nodesTmp);
                        let pStart = this.getDateStr(0);
                        let pEnd = this.getDateStr(1);
                        for(let i = 0; i < nodesTmp.length; ++i){
                            let data = new FormData();
                            data.append('nodeId', nodesTmp[i].nodeId);
                            data.append('startTime', pStart);
                            data.append('endTime', pEnd);
                            axios.post(`http://${IP}/WsnWeb/api/node_warning_list/`, data)
                                .then(res=>{
                                    console.log(res);
                                    console.log(res.data);
                                    let preList = this.state.warningEventList;
                                    preList = preList.concat(res.data);
                                    this.setState({
                                        warningEventList: preList
                                    })
                                }).catch(res=>{
                                    console.log(res);
                                })
                        }
                    });
            }
        }catch(e){
        console.log(e);
        }
    }
    handleShowAddresse (v) {
        console.log(v);
        this.setState({
            showAddress: true,
            addressContent: v
        });
    }
    hideDialog() {
        this.setState({
            showAddress: false
        });
    }
    render(){
        let { warningEventList } = this.state;
        warningEventList = warningEventList.slice(0,20);
        console.log(warningEventList);
        return (
        <Page className="preview wariningEvent">
            { warningEventList.map((v) => (
                <Preview>
                    <PreviewHeader>
                        <PreviewItem label="事件" value={v.dataType} />
                    </PreviewHeader>
                    <PreviewBody>
                        <PreviewItem label="设备" value={v.nodeName} />
                        <PreviewItem label="参数" value={v.parameter} />
                        <PreviewItem label="时间" value={v.warningTime} />
                        <PreviewItem label="内容" value={v.content} />
                    </PreviewBody>
                    <PreviewFooter>
                        <PreviewButton primary  onClick={ this.handleShowAddresse.bind(this, v.nodeAddress)}>查看地址</PreviewButton>
                    </PreviewFooter>
                </Preview>
            ))
            }
            
            <Dialog type="android" title='设备地址' buttons={this.state.dialogStyle.buttons} show={this.state.showAddress}>
                {this.state.addressContent}
            </Dialog>
        </Page>
        )
    }
}

export default WarningEvent;