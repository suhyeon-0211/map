package com.map.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@Autowired
	private TestBO testBO;
	
	@ResponseBody
	@RequestMapping("/test1")
	public String helloworld() {
		return "Hello world!!";
	}
	
	@RequestMapping("/test2")
	@ResponseBody
	public Map<String, String> test2() {
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
	
	// jsp 연결 테스트
	@RequestMapping("/test3")
	public String test3() {
		return "test/test1";
	}

	
	@RequestMapping("/test4")
	@ResponseBody 
	public List<Map<String, Object>> test4() { 
		return testBO.getScore(); 
	}
	
}
