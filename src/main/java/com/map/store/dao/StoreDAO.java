package com.map.store.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.map.store.model.Store;

@Repository
public interface StoreDAO {
	public List<Store> selectStore();
	public List<Store> selectStoreIsUse();
	public void insertStore(List<String> storeList);
	public void deleteStore(List<Integer> deleteStoreList);
}
