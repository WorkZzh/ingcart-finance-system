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
import com.pythe.rest.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	
	/**
	 * 微信信息请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wxSession/request", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult updateRequest(
			@RequestParam(required = true, value = "code") String code,
			@RequestParam(defaultValue="0") Integer userType) {
		try {
			return (customerService.wxSessionRequest(code, userType));
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	/**
	 * 检查客户是否已存在
	 * @return
	 */
	@RequestMapping(value = "/customer/register/check", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult registerCheck(@RequestBody String parameters)
	{
		try {
			return customerService.registerCheck(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	
	
	/**
	 * 使用验证码注册登录
	 * @return
	 */
	@RequestMapping(value = "/customer/register", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult register(@RequestBody String parameters)
	{
		try {
			return customerService.register(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping(value = "/customer/loginout", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult Loginout(@RequestBody String parameters)
	{
		try {
			return customerService.Loginout(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	/**
	 * 检查管理员是否已存在
	 * @return
	 */
	@RequestMapping(value = "/manage/register/check/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult registerCheckByManger(@RequestBody String parameters)
	{
		try {
			return customerService.registerCheckByManger(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	/**
	 * 管理员注册
	 * @return
	 */
	@RequestMapping(value = "/manage/register", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult registerByManager(@RequestBody String parameters)
	{
		try {
			return customerService.registerByManager(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	/**
	 * 查询用户个人信息查询
	 * @return
	 */
	@RequestMapping(value = "/customer/select", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectPersonalImformationByCustomerId(@RequestParam(required = true, value = "customerId") Long customerId) {
		try {
			return (customerService.selectPersonalImformationByCustomerId(customerId));
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	/**
	 * 使用赠品券，领取赠品
	 * @return
	 */
	@RequestMapping(value = "/coupon/receiveGift", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult receiveGift(@RequestBody String parameters) {
		try {
			return (customerService.receiveGift(parameters));
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	/**
	 * 通过电话号码查询用户使用车的情况
	 * @return
	 */
	@RequestMapping(value = "/select/cr", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult  selectCustomerByPhoneNum(@RequestBody String parameters) {
		try {
			return (customerService.selectCustomerByPhoneNum(parameters));
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	

	
	

}
