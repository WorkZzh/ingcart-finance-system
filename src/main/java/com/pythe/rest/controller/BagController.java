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
import com.pythe.rest.service.MaintenanceService;

@Controller
public class BagController {

	@Autowired  
    private BagService service;  
	@Autowired  
    private ActivityService activityService;
	
      
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
	
	
	@RequestMapping(value = "/select/store/bag", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectStoreBag(@RequestParam(required = true,value = "code") String code ) {
		try {
			return service.selectStoreBag(code);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping(value = "/delete/store", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult deleteBagStore(@RequestParam(required = true,value = "code") String code ) {
		try {
			return service.deleteBagStore(code);
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
	
	
	@RequestMapping(value = "/insert/store/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult insertStore(@RequestBody String parameters) throws Exception {
		try {
			return service.insertStore(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	@RequestMapping(value = "/update/store/goods/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult updateStoreGoods(@RequestBody String parameters) throws Exception {
		try {
			return service.updateStoreGoods(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
    /**
	 *布套
	 * @return
	 */
	@RequestMapping(value = "/bag", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView bag(@RequestParam(required = true,value = "id") String id) throws Exception {
		ModelAndView mv = new ModelAndView(new RedirectView("https://wx.ingcart.com/source/todownload.html"));
		return mv;
	}
	
	/**
	 * 查看所有门店
	 * https://ingcart.com/select/all/store
	 */
	@RequestMapping(value = "/select/all/store", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectAllStore() throws Exception {
		try {
			return service.selectAllStore();
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	
  
}
