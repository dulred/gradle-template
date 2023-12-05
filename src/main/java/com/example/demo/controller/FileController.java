package com.example.demo.controller;

import com.example.demo.model.Bjtxz;
import com.example.demo.model.JckBjtxz;
import com.example.demo.utils.SqlStrUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jooq.DSLContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * @author dulred
 * @description
 * @github https://github.com/dulred
 */
@Slf4j
@RestController
public class FileController {

    private final DSLContext dslContext;

    public FileController(DSLContext dslContext) {
        this.dslContext = dslContext;
    }


    @GetMapping("/exportFile1")
    public void  exportFile1(HttpServletResponse response){

        try {
            // 创建工作簿
            Workbook workbook = new XSSFWorkbook();

            // 创建工作表
            Sheet sheet = workbook.createSheet("Sheet1");

            // 设置每列的列宽，参数是列宽的字符数
            int columnWidth = 18; // 15个字符的列宽
            for (int i = 0; i < 9; i++) {
                sheet.setColumnWidth(i, columnWidth * 256);
            }


            // 创建第一行（大标题）
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("进出口贸易企业办理边境通行证统计表");
            titleCell.setCellStyle(getTitleCellStyle(workbook)); // 设置样式
//            设置第一行的行高
            titleRow.setHeightInPoints(60);
            // 合并第一行的13列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));


         String startTime  = "2023-05-01";
        String endTime = "2023-06-01";
            String  placeName = "孟连县";
            // 创建第二行（小标题）
            Row subtitleRow = sheet.createRow(1);
//           设置行高
            subtitleRow.setHeightInPoints(30);

//            第二行->第一个单元格值和样式
            Cell subCell1 = subtitleRow.createCell(0);
            subCell1.setCellValue("统计时间：");
            subCell1.setCellStyle(getSubtitleCellStyle(workbook));

//            第二行->第二个单元格值和样式
            Cell subCell2 = subtitleRow.createCell(1);
//            合并第二行第二列和第三列
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
            // 解析日期字符串
            LocalDate localDate1 = LocalDate.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // 提取年、月、日
            subCell2.setCellValue(localDate1.getYear() +"年" +localDate1.getMonthValue() + "月" + localDate1.getDayOfMonth() + "日");
            subCell2.setCellStyle(getSubtitleCellStyle(workbook));

//            第二行->第三个单元格值和样式
            Cell subCell3 = subtitleRow.createCell(3);
            subCell3.setCellValue("至");
            subCell3.setCellStyle(getSubtitleCellStyle(workbook));

            //            第二行->第四个单元格值和样式
            Cell subCell4 = subtitleRow.createCell(4);
//            合并第二行第5列和第六列
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));
            // 解析日期字符串
            LocalDate localDate2 = LocalDate.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // 提取年、月、日
            subCell4.setCellValue(localDate2.getYear() +"年" +localDate2.getMonthValue() + "月" + localDate2.getDayOfMonth() + "日");
            subCell4.setCellStyle(getSubtitleCellStyle(workbook));

//            第二行->第5个单元格值和样式
            Cell subCell5 = subtitleRow.createCell(6);
            subCell5.setCellValue("办证大厅：");
            subCell5.setCellStyle(getSubtitleCellStyle(workbook));
//            第二行->第6个单元格值和样式
            Cell subCell6 = subtitleRow.createCell(7);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 8));
            subCell6.setCellValue( placeName + "出入境办证大厅");
            subCell6.setCellStyle(getSubtitleCellStyle(workbook));


          // 创建第三行（小标题）
          Row threeRow = sheet.createRow(2);
          //           设置行高
            threeRow.setHeightInPoints(63);
            Cell Cell0 = threeRow.createCell(0);
            Cell0.setCellValue("进出口贸易公司名称");
            Cell0.setCellStyle(getCommonCellStyle(workbook));
           Cell Cell1 = threeRow.createCell(1);
           Cell1.setCellValue("办证人员数");
           Cell1.setCellStyle(getCommonCellStyle(workbook));
           Cell Cell2 = threeRow.createCell(2);
           Cell2.setCellValue("普洱籍人员办证数");
           Cell2.setCellStyle(getCommonCellStyle(workbook));
           Cell Cell3 = threeRow.createCell(3);
           Cell3.setCellValue("云南籍人员办证数");
           Cell3.setCellStyle(getCommonCellStyle(workbook));
           Cell Cell4 = threeRow.createCell(4);
           Cell4.setCellValue("外省籍人员办证数");
           Cell4.setCellStyle(getCommonCellStyle(workbook));
           Cell Cell5 = threeRow.createCell(5);
           Cell5.setCellValue("男性办证数");
           Cell5.setCellStyle(getCommonCellStyle(workbook));
           Cell Cell6 = threeRow.createCell(6);
           Cell6.setCellValue("女性办证数");
           Cell6.setCellStyle(getCommonCellStyle(workbook));
           Cell Cell7 = threeRow.createCell(7);
           Cell7.setCellValue("35岁及以下人员办证数");
           Cell7.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell8 = threeRow.createCell(8);
            Cell8.setCellValue("35岁以上人员办证数");
            Cell8.setCellStyle(getCommonCellStyle(workbook));



            String sql = "--进出口贸易企业办理边境通行证统计\n" +
                    "select company,count(distinct \"applyId\") bz_num,--办证人员数\n" +
                    "sum(case when substring(\"idCard\",1,4) = '5308' then 1 else 0 end) pr_num,--普洱籍人数\n" +
                    "sum(case when substring(\"idCard\",1,3) = '530' then 1 else 0 end) yn_num,--云南籍人数\n" +
                    "sum(case when substring(\"idCard\",1,3) <> '530' then 1 else 0 end) wj_num,--外籍人数\n" +
                    "sum(case when gender = '男' then 1 else 0 end) man_num,--男性办证人数\n" +
                    "sum(case when gender = '女' then 1 else 0 end) woman_num,--女性办证人数\n" +
                    "sum(case when age < '35' then 1 else 0 end) down_num, --35岁以上人数\n" +
                    "sum(case when age >= '35' then 1 else 0 end) up_num --35岁以上人数\n" +
                    "from exit_and_entry.check_information \n" +
                    "where substring(\"placeName\",1,3) = " + "'" + placeName +"'" + " --办证大厅\n" +
                    "and substring(\"createTime\",1,10) between  "+ "'" +startTime +"'" +" and " + "'" + endTime + "'" + " --时间范围\n" +
                    "group by company";

            List<JckBjtxz>  jckBjtxzs = dslContext.resultQuery(sql).fetchInto(JckBjtxz.class);

            // 动态创建四行（小标题）,从第四行开始
            for (int i = 0; i < jckBjtxzs.size(); i++) {
                Row dyRow = sheet.createRow(i+3);
                //           设置行高
                dyRow.setHeightInPoints(30);
                Cell dyCell1 = dyRow.createCell(0);
                dyCell1.setCellValue(jckBjtxzs.get(i).getCompany());
                dyCell1.setCellStyle(getCommonCellStyle(workbook));
                Cell dyCell2 = dyRow.createCell(1);
                dyCell2.setCellValue(jckBjtxzs.get(i).getBzNum());
                dyCell2.setCellStyle(getCommonCellStyle(workbook));
                Cell dyCell3 = dyRow.createCell(2);
                dyCell3.setCellValue(jckBjtxzs.get(i).getPrNum());
                dyCell3.setCellStyle(getCommonCellStyle(workbook));
                Cell dyCell4 = dyRow.createCell(3);
                dyCell4.setCellValue(jckBjtxzs.get(i).getYnNum());
                dyCell4.setCellStyle(getCommonCellStyle(workbook));
                Cell dyCell5 = dyRow.createCell(4);
                dyCell5.setCellValue(jckBjtxzs.get(i).getWjNum());
                dyCell5.setCellStyle(getCommonCellStyle(workbook));
                Cell dyCell6 = dyRow.createCell(5);
                dyCell6.setCellValue(jckBjtxzs.get(i).getManNum());
                dyCell6.setCellStyle(getCommonCellStyle(workbook));
                Cell dyCell7 = dyRow.createCell(6);
                dyCell7.setCellValue(jckBjtxzs.get(i).getWomanNum());
                dyCell7.setCellStyle(getCommonCellStyle(workbook));
                Cell dyCell8 = dyRow.createCell(7);
                dyCell8.setCellValue(jckBjtxzs.get(i).getDownNum());
                dyCell8.setCellStyle(getCommonCellStyle(workbook));
                Cell dyCell9 = dyRow.createCell(8);
                dyCell9.setCellValue(jckBjtxzs.get(i).getUpNum());
                dyCell9.setCellStyle(getCommonCellStyle(workbook));
            }


            String fileName = "进出口贸易企业办理边境通行证统计表.xlsx";
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename= " + encodedFileName );

            // 输出到响应流
            workbook.write(response.getOutputStream());

            // 关闭工作簿
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // 获取大标题样式
    private CellStyle getTitleCellStyle(Workbook workbook) {
        CellStyle titleCellStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 28);
//        titleFont.setBold(true);
        titleFont.setFontName("方正小标宋_GBK");
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return titleCellStyle;
    }

    private CellStyle getSubtitleCellStyle(Workbook workbook){
        CellStyle commonCellStyle = workbook.createCellStyle();
        Font commonFont = workbook.createFont();
        commonFont.setFontHeightInPoints((short) 11);
        commonFont.setFontName("等线");
        commonCellStyle.setFont(commonFont);
        commonCellStyle.setWrapText(true);
        commonCellStyle.setAlignment(HorizontalAlignment.CENTER);
        commonCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return commonCellStyle;
    }
    // 获取普通字体样式
    private CellStyle getCommonCellStyle(Workbook workbook) {
        CellStyle commonCellStyle = workbook.createCellStyle();
        Font commonFont = workbook.createFont();
        commonFont.setFontHeightInPoints((short) 11);
        commonFont.setFontName("等线");

        // 设置边框样式为实线
        commonCellStyle.setBorderBottom(BorderStyle.THIN);
        commonCellStyle.setBorderTop(BorderStyle.THIN);
        commonCellStyle.setBorderLeft(BorderStyle.THIN);
        commonCellStyle.setBorderRight(BorderStyle.THIN);

        // 设置边框颜色为黑色
        commonCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        commonCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        commonCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        commonCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());


        commonCellStyle.setFont(commonFont);
        commonCellStyle.setWrapText(true);
        commonCellStyle.setAlignment(HorizontalAlignment.CENTER);
        commonCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return commonCellStyle;
    }
    @GetMapping("/test")
    public  void test (){
        String startTime  = "2023-05-01";
        String endTime = "2023-06-01";
        String sql = "--办理边境通行证统计表\n" +
                "select place,sum(jck_num) jck_num,sum(single_num) single_num,sum(bm_num) bm_num,sum(pr_num) pr_num,sum(yn_num) yn_num,sum(wj_num) wj_num,sum(man_num) man_num,sum(woman_num) woman_num,sum(down_num) down_num,sum(up_num) up_num,sum(jck_p_num) jck_p_num,sum(single_p_num) single_p_num from (\n" +
                "select substring(\"placeName\",1,3) as place,\n" +
                "sum(case when company <> '边民' then 1 else 0 end) jck_num, --进出口贸易公司人数\n" +
                "0 single_num,--个体公司人数\n" +
                "sum(case when company = '边民' then 2 else 0 end) bm_num,--边民人数\n" +
                "sum(case when substring(\"idCard\",1,4) = '5308' then 1 else 0 end) pr_num,--普洱籍人数\n" +
                "sum(case when substring(\"idCard\",1,3) = '530' then 1 else 0 end) yn_num,--云南籍人数\n" +
                "sum(case when substring(\"idCard\",1,3) <> '530' then 1 else 0 end) wj_num,--外籍人数\n" +
                "sum(case when gender = '男' then 1 else 0 end) man_num,--男性办证人数\n" +
                "sum(case when gender = '女' then 1 else 0 end) woman_num,--女性办证人数\n" +
                "sum(case when age < '35' then 1 else 0 end) down_num, --35岁以上人数\n" +
                "sum(case when age >= '35' then 1 else 0 end) up_num, --35岁以上人数\n" +
                "case when company <> '边民' then count(distinct company) else 0  end as jck_p_num,--进出口公司数\n" +
                "0 single_p_num\n" +
                "from exit_and_entry.check_information \n" +
                "where substring(\"placeName\",1,3) \n  in" + "('江城县','思茅区','孟连县','澜沧县')" +"--地址选择\n" +
                "and substring(\"createTime\",1,10) between "+ "'" +startTime +"'" +" and " + "'" + endTime + "'" + " --时间范围\n" +
                "group by substring(\"placeName\",1,3),company ) a\n" +
                "group by place";
        List<Bjtxz>  bjtxzs = dslContext.resultQuery(sql).fetchInto(Bjtxz.class);
        System.out.println(bjtxzs);
    }

    @GetMapping("/exportFile2")
    public void exportFile2(HttpServletResponse response){

        try {
            // 创建工作簿
            Workbook workbook = new XSSFWorkbook();

            // 创建工作表
            Sheet sheet = workbook.createSheet("Sheet1");

            // 设置每列的列宽，参数是列宽的字符数
            int columnWidth = 10; // 15个字符的列宽
            for (int i = 0; i < 13; i++) {
                sheet.setColumnWidth(i, columnWidth * 256);
            }


            // 创建第一行（大标题）
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("办理边境通行证统计表");
            titleCell.setCellStyle(getTitleCellStyle(workbook)); // 设置样式
//            设置第一行的行高
            titleRow.setHeightInPoints(60);
            // 合并第一行的13列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));


            String startTime  = "2023-05-01";
            String endTime = "2023-06-01";
            // 创建第二行（小标题）
            Row subtitleRow = sheet.createRow(1);
//           设置行高
            subtitleRow.setHeightInPoints(30);

//            第二行->第一个单元格值和样式
            Cell subCell1 = subtitleRow.createCell(0);
            subCell1.setCellValue("统计时间：");
            subCell1.setCellStyle(getSubtitleCellStyle(workbook));

//            第二行->第二个单元格值和样式
            Cell subCell2 = subtitleRow.createCell(1);
//            合并第二行第二列和第三列
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
            // 解析日期字符串
            LocalDate localDate1 = LocalDate.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // 提取年、月、日
            subCell2.setCellValue(localDate1.getYear() +"年" +localDate1.getMonthValue() + "月" + localDate1.getDayOfMonth() + "日");
            subCell2.setCellStyle(getSubtitleCellStyle(workbook));

//            第二行->第三个单元格值和样式
            Cell subCell3 = subtitleRow.createCell(3);
            subCell3.setCellValue("至");
            subCell3.setCellStyle(getSubtitleCellStyle(workbook));

            //            第二行->第四个单元格值和样式
            Cell subCell4 = subtitleRow.createCell(4);
//            合并第二行第5列和第六列
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));
            // 解析日期字符串
            LocalDate localDate2 = LocalDate.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // 提取年、月、日
            subCell4.setCellValue(localDate2.getYear() +"年" +localDate2.getMonthValue() + "月" + localDate2.getDayOfMonth() + "日");
            subCell4.setCellStyle(getSubtitleCellStyle(workbook));


            // 创建第三行（小标题）
            Row threeRow = sheet.createRow(2);
            //           设置行高
            threeRow.setHeightInPoints(63);
            Cell Cell0 = threeRow.createCell(0);
            Cell0.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell1 = threeRow.createCell(1);
            Cell1.setCellValue("进出口贸易公司办证人员数");
            Cell1.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell2 = threeRow.createCell(2);
            Cell2.setCellValue("个体户办证人员数");
            Cell2.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell3 = threeRow.createCell(3);
            Cell3.setCellValue("边民办证人员数");
            Cell3.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell4 = threeRow.createCell(4);
            Cell4.setCellValue("普洱籍人员办证数");
            Cell4.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell5 = threeRow.createCell(5);
            Cell5.setCellValue("云南籍人员办证数");
            Cell5.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell6 = threeRow.createCell(6);
            Cell6.setCellValue("外省籍人员办证数");
            Cell6.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell7 = threeRow.createCell(7);
            Cell7.setCellValue("男性办证数");
            Cell7.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell8 = threeRow.createCell(8);
            Cell8.setCellValue("女性办证数");
            Cell8.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell9 = threeRow.createCell(9);
            Cell9.setCellValue("35岁以下人员办证数");
            Cell9.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell10 = threeRow.createCell(10);
            Cell10.setCellValue("35岁以上人员办证数");
            Cell10.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell11 = threeRow.createCell(11);
            Cell11.setCellValue("有员工办证进出口贸易公司数");
            Cell11.setCellStyle(getCommonCellStyle(workbook));
            Cell Cell12 = threeRow.createCell(12);
            Cell12.setCellValue("有员工办证个体户数");
            Cell12.setCellStyle(getCommonCellStyle(workbook));


            String placeName = "江城县,孟连县,澜沧县,思茅区";
            String place = SqlStrUtils.MutiSelection(placeName);

            String sql = "--办理边境通行证统计表\n" +
                    "select place,sum(jck_num) jck_num,sum(single_num) single_num,sum(bm_num) bm_num,sum(pr_num) pr_num,sum(yn_num) yn_num,sum(wj_num) wj_num,sum(man_num) man_num,sum(woman_num) woman_num,sum(down_num) down_num,sum(up_num) up_num,sum(jck_p_num) jck_p_num,sum(single_p_num) single_p_num from (\n" +
                    "select substring(\"placeName\",1,3) as place,\n" +
                    "sum(case when company <> '边民' then 1 else 0 end) jck_num, --进出口贸易公司人数\n" +
                    "0 single_num,--个体公司人数\n" +
                    "sum(case when company = '边民' then 2 else 0 end) bm_num,--边民人数\n" +
                    "sum(case when substring(\"idCard\",1,4) = '5308' then 1 else 0 end) pr_num,--普洱籍人数\n" +
                    "sum(case when substring(\"idCard\",1,3) = '530' then 1 else 0 end) yn_num,--云南籍人数\n" +
                    "sum(case when substring(\"idCard\",1,3) <> '530' then 1 else 0 end) wj_num,--外籍人数\n" +
                    "sum(case when gender = '男' then 1 else 0 end) man_num,--男性办证人数\n" +
                    "sum(case when gender = '女' then 1 else 0 end) woman_num,--女性办证人数\n" +
                    "sum(case when age < '35' then 1 else 0 end) down_num, --35岁以上人数\n" +
                    "sum(case when age >= '35' then 1 else 0 end) up_num, --35岁以上人数\n" +
                    "case when company <> '边民' then count(distinct company) else 0  end as jck_p_num,--进出口公司数\n" +
                    "0 single_p_num\n" +
                    "from exit_and_entry.check_information \n" +
                    "where substring(\"placeName\",1,3) \n  in" +place +"--地址选择\n" +
                    "and substring(\"createTime\",1,10) between "+ "'" +startTime +"'" +" and " + "'" + endTime + "'" + " --时间范围\n" +
                    "group by substring(\"placeName\",1,3),company ) a\n" +
                    "group by place";

            List<Bjtxz>  bjtxzs = dslContext.resultQuery(sql).fetchInto(Bjtxz.class);

            String[] stringArray = placeName.split(",");

            HashMap<String,Bjtxz> placeMap = new HashMap();

            for (Bjtxz bj: bjtxzs
            ) {
                placeMap.put(bj.getPlace(),bj);
            }

            // 动态创建四行（小标题）,从第四行开始
            for (int i = 0; i < stringArray.length; i++) {
                Row dyRow = sheet.createRow(i+3);
                //           设置行高
                dyRow.setHeightInPoints(30);
                Cell dyCell = dyRow.createCell(0);
                dyCell.setCellValue(stringArray[i]);
                dyCell.setCellStyle(getCommonCellStyle(workbook));

                if (placeMap.get(stringArray[i])!=null){

                    Cell dyCells1 = dyRow.createCell(1);
                    dyCells1.setCellValue(placeMap.get(stringArray[i]).getJckNum());
                    dyCells1.setCellStyle(getCommonCellStyle(workbook));
                    Cell dyCells2 = dyRow.createCell(2);
                    dyCells2.setCellValue(placeMap.get(stringArray[i]).getSingleNum());
                    dyCells2.setCellStyle(getCommonCellStyle(workbook));
                    Cell dyCells3 = dyRow.createCell(3);
                    dyCells3.setCellValue(placeMap.get(stringArray[i]).getBmNum());
                    dyCells3.setCellStyle(getCommonCellStyle(workbook));
                    Cell dyCells4 = dyRow.createCell(4);
                    dyCells4.setCellValue(placeMap.get(stringArray[i]).getPrNum());
                    dyCells4.setCellStyle(getCommonCellStyle(workbook));
                    Cell dyCells5 = dyRow.createCell(5);
                    dyCells5.setCellValue(placeMap.get(stringArray[i]).getYnNum());
                    dyCells5.setCellStyle(getCommonCellStyle(workbook));
                    Cell dyCells6 = dyRow.createCell(6);
                    dyCells6.setCellValue(placeMap.get(stringArray[i]).getWjNum());
                    dyCells6.setCellStyle(getCommonCellStyle(workbook));
                    Cell dyCells7 = dyRow.createCell(7);
                    dyCells7.setCellValue(placeMap.get(stringArray[i]).getManNum());
                    dyCells7.setCellStyle(getCommonCellStyle(workbook));
                    Cell dyCells8 = dyRow.createCell(8);
                    dyCells8.setCellValue(placeMap.get(stringArray[i]).getWomanNum());
                    dyCells8.setCellStyle(getCommonCellStyle(workbook));
                    Cell dyCells9 = dyRow.createCell(9);
                    dyCells9.setCellValue(placeMap.get(stringArray[i]).getDownNum());
                    dyCells9.setCellStyle(getCommonCellStyle(workbook));
                    Cell dyCells10 = dyRow.createCell(10);
                    dyCells10.setCellValue(placeMap.get(stringArray[i]).getUpNum());
                    dyCells10.setCellStyle(getCommonCellStyle(workbook));
                    Cell dyCells11 = dyRow.createCell(11);
                    dyCells11.setCellValue(placeMap.get(stringArray[i]).getJckPNum());
                    dyCells11.setCellStyle(getCommonCellStyle(workbook));
                    Cell dyCells12 = dyRow.createCell(12);
                    dyCells12.setCellValue(placeMap.get(stringArray[i]).getSinglePNum());
                    dyCells12.setCellStyle(getCommonCellStyle(workbook));


                }else {
                    for (int j = 0; j < 12; j++) {
                        Cell dyCells = dyRow.createCell(j+1);
                        dyCells.setCellValue("0");
                        dyCells.setCellStyle(getCommonCellStyle(workbook));
                    }
                }

            }


            String fileName = "办理边境通行证统计表.xlsx";
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename= " + encodedFileName );

            // 输出到响应流
            workbook.write(response.getOutputStream());

            // 关闭工作簿
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
