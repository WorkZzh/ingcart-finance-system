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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wxSession/request", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult updateRequest(@RequestParam(required = true, value = "code") String code) {
		try {

			return (customerService.wxSessionRequest(code));
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
	 * 客户注册
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
	
	

	

}
