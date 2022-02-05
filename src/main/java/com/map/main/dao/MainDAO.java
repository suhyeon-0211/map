package com.map.main.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.map.main.model.ExcelDownloadModel;

@Repository
public interface MainDAO {
	public List<ExcelDownloadModel> selectExcelDownloadModel(int userId);
}
