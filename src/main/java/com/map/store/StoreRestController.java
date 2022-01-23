package com.map.store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.map.store.bo.StoreBO;

@RestController
public class StoreRestController {

	@Autowired
	private StoreBO storeBO;
	
	@PostMapping("/store/change")
	public Map<String, Object> storeUpdate(
			@RequestParam("storeList[]") List<String> storeList) {
		
		// db update
		storeBO.updateStore(storeList);
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		return result;
	}
}
