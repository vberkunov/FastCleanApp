package com.app.files;

import com.app.entity.Item;
import com.app.repository.ItemRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

import java.util.List;

public class ExelFile {

    private static List<Item> items;

    public ExelFile(List<Item> items) throws FileNotFoundException {
        this.items = items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void writeToFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Items");

        writeHeader(workbook,sheet);
        writeContent(workbook,sheet);

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp1.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();

    }

    public static void writeHeader(Workbook workbook, Sheet sheet){

        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 6000);
        sheet.setColumnWidth(7, 4000);
        sheet.setColumnWidth(8, 4000);
        sheet.setColumnWidth(9, 6000);
        sheet.setColumnWidth(10,4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Артикул");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Размер");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("ФИО");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Предприятие");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Город");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Цех");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Табельный номер");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Дата");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(8);
        headerCell.setCellValue("EPC код");
        headerCell.setCellStyle(headerStyle);


        headerCell = header.createCell(9);
        headerCell.setCellValue("Подразделение ГО");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(10);
        headerCell.setCellValue("Отгрузка");
        headerCell.setCellStyle(headerStyle);

    }

    public static void writeContent(Workbook workbook, Sheet sheet){
        CellStyle style = workbook.createCellStyle();
            int k = 0;
        for(int i = 1; i < items.size() +1 ; i++){
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(items.get(k).getVendorCode());
            cell.setCellStyle(style);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(items.get(k).getSize().toString());
            cell1.setCellStyle(style);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(items.get(k).getName());
            cell2.setCellStyle(style);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(items.get(k).getOrganization());
            cell3.setCellStyle(style);

            Cell cell4 = row.createCell(4);
            cell4.setCellValue(items.get(k).getCity());
            cell4.setCellStyle(style);

            Cell cell5 = row.createCell(5);
            cell5.setCellValue(items.get(k).getDepartment());
            cell5.setCellStyle(style);

            Cell cell6 = row.createCell(6);
            cell6.setCellValue(items.get(k).getInventoryCode());
            cell6.setCellStyle(style);

            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("d/m/yy"));
            Cell cell7 = row.createCell(7);
            cell7.setCellValue(java.sql.Date.valueOf(items.get(k).getDate()));
            cell7.setCellStyle(cellStyle);

            Cell cell8 = row.createCell(8);
            cell8.setCellValue(items.get(k).getEpc());
            cell8.setCellStyle(style);

            Cell cell9 = row.createCell(9);
            cell9.setCellValue(items.get(k).getOfficeDepartment());
            cell9.setCellStyle(style);

            Cell cell10 = row.createCell(10);
            cell10.setCellValue(0);
            cell10.setCellStyle(style);

            k++;
        }





    }
}
