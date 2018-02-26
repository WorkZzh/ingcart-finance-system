package com.pythe.rest.service;

import com.pythe.common.pojo.PytheResult;

public interface ManagerService {

	PytheResult updateVersion(String parameters);

	PytheResult selectVersion(String parameters);


	PytheResult countCarCondition(Integer pageNum, Integer pageSize);

	PytheResult updateFixedPointForCar(String parameters);

	PytheResult deleteFixedPointForCar(String parameters);

	PytheResult insertAttraction(String parameters);

	PytheResult selectCarAttraction(String parameters);



	PytheResult selectAllCity();


	PytheResult selectAllAreaByCity(String city, Integer pageNum, Integer pageSize);

	PytheResult selectMaintennanceCondition(Integer pageNum, Integer pageSize);

	PytheResult selectPriceLevel();

	PytheResult updateMaintenanceStatus(Long id);





}
