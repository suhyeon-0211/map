package com.map.orderedList.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.map.orderedList.model.OrderedList;

@Repository
public interface OrderedListDAO {
	public void insertOrder(
			@Param("storeId") int storeId, 
			@Param("address") String address, 
			@Param("latitude") double latitude, 
			@Param("longtitude") double longtitude);
	public OrderedList existAddress(
			@Param("storeId") int storeId,
			@Param("address") String address);
	public boolean existOrder(int id);
	
	public void updateOrder(
			@Param("id") int id,
			@Param("address") String address);

	/*
	 * public void updateOrder(
	 * 
	 * @Param("storeId") int storeId,
	 * 
	 * @Param("address") String address);
	 */
	public List<OrderedList> selectOrderedListByStoreId(int storeId);

	/* public void updateOrderCountById(Map<String, Object> orderCountMap); */
	public void deleteOrderedListById(int id);
	public List<OrderedList> selectOrderedList();
	public OrderedList selectOrderedListById(Object id);
	public List<OrderedList> selectOrderedListByDate(
			@Param("storeId") int storeId,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);
}
