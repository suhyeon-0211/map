package com.map.orderedList;

import java.text.ParseException;
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
			@RequestParam("addressList[]") List<String> addressList) {
		
		// db update
		List<Map<String, Object>> orderCountUpdateList = new ArrayList<>();
		Map<String, Object> tempMap = new HashMap<>();
		for(int i = 0; i < idList.size(); i++) {
			tempMap = new HashMap<>();
			tempMap.put("id", idList.get(i));
			tempMap.put("address", addressList.get(i));
			orderCountUpdateList.add(tempMap);
		}
		
		orderedListBO.updateOrderById(orderCountUpdateList);
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		return result;
	}
	
	@PostMapping("/order/delete")
	public Map<String, Object> OrderDelete(
			@RequestParam("id") Integer id) {
		
		// db delete
		if (id != null && id != 0) {
			orderedListBO.deleteOrderedListById(id);
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		return result;
	}
	
	@PostMapping("/order/select_by_date")
	public Map<String, Object> selectOrderedListByDate(
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("storeId") int storeId) throws ParseException {
		/*
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd (EEE)"); Date
		 * tempStartDate = sdf.parse(startDate); Date tempEndDate = sdf.parse(endDate);
		 */
		
		Map<String, Object> result = new HashMap<>();
		// db select
		result.put("result", orderedListBO.getOrderedListByDate(storeId, startDate, endDate));
		return result;
	}
	
	@PostMapping("/order/add")
	public Map<String, Object> AddOrder(
			@RequestParam("id") Integer id) {
		
		if (id != null) {
			orderedListBO.addOrder(id);
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		return result;
	}
	
}
