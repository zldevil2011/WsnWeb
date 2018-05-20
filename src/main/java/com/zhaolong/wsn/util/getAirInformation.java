package com.zhaolong.wsn.util;

import com.zhaolong.wsn.entity.Information;
import org.apache.poi.hssf.usermodel.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhaolong on 2018/5/5.
 */
public class getAirInformation {
    private List<ArrayList<String>> stationData = new ArrayList<ArrayList<String>>();

    public String RegexString(String targetStr, String patternStr)
    {
        // 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容 // 相当于埋好了陷阱匹配的地方就会掉下去
        Pattern pattern = Pattern.compile(patternStr);
        // 定义一个matcher用来做匹配

        Matcher matcher = pattern.matcher(targetStr);
        List<String> resList = new ArrayList<String>();
        // 如果找到了
        while (matcher.find())
        {
            String tmp = matcher.group(1).trim();
            resList.add(tmp);
        }
        if (resList.size() == 3){
            // 代表是第一列
            ArrayList<String> tmp1 = new ArrayList<String>();
            tmp1.add("环保大楼");
            tmp1.add(resList.get(0));

            ArrayList<String> tmp2 = new ArrayList<String>();
            tmp2.add("池州学院");
            tmp2.add(resList.get(1));

            ArrayList<String> tmp3 = new ArrayList<String>();
            tmp3.add("平天湖");
            tmp3.add(resList.get(2));

            this.stationData.add(tmp1);
            this.stationData.add(tmp2);
            this.stationData.add(tmp3);

        } else {
            // 代表是后续的21条数据
            for(int i = 0; i < resList.size(); ++i){
                if(i <= 6){
                    this.stationData.get(0).add(resList.get(i));
                } else if ( i <= 13){
                    this.stationData.get(1).add(resList.get(i));
                } else {
                    this.stationData.get(2).add(resList.get(i));
                }
            }
        }
        return "";
    }
    public void getData() throws IOException {
        this.stationData.clear();
        Spider s = new Spider();
        String url = "http://hbj.chizhou.gov.cn/hjsj.html";
        System.out.println("starting.....");
        String result = "";
        try {
            result = s.SendGet(url);
            System.out.println(result);
        }catch (Exception e){
            System.out.println("error....." + e);
        }
        String regRsult = this.RegexString(result, "height=\"26px\">(.*?)</td>");
        String regRsult2 = this.RegexString(result, "<td widtd=\"80px\" algin=\"center\">(.*?)</td>");
        for(int i = 0; i < this.stationData.size(); ++i){
            ArrayList<String> t = this.stationData.get(i);
            for(int j = 0; j < t.size(); ++j){
                System.out.print(t.get(j) + " ");
            }
            System.out.println("\n");
        }
        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("sheet1");
        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 设置表头
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("站点");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("NO2");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("SO2");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("CO");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("O3");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("PM10");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("PM2.5");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("质量指数");
        cell.setCellStyle(style);

        cell = row.createCell(8);
        cell.setCellValue("状况");
        cell.setCellStyle(style);


        // 循环将数据写入Excel
        for(int i = 0; i < this.stationData.size(); ++i){
            row = sheet.createRow((int) i + 1);
            ArrayList<String> t = this.stationData.get(i);
            for(int j = 0; j < t.size(); ++j){
                row.createCell(j).setCellValue(t.get(j) );
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH");//设置日期格式
        String currentTime = df.format(new Date());// new Date()为获取当前系统时间
        String fileName = currentTime + ".xls";
        System.out.println(fileName);
        FileOutputStream os= new FileOutputStream(fileName);
        wb.write(os);
        os.flush();
        os.close();
    }
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        final getAirInformation gf = new getAirInformation();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("TimerTask is called!");
                try {
                    gf.getData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 1000 * 60 * 60);
    }
}
