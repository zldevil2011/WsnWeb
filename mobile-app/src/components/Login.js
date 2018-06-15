import React, { Component } from 'react';
import { Link } from 'react-router'
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