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
	 * 创建新的版本 localhost:8084/rest/update/version
	 */
	@RequestMapping(value = "/update/version", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult updateVersion(@RequestBody String parameters) {
		try {
			return service.updateVersion(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 统计车的使用情况 localhost:8084/count/car/condition
	 */
	@RequestMapping(value = "/count/car/condition", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult countCarCondition(@RequestParam(defaultValue = "0") String level,
			@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
		try {
			return service.countCarCondition(level, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 统计车的使用情况 查看一级目录 BUSINESS 为运营 ，TEASURER 为财务
	 */
	@RequestMapping(value = "/select/one/level", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectOneLevel(@RequestParam(required = true, value = "managerId") Long managerId) {
		try {
			return service.selectOneLevel(managerId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 财务一级列表
	 */
	@RequestMapping(value = "/teasurer/one/level", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectTeasurerOneLevel(@RequestParam(required = true, value = "managerId") Long managerId) {
		try {
			return service.selectTeasurerOneLevel(managerId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 二级 level为1时候，会显示2个，所以需要加多一个level字段
	 */
	@RequestMapping(value = "/teasurer/two/level", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectTeasurerTwoLevel(@RequestParam(required = true, value = "c1_id") String c1_id,
			@RequestParam(required = true, value = "level") Integer level,
			@RequestParam(required = true, value = "catalog_id") String catalog_id) {
		try {
			return service.selectTeasurerTwoLevel(c1_id, level, catalog_id);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 二级
	 */
	@RequestMapping(value = "/select/two/level", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectTwoLevel(@RequestParam(required = true, value = "c1_id") String c1_id,
			@RequestParam(required = true, value = "level") Integer level,
			@RequestParam(required = true, value = "catalog_id") String catalog_id) {
		try {
			return service.selectTwoLevel(c1_id, level, catalog_id);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 创建新的版本 localhost:8084/rest/select/version
	 */
	@RequestMapping(value = "/select/version", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult selectVersion(@RequestBody String parameters) {
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
	 * 给集团添加旗下的景区
	 * 
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
	 * 创建集团
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert/group/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult insertGroup(@RequestBody String parameters) throws Exception {
		try {
			return service.insertGroup(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 更新车的位置
	 * 
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
	 * 插入一个婴咖管理（需要变成4级管理的等级）
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert/ingcart/manage", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult insertIngcartManage(@RequestBody String parameters) throws Exception {
		try {
			return service.insertIngcartManage(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 插入一个运营专员（需要变成1级管理的等级）
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert/operator/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult insertOperator(@RequestBody String parameters) throws Exception {
		try {
			return service.insertOperator(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 插入一个运营管理者（需要变成2级管理的等级）
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert/operator/mange/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult insertOperatorManager(@RequestBody String parameters) throws Exception {
		try {
			return service.insertOperatorManager(parameters);
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

	/**
	 * 查看某个城市，所有景区
	 * https://ingcart.com/select/all/area?city=广州市&pageNum=1&pageSize=10
	 */
	@RequestMapping(value = "/select/all/area", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectAllAreaByCity(@RequestParam(required = true, value = "city") String city,
			@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize)
			throws Exception {
		try {
			return service.selectAllAreaByCity(city, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 查看所有城市 https://ingcart.com/select/all/city
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
	public PytheResult selectMaintennanceCondition(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize) throws Exception {
		try {
			return service.selectMaintennanceCondition(pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 修改维修车辆的寻找状态 https://ingcart.com/update/maintenance/status?id=1
	 */
	@RequestMapping(value = "/update/maintenance/status", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult updateMaintenanceStatus(@RequestParam(required = true, value = "id") Long id) throws Exception {
		try {
			return service.updateMaintenanceStatus(id);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 解修 https://ingcart.com/delete/maintenance/status?qrId=1
	 */
	@RequestMapping(value = "/delete/maintenance/status", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult deleteMaintenanceStatus(@RequestParam(required = true, value = "qrId") Long qrId)
			throws Exception {
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
	 * 
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

	@RequestMapping(value = "/record/bill/query", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult queryRecordBill(@RequestBody String parameters) throws Exception {
		try {
			return service.queryRecordBill(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	// 按各种维度查看消费总额
	@RequestMapping(value = "/select/bill/sum", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult selectSumByTime(@RequestBody String parameters) {
		try {
			return service.selectSumByTime(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/record/bill/query/times", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult queryRecordBillByTimes(@RequestBody String parameters) throws Exception {
		try {
			return service.queryRecordBillByTimes(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/select/bill/sum/times", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult selectSumByTimes(@RequestBody String parameters) {
		try {
			return service.selectSumByTimes(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/record/bill/download/time", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult downloadByTime(@RequestBody String parameters) throws Exception {
		try {
			return service.downloadByTime(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/select/bill/sum/year", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult selectSumByYear(@RequestBody String parameters) {
		try {
			return service.selectSumByYear(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 查看运营人员的使用情况
	 */
	@RequestMapping(value = "/select/operator/condition", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectOperatorCondition(@RequestParam(required = true, value = "level") String level,
			@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize)
			throws Exception {
		try {
			return service.selectOperatorCondition(level, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	/**
	 * 查询人员添加记录
	 */
	@RequestMapping(value = "/select/add/record", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectAddOperatorRecord(@RequestParam(required = true, value = "level") String level,
			@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize)
			throws Exception {
		try {
			return service.selectAddOperatorRecord(level,pageNum,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 删除管理人员
	 */
	@RequestMapping(value = "/delete/manager", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult deleteOperator(@RequestBody String parameters) {
		try {
			return service.deleteOperator(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
