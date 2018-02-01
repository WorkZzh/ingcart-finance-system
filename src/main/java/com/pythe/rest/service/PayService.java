package com.pythe.rest.service;

import org.dom4j.DocumentException;

import com.pythe.common.pojo.PytheResult;

public interface PayService {

	PytheResult chargeForAccount(String parameters) throws Exception;

	PytheResult wxChargeConfirm(String parameters) throws Exception;

	PytheResult chargeForAccountInApp(String parameters) throws Exception;

	String wxChargeConfirmInApp(String parameters) throws Exception;


	
}
