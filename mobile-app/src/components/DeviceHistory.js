import React, { Component } from 'react';
import { browserHistory } from 'react-router';
import ReactHighcharts from 'react-highcharts';
import Highcharts from 'highcharts'
import IP from './utils'
import axios from 'axios';

import Carousel from 'antd-mobile/lib/carousel';
import NoticeBar from 'antd-mobile/lib/notice-bar';
import WhiteSpace from 'antd-mobile/lib/white-space';
import Icon from 'antd-mobile/lib/icon';
import WingBlank from 'antd-mobile/lib/wing-blank';
import Card from 'antd-mobile/lib/card';
import { DatePicker, Picker} from 'antd-mobile/lib';
import List from 'antd-mobile/lib/list';

import Table from 'rc-table';
import 'rc-table/assets/index.css';

class DeviceHistory extends Component {
  constructor(props){
    super(props);
    this.state={
      stationName: '',
      userNodesList: [],
      startTime: '',
      endTime: '',
      pm25List: [],
      pm25TimeList: [],
      nodeRealData: []
    }
    this.changeStation = this.changeStation.bind(this);
    this.loadDataCharts = this.loadDataCharts.bind(this);
    this.getNodeData = this.getNodeData.bind(this);
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
          //获取用户管理的节点的列表
          axios.get(`http://${IP}/WsnWeb/api/getPersonNodes/${userId}`)
              .then(res => {
                  let userNodesInfo = res.data;
                  console.log(userNodesInfo);
                  let nodesTmp = [];
                  for(let i = 0; i < userNodesInfo.length; ++i){
                      let obj = {
                          value: userNodesInfo[i].id,
                          label: userNodesInfo[i].nodeName
                      }
                      nodesTmp.push(obj);
                  }
                  if(nodesTmp.length > 0) {
                      this.setState({
                        stationName: [nodesTmp[0].value]
                      })
                      let firstNodeId = nodesTmp[0].value;
                      this.getNodeData(firstNodeId);
                  }
                  console.log(nodesTmp);
                  this.setState({
                    userNodesList: nodesTmp
                  })
              });
      }
    }catch(e){
      console.log(e);
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
  getNodeData(nodeId){
    // 加载完节点列表之后默认加载第一个节点的数据，因此异步获取第一个在当天的数据，即只有一条数据
    let data = new FormData();
    data.append('requestType', 'day');
    data.append('startTime', this.getDateStr(-7));
    data.append('endTime', this.getDateStr(1));
    let pStart = this.getDateStr(-7);
    let pEnd = this.getDateStr(0);
    axios.post(`http://${IP}/WsnWeb/api/node_historical_data/${nodeId}`, data)
      .then(res=>{
          console.log(res);
          console.log(res.data);
          let pm25DataTmp = [];
          let pm25TimeTmp = [];
          for(let i = res.data.length-1; i >= 0 ; --i){
            pm25DataTmp.push(Math.round(res.data[i].pm25));
            pm25TimeTmp.push(res.data[i].updateTime);
          }
          this.setState({
            nodeRealData: res.data,
            pm25List: pm25DataTmp,
            pm25TimeList: pm25TimeTmp
          });
      }).catch(res=>{
          console.log(res);
      })
  }
  loadDataCharts(){
    
  }
  changeStation(v){
    // v => this.setState({ stationName: v })
    this.setState({
      stationName: v
    });
    this.getNodeData(v[0]);
  }
  render() {
    const { pm25List, pm25TimeList, nodeRealData} = this.state;
    console.log(pm25List);
    let config = {
      title: {
        text: '日平均PM2.5数据'
      },
      xAxis: {
        categories: pm25TimeList
      },
      yAxis: {
        title: {
          text: '浓度'
        }
      },
      tooltip: {
        pointFormat: '<span>{point.y:.2f}ug/m3<span/>'
      },
      series: [{
        name: '浓度',
        data: pm25List
      }]
      
    };
    const columns = [
      { title: 'Pm2.5', dataIndex: 'pm25', key: 'pm25', width: '1rem', render:v=> v ? v.toFixed(2):'' },
      { title: 'Pm10', dataIndex: 'pm10', key: 'pm10', width: '1rem', render:v=> v ? v.toFixed(2):''  },
      { title: 'So2', dataIndex: 'so2', key: 'so2', width: '1rem' , render:v=> v ? v.toFixed(2):'' },
      { title: 'No2', dataIndex: 'no2', key: 'no2', width: '1rem', render:v=> v ? v.toFixed(2):''  },
      { title: 'Co', dataIndex: 'co', key: 'co', width: '1rem', render:v=> v ? v.toFixed(2):''  },
      { title: 'O3', dataIndex: 'o3', key: 'o3', width: '1rem', render:v=> v ? v.toFixed(2):''  },
      { title: 'AQI', dataIndex: 'aqi', key: 'aqi', width: '1rem', render:v=> v ? v.toFixed(2):'' },
      { title: '日期', dataIndex: 'updateTime', key: 'updateTime', width: '1rem' }
    ];
    
    const data = nodeRealData;
    const { realPm25, realPm10, realAQI, realAQITip, startTime, endTime } = this.state;
    const { userNodesList, stationName } = this.state;
    return (
      this.props.children ? this.props.children:<div>
        <Picker
          data={userNodesList}
          cols={1}
          value={stationName}
          onChange={ this.changeStation }
          className="forss">
          <List.Item arrow="horizontal">选择站点</List.Item>
        </Picker>
        <DatePicker
          mode="date"
          title="选择日期"
          extra="开始日期"
          value={startTime}
          onChange={v => { console.log(v), this.setState({ startTime: v })}}
        >
          <List.Item arrow="horizontal">开始日期</List.Item>
        </DatePicker>
        <DatePicker
          mode="date"
          title="选择日期"
          extra="结束日期"
          value={endTime}
          onChange={v => this.setState({ endTime: v })}
        >
          <List.Item arrow="horizontal">结束日期</List.Item>
        </DatePicker>
        
        <div className="line-data">
          <ReactHighcharts config={config}></ReactHighcharts>
        </div>
        <div className="data-table">
        <Table
            titleFixed
            columns={columns}
            showHeader={true}
            data={data}
          />
        </div>
      </div>
    );
  }
}
export default DeviceHistory;