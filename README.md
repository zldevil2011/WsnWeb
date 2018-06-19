# WsnWeb

基于多种采集协议实现区域内的大气环境质量的网格化监控

采用WEB+APP的方式实现

WEB功能
一级功能:  数据可视化模块

二级功能	功能描述

采集设备位置管理	通过地理位置信息展示数据采集设备的位置信息，可准确定位，同时提供了当前设备的数据概述。

设备实时数据获取	针对每一台采集设备，实现了设备采集数据的实时可视化，用户可查看当前设备最近24小时的数据以及30天的日平均数据，提供数据曲线以及数据表格两种方式。

所有设备最新数据	可查看所有安装设备的最新数据，通过列表形式展示数据，通过站点名称可进入单一站点数据

数据演变功能	借助地图接口实现数据的变化趋势演变，通过热力图的方式显示数据的污染程度，可针对不同参数的不同时间段下的不同尺度的数据演变。

大气空气质量分析	计算分析每一个采集站点的最新采集数据的最新空气质量，计算AQI、首要污染物、空气等级以及质量

空气质量对比	通过不同时间尺度对比不同站点的环境质量，包括今日实时、昨日排名、一周排名以及月平均排名分析不同站点的趋势变化。

空气预警列表	按照系统设定的预警策略，当采集数据出发预警规则时，则可以在此功能模块查看当前发生的预警事件。

数据下载功能	提供采集数据分析导出功能，对不同站点以及不同时间尺度的数据提供分析导出功能，提供数据集合。

信息发布功能模块	提供爬取的最新相关新闻以及本地发布的数据新闻预览功能。

天气状况功能模块	提供若干地区的天气状况的预报数据，包括当前实时以及7日预报（采用第三方API获取）


加入了free marker和grunt打包的使用
freemarker
在pom中配置freemarker需要的包，然后在spring-mvc中配置
对于原来的路由，需要将模版写为ftl后缀

grunt
GruntFile.js是grunt的配置文件，通过配置task实现任务的处理
最长用到的是less转css: 执行 grunt les
监听文件的变化: grunt watcher

