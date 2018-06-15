import React, { Component } from 'react';
import { browserHistory } from 'react-router';

class Welcome extends Component {
	constructor(props){
        super(props);
        this.state = {
            showToptips: true
        }
    }
    componentDidMount(){
        try{
            let userLogin = localStorage.getItem("userLogin");
            console.log(userLogin);
            if(userLogin === null){
                // 未登陆状态，跳转到登陆页面
                // browserHistory.push(/\//);
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
    render(){
        return (
            <div style={{height:'100%',display: '-webkit-flex',alignItems: 'center', justifyContent:'center', flexDirection:'column'}}>
                <p style={{fontSize:'48px'}}>关注环境</p>
                <br/>
                <p style={{fontSize:'48px'}}>关注生活</p>
            </div>
        )
    }
}

export default Welcome;