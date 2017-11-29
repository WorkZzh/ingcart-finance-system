package com.pythe.rest.service;

import com.pythe.common.pojo.PytheResult;

public interface CartService {


	PytheResult unloke(String parameters);

	PytheResult loke(String parameters);

	PytheResult selectCouponByCustomerId(Long customerId);


	PytheResult selectCartPositionByMap(Double longitude, Double latitude);

	PytheResult computeFee(String parameters);

	PytheResult holdCartByCustomerId(String parameters);


	PytheResult selectSaveRestTimeByCustomerId(Long customerId);

	PytheResult selectUseCarTimeByCarId(String carId);

	PytheResult deleteAppointmentByCustomerId(Long customerId);

}
