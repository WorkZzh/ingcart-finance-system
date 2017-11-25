package com.pythe.rest.controller;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.ExceptionUtil;
import com.pythe.rest.service.MessageService;





@Controller
public class MessageController {  
	
	@Autowired  
    private MessageService service;  
      
    
    /**
	 *发送一条短信
	 * @return
	 */
	@RequestMapping(value = "/message/verification", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult singleSend(@RequestBody String parameters) throws Exception {
		System.out.println("mobile" + parameters);
		parameters = URLDecoder.decode(parameters, "UTF-8");

		try {
			return service.singleSend(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
    
    
}  
