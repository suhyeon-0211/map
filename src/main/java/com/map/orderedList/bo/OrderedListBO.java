package com.map.orderedList.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.map.orderedList.dao.OrderedListDAO;
import com.map.orderedList.model.OrderedList;
import com.map.orderedList.model.OrderedListTotal;
import com.map.orderedListDetail.bo.OrderedListDetailBO;
import com.map.orderedListDetail.model.OrderedListDetail;

@Service
public class OrderedListBO {

	@Autowired
	private OrderedListDAO orderedListDAO;
	
	@Autowired
	private OrderedListDetailBO orderedListDetailBO;
	
	
	public void insertOrder(int storeId, String address, double latitude, double longtitude) {
		OrderedList tempOrder = existAddress(storeId, address);
		if(tempOrder == null) {
			orderedListDAO.insertOrder(storeId, address, latitude, longtitude);
			tempOrder = existAddress(storeId, address);
		}
		orderedListDetailBO.insertOrderedListDetail(tempOrder.getId());
	}
	
	
	/*
	 * public void insertOrderCount(int storeId, String address, double latitude,
	 * double longtitude, int orderCount) { if(existAddress(storeId, address)) {
	 * orderedListDAO.updateOrder(storeId, address); } else {
	 * orderedListDAO.insertOrderCount(storeId, address, latitude, longtitude,
	 * orderCount); } }
	 */
	
	public OrderedList existAddress(int storeId, String address) {
		return orderedListDAO.existAddress(storeId, address);
	}
	
	public boolean existOrder(int id) {
		return orderedListDAO.existOrder(id);
	}
	
	public List<OrderedListTotal> getOrderedListByStoreId(int storeId) {
		List<OrderedList> tempOrderedList = orderedListDAO.selectOrderedListByStoreId(storeId);
		List<OrderedListTotal> OrderedListTotal = new ArrayList<>();
		for (OrderedList tempOrder : tempOrderedList) {
			List<OrderedListDetail> tempOrderedListDetail = orderedListDetailBO.getOrderedListDetailByOrderId(tempOrder.getId());
			OrderedListTotal tempOrderedListTotal = new OrderedListTotal();
			tempOrderedListTotal.setOrderedList(tempOrder);
			tempOrderedListTotal.setOrderCount(tempOrderedListDetail.size());
			OrderedListTotal.add(tempOrderedListTotal);
		}
		return OrderedListTotal;
	}
	
	public List<OrderedListTotal> getOrderedListByDate(int storeId, String startDate, String endDate) {
		List<OrderedList> tempOrderedList = orderedListDAO.selectOrderedListByStoreId(storeId);
		List<OrderedListTotal> OrderedListTotal = new ArrayList<>();
		for (OrderedList tempOrder : tempOrderedList) {
			List<OrderedListDetail> tempOrderedListDetail = orderedListDetailBO.getOrderedListDetailByDate(tempOrder.getId(), startDate, endDate);
			if (tempOrderedListDetail.size() <= 0) {
				continue;
			}
			OrderedListTotal tempOrderedListTotal = new OrderedListTotal();
			tempOrderedListTotal.setOrderedList(tempOrder);
			tempOrderedListTotal.setOrderCount(tempOrderedListDetail.size());
			OrderedListTotal.add(tempOrderedListTotal);
		}
		
		return OrderedListTotal;
	}
	
	
	public OrderedList getOrderedListById(Object object) {
		return orderedListDAO.selectOrderedListById(object);
	}
	
	
	public void updateOrderById(List<Map<String, Object>> orderCountUpdateList) {
		OrderedList tempOrderedList = getOrderedListById(orderCountUpdateList.get(0).get("id"));
		for (Map<String,Object> map : orderCountUpdateList) { 
			if (Integer.parseInt(map.get("id").toString()) == 0) {
				insertOrder(tempOrderedList.getStoreId(), map.get("address").toString(), tempOrderedList.getLatitude(), tempOrderedList.getLongtitude());
			} else if (Integer.parseInt(map.get("id").toString()) != 0 && existAddress(tempOrderedList.getStoreId(), map.get("address").toString()) == null) {
				// 같은 위치에 주소가 변경된 경우 주소만 업데이트
				updateOrder(Integer.parseInt(map.get("id").toString()), map.get("address").toString());
			} 
		} 
	}
	
	public void updateOrder(int id, String address) {
		orderedListDAO.updateOrder(id, address);
	}
	
	public void deleteOrderedListById(int id) {
		orderedListDAO.deleteOrderedListById(id);
		orderedListDetailBO.deleteOrderedListDetailByOrderId(id);
	}
	
	public List<OrderedList> getOrderedList() {
		return orderedListDAO.selectOrderedList();
	}
	
	public void addOrder(int id) {
		orderedListDetailBO.insertOrderedListDetail(id);
	}
	
}
