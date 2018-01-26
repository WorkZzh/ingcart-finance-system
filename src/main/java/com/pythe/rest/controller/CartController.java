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
import com.pythe.rest.service.CartService;


@Controller
public class CartController {

	
	@Autowired
	private CartService service;
	
    /**
	 *开锁检测
	 * @return
	 * localhost:8084/rest/unlock/prepare?customerId=9&carId=C8:FD:19:92:17:29
	 */
    @RequestMapping(value = "/unlock/prepare", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult prepareUnlock(
			@RequestParam(required = true,value = "customerId") Long customerId,
			@RequestParam(required = true,value = "carId") String carId
			) throws Exception {
		try {
			return service.prepareUnlock(customerId,carId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
    
    
    
    /**
	 *开锁检测_IOS
	 * @return
	 * localhost:8084/rest/unlock/prepare?customerId=9&carId=C8:FD:19:92:17:29
	 */
    @RequestMapping(value = "/qr/unlock/prepare", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult prepareUnlockGyQrId(
			@RequestParam(required = true,value = "customerId") Long customerId,
			@RequestParam(required = true,value = "qrId") Long qrId
			) throws Exception {
		try {
			return service.prepareUnlockGyQrId(customerId,qrId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
    
    /**
	 *开锁
	 * @return
	 */
	@RequestMapping(value = "/unlock", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView outsideUnlock(@RequestParam(required = true,value = "id") Long id) throws Exception {
		ModelAndView mv = new ModelAndView(new RedirectView("https://wx.ingcart.com/source/todownload.html"));
		//RedirectView rv = new RedirectView("https://wx.ingcart.com/source/todownload.html");
		//RedirectView rv = new RedirectView("https://wx.ingcart.com/source/todownload.html");
		return mv;
	}
	
    /**
	 *开锁
	 * @return
	 */
	@RequestMapping(value = "/use/unlock", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult unlock(@RequestBody String parameters) throws Exception {
		try {
			return service.unlock(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	
	
    /**
	 *关锁
	 */
	@RequestMapping(value = "/use/lock", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult lock(@RequestBody String parameters) throws Exception {
		try {
			return service.lock(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	/**
	 * 开锁失败后，需要重置车的状态并删除记录
	 */
	@RequestMapping(value = "/unlock/false/reset", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult unlockFalseReset(@RequestBody String parameters) throws Exception {
		try {
			return service.unlockFalseReset(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
    /**
	 *管理员关锁
	 */
	@RequestMapping(value = "/manager/lock", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult managerLock(@RequestBody String parameters) throws Exception {
		try {
			return service.managerLock(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
    /**
	 *确认结算
	 */
	@RequestMapping(value = "/use/computeFee", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult computeFee(@RequestBody String parameters) throws Exception {
		try {
			return service.computeFee(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
    /**
	 *保留车辆
	 * @return
	 */
	@RequestMapping(value = "/use/hold", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult holdCartByCustomerId(@RequestBody String parameters) throws Exception {

		try {
			return service.holdCartByCustomerId(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	
	//0为可用，1为已用，2为过时，3为取消
    @RequestMapping(value = "/coupon/select", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectCouponByCustomerId(	
			@RequestParam(required = true,value = "customerId") Long customerId
			){
    	try {
			return service.selectCouponByCustomerId(customerId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
    
    
    
    /**
	 *显示共享车位置
	 */
    @RequestMapping(value = "/map/show", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectCartPositionByMap
	(@RequestParam(required = true,value = "longitude") Double longitude,
			@RequestParam(required = true,value = "latitude") Double latitude
			) {
		try {
			return service.selectCartPositionByMap(longitude,latitude);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
    
    
    
    /**
	 *显示保留的剩余时间,时间够会自动返回400结束
	 * @return
	 */
    @RequestMapping(value = "/save/time", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectSaveRestTimeByCustomerId
	(@RequestParam(required = true,value = "customerId") Long customerId
			) {
		try {
			return service.selectSaveRestTimeByCustomerId(customerId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
    
    /**
	 *取消预约
	 * @return
	 */
    @RequestMapping(value = "/cancel/appointment", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult deleteAppointmentByCustomerId
	(@RequestParam(required = true,value = "customerId") Long customerId
			) {
		try {
			return service.deleteAppointmentByCustomerId(customerId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
    
    
    /**
	 *显示使用的时间
	 * @return
	 */
    @RequestMapping(value = "/use/car/time", method = RequestMethod.GET)
	@ResponseBody
	public PytheResult selectUseCarTimeByCarId
	(@RequestParam(required = true,value = "carId") String carId
			) {
		try {
			return service.selectUseCarTimeByCarId(carId);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
    
    /**
     * 开锁加密
     */
	@RequestMapping(value = "/unlock/encode", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult unLockEncodeByCartId(@RequestBody String parameters) throws Exception {

		try {
			return service.unLockEncodeByCartId(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	/**
	 *记录车锁的设备UUID
	 * @return
	 */
	@RequestMapping(value = "/record/deviceInfo", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult recordDeviceInfo(@RequestBody String parameters) throws Exception {
		try {
			return service.recordDeviceInfo(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

//    /**
//	 *解密
//	 * @return
//	 */
//    @RequestMapping(value = "/decode", method = RequestMethod.GET)
//	@ResponseBody
//	public PytheResult decode
//	(@RequestParam(required = true,value = "code") String code
//			) {
//
//		try {
//			return service.decode(code);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
//		}
//	}
//    
    
	
	/**
	 *二维码ID换MAC
	 * @return
	 */
	@RequestMapping(value = "/use/QR2MAC", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult qrToMac(@RequestBody String parameters) throws Exception {
		try {
			return service.qrToMac(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
	
	/**
	 *通过Mac来得到密钥
	 * @return
	 */
	@RequestMapping(value = "/switch/key", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult macSwitchKey(@RequestBody String parameters) throws Exception {
		try {
			return service.macSwitchKey(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
    /**
	 *蓝牙通信加密
	 * @return
	 */
	@RequestMapping(value = "/bluetooth/encrypt", method = RequestMethod.POST)
	@ResponseBody
	public String bluetoothEncrypt(@RequestBody String parameter) throws Exception {
		

		//System.out.println("==========================>bluetooth: " + parameter);
		String encryptedStr = service.bluetoothEncrypt(parameter);
		//System.out.println("==========================>bluetooth encrypt: " + encryptedStr);
		
		return encryptedStr;
		
	}
	

	
	
	
	
	/**
	 *
	 * @return
	 */
	@RequestMapping(value = "/bluetooth/decrypt", method = RequestMethod.POST)
	@ResponseBody
	public String bluetoothDecrypt(@RequestBody String parameter) throws Exception {

		//System.out.println("==========================>bluetooth: " + parameter);
		String decryptedStr = service.bluetoothDecrypt(parameter);
		//System.out.println("==========================>bluetooth decrypt: " + decryptedStr);
		
		return decryptedStr;	
	}
	
	
	
	
	
	
	
	
	
}
