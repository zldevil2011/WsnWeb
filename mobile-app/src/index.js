import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { render } from 'react-dom'
import { Router, Route, browserHistory, hashHistory } from 'react-router'
import routes from './routes';

//import styles
import 'antd-mobile/dist/antd-mobile.css';
import './index.less';
import 'react-weui/build/dist/react-weui.css';


ReactDOM.render(
    <Router routes={routes} history={browserHistory} />,
    document.getElementById('container')
);