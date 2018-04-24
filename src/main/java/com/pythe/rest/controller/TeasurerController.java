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
import com.pythe.rest.service.TeasurerService;

@Controller
public class TeasurerController {
	@Autowired
	private TeasurerService teasurerService;

	@RequestMapping(value = "/teasurer/add/one/level", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult insertLevelOneTeasurer(@RequestBody String parameters) throws Exception {
		try {
			return teasurerService.insertLevelOneTeasurer(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/teasurer/add/two/level", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult insertLevelTwoTeasurer(@RequestBody String parameters) throws Exception {
		try {
			return teasurerService.insertLevelTwoTeasurer(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/teasurer/delete", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult deleteTeasurer(@RequestBody String parameters) throws Exception {
		try {
			return teasurerService.deleteTeasurer(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/teasurer/list", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult selectTeasurerList(@RequestBody String parameters) throws Exception {
		try {
			return teasurerService.selectTeasurerList(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/teasurer/login/password", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult loginByPassowrd(@RequestBody String parameters) throws Exception {
		try {
			return teasurerService.loginByPassowrd(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/teasurer/login/message", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult loginByMessage(@RequestBody String parameters) throws Exception {
		try {
			return teasurerService.loginByMessage(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/teasurer/update/password", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult updatePassword(@RequestBody String parameters) throws Exception {
		try {
			return teasurerService.updatePassword(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/teasurer/rocord/list", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult selectTeasurerRocordList(@RequestBody String parameters) throws Exception {
		try {
			return teasurerService.selectTeasurerRocordList(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value = "/teasurer/select/id", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectTeasurerTwoLevel(@RequestParam(required = true, value = "id") Long id) {
		try {
			return teasurerService.selectTeasurerById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping(value = "/teasurer/select/cars/level", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectCarsByLevel(@RequestParam(required = true, value = "level") String level,
			@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
		try {
			return teasurerService.selectCarsByLevel(level,pageNum,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
