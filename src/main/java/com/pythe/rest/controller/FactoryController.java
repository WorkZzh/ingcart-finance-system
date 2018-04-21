package com.pythe.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.ExceptionUtil;
import com.pythe.rest.service.ActivityService;
import com.pythe.rest.service.BagService;
import com.pythe.rest.service.FactoryService;
import com.pythe.rest.service.MaintenanceService;

@Controller
public class FactoryController {

	@Autowired  
    private FactoryService service;  
	
			
	@RequestMapping(value = "/car/insert/record", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult insetRecordIncar(@RequestParam(required = true,value = "storeId") String storeId) {
		try {
			return service.insetRecordIncar(storeId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	//(1)将订单进行group by 统计一下。
	@RequestMapping(value = "/groupby/ordernum", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult groupByOrderNumSum() {
		try {
			return service.groupByOrderNumSum();
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	//(2)生产账单
	@RequestMapping(value = "/car/pay", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult insetCarPay() {
		try {
			return service.insetCarPay();
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	//(3)生产数据
	@RequestMapping(value = "/insert/bill/record", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult insertBillRecord() {
		try {
			return service.insertBillRecord();
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	

	
	
  
}
