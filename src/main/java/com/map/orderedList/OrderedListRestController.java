package com.map.orderedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.map.orderedList.bo.OrderedListBO;

@RestController
public class OrderedListRestController {

	@Autowired
	private OrderedListBO orderedListBO;
	
	@PostMapping("/order/create")
	public Map<String, Object> AddressCreate(
			@RequestParam("storeId") int storeId,
			@RequestParam("address") String address,
			@RequestParam("latitude") double latitude,
			@RequestParam("longtitude") double longtitude) {
		
		
		// db insert
		orderedListBO.insertOrder(storeId, address, latitude, longtitude);
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		return result;
	}
	
	@PostMapping("/order/select")
	public Map<String, Object> OrderSelectByStoreId(
			@RequestParam("storeId") int storeId) {
		
		Map<String, Object> result = new HashMap<>();
		// db select
		result.put("result", orderedListBO.getOrderedListByStoreId(storeId));
		return result;
	}
	
	@PostMapping("/order/update")
	public Map<String, Object> OrderUpdate(
			@RequestParam("idList[]") List<Integer> idList,
			@RequestParam("valueList[]") List<Integer> orderCountList) {
		
		
		// db update
		List<Map<String, Integer>> orderCountUpdateList = new ArrayList<>();
		Map<String, Integer> tempMap = new HashMap<>();
		for(int i = 0; i < idList.size(); i++) {
			tempMap = new HashMap<>();
			tempMap.put("id", idList.get(i));
			tempMap.put("orderCount", orderCountList.get(i));
			orderCountUpdateList.add(tempMap);
		}
		
		orderedListBO.updateOrderCountById(orderCountUpdateList);
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		return result;
	}
	
	@PostMapping("/order/delete")
	public Map<String, Object> OrderDelete(
			@RequestParam("id") Integer id) {
		
		// db delete
		if (id != null) {
			orderedListBO.deleteOrderedListById(id);
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		return result;
	}
}
