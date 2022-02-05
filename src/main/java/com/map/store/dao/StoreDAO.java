package com.map.store.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.map.store.model.Store;

@Repository
public interface StoreDAO {
	public List<Store> selectStore();
	public List<Store> selectStoreIsUse(int userId);
	public void insertStore(
			@Param("storeList") List<String> storeList,
			@Param("userId") int userId);
	public void deleteStore(List<Integer> deleteStoreList);
	public void updateStore(Map<String, Object> updateStoreList);
}
