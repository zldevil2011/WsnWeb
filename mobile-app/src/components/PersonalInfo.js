import React, { Component } from 'react';
import { browserHistory } from 'react-router';
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
            userId: null,
            showTopErrorTips: false,
            showTopSuccessTips: false,
            pUsername: '默认',
            pPhone: '电话',
            pAddress: '地址',
            realPassword: '',
            pPassword: '',
            pNewPassword: '',
            pNewRepeatPassword: ''
        }
        this.handleLogoutClick = this.handleLogoutClick.bind(this);
        this.handlePhoneChange = this.handlePhoneChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleNewPasswordChange = this.handleNewPasswordChange.bind(this);
        this.handleNewRepeatPasswordChange = this.handleNewRepeatPasswordChange.bind(this);
        this.handleAddressChange = this.handleAddressChange.bind(this);
        this.updatePersonalInfo = this.updatePersonalInfo.bind(this);
    }
    componentDidMount() {
        try{
            let userLogin = localStorage.getItem("userLogin");
            if(userLogin === null){
                // 未登陆状态，跳转到登陆页面
                let a1=document.createElement('a');
                a1.setAttribute('href','/');
                a1.click();
            }else{
                // 已经登陆，获取个人信息
                let userId = localStorage.getItem("userLogin");
                axios.get(`http://localhost:8080/WsnWeb/api/personalInfo/${userId}`)
                    .then(res => {
                        let userInfo = res.data;
                        this.setState({
                            userId: userId,
                            userInfo: userInfo,
                            pUsername: userInfo.username,
                            pPhone: userInfo.phone,
                            pAddress: userInfo.address,
                            realPassword: userInfo.password
                        })
                    });
            }
        }catch(e){
            console.log(e);
        }
    }
    updatePersonalInfo(){
        const { realPassword, userId, pUsername, pPhone, pAddress, pPassword, pNewPassword, pNewRepeatPassword } = this.state;

        if(pNewPassword !== pNewRepeatPassword || realPassword != pPassword){
            if(this.state.showTopErrorTips) return;
            this.setState({showTopErrorTips: !this.state.showTopErrorTips})
            setTimeout(e=> this.setState({showTopErrorTips: !this.state.showTopErrorTips}), 2000);
            return false;
        }
        let data = new FormData();
        data.append('username', pUsername);
        data.append('phone', pPhone);
        data.append('address', pAddress);
        if(pNewPassword.trim().length > 0){
            data.append('password', pNewPassword);
        }
        // 验证通过，则提交服务器验证是否正确
        // axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded';
        axios.post(`http://localhost:8080/WsnWeb/api/updateUserInfo/${userId}`, data)
            .then(res=>{
                console.log(res);
                console.log(res.data);
                if(res.status == 200){
                    // 更新成功，显示提示
                    if(this.state.showTopSuccessTips) return;
                    this.setState({showTopSuccessTips: !this.state.showTopSuccessTips})
                    setTimeout(e=> this.setState({showTopSuccessTips: !this.state.showTopSuccessTips}), 2000);
                }else{
                    // 否则登陆失败，提示用户名或密码错误
                    if(this.state.showTopErrorTips) return;
                    this.setState({showTopErrorTips: !this.state.showTopErrorTips})
                    setTimeout(e=> this.setState({showTopErrorTips: !this.state.showTopErrorTips}), 2000);
                }
            }).catch(res=>{
                console.log(res);
                alert('原始密码不正确或新密码不一致');
                if(this.state.showTopErrorTips) return;
                this.setState({showTopErrorTips: !this.state.showTopErrorTips})
                setTimeout(e=> this.setState({showTopErrorTips: !this.state.showTopErrorTips}), 2000);
            })
    }
    handlePhoneChange(e){
        this.setState({
            'pPhone':e.target.value
        });
    }
    handlePasswordChange(e){
        this.setState({ 'pPassword':e.target.value });
    }
    handleNewPasswordChange(e){
        this.setState({ 'pNewPassword':e.target.value });
    }
    handleNewRepeatPasswordChange(e){
        this.setState({ 'pNewRepeatPassword':e.target.value });
    }
    handleAddressChange(e){
        this.setState({ 'pAddress': e.target.value });
    }
    handleLogoutClick(){
        localStorage.removeItem('userLogin');
        let a1=document.createElement('a');
        a1.setAttribute('href','/');
        a1.click();
    }
    render(){
        const appMsgIcon = <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHgAAAB4CAMAAAAOusbgAAAAeFBMVEUAwAD///+U5ZTc9twOww7G8MYwzDCH4YcfyR9x23Hw+/DY9dhm2WZG0kbT9NP0/PTL8sux7LFe115T1VM+zz7i+OIXxhes6qxr2mvA8MCe6J6M4oz6/frr+us5zjn2/fa67rqB4IF13XWn6ad83nxa1loqyirn+eccHxx4AAAC/klEQVRo3u2W2ZKiQBBF8wpCNSCyLwri7v//4bRIFVXoTBBB+DAReV5sG6lTXDITiGEYhmEYhmEYhmEYhmEY5v9i5fsZGRx9PyGDne8f6K9cfd+mKXe1yNG/0CcqYE86AkBMBh66f20deBc7wA/1WFiTwvSEpBMA2JJOBsSLxe/4QEEaJRrASP8EVF8Q74GbmevKg0saa0B8QbwBdjRyADYxIhqxAZ++IKYtciPXLQVG+imw+oo4Bu56rjEJ4GYsvPmKOAB+xlz7L5aevqUXuePWVhvWJ4eWiwUQ67mK51qPj4dFDMlRLBZTqF3SDvmr4BwtkECu5gHWPkmDfQh02WLxXuvbvC8ku8F57GsI5e0CmUwLz1kq3kD17R1In5816rGvQ5VMk5FEtIiWislTffuDpl/k/PzscdQsv8r9qWq4LRWX6tQYtTxvI3XyrwdyQxChXioOngH3dLgOFjk0all56XRi/wDFQrGQU3Os5t0wJu1GNtNKHdPqYaGYQuRDfbfDf26AGLYSyGS3ZAK4S8XuoAlxGSdYMKwqZKM9XJMtyqXi7HX/CiAZS6d8bSVUz5J36mEMFDTlAFQzxOT1dzLRljjB6+++ejFqka+mXIe6F59mw22OuOw1F4T6lg/9VjL1rLDoI9Xzl1MSYDNHnPQnt3D1EE7PrXjye/3pVpr1Z45hMUdcACc5NVQI0bOdS1WA0wuz73e7/5TNqBPhQXPEFGJNV2zNqWI7QKBd2Gn6AiBko02zuAOXeWIXjV0jNqdKegaE/kJQ6Bfs4aju04lMLkA2T5wBSYPKDGF3RKhFYEa6A1L1LG2yacmsaZ6YPOSAMKNsO+N5dNTfkc5Aqe26uxHpx7ZirvgCwJpWq/lmX1hA7LyabQ34tt5RiJKXSwQ+0KU0V5xg+hZrd4Bn1n4EID+WkQdgLfRNtvil9SPfwy+WQ7PFBWQz6dGWZBLkeJFXZGCfLUjCgGgqXo5TuSu3cugdcTv/HjqnBTEMwzAMwzAMwzAMwzAMw/zf/AFbXiOA6frlMAAAAABJRU5ErkJggg==" />;
        console.log(this.state);
        const { pUsername, pPhone, pAddress, pPassword, pNewPassword, pNewRepeatPassword } = this.state;
        return (
        <Page className="personalInfo">
            <CellsTitle>个人信息</CellsTitle>
            <Panel>
                <PanelBody>
                    <MediaBox type="appmsg" href="javascript:void(0);">
                        <MediaBoxHeader>{appMsgIcon}</MediaBoxHeader>
                        <MediaBoxBody>
                            <MediaBoxTitle>{pUsername}</MediaBoxTitle>
                            <MediaBoxDescription>
                                {pPhone}
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
                        <p>{pUsername}</p>
                    </CellBody>
                </FormCell>
                <FormCell>
                    <CellHeader>
                        <Label>电话</Label>
                    </CellHeader>
                    <CellBody>
                        <Input type="tel" placeholder="" value={pPhone} onChange={this.handlePhoneChange}/>
                    </CellBody>
                </FormCell>
                <FormCell>
                    <CellHeader>
                        <Label>地址</Label>
                    </CellHeader>
                    <CellBody>
                        <Input type="text" placeholder="" value={pAddress} onChange={this.handleAddressChange}/>
                    </CellBody>
                </FormCell>
                <FormCell>
                    <CellHeader>
                        <Label>旧密码</Label>
                    </CellHeader>
                    <CellBody>
                        <Input type="password" placeholder="" value={pPassword} onChange={this.handlePasswordChange}/>
                    </CellBody>
                </FormCell>
                <FormCell>
                    <CellHeader>
                        <Label>新密码</Label>
                    </CellHeader>
                    <CellBody>
                        <Input type="password" placeholder="" value={pNewPassword} onChange={this.handleNewPasswordChange}/>
                    </CellBody>
                </FormCell>
                <FormCell>
                    <CellHeader>
                        <Label>新密码</Label>
                    </CellHeader>
                    <CellBody>
                        <Input type="password" placeholder="" value={pNewRepeatPassword} onChange={this.handleNewRepeatPasswordChange}/>
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
                    onClick={ this.updatePersonalInfo }>
                    更新个人信息
                </Button>
                <br/>
                <Button
                    type="warn" 
                    onClick={ this.handleLogoutClick }>
                    退出登陆
                </Button>
            </ButtonArea>

            <Toptips type="warn" show={this.state.showTopErrorTips}>
                失败，原始密码不正确或新密码不一致
            </Toptips>
            <Toptips type="info" show={this.state.showTopSuccessTips}>
                更新成功
            </Toptips>
        </Page>
        )
    }
}

export default PersonalInfo;