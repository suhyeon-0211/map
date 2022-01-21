package com.map.test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestBO {

	@Autowired
	private TestDAO testDAO;
	
	public List<Map<String, Object>> getScore() {
		return testDAO.selectScore();
	}
}
