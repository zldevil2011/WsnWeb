<div class="row clearfix">
    <div class="col-md-12 column">
        <nav class="navbar navbar-default" role="navigation">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> <a class="navbar-brand" href="#">管理</a>
            </div>
            <div class="collapse navbar-collapse" id="admin-navbar">
                <ul class="nav navbar-nav">
                    <li :class="{active: indexActive}">
                        <a href="index">首页</a>
                    </li>
                    <li :class="{active: deviceActive}">
                        <a href="deviceList">设备</a>
                    </li>
                    <li :class="{active: warningActive}">
                        <a href="warningEventList">预警</a>
                    </li>
                    <li class="dropdown" :class="{active: dataActive}">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">数据<strong class="caret"></strong></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="deviceDataExport">数据下载</a>
                            </li>
                            <li>
                                <a href="deviceDataList">数据查看</a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown" :class="{active: documentActive}">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">文档信息<strong class="caret"></strong></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="#">文档列表</a>
                            </li>
                            <li>
                                <a href="#">文档爬取</a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">NewlyDawn<strong class="caret"></strong></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="#">个人信息</a>
                            </li>
                            <li>
                                <a href="#">登出</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</div>