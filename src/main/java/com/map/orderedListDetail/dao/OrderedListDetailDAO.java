package com.map.orderedListDetail.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.map.orderedListDetail.model.OrderedListDetail;

@Repository
public interface OrderedListDetailDAO {
	public void insertOrderedListDetail(int id);
	public List<OrderedListDetail> selectOrderedListDetailByOrderId(int orderId);
	public void deleteOrderedListDetailByOrderId(int orderId);
	public List<OrderedListDetail> selectOrderedListDetailByDate(
			@Param("orderId") int orderId,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);
	
}
