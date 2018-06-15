import React, { Component } from 'react';
import axios from 'axios'
//import using commonJS Module *Require Plugins
//import { Button } from 'react-weui'

//import Using ES6 syntax
import 'weui';
import 'react-weui/build/packages/react-weui.css';
import WeUI from 'react-weui';


const { Page, CellsTitle, Panel, PanelHeader, PanelBody, MediaBox, MediaBoxHeader, MediaBoxBody,MediaBoxTitle,MediaBoxDescription,
    PanelFooter, CellMore, Form, FormCell, CellHeader, Label, CellBody,
    Input, Select, ButtonArea, Button, Toptips
} = WeUI;

class PersonalInfo extends Component {
	constructor(props){
        super(props);

        this.state = {
            showToptips: false
        }
    }
    componentDidMount() {
        axios.get(`https://www.baidu.com/`)
          .then(res => {
            console.log(res);
        });
    }
    render(){
        const appMsgIcon = <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHgAAAB4CAMAAAAOusbgAAAAeFBMVEUAwAD///+U5ZTc9twOww7G8MYwzDCH4YcfyR9x23Hw+/DY9dhm2WZG0kbT9NP0/PTL8sux7LFe115T1VM+zz7i+OIXxhes6qxr2mvA8MCe6J6M4oz6/frr+us5zjn2/fa67rqB4IF13XWn6ad83nxa1loqyirn+eccHxx4AAAC/klEQVRo3u2W2ZKiQBBF8wpCNSCyLwri7v//4bRIFVXoTBBB+DAReV5sG6lTXDITiGEYhmEYhmEYhmEYhmEY5v9i5fsZGRx9PyGDne8f6K9cfd+mKXe1yNG/0CcqYE86AkBMBh66f20deBc7wA/1WFiTwvSEpBMA2JJOBsSLxe/4QEEaJRrASP8EVF8Q74GbmevKg0saa0B8QbwBdjRyADYxIhqxAZ++IKYtciPXLQVG+imw+oo4Bu56rjEJ4GYsvPmKOAB+xlz7L5aevqUXuePWVhvWJ4eWiwUQ67mK51qPj4dFDMlRLBZTqF3SDvmr4BwtkECu5gHWPkmDfQh02WLxXuvbvC8ku8F57GsI5e0CmUwLz1kq3kD17R1In5816rGvQ5VMk5FEtIiWislTffuDpl/k/PzscdQsv8r9qWq4LRWX6tQYtTxvI3XyrwdyQxChXioOngH3dLgOFjk0all56XRi/wDFQrGQU3Os5t0wJu1GNtNKHdPqYaGYQuRDfbfDf26AGLYSyGS3ZAK4S8XuoAlxGSdYMKwqZKM9XJMtyqXi7HX/CiAZS6d8bSVUz5J36mEMFDTlAFQzxOT1dzLRljjB6+++ejFqka+mXIe6F59mw22OuOw1F4T6lg/9VjL1rLDoI9Xzl1MSYDNHnPQnt3D1EE7PrXjye/3pVpr1Z45hMUdcACc5NVQI0bOdS1WA0wuz73e7/5TNqBPhQXPEFGJNV2zNqWI7QKBd2Gn6AiBko02zuAOXeWIXjV0jNqdKegaE/kJQ6Bfs4aju04lMLkA2T5wBSYPKDGF3RKhFYEa6A1L1LG2yacmsaZ6YPOSAMKNsO+N5dNTfkc5Aqe26uxHpx7ZirvgCwJpWq/lmX1hA7LyabQ34tt5RiJKXSwQ+0KU0V5xg+hZrd4Bn1n4EID+WkQdgLfRNtvil9SPfwy+WQ7PFBWQz6dGWZBLkeJFXZGCfLUjCgGgqXo5TuSu3cugdcTv/HjqnBTEMwzAMwzAMwzAMwzAMw/zf/AFbXiOA6frlMAAAAABJRU5ErkJggg==" />;
        return (
        <Page className="personalInfo">
            <CellsTitle>个人信息</CellsTitle>
            <Panel>
                <PanelBody>
                    <MediaBox type="appmsg" href="javascript:void(0);">
                        <MediaBoxHeader>{appMsgIcon}</MediaBoxHeader>
                        <MediaBoxBody>
                            <MediaBoxTitle>赵龙</MediaBoxTitle>
                            <MediaBoxDescription>
                                18810553591
                            </MediaBoxDescription>
                        </MediaBoxBody>
                    </MediaBox>
                </PanelBody>
            </Panel>
            <Form>
                <FormCell>
                    <CellHeader>
                        <Label>用户名</Label>
                    </CellHeader>
                    <CellBody>
                        <p>管理员</p>
                    </CellBody>
                </FormCell>
                <FormCell>
                    <CellHeader>
                        <Label>电话</Label>
                    </CellHeader>
                    <CellBody>
                        <Input type="tel" placeholder=""/>
                    </CellBody>
                </FormCell>
                <FormCell>
                    <CellHeader>
                        <Label>注册日期</Label>
                    </CellHeader>
                    <CellBody>
                        <Input type="date" defaultValue="" onChange={ e=> console.log(e.target.value)}/>
                    </CellBody>
                </FormCell>
                <FormCell>
                    <CellHeader>
                        <Label>上次登录</Label>
                    </CellHeader>
                    <CellBody>
                        <Input type="datetime-local" defaultValue="" placeholder=""/>
                    </CellBody>
                </FormCell>
                <FormCell>
                    <CellHeader>
                        <Label>旧密码</Label>
                    </CellHeader>
                    <CellBody>
                        <Input type="password" placeholder=""/>
                    </CellBody>
                </FormCell>
                <FormCell>
                    <CellHeader>
                        <Label>新密码</Label>
                    </CellHeader>
                    <CellBody>
                        <Input type="password" placeholder=""/>
                    </CellBody>
                </FormCell>
                <FormCell>
                    <CellHeader>
                        <Label>新密码</Label>
                    </CellHeader>
                    <CellBody>
                        <Input type="password" placeholder=""/>
                    </CellBody>
                </FormCell>
            </Form>
            <CellsTitle>站点信息</CellsTitle>
            <Form>
                <FormCell select selectPos="after">
                    <CellHeader>
                        <Label>管理节点</Label>
                    </CellHeader>
                    <CellBody>
                        <Select data={[
                            {
                                value: 1,
                                label: 'China'
                            },
                            {
                                value: 2,
                                label: 'United States'
                            },
                            {
                                value: 3,
                                label: 'Germany'
                            }
                        ]} />
                    </CellBody>
                </FormCell>
            </Form>

            <ButtonArea>
                <Button
                    //button to display toptips
                    onClick={ e=> {
                        if(this.state.showToptips) return;
                        this.setState({showToptips: !this.state.showToptips})
                        window.setTimeout(e=> this.setState({showToptips: !this.state.showToptips}), 2000)
                    }
                }>
                    更新个人信息
                </Button>
            </ButtonArea>

            <Toptips type="warn" show={this.state.showToptips}>
                something is wrong!
            </Toptips>
        </Page>
        )
    }
}

export default PersonalInfo;