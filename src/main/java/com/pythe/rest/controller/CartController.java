package com.pythe.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.ExceptionUtil;
import com.pythe.rest.service.CartService;


@Controller
public class CartController {

	
	@Autowired
	private CartService service;
	
    /**
	 *关锁
	 * @return
	 */
	@RequestMapping(value = "/use/unlock", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult chargeForAccount(@RequestBody String parameters) throws Exception {

		try {
			return service.unloke(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
    /**
	 *开锁
	 * @return
	 */
	@RequestMapping(value = "/use/lock", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult lock(@RequestBody String parameters) throws Exception {

		try {
			return service.loke(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
    /**
	 *确认结算
	 * @return
	 */
	@RequestMapping(value = "/use/computeFee", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult computeFee(@RequestBody String parameters) throws Exception {

		try {
			return service.computeFee(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
    /**
	 *保留车辆
	 * @return
	 */
	@RequestMapping(value = "/use/hold", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult holdCartByCustomerId(@RequestBody String parameters) throws Exception {

		try {
			return service.holdCartByCustomerId(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	//0为可用，1为已用，2为过时，3为取消

    @RequestMapping(value = "/coupon/select", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectCouponByCustomerId(	
			@RequestParam(required = true,value = "customerId") Long customerId
			){
    	try {
			return service.selectCouponByCustomerId(customerId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
    
    /**
	 *显示共享车位置
	 * @return
	 */
    @RequestMapping(value = "/map/carShow", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectCartPositionByMap
	(@RequestParam(required = true,value = "longitude") Integer longitude,
			@RequestParam(required = true,value = "latitude") Integer latitude
			) {

		try {
			return service.selectCartPositionByMap(longitude,latitude);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	

	
	
	
	
}