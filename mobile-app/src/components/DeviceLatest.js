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
import Picker from 'antd-mobile/lib/picker';
import List from 'antd-mobile/lib/list';

import Table from 'rc-table';
import 'rc-table/assets/index.css';

class DeviceLatest extends Component {
  constructor(props){
    super(props);
    this.state={
      stationName: '',
      userNodesList: [],
      realPm25: '',
      realPm10: '',
      realAQI: '',
      realAQITip: '',
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
    Highcharts.theme = {
      colors: ['#2b908f', '#90ee7e', '#f45b5b', '#7798BF', '#aaeeee', '#ff0066', '#eeaaee',
        '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'],
      chart: {
        backgroundColor: {
          linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
          stops: [
            [0, '#585060'],
            [1, '#585060']
          ]
        },
        style: {
          fontFamily: '\'Unica One\', sans-serif'
        },
        plotBorderColor: '#606063'
      },
      title: {
        style: {
          color: '#E0E0E3',
          textTransform: 'uppercase',
          fontSize: '20px'
        }
      },
      subtitle: {
        style: {
          color: '#E0E0E3',
          textTransform: 'uppercase'
        }
      },
      xAxis: {
        gridLineColor: '#707073',
        labels: {
          style: {
            color: '#E0E0E3'
          }
        },
        lineColor: '#707073',
        minorGridLineColor: '#505053',
        tickColor: '#707073',
        title: {
          style: {
            color: '#A0A0A3'

          }
        }
      },
      yAxis: {
        gridLineColor: '#707073',
        labels: {
          style: {
            color: '#E0E0E3'
          }
        },
        lineColor: '#707073',
        minorGridLineColor: '#505053',
        tickColor: '#707073',
        tickWidth: 1,
        title: {
          style: {
            color: '#A0A0A3'
          }
        }
      },
      tooltip: {
        backgroundColor: 'rgba(0, 0, 0, 0.85)',
        style: {
          color: '#F0F0F0'
        }
      },
      plotOptions: {
        series: {
          dataLabels: {
            color: '#B0B0B3'
          },
          marker: {
            lineColor: '#333'
          }
        },
        boxplot: {
          fillColor: '#505053'
        },
        candlestick: {
          lineColor: 'white'
        },
        errorbar: {
          color: 'white'
        }
      },
      legend: {
        itemStyle: {
          color: '#E0E0E3'
        },
        itemHoverStyle: {
          color: '#FFF'
        },
        itemHiddenStyle: {
          color: '#606063'
        }
      },
      credits: {
        style: {
          color: '#666'
        }
      },
      labels: {
        style: {
          color: '#707073'
        }
      },

      drilldown: {
        activeAxisLabelStyle: {
          color: '#F0F0F3'
        },
        activeDataLabelStyle: {
          color: '#F0F0F3'
        }
      },

      navigation: {
        buttonOptions: {
          symbolStroke: '#DDDDDD',
          theme: {
            fill: '#505053'
          }
        }
      },

      // scroll charts
      rangeSelector: {
        buttonTheme: {
          fill: '#505053',
          stroke: '#000000',
          style: {
            color: '#CCC'
          },
          states: {
            hover: {
              fill: '#707073',
              stroke: '#000000',
              style: {
                color: 'white'
              }
            },
            select: {
              fill: '#000003',
              stroke: '#000000',
              style: {
                color: 'white'
              }
            }
          }
        },
        inputBoxBorderColor: '#505053',
        inputStyle: {
          backgroundColor: '#333',
          color: 'silver'
        },
        labelStyle: {
          color: 'silver'
        }
      },

      navigator: {
        handles: {
          backgroundColor: '#666',
          borderColor: '#AAA'
        },
        outlineColor: '#CCC',
        maskFill: 'rgba(255,255,255,0.1)',
        series: {
          color: '#7798BF',
          lineColor: '#A6C7ED'
        },
        xAxis: {
          gridLineColor: '#505053'
        }
      },

      scrollbar: {
        barBackgroundColor: '#808083',
        barBorderColor: '#808083',
        buttonArrowColor: '#CCC',
        buttonBackgroundColor: '#606063',
        buttonBorderColor: '#606063',
        rifleColor: '#FFF',
        trackBackgroundColor: '#404043',
        trackBorderColor: '#404043'
      },

      // special colors for some of the
      legendBackgroundColor: 'rgba(0, 0, 0, 0.5)',
      background2: '#505053',
      dataLabelsColor: '#B0B0B3',
      textColor: '#C0C0C0',
      contrastTextColor: '#F0F0F3',
      maskColor: 'rgba(255,255,255,0.3)'
    };

  // Apply the theme
    Highcharts.setOptions(Highcharts.theme);

    console.log("--------------------");
    // Create the chart
    
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
    // 加载完节点列表之后默认加载第一个节点的数据，因此异步获取第一个节点最新的数据
    // 最新的一条采集数据
    axios.get(`http://${IP}/WsnWeb/api/node_latest_data/${nodeId}`)
    .then(res => {
        console.log(res.data);
        this.setState({
          realPm25:res.data.pm25,
          realPm10:res.data.pm10,
          realAQI:res.data.aqi,
          realAQITip:res.data.conclusion
        });
        this.loadDataCharts();
    });
    // 加载完节点列表之后默认加载第一个节点的数据，因此异步获取第一个节点最新的数据
    // 该节点当天的数据
    axios.get(`http://${IP}/WsnWeb/api/data_list/${nodeId}?dataType=day`)
        .then(res => {
            console.log(res.data);
            this.setState({
              pm25List: res.data[0],
              pm25TimeList: res.data[7]
            })
        });
    // 获取当天的分钟数据，展示在下方的表格中
    let data = new FormData();
    data.append('requestType', 'all');
    data.append('startTime', this.getDateStr(0));
    data.append('endTime', this.getDateStr(1));
    axios.post(`http://${IP}/WsnWeb/api/node_historical_data/${nodeId}`, data)
      .then(res=>{
          console.log(res);
          console.log(res.data);
          this.setState({
            nodeRealData: res.data
          })
      }).catch(res=>{
          console.log(res);
      })
  }
  loadDataCharts(){
    Highcharts.chart('tabPanel', {
      chart: {
        type: 'column'
      },
      title: {
        text: '最新数据'
      },
      xAxis: {
        type: 'category'
      },
      yAxis: {
        title: {
          text: '浓度'
        }
      },
      legend: {
        enabled: false
      },
      plotOptions: {
        series: {
          borderWidth: 0,
          dataLabels: {
            enabled: true,
            format: '{point.y:.1f}'
          }
        }
      },

      tooltip: {
        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}</b><br/>'
      },

      series: [{
        name: '浓度',
        colorByPoint: true,
        data: [{
          name: 'PM2.5',
          y: this.state.realPm25,
          drilldown: 'PM2.5'
        }, {
          name: 'PM10',
          y: 24.03,
          drilldown: 'PM10'
        }, {
          name: 'AQI',
          y: 10.38,
          drilldown: 'AQI'
        }
        ]
      }]
    });
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
    let config = {
      title: {
        text: '今日数据'
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
      { title: '站点', dataIndex: 'nodeName', key: 'nodeName', width: '1rem'},
      { title: 'Pm2.5', dataIndex: 'pm25', key: 'pm25', width: '1rem' },
      { title: 'Pm10', dataIndex: 'pm10', key: 'pm10', width: '1rem' },
      { title: 'So2', dataIndex: 'so2', key: 'so2', width: '1rem' },
      { title: 'No2', dataIndex: 'no2', key: 'no2', width: '1rem' },
      { title: 'Co', dataIndex: 'co', key: 'co', width: '1rem' },
      { title: 'O3', dataIndex: 'o3', key: 'o3', width: '1rem' },
      { title: 'AQI', dataIndex: 'aqi', key: 'aqi', width: '1rem' },
      { title: '日期', dataIndex: 'updateTime', key: 'updateTime', width: '1rem' }
    ];
    
    const data = nodeRealData;
    const { realPm25, realPm10, realAQI, realAQITip } = this.state;
    const { userNodesList, stationName } = this.state;
    return (
      this.props.children ? this.props.children:<div>
        <Picker
          data={userNodesList}
          cols={1}
          value={stationName}
          // onChange={ v => { console.log(v); this.setState({ stationName: v })} }
          onChange={ this.changeStation }
          className="forss">
          <List.Item arrow="horizontal">选择站点</List.Item>
        </Picker>
        <WhiteSpace/>
        {/* <NoticeBar mode="closable" icon={<Icon type="check-circle-o" size="xxs" />}>
          Customized icon.
        </NoticeBar> */}
        <WhiteSpace/>
        <Card>
          <Card.Header
            title="PM2.5"
            thumb="https://cloud.githubusercontent.com/assets/1698185/18039916/f025c090-6dd9-11e6-9d86-a4d48a1bf049.png"
            extra={<span>{realPm25}ug/m3</span>}
          />
          {/* <Card.Body>
            <div>一级，正常水平</div>
          </Card.Body> */}
        </Card>
        <WhiteSpace/>
        <Card>
          <Card.Header
            title="PM10"
            thumb="https://cloud.githubusercontent.com/assets/1698185/18039916/f025c090-6dd9-11e6-9d86-a4d48a1bf049.png"
            extra={<span>{realPm10}ug/m3</span>}
          />
          {/* <Card.Body>
            <div>一级，正常水平</div>
          </Card.Body> */}
        </Card>
        <WhiteSpace/>
        <Card>
          <Card.Header
            title="AQI"
            thumb="https://cloud.githubusercontent.com/assets/1698185/18039916/f025c090-6dd9-11e6-9d86-a4d48a1bf049.png"
            extra={<span>{realAQI}</span>}
          />
          <Card.Body>
            <div>{realAQITip}</div>
          </Card.Body>
        </Card>
        <WhiteSpace/>
        
        <div className="line-data">
          <div id="tabPanel"></div>
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
export default DeviceLatest;