import React, { Component } from 'react';

import 'weui';
import 'react-weui/build/packages/react-weui.css';
import WeUI from 'react-weui';

const { Page, Panel, PanelBody, MediaBox, MediaBoxHeader, MediaBoxBody, MediaBoxDescription, MediaBoxTitle} = WeUI;

class NewsList extends Component {
	constructor(props){
        super(props);
        this.state={
            showAddredd: false,
            dialogStyle: {
                buttons: [
                    {
                        label: 'Ok',
                        onClick: this.hideDialog.bind(this)
                    }
                ]
            },
        }
    }
    hideDialog() {
        this.setState({
            showAddredd: false
        });
    }
    render(){
        const appMsgIcon = <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHgAAAB4CAMAAAAOusbgAAAAeFBMVEUAwAD///+U5ZTc9twOww7G8MYwzDCH4YcfyR9x23Hw+/DY9dhm2WZG0kbT9NP0/PTL8sux7LFe115T1VM+zz7i+OIXxhes6qxr2mvA8MCe6J6M4oz6/frr+us5zjn2/fa67rqB4IF13XWn6ad83nxa1loqyirn+eccHxx4AAAC/klEQVRo3u2W2ZKiQBBF8wpCNSCyLwri7v//4bRIFVXoTBBB+DAReV5sG6lTXDITiGEYhmEYhmEYhmEYhmEY5v9i5fsZGRx9PyGDne8f6K9cfd+mKXe1yNG/0CcqYE86AkBMBh66f20deBc7wA/1WFiTwvSEpBMA2JJOBsSLxe/4QEEaJRrASP8EVF8Q74GbmevKg0saa0B8QbwBdjRyADYxIhqxAZ++IKYtciPXLQVG+imw+oo4Bu56rjEJ4GYsvPmKOAB+xlz7L5aevqUXuePWVhvWJ4eWiwUQ67mK51qPj4dFDMlRLBZTqF3SDvmr4BwtkECu5gHWPkmDfQh02WLxXuvbvC8ku8F57GsI5e0CmUwLz1kq3kD17R1In5816rGvQ5VMk5FEtIiWislTffuDpl/k/PzscdQsv8r9qWq4LRWX6tQYtTxvI3XyrwdyQxChXioOngH3dLgOFjk0all56XRi/wDFQrGQU3Os5t0wJu1GNtNKHdPqYaGYQuRDfbfDf26AGLYSyGS3ZAK4S8XuoAlxGSdYMKwqZKM9XJMtyqXi7HX/CiAZS6d8bSVUz5J36mEMFDTlAFQzxOT1dzLRljjB6+++ejFqka+mXIe6F59mw22OuOw1F4T6lg/9VjL1rLDoI9Xzl1MSYDNHnPQnt3D1EE7PrXjye/3pVpr1Z45hMUdcACc5NVQI0bOdS1WA0wuz73e7/5TNqBPhQXPEFGJNV2zNqWI7QKBd2Gn6AiBko02zuAOXeWIXjV0jNqdKegaE/kJQ6Bfs4aju04lMLkA2T5wBSYPKDGF3RKhFYEa6A1L1LG2yacmsaZ6YPOSAMKNsO+N5dNTfkc5Aqe26uxHpx7ZirvgCwJpWq/lmX1hA7LyabQ34tt5RiJKXSwQ+0KU0V5xg+hZrd4Bn1n4EID+WkQdgLfRNtvil9SPfwy+WQ7PFBWQz6dGWZBLkeJFXZGCfLUjCgGgqXo5TuSu3cugdcTv/HjqnBTEMwzAMwzAMwzAMwzAMw/zf/AFbXiOA6frlMAAAAABJRU5ErkJggg==" />;
        return (
            <Page className="panel newsList">
                <Panel>
                    <PanelBody>
                        <MediaBox type="appmsg" href="javascript:void(0);">
                            <MediaBoxHeader>{appMsgIcon}</MediaBoxHeader>
                            <MediaBoxBody>
                                <MediaBoxTitle>标题</MediaBoxTitle>
                                <MediaBoxDescription>
                                    简介
                                </MediaBoxDescription>
                            </MediaBoxBody>
                        </MediaBox>
                        <MediaBox type="appmsg" href="javascript:void(0);">
                            <MediaBoxHeader>{appMsgIcon}</MediaBoxHeader>
                            <MediaBoxBody>
                                <MediaBoxTitle>标题</MediaBoxTitle>
                                <MediaBoxDescription>
                                    简介
                                </MediaBoxDescription>
                            </MediaBoxBody>
                        </MediaBox>
                        <MediaBox type="appmsg" href="javascript:void(0);">
                            <MediaBoxHeader>{appMsgIcon}</MediaBoxHeader>
                            <MediaBoxBody>
                                <MediaBoxTitle>标题</MediaBoxTitle>
                                <MediaBoxDescription>
                                    简介
                                </MediaBoxDescription>
                            </MediaBoxBody>
                        </MediaBox>
                        <MediaBox type="appmsg" href="javascript:void(0);">
                            <MediaBoxHeader>{appMsgIcon}</MediaBoxHeader>
                            <MediaBoxBody>
                                <MediaBoxTitle>标题</MediaBoxTitle>
                                <MediaBoxDescription>
                                    简介
                                </MediaBoxDescription>
                            </MediaBoxBody>
                        </MediaBox>
                        <MediaBox type="appmsg" href="javascript:void(0);">
                            <MediaBoxHeader>{appMsgIcon}</MediaBoxHeader>
                            <MediaBoxBody>
                                <MediaBoxTitle>标题</MediaBoxTitle>
                                <MediaBoxDescription>
                                    简介
                                </MediaBoxDescription>
                            </MediaBoxBody>
                        </MediaBox>
                        <MediaBox type="appmsg" href="javascript:void(0);">
                            <MediaBoxHeader>{appMsgIcon}</MediaBoxHeader>
                            <MediaBoxBody>
                                <MediaBoxTitle>标题</MediaBoxTitle>
                                <MediaBoxDescription>
                                    简介
                                </MediaBoxDescription>
                            </MediaBoxBody>
                        </MediaBox>
                        <MediaBox type="appmsg" href="javascript:void(0);">
                            <MediaBoxHeader>{appMsgIcon}</MediaBoxHeader>
                            <MediaBoxBody>
                                <MediaBoxTitle>标题</MediaBoxTitle>
                                <MediaBoxDescription>
                                    简介
                                </MediaBoxDescription>
                            </MediaBoxBody>
                        </MediaBox>
                        <MediaBox type="appmsg" href="javascript:void(0);">
                            <MediaBoxHeader>{appMsgIcon}</MediaBoxHeader>
                            <MediaBoxBody>
                                <MediaBoxTitle>标题</MediaBoxTitle>
                                <MediaBoxDescription>
                                    简介
                                </MediaBoxDescription>
                            </MediaBoxBody>
                        </MediaBox>
                        <MediaBox type="appmsg" href="javascript:void(0);">
                            <MediaBoxHeader>{appMsgIcon}</MediaBoxHeader>
                            <MediaBoxBody>
                                <MediaBoxTitle>标题</MediaBoxTitle>
                                <MediaBoxDescription>
                                    简介
                                </MediaBoxDescription>
                            </MediaBoxBody>
                        </MediaBox>
                    </PanelBody>
                </Panel>
            </Page>
        )
    }
}

export default NewsList;