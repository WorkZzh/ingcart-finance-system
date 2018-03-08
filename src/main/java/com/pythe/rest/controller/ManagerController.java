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
     * 统计车的使用情况
	 * localhost:8084/count/car/condition
     */
    @RequestMapping(value = "/count/car/condition", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult countCarCondition(	@RequestParam(defaultValue="1") Integer pageNum,
			@RequestParam(defaultValue="10") Integer pageSize	){
    	try {
			return service.countCarCondition(pageNum,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	 /**
     * 创建新的版本
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
	
	
	/***
	 * 添加是否应该定点还车
	 */
	@RequestMapping(value = "/update/car/point/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult updateFixedPointForCar(@RequestBody String parameters) throws Exception {
		try {
			return service.updateFixedPointForCar(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	/***
	 * 删除定点还车，还原会不定点还车
	 */
	@RequestMapping(value = "/delete/car/point/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult deleteFixedPointForCar(@RequestBody String parameters) throws Exception {
		try {
			return service.deleteFixedPointForCar(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	

	
	/**
	 * 插入景区信息
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert/attraction/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult insertAttraction(@RequestBody String parameters) throws Exception {
		try {
			return service.insertAttraction(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	
	/**
	 * 更新车的位置
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/car/loc", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult updateLocation(@RequestBody String parameters) throws Exception {
		try {
			return service.updateLocation(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	

	
	
	
	
	
	
	/**
	 * 插入一个管理员
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert/manager/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult insertManager(@RequestBody String parameters) throws Exception {
		try {
			return service.insertManager(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	@RequestMapping(value = "/select/attraction/car/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult selectCarAttraction(@RequestBody String parameters) throws Exception {
		try {
			return service.selectCarAttraction(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	
//	/**
//	 * 让车关联景区
//	 * @param parameters
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/associate/attraction/", method = RequestMethod.POST)
//	@ResponseBody
//	public PytheResult associateAttractionByQrId(@RequestBody String parameters) throws Exception {
//		try {
//			return service.associateAttractionByQrId(parameters);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
//		}
//	}
	
	/**
	 * 查看某个城市，所有景区
	 * https://ingcart.com/select/all/area?city=广州市&pageNum=1&pageSize=10
	 */
	@RequestMapping(value = "/select/all/area", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectAllAreaByCity(
			@RequestParam(required = true,value = "city") String city,
			@RequestParam(defaultValue="1") Integer pageNum,
			@RequestParam(defaultValue="10") Integer pageSize) throws Exception {
		try {
			return service.selectAllAreaByCity(city,pageNum,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	/**
	 * 查看所有城市
	 * https://ingcart.com/select/all/city
	 */
	@RequestMapping(value = "/select/all/city", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectAllCity() throws Exception {
		try {
			return service.selectAllCity();
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	
	/**
	 * 查看维修列表
	 * https://ingcart.com/select/maintenance/condition?pageNum=1&pageSize=10
	 */
	@RequestMapping(value = "/select/maintenance/condition", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectMaintennanceCondition(			@RequestParam(defaultValue="1") Integer pageNum,
			@RequestParam(defaultValue="10") Integer pageSize) throws Exception {
		try {
			return service.selectMaintennanceCondition(pageNum,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	/**
	 * 修改维修车辆的寻找状态
	 * https://ingcart.com/update/maintenance/status?id=1
	 */
	@RequestMapping(value = "/update/maintenance/status", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult updateMaintenanceStatus(@RequestParam(required = true,value = "id") Long id) throws Exception {
		try {
			return service.updateMaintenanceStatus(id);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	
	/**
	 * 解修
	 * https://ingcart.com/delete/maintenance/status?qrId=1
	 */
	@RequestMapping(value = "/delete/maintenance/status", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult deleteMaintenanceStatus(@RequestParam(required = true,value = "qrId") Long qrId) throws Exception {
		try {
			return service.deleteMaintenanceStatus(qrId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	
	/**
	 * 查看园区收费等级
	 */
	@RequestMapping(value = "/select/level", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectPriceLevel() throws Exception {
		try {
			return service.selectPriceLevel();
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	
	/**
	 * 管理员对特定用户账户清零
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/manage/account/zeroClean", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult zeroCleanAccount(@RequestBody String parameters) throws Exception {
		try {
			return service.zeroCleanAccount(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
}
