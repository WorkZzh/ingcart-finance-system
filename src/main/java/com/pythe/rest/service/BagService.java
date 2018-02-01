package com.pythe.rest.service;


import com.pythe.common.pojo.PytheResult;

public interface BagService {

	PytheResult maiBag(String parameters);

	PytheResult selectStoreLocation(String storeId);

	PytheResult selectComboDetailList();

	PytheResult insertStore(String parameters);

	PytheResult updateStoreGoods(String parameters);



	


	
}
