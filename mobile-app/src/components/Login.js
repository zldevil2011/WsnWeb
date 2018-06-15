import React, { Component } from 'react';
import { Link } from 'react-router'
import axios from 'axios'
import List from 'antd-mobile/lib/list';
import InputItem from 'antd-mobile/lib/input-item';
import WhiteSpace from 'antd-mobile/lib/white-space';
import { Button } from 'antd-mobile/lib';

class Login extends Component {
	constructor(props){
        super(props);
        this.state = {
            showToptips: true
        }
        this.handleClick = this.handleClick.bind(this)
    }
    handleClick (){
        console.log('loginFunc');
        // axios.get(`http://139.199.125.158:3000/`)
        //   .then(res => {
        //     console.log(res);
        //     localStorage.setItem('userLogin', 'zhaolong');
        // });
        localStorage.setItem('userLogin', 'zhaolong');
        let a1=document.createElement('a');
        a1.setAttribute('href','/personalInfo/');
        a1.click();
    }
    render(){
        return (
            this.props.children ? this.props.children:
            <div className="login-form" style={{height:'100%',display: 'flex',alignItems: 'center'}}>
                <List style={{width:'100%'}}>
                    <InputItem
                        clear
                        placeholder=""
                        autoFocus
                    >用户名</InputItem>
                    <InputItem
                        clear
                        type="password"
                        placeholder=""
                        focused={this.state.focused}
                        onFocus={() => {
                        this.setState({
                            focused: false,
                        });
                        }}
                    >密码</InputItem>
                    <List.Item>
                        <div
                        style={{ width: '100%', color: '#108ee9', textAlign: 'center' }}
                        onClick={() => {
                            this.setState({
                            focused: true,
                            });
                        }}
                        >
                        <Button type="primary" onClick={this.handleClick}>登陆</Button><WhiteSpace />
                        <WhiteSpace/>
                        <Button type="primary" onClick={() => window.location.href='/personalInfo/'}>个人中心</Button><WhiteSpace />
                        <WhiteSpace/>
                        <Button type="ghost" onClick={() => window.location.href='/deviceHistory/'}>历史数据</Button><WhiteSpace />
                        <WhiteSpace/>
                        <Button type="warning" onClick={() => window.location.href='/deviceLatest/'}>最新数据</Button><WhiteSpace />
                        </div>
                    </List.Item>
                </List>
            </div>
        )
    }
}

export default Login;