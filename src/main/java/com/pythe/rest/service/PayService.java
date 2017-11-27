package com.pythe.rest.service;

import com.pythe.common.pojo.PytheResult;

public interface PayService {

	PytheResult chargeForAccount(String parameters) throws Exception;

	PytheResult wxChargeConfirm(String parameters) throws Exception;


	
}
