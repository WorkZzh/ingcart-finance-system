package com.pythe.rest.service;

import com.pythe.common.pojo.PytheResult;

public interface ManagerService {

	PytheResult updateVersion(String parameters);

	PytheResult selectVersion(String parameters);

	PytheResult countCarCondition(String level, Integer pageNum, Integer pageSize);

	PytheResult updateFixedPointForCar(String parameters);

	PytheResult deleteFixedPointForCar(String parameters);

	PytheResult insertAttraction(String parameters);

	PytheResult selectCarAttraction(String parameters);

	PytheResult selectAllCity();

	PytheResult selectAllAreaByCity(String city, Integer pageNum, Integer pageSize);

	PytheResult selectMaintennanceCondition(Integer pageNum, Integer pageSize);

	PytheResult selectPriceLevel();

	PytheResult updateMaintenanceStatus(Long id);

	PytheResult insertManager(String parameters);

	PytheResult updateLocation(String parameters);

	PytheResult deleteMaintenanceStatus(Long qrId);

	PytheResult zeroCleanAccount(String parameters);

	PytheResult queryRecordBill(String parameters);

	PytheResult selectOneLevel(Long managerId);

	PytheResult selectSumByTime(String parameters);

	PytheResult insertCompany(String parameters);

	PytheResult selectTeasurerOneLevel(Long managerId);

	PytheResult queryRecordBillByTimes(String parameters);

	PytheResult selectSumByTimes(String parameters);

	PytheResult downloadByTime(String parameters);

	PytheResult selectTeasurerTwoLevel(String c1_id, Integer level, String catalog_id);

	PytheResult selectTwoLevel(String c1_id, Integer level, String catalog_id);
}
