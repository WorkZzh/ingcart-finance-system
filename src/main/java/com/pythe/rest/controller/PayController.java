package com.pythe.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	 *微信确认
	 * @return
	 */
	@RequestMapping(value = "/account/wxChargeConfirm", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult wxChargeConfirm(@RequestBody String parameters) throws Exception {

		try {
			return service.wxChargeConfirm(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
    
    
}  
