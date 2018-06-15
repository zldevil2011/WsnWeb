import React, { Component } from 'react';
import { browserHistory } from 'react-router';

import 'weui';
import 'react-weui/build/packages/react-weui.css';
import WeUI from 'react-weui';

const { Page, Preview, PreviewHeader, PreviewFooter, PreviewBody, PreviewItem, PreviewButton, Dialog} = WeUI;

class WarningEvent extends Component {
	constructor(props){
        super(props);
        this.state={
            showAddredd: false,
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
            }
        }catch(e){
        console.log(e);
        }
    }
    hideDialog() {
        this.setState({
            showAddredd: false
        });
    }
    render(){
        return (
        <Page className="preview wariningEvent">
            <Preview>
                <PreviewHeader>
                    <PreviewItem label="事件" value="超过警戒值" />
                </PreviewHeader>
                <PreviewBody>
                    <PreviewItem label="设备" value="1号站点" />
                    <PreviewItem label="参数" value="PM2.5" />
                    <PreviewItem label="时间" value="2018-12-12 12:12:12" />
                    <PreviewItem label="采集值" value="123" />
                </PreviewBody>
                <PreviewFooter>
                    <PreviewButton primary  onClick={ e=> this.setState({ showAddredd: true}) }>查看地址</PreviewButton>
                </PreviewFooter>
            </Preview>
            <br/>
            <Preview>
                <PreviewHeader>
                    <PreviewItem label="事件" value="超过警戒值" />
                </PreviewHeader>
                <PreviewBody>
                    <PreviewItem label="设备" value="2号站点" />
                    <PreviewItem label="参数" value="PM2.5" />
                    <PreviewItem label="时间" value="2018-12-12 12:12:12" />
                    <PreviewItem label="采集值" value="123" />
                </PreviewBody>
                <PreviewFooter>
                    <PreviewButton primary onClick={ e=> this.setState({ showAddredd: true}) }>查看地址</PreviewButton>
                </PreviewFooter>
            </Preview>
            <br/>
            <Preview>
                <PreviewHeader>
                    <PreviewItem label="事件" value="超过警戒值" />
                </PreviewHeader>
                <PreviewBody>
                    <PreviewItem label="设备" value="3号站点" />
                    <PreviewItem label="参数" value="PM2.5" />
                    <PreviewItem label="时间" value="2018-12-12 12:12:12" />
                    <PreviewItem label="采集值" value="123" />
                </PreviewBody>
                <PreviewFooter>
                    <PreviewButton primary onClick={ e=> this.setState({ showAddredd: true}) }>查看地址</PreviewButton>
                </PreviewFooter>
            </Preview>
            <br/>
            <Preview>
                <PreviewHeader>
                    <PreviewItem label="事件" value="超过警戒值" />
                </PreviewHeader>
                <PreviewBody>
                    <PreviewItem label="设备" value="4号站点" />
                    <PreviewItem label="参数" value="PM2.5" />
                    <PreviewItem label="时间" value="2018-12-12 12:12:12" />
                    <PreviewItem label="采集值" value="123" />
                </PreviewBody>
                <PreviewFooter>
                    <PreviewButton primary onClick={ e=> this.setState({ showAddredd: true}) }>查看地址</PreviewButton>
                </PreviewFooter>
            </Preview>
            <Dialog type="android" title='设备地址' buttons={this.state.dialogStyle.buttons} show={this.state.showAddredd}>
                北京西站南广场东
            </Dialog>
        </Page>
        )
    }
}

export default WarningEvent;