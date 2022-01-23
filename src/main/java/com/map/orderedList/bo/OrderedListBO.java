package com.map.orderedList.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.map.orderedList.dao.OrderedListDAO;
import com.map.orderedList.model.OrderedList;

@Service
public class OrderedListBO {

	@Autowired
	private OrderedListDAO orderedListDAO;
	
	public void insertOrder(int storeId, String address, double latitude, double longtitude) {
		if(existAddress(storeId, address)) {
			orderedListDAO.updateOrder(storeId, address);
		} else {
			orderedListDAO.insertOrder(storeId, address, latitude, longtitude);
		}
	}
	
	public boolean existAddress(int storeId, String address) {
		return orderedListDAO.existAddress(storeId, address);
	}
	
	public List<OrderedList> getOrderedListByStoreId(int storeId) {
		return orderedListDAO.selectOrderedListByStoreId(storeId);
	}
	
	public void updateOrderCountById(List<Map<String, Integer>> orderCountUpdateList) {
		for (Map<String, Integer> map : orderCountUpdateList) {
			orderedListDAO.updateOrderCountById(map);
		}
	}
	
	public void deleteOrderedListById(int id) {
		orderedListDAO.deleteOrderedListById(id);
	}
	
	public List<OrderedList> getOrderedList() {
		return orderedListDAO.selectOrderedList();
	}
}
