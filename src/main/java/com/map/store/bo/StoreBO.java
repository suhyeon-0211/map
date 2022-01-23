package com.map.store.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.map.store.dao.StoreDAO;
import com.map.store.model.Store;

@Service
public class StoreBO {

	@Autowired
	private StoreDAO storeDAO;
	
	public List<Store> getStore() {
		return storeDAO.selectStore();
	}
	
	public List<Store> getStoreIsUse() {
		return storeDAO.selectStoreIsUse();
	}
	
	@Transactional
	public void updateStore(List<String> storeList) {
		// 기존의 있던 id 저장
		List<Store> storeOldList = getStore();

		List<Integer> deleteStoreList = new ArrayList<>();
		// 추가해야되는 메뉴인지 제거해야하는 메뉴인지 확인
		for (Store store : storeOldList) {
			boolean isDel = true;
			if (!storeList.isEmpty()) {
				for (String menu : storeList) {
					if (store.getStoreName().equals(menu)) {
						storeList.remove(menu);
						isDel = false;
						break;
					}
				}
			}
			if (isDel) {
				deleteStoreList.add(store.getId());
			}
		}
			
		// db delete
		if (!deleteStoreList.isEmpty()) {
			deleteStore(deleteStoreList);
		}
		
		// db insert
		if (!storeList.isEmpty()) {
			insertStore(storeList);
		}
	}
	
	public void insertStore(List<String> storeList) {
		storeDAO.insertStore(storeList);
	}
	
	public void deleteStore(List<Integer> deleteStoreList) {
		storeDAO.deleteStore(deleteStoreList);
	}
}
