package com.pythe.rest.service;

import com.pythe.common.pojo.PytheResult;

public interface MaintenanceService {

	PytheResult callRepair(String parameters);

	PytheResult selectTripBillByCustomerId(Long customerId, Integer pageNum, Integer pageSize);

	PytheResult recordMaintenance(String parameter);

}
