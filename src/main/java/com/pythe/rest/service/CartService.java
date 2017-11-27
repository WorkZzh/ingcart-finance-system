package com.pythe.rest.service;

import com.pythe.common.pojo.PytheResult;

public interface CartService {


	PytheResult unloke(String parameters);

	PytheResult loke(String parameters);

	PytheResult selectCouponByCustomerId(Long customerId);


	PytheResult selectCartPositionByMap(Integer longitude, Integer latitude);

	PytheResult computeFee(String parameters);

	PytheResult holdCartByCustomerId(String parameters);

}
