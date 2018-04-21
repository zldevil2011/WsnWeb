package com.zhaolong.wsn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhaolong on 2018/4/21.
 */
@Controller
@RequestMapping(value = "/adminWsn/*")
public class AdminSite {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String AdminIndex() {
        System.out.println("admin index template");
        return "admin/adminIndex";
    }
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String StartPage() {
        System.out.println("admin index template");
        return "admin/adminIndex";
    }
    @RequestMapping(value = "/deviceList", method = RequestMethod.GET)
    public String DeviceList() {
        System.out.println("admin deviceList template");
        return "admin/deviceList";
    }
    @RequestMapping(value = "/warningEventList", method = RequestMethod.GET)
    public String WarningEventList() {
        System.out.println("admin warningEventList template");
        return "admin/warningEventList";
    }
    @RequestMapping(value = "/deviceDataList", method = RequestMethod.GET)
    public String DeviceDataList() {
        System.out.println("admin deviceDataList template");
        return "admin/deviceDataList";
    }
}
