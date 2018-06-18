import React, { Component } from 'react';
import IP from './utils'
import List from 'antd-mobile/lib/list';
import { InputItem, WhiteSpace, Button } from 'antd-mobile/lib/';
import { createForm } from 'rc-form';
import axios from 'axios';

class LoginForm extends Component {
	constructor(props){
        super(props);
        this.state = {
            showToptips: true,
        }
        this.handleClick = this.handleClick.bind(this)
        this.setStateByKey = this.setStateByKey.bind(this);
    }
    handleClick (){
        console.log('loginFunc');
        // axios.get(`http://139.199.125.158:3000/`)
        //   .then(res => {
        //     console.log(res);
        //     localStorage.setItem('userLogin', 'zhaolong');
        // });
        this.props.form.validateFields({ force: true }, (error) => {
            if (!error) {
              console.log(this.props.form.getFieldsValue());
            } else {
              alert('信息不正确');
              return false;
            }
        });
        let formData = this.props.form.getFieldsValue();
        let data = new FormData();
        data.append('username', formData.username);
        data.append('password', formData.password);
        // 验证通过，则提交服务器验证是否正确
        // axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded';
        axios.post(`http://${IP}/WsnWeb/api/login`, data)
            .then(res=>{
                console.log(res);
                console.log(res.data);
                if(res.status == 200){
                    // 登陆成功，记录本地存储并跳转到个人中心
                    localStorage.setItem('userLogin', res.data);
                    let a1=document.createElement('a');
                    a1.setAttribute('href','/personalInfo/');
                    a1.click();
                }else{
                    // 否则登陆失败，提示用户名或密码错误
                    alert('用户名或密码不正确');
                }
            }).catch(res=>{
                console.log(res);
                alert('用户名或密码不正确');
            })
        
    }
    setStateByKey(k, e) {  
        console.log(k);
        console.log(e);
        console.log(e.target);
        // let v = e.target.value;  
        this.setState({  
          k: e
        });
      } 
    render(){
        const { getFieldProps, getFieldError } = this.props.form;
        return (
            this.props.children ? this.props.children:
            <div className="login-form" style={{height:'100%',display: 'flex',alignItems: 'center',flexDirection: 'column',justifyContent: 'center'}}>
                <div className="app-icon" style={{marginBottom:'50px'}}>
                    <img src="https://www.easyicon.net/api/resizeApi.php?id=1072607&size=128"/>
                </div>
                <form style={{width:'100%'}}>
                <List style={{width:'100%'}}>
                    <InputItem
                        {...getFieldProps('username', {
                            // initialValue: 'little ant',
                            rules: [
                                { required: true, message: 'Please input account' },
                                { validator: this.validateAccount },
                            ],
                        })}
                        clear
                        placeholder=""
                        autoFocus
                    >用户名</InputItem>
                    <InputItem
                        {...getFieldProps('password')}
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
                        {/* spe="warning" onClick={() => window.location.href='/deviceLatest/'}>最新数据</Button><WhiteSpace /> */}
                        </div>
                    </List.Item>
                </List>
                </form>
            </div>
        )
    }
}
const Login = createForm()(LoginForm);
export default Login;