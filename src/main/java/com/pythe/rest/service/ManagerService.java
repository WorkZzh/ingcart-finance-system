package com.pythe.rest.service;

import com.pythe.common.pojo.PytheResult;

public interface ManagerService {

	PytheResult updateVersion(String parameters);

	PytheResult selectVersion(String parameters);


	PytheResult countCarCondition(Integer pageNum, Integer pageSize);




}
