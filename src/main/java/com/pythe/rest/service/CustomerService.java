package com.pythe.rest.service;


import com.pythe.common.pojo.PytheResult;


public interface CustomerService  {

	PytheResult register(String parameters);

	PytheResult registerCheck(String parameters);

	PytheResult wxSessionRequest(String code, Integer userType);

//	PytheResult selectPersonalImformationByCustomerId(Long customerId);

	PytheResult receiveGift(String parameters);

	PytheResult selectPersonalImformationByCustomerId(Long customerId);

	PytheResult selectCustomerByPhoneNum(String parameters);

	PytheResult registerByManager(String parameters);

	PytheResult registerCheckByManger(String parameters);


//	PytheResult userLoginByVerificationCode(String parameters);
    
	
	
	
}
