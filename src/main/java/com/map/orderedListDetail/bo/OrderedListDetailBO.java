package com.map.orderedListDetail.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.map.orderedListDetail.dao.OrderedListDetailDAO;
import com.map.orderedListDetail.model.OrderedListDetail;

@Service
public class OrderedListDetailBO {

	@Autowired
	private OrderedListDetailDAO orderedListDetailDAO;
	
	public void insertOrderedListDetail(int id) {
		orderedListDetailDAO.insertOrderedListDetail(id);
	}
	
	public List<OrderedListDetail> getOrderedListDetailByOrderId(int orderId) {
		return orderedListDetailDAO.selectOrderedListDetailByOrderId(orderId);
	}
	
	public void deleteOrderedListDetailByOrderId(int orderId) {
		orderedListDetailDAO.deleteOrderedListDetailByOrderId(orderId);
	}
	
	public List<OrderedListDetail> getOrderedListDetailByDate(int orderId, String startDate, String endDate) {
		return orderedListDetailDAO.selectOrderedListDetailByDate(orderId, startDate, endDate);
	}
}
