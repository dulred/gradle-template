package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dulred
 * @description
 * @github https://github.com/dulred
 */
@Slf4j
@RestController
public class FileController {

    @GetMapping("/exportFile1")
    public void  exportFile1(HttpServletResponse response){

        try {
            // 创建工作簿
            Workbook workbook = new XSSFWorkbook();

            // 创建工作表
            Sheet sheet = workbook.createSheet("Sheet1");

            // 创建第一行（大标题）
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("大标题");
            titleCell.setCellStyle(getTitleCellStyle(workbook)); // 设置样式

            // 合并第一行的5列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

            // 创建第二行（小标题）
            Row subtitleRow = sheet.createRow(1);
            for (int i = 0; i < 5; i++) {
                Cell subtitleCell = subtitleRow.createCell(i);
                subtitleCell.setCellValue("小标题" + (i + 1));
                subtitleCell.setCellStyle(getSubtitleCellStyle(workbook)); // 设置样式
            }

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=exported_file.xlsx");

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
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setBold(true);
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return titleCellStyle;
    }

    // 获取小标题样式
    private CellStyle getSubtitleCellStyle(Workbook workbook) {
        CellStyle subtitleCellStyle = workbook.createCellStyle();
        Font subtitleFont = workbook.createFont();
        subtitleFont.setFontHeightInPoints((short) 12);
        subtitleFont.setBold(true);
        subtitleCellStyle.setFont(subtitleFont);
        subtitleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        subtitleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        subtitleCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        subtitleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return subtitleCellStyle;
    }


    @GetMapping("/exportFile2")
    public void exportFile2( ){

        System.out.println("你是猪22");

    }


}
