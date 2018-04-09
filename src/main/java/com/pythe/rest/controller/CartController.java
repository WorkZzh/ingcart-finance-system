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
	


//    /**
//	 * 管理员紧急关锁
//	 * @return
//	 * localhost:8084/manage/urgent/lock
//	 * phoneNum=13828494261
//	 * date=2018-01-30 20:49:22
//	 */
//    @RequestMapping(value = "/manage/urgent/lock/", method = RequestMethod.POST)
//	@ResponseBody
//	public PytheResult urgentUnlock(
//			@RequestBody String parameters
//			) throws Exception {
//		try {
//			return service.urgentUnlock(parameters);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
//		}
//	}
    
    
    
    /**
	 * 管理员紧急关锁并返回用户20元：退款功能
	 * @return
	 * localhost:8084/manage/urgent/refund
	 * phoneNum=13828494261&date=2018-01-30 20:49:22
	 */
    @RequestMapping(value = "/manage/urgent/refund/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult manageUrgentRefund(
			@RequestBody String parameters
			) throws Exception {
		try {
			return service.manageUrgentRefund(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
    
//    /**
//	 * 全额退款
//	 * @return 
//	 */
//    @RequestMapping(value = "/customer/auto/refund/", method = RequestMethod.POST)
//	@ResponseBody
//	public PytheResult autoRefundByCustomer(
//			@RequestBody String parameters
//			) throws Exception {
//		try {
//			return service.autoRefundByCustomer(parameters);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
//		}
//	}
    
    
    
    
    /**
	 * 无行程，全额退款
	 * @return
	 */
    @RequestMapping(value = "/manage/auto/refund/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult refundByTopManager(
			@RequestBody String parameters
			) throws Exception {
		try {
			return service.refundByTopManager(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
    
    
    /**
	 * 用户临时关锁功能
	 * @return
	 * localhost:8084/manage/urgent/refund
	 * phoneNum=13828494261&date=2018-01-30 20:49:22
	 */
    @RequestMapping(value = "/customer/urgent/lock/", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult customerUrgentLock(
			@RequestBody String parameters
			) throws Exception {
		try {
			return service.customerUrgentLock(parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
    
    
    
    
    /**
	 *凌晨定时给用户关锁
	 * @return
	 * localhost:8084/auto/lock
	 */
    @RequestMapping(value = "/auto/lock", method = RequestMethod.GET)
	@ResponseBody
	public void autoLock() throws Exception {
			service.autoLock();
	}
    
    
//    /**
//	 * 用户紧急关锁
//	 * @return
//	 * localhost:8084/client/urgent/lock/
//	 */
//    @RequestMapping(value = "/client/urgent/lock/", method = RequestMethod.POST)
//	@ResponseBody
//	public PytheResult urgentUnlockByClient(@RequestBody String parameters) throws Exception {
//		try {
//			return service.urgentUnlockByClient(parameters);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
//		}
//	}
    
    
    /**
	 *开锁检测_IOS
	 * localhost:8084/rest/unlock/prepare?customerId=9&qrId=100001
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
    
    
//    /**
//	 *扫描跳转
//	 * @return
//	 */
//	@RequestMapping(value = "/unlock", method = RequestMethod.GET)
//	@ResponseBody
//	public ModelAndView outsideUnlock(@RequestParam(required = true,value = "id") Long id) throws Exception {
//		ModelAndView mv = new ModelAndView(new RedirectView("https://wx.ingcart.com/source/todownload.html"));
//		return mv;
//	}
	
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
	
	
	
	
	
	
	
	
	
	
//    /**
//	 *关锁
//	 */
//	@RequestMapping(value = "/use/lock", method = RequestMethod.POST)
//	@ResponseBody
//	public PytheResult lock(@RequestBody String parameters) throws Exception {
//		try {
//			return service.lock(parameters);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
//		}
//	}
	
	
    /**
	 *临时关锁保留
	 * @return
	 */
	@RequestMapping(value = "/lock/hold", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult lockHold(@RequestBody String parameters) throws Exception {
		try {
			return service.lockHold(parameters);
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
	
	
	
//    /**
//	 *管理员关锁
//	 */
//	@RequestMapping(value = "/manager/lock", method = RequestMethod.POST)
//	@ResponseBody
//	public PytheResult managerLock(@RequestBody String parameters) throws Exception {
//		try {
//			return service.managerLock(parameters);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
//		}
//	}
	
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
	(@RequestParam(required = true,value = "carId") String carId) {
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
     * 查锁状态加密
     */
	@RequestMapping(value = "/checkLockStatus/encode", method = RequestMethod.POST)
	@ResponseBody
	public PytheResult checkLockStatusEncode(@RequestBody String parameters) throws Exception {

		try {
			return service.checkLockStatusEncode(parameters);
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
	
	
	
	
	
//	@RequestMapping(value = "/customer/account", method = RequestMethod.GET)
//	@ResponseBody
//	public PytheResult updateCustomerAccount() {
//		try {
//			return service.updateCustomerAccount();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return PytheResult.build(500, ExceptionUtil.getStackTrace(e));
//		}
//	}
	
	
	
	
	
	
}
