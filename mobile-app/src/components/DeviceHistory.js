import React, { Component } from 'react';
import { browserHistory } from 'react-router';
import ReactHighcharts from 'react-highcharts';
import Highcharts from 'highcharts'

import Picker from 'antd-mobile/lib/picker';
import DatePicker from 'antd-mobile/lib/date-picker';
import List from 'antd-mobile/lib/list';

import Table from 'rc-table';
import 'rc-table/assets/index.css';

class DeviceHistory extends Component {
  constructor(props){
    super(props);
    this.state={
      stationName: 2013,
      startTime: '',
      endTime: ''
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
    Highcharts.chart('tabPanel', {
      chart: {
        type: 'column'
      },
      title: {
        text: '任务数量'
      },
      xAxis: {
        type: 'category'
      },
      yAxis: {
        title: {
          text: '数量'
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
            format: '{point.y:.1f}%'
          }
        }
      },

      tooltip: {
        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
      },

      series: [{
        name: 'Brands',
        colorByPoint: true,
        data: [{
          name: '任务总数',
          y: 56.33,
          drilldown: '任务总数'
        }, {
          name: '待完成',
          y: 24.03,
          drilldown: '待完成'
        }, {
          name: '已完成',
          y: 10.38,
          drilldown: '已完成'
        }
        ]
      }]
    });
  }
  render() {
    const district = [
      {
        label: '2013',
        value: '2013',
      },
      {
        label: '2014',
        value: '2014',
      },
    ];
    let config = {
      xAxis: {
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
      },
      series: [{
        data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 295.6, 454.4]
      }]
    };
    const columns = [
      { title: '站点', dataIndex: 'title', key: 'title', width: '1rem'},
      { title: 'Pm2.5', dataIndex: 'a', key: 'a', width: '1rem' },
      { title: 'Pm10', dataIndex: 'b', key: 'b', width: '1rem' },
      { title: 'So2', dataIndex: 'c', key: 'c', width: '1rem' },
      { title: 'No2', dataIndex: 'd', key: 'd', width: '1rem' },
      { title: 'Co', dataIndex: 'e', key: 'e', width: '1rem' },
      { title: 'O3', dataIndex: 'f', key: 'f', width: '1rem' },
      { title: 'AQI', dataIndex: 'g', key: 'g', width: '1rem' },
    ];
    
    const data = [
      { title: '人物1', a: '19', b: '32', c:'111', d: '2', e: '33', f:'12', g:'1' ,key: '1' },
      { title: '人物2', a: '19', b: '32', c:'111', d: '2', e: '33', f:'12', g:'1' ,key: '2' },
      { title: '人物3', a: '19', b: '32', c:'111', d: '2', e: '33', f:'12', g:'1' ,key: '3' },
      { title: '人物4', a: '19', b: '32', c:'111', d: '2', e: '33', f:'12', g:'1' ,key: '4' }
    ];
    const { stationName, startTime, endTime } = this.state;
    return (
      this.props.children ? this.props.children:<div>
        <Picker
          data={district}
          cols={1}
          value={stationName}
          onChange={v => this.setState({ stationName: v })}
          className="forss">
          <List.Item arrow="horizontal">选择站点</List.Item>
        </Picker>
        <DatePicker
          mode="date"
          title="选择日期"
          extra="开始日期"
          value={startTime}
          onChange={v => this.setState({ startTime: v })}
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
export default DeviceHistory;