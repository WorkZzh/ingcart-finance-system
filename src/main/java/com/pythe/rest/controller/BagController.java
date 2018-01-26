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
import com.pythe.rest.service.ActivityService;
import com.pythe.rest.service.BagService;
import com.pythe.rest.service.MaintenanceService;

@Controller
public class BagController {

	@Autowired  
    private BagService service;  
	
	
      
	@RequestMapping(value = "/mai/bag", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult buyBag(@RequestBody String parameters) throws Exception {
		try {
			return service.maiBag(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
			
	@RequestMapping(value = "/store/detail", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectStoreLocation(@RequestParam(required = true,value = "storeId") String storeId) {
		try {
			return service.selectStoreLocation(storeId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	@RequestMapping(value = "/combo/detail", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectComboDetailList() {
		try {
			return service.selectComboDetailList();
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	@Autowired  
    private ActivityService activityService;
	
	@RequestMapping(value = "/query/activity", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult queryActivity(@RequestBody String parameters) throws Exception {
		try {
			return activityService.queryActivity(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
  
}
