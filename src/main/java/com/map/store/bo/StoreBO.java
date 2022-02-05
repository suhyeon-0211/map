package com.map.store.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<Store> getStoreIsUse(int userId) {
		return storeDAO.selectStoreIsUse(userId);
	}
	
	@Transactional
	public void updateStore(List<String> storeList, List<Integer> storeIdList, int userId) {
		// 기존의 있던 id 저장
		List<Store> storeOldList = getStoreIsUse(userId);

		List<Integer> deleteStoreList = new ArrayList<>();
		
		List<Map<String, Object>> updateStoreList = new ArrayList<>();
		
		List<String> tempStoreList = new ArrayList<>();
		tempStoreList.addAll(storeList);
		// 추가해야되는 메뉴인지 제거해야하는 메뉴인지 확인
		for (Store store : storeOldList) {
			boolean isDel = true;
			if (!storeList.isEmpty()) {
				for (int i = 0; i < storeList.size(); i++) {
					if (storeIdList.get(i) != 0 && storeIdList.get(i) == store.getId() && !storeList.get(i).equals(store.getStoreName())) {
						// 새로 받은 리스트의 id가 0 이 아니고 기존 리스트의 id와 새로운 리스트의 id가 같고 이름은 다른것들
						Map<String, Object> tempMap = new HashMap<>();
						tempMap.put("id", storeIdList.get(i));
						tempMap.put("address", storeList.get(i));
						updateStoreList.add(tempMap);
						tempStoreList.remove(storeList.get(i));
						isDel = false;
						break;
					} else if (storeIdList.get(i) != 0 && storeIdList.get(i) == store.getId() && storeList.get(i).equals(store.getStoreName())) {
						// 새로 받은 리스트의 id가 0이 아니고 기존리스트의 id와 새로운 리스트의 id가 같고 이름도 같은것들
						tempStoreList.remove(storeList.get(i));
						isDel = false;
						break;
					}
				}
			}
			if (isDel) {
				deleteStoreList.add(store.getId());
			}
		}
		
		/*
		 * for (int i = 0; i < storeList.size(); i++) { boolean isDel = true; for (Store
		 * store : storeOldList) { if (storeIdList.get(i) != 0 && storeIdList.get(i) ==
		 * store.getId() && !storeList.get(i).equals(store.getStoreName())) {
		 * Map<String, Object> tempMap = new HashMap<>(); tempMap.put("id",
		 * storeIdList.get(i)); tempMap.put("address", storeList.get(i));
		 * updateStoreList.add(tempMap); } else if (storeIdList.get(i) != 0 &&
		 * storeIdList.get(i) != store.getId()) { // 기존 리스트에는 있는데 새로운 리스트에는 없는 상점 } } }
		 */
			
		
		// db update
		if (!updateStoreList.isEmpty()) {
			updateStore(updateStoreList);
		}
		
		// db delete
		if (!deleteStoreList.isEmpty()) {
			deleteStore(deleteStoreList);
		}
		
		// db insert
		if (!tempStoreList.isEmpty()) {
			insertStore(tempStoreList, userId);
		}
	}
	
	public void insertStore(List<String> storeList, int userId) {
		storeDAO.insertStore(storeList, userId);
	}
	
	public void deleteStore(List<Integer> deleteStoreList) {
		storeDAO.deleteStore(deleteStoreList);
	}
	
	public void updateStore(List<Map<String, Object>> updateStoreList) {
		for (Map<String, Object> tempMap : updateStoreList) {
			storeDAO.updateStore(tempMap);
		}
	}
}
