package com.pythe.rest.service;

import java.util.List;

import com.pythe.common.pojo.PytheResult;
import com.pythe.pojo.TblCustomer;


public interface CustomerService  {

	PytheResult register(String parameters);

	PytheResult registerCheck(String parameters);

	PytheResult wxSessionRequest(String code);

	PytheResult selectPersonalImformationByCustomerId(Long customerId);

	PytheResult receiveGift(String parameters);
    
	
	
	
}
