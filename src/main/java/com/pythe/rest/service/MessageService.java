package com.pythe.rest.service;

import com.pythe.common.pojo.PytheResult;

public interface MessageService {

	PytheResult singleSend(String parameters) throws Exception;

	
}
