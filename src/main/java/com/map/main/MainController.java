package com.map.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.map.main.bo.MainBO;
import com.map.main.model.ExcelDownloadModel;
import com.map.store.bo.StoreBO;
import com.map.store.model.Store;

@Controller
public class MainController {

	@Autowired
	private StoreBO storeBO;
	
	@Autowired
	private MainBO mainBO;
	
	@RequestMapping("/main")
	public String main(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		List<Store> storeList = new ArrayList<>();
		if (userId != null) {
			storeList = storeBO.getStoreIsUse(userId);
		}
		
		model.addAttribute("storeList", storeList);
		return "map/main";
	}
	
	@GetMapping("/excel/download")
    public void excelDownload(HttpServletResponse response, HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		
		List<ExcelDownloadModel> ExcelDownloadModelList = mainBO.getExcelDownloadModel(userId);
		
        Workbook wb = new XSSFWorkbook();
        Sheet sheet =  wb.createSheet("첫번째 시트");
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        // Header
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("로그인 아이디");
        cell = row.createCell(1);
        cell.setCellValue("사용자 이름");
        cell = row.createCell(2);
        cell.setCellValue("매장 이름");
        cell = row.createCell(3);
        cell.setCellValue("주문 주소");
        cell = row.createCell(4);
        cell.setCellValue("주문 날짜");

        // Body
        for (int i=0; i< ExcelDownloadModelList.size(); i++) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(ExcelDownloadModelList.get(i).getLoginId());
            cell = row.createCell(1);
            cell.setCellValue(ExcelDownloadModelList.get(i).getName());
            cell = row.createCell(2);
            cell.setCellValue(ExcelDownloadModelList.get(i).getStoreName());
            cell = row.createCell(3);
            cell.setCellValue(ExcelDownloadModelList.get(i).getAddress());
            cell = row.createCell(4);
            cell.setCellValue(ExcelDownloadModelList.get(i).getCreatedAt());
            cell = row.createCell(5);
        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
//        response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename=ExcelDownloadModelList_database.xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }
}
