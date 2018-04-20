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
import com.pythe.rest.service.MaintenanceService;

@Controller
public class MaintenanceController {

	@Autowired
	private MaintenanceService service;

	/**
	 * 提交维修
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/use/callRepair", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult callRepair(@RequestBody String parameters) throws Exception {

		try {
			return service.callRepair(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	// 历史行程
	@RequestMapping(value = "/user/trip", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectTripBillByCustomerId(@RequestParam(required = true, value = "customerId") Long customerId,
			@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "30") Integer pageSize) {
		try {
			return service.selectTripBillByCustomerId(customerId, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 检修
	 */
	@RequestMapping(value = "/record/maintenance", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult recordMaintenance(@RequestBody String parameter) throws Exception {
		return service.recordMaintenance(parameter);
	}


	@RequestMapping(value = "/select/area/level", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectAllAreaByLevel(@RequestParam(required = true, value = "level") String level,
			@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize)
			throws Exception {
		try {
			return service.selectAllAreaByLevel(level, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
