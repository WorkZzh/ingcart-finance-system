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
import com.pythe.rest.service.ManagerService;


@Controller
public class ManagerController {

	@Autowired
	private ManagerService service;
	
	 /**
     * 创建新的版本
     * 
     * localhost:8084/rest/update/version
     */
	@RequestMapping(value = "/update/version", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult updateVersion(	
			@RequestBody String parameters
			){
    	try {
			return service.updateVersion(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	 /**
     * 创建新的版本
     * 
     * localhost:8084/rest/select/version
     */
	@RequestMapping(value = "/select/version", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult selectVersion(	
			@RequestBody String parameters
			){
    	try {
			return service.selectVersion(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
}
