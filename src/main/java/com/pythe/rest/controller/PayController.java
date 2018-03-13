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
import com.pythe.rest.service.PayService;





@Controller
public class PayController {  
	
	@Autowired  
    private PayService service;  
      
    
    /**
	 *充值下单
	 * @return
	 */
	@RequestMapping(value = "/account/charge", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult chargeForAccount(@RequestBody String parameters) throws Exception {

		try {
			return service.chargeForAccount(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	/**
	 *另一商户充值下单
	 * @return
	 */
	@RequestMapping(value = "/account/another", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult chargeToAnotherMerchant(@RequestBody String parameters) throws Exception {
		try {
			return service.chargeToAnotherMerchant(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping(value = "/hiddle/charge", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult hiddleCharge() {
		try {
			return service.hiddleCharge();
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	  /**
		 *APP充值下单
		 * @return
		 */
		@RequestMapping(value = "/app/account/charge", method = RequestMethod.POST)
		@ResponseBody
		public PytheResult chargeForAccountInApp(@RequestBody String parameters) throws Exception {

			try {
				return service.chargeForAccountInApp(parameters);
			} catch (Exception e) {
				e.printStackTrace();
				return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
			}
		}
		
	
    /**
	 *微信确认
	 * @return
	 */
	@RequestMapping(value = "/wx/account/wxChargeConfirm", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult wxChargeConfirm(@RequestBody String parameters) throws Exception {

		try {
			return service.wxChargeConfirm(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	/**
	 *app微信支付确认
	 * @return
	 */
	@RequestMapping(value = "/app/account/wxChargeConfirm", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult wxChargeConfirmInApp(@RequestBody String parameters) throws Exception {

		try {
			return service.wxChargeConfirmInApp(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
	/**
	 *app订单状态查询
	 * @return
	 */
	@RequestMapping(value = "/app/wxOrderQuery", method = RequestMethod.POST)
	@ResponseBody
	public String wxOrderQueryInApp(@RequestBody String parameters) throws Exception {

		try {
			return service.wxOrderQueryInApp(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
	
	/**
	 * 订单退款
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/pay/refund", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult refundByOrderInWX(@RequestBody String url) throws Exception {
	

		try {
			return service.refundByOrderInWX(url);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
}  
