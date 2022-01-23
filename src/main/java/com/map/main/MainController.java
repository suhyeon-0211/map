package com.map.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.map.orderedList.bo.OrderedListBO;
import com.map.orderedList.model.OrderedList;
import com.map.store.bo.StoreBO;
import com.map.store.model.Store;

@Controller
public class MainController {

	@Autowired
	private StoreBO storeBO;
	
	@Autowired
	private OrderedListBO orderedListBO;
	
	@RequestMapping("/main")
	public String main(Model model) {
		List<Store> storeList = storeBO.getStoreIsUse();
		
		model.addAttribute("storeList", storeList);
		return "map/main";
	}
	
	@GetMapping("/excel/download")
    public void excelDownload(HttpServletResponse response) throws IOException {
		List<OrderedList> orderedList = orderedListBO.getOrderedList();
		
        Workbook wb = new XSSFWorkbook();
        Sheet sheet =  wb.createSheet("첫번째 시트");
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        // Header
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("id");
        cell = row.createCell(1);
        cell.setCellValue("storeId");
        cell = row.createCell(2);
        cell.setCellValue("address");
        cell = row.createCell(3);
        cell.setCellValue("orderCount");
        cell = row.createCell(4);
        cell.setCellValue("latitude");
        cell = row.createCell(5);
        cell.setCellValue("longtitude");
        cell = row.createCell(6);
        cell.setCellValue("createdAt");
        cell = row.createCell(7);
        cell.setCellValue("updatedAt");

        // Body
        for (int i=0; i<orderedList.size(); i++) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(orderedList.get(i).getId());
            cell = row.createCell(1);
            cell.setCellValue(orderedList.get(i).getStoreId());
            cell = row.createCell(2);
            cell.setCellValue(orderedList.get(i).getAddress());
            cell = row.createCell(3);
            cell.setCellValue(orderedList.get(i).getOrderCount());
            cell = row.createCell(4);
            cell.setCellValue(orderedList.get(i).getLatitude());
            cell = row.createCell(5);
            cell.setCellValue(orderedList.get(i).getLongtitude());
            cell = row.createCell(6);
            cell.setCellValue(orderedList.get(i).getCreatedAt());
            cell = row.createCell(7);
            cell.setCellValue(orderedList.get(i).getUpdatedAt());
        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
//        response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename=ordered_list_database.xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }
}
