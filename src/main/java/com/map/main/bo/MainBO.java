package com.map.main.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.map.main.dao.MainDAO;
import com.map.main.model.ExcelDownloadModel;

@Service
public class MainBO {

	@Autowired
	private MainDAO mainDAO;
	
	public List<ExcelDownloadModel> getExcelDownloadModel(int userId) {
		return mainDAO.selectExcelDownloadModel(userId);
	}
}
