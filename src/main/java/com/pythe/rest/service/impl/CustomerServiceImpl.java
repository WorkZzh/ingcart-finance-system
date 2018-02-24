package com.pythe.rest.service.impl;

import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.DateUtils;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.common.utils.HttpClientUtil;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblCouponMapper;
import com.pythe.mapper.TblCustomerMapper;
import com.pythe.mapper.TblSessionMapper;
import com.pythe.mapper.TblVerificationMapper;
import com.pythe.mapper.VCustomerMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblCoupon;
import com.pythe.pojo.TblCustomer;
import com.pythe.pojo.TblCustomerExample;
import com.pythe.pojo.TblSession;
import com.pythe.pojo.TblVerification;
import com.pythe.pojo.TblVerificationExample;
import com.pythe.pojo.VCustomer;
import com.pythe.pojo.VCustomerExample;
import com.pythe.rest.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Value("${WX_APPID}")
	private String WX_APPID;

	@Value("${WX_APPSECRET}")
	private String WX_APPSECRET;

	@Value("${WX_KEY}")
	private String WX_KEY;

	@Value("${WX_MCH_ID}")
	private String WX_MCH_ID;

	@Autowired
	private TblCustomerMapper customerMapper;

	@Autowired
	private TblAccountMapper accountMapper;

	@Autowired
	private TblSessionMapper sessionMapper;

	@Autowired
	private VCustomerMapper vCustomerMapper;

	@Autowired
	private TblCouponMapper couponMapper;

	@Autowired
	private TblVerificationMapper verificationMapper;

	@Override
	public PytheResult register(String parameters) {

		// 用户
		JSONObject customerInformation = JSONObject.parseObject(parameters);
		String verificationCode = customerInformation.getString("verificationCode");
		Integer type = customerInformation.getInteger("type");
		String phoneNum = customerInformation.getString("phoneNum").trim();
		
		TblVerificationExample example = new TblVerificationExample();
		example.createCriteria().andPhoneNumEqualTo(phoneNum)
				.andTimeGreaterThanOrEqualTo(new DateTime().minusMinutes(1).toDate());
		List<TblVerification> verificationList = verificationMapper.selectByExample(example);
		// 验证码对象不存在：是因为之前没有插入或者超时导致的
		if (verificationList.size() == 0) {
			return PytheResult.build(400, "验证码错误或过期");
		}

		TblVerification verification_info = verificationList.get(0);
		//验证码不正确
		if (!verification_info.getVerificationCode().equals(verificationCode)) {
			return PytheResult.build(400, "验证码错误或过期");
		}
		
		String openId = null;
		String unionId = null;
		if (type == 0) {
			openId = customerInformation.getString("openId");
			unionId = customerInformation.getString("unionId");
			VCustomerExample example5 = new VCustomerExample();
			example5.createCriteria().andPhoneNumEqualTo(phoneNum).andLevelEqualTo(1);
			List<VCustomer> customerList2 = vCustomerMapper.selectByExample(example5);
			if (customerList2.isEmpty()) {
				return PytheResult.build(202, "抱歉，小程序暂供管理员使用，如需使用请下载APP");
			}
			
//			//避免小程序再次进入的情况
//			VCustomer manager = customerList2.get(0);
//			if (null!=manager.getOpenId()) {
//				return PytheResult.ok("管理员验证成功");
//			}
			
			TblCustomer record = new TblCustomer();
			record.setOpenId(openId);
			record.setCreated(new Date());
			TblCustomerExample example3 = new TblCustomerExample();
			example3.createCriteria().andPhoneNumEqualTo(phoneNum);
			customerMapper.updateByExampleSelective(record, example3);
			
			TblAccount newAccount = new TblAccount();
			newAccount.setCustomerId(customerList2.get(0).getCustomerId());
			newAccount.setAmount(10000d);
			newAccount.setLevel(1);
			newAccount.setInAmount(10000d);
			newAccount.setOutAmount(0d);
			newAccount.setGivingAmount(0d);
			accountMapper.insert(newAccount);
			return PytheResult.ok("管理员验证成功");
		}else{
			openId = FactoryUtils.getUUID();
		}
		
		//非管理员的验证流程
		VCustomerExample example2 = new VCustomerExample();
		example2.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<VCustomer> customerList = vCustomerMapper.selectByExample(example2);
		
		//管理员临时使用逻辑，获得管理员权限
		if (!customerList.isEmpty()) {
			VCustomer tmpCustomer = customerList.get(0);
//			if (tmpCustomer.getType().equals(0)) {
//				return PytheResult.build(400, "该用户已为管理员，请换用微信小程序使用");
//			}
			return PytheResult.ok(tmpCustomer);
		} 
		
		TblCustomer newCustomer = new TblCustomer();
		newCustomer.setUnionId(unionId);
		newCustomer.setLevel(0);
		newCustomer.setOpenId(openId);
		newCustomer.setName(phoneNum.substring(0, 3) + "••••" + phoneNum.substring(7));
		newCustomer.setPhoneNum(phoneNum);
		newCustomer.setCreated(new Date());
		newCustomer.setType(type);
		customerMapper.insert(newCustomer);
		

		
		
		List<VCustomer> customers = vCustomerMapper.selectByExample(example2);
		VCustomer customer = customers.get(0);
		customer.setAmount(0.0);
		
		
		TblAccount newAccount = new TblAccount();
		newAccount.setCustomerId(customer.getCustomerId());
		newAccount.setAmount(8d);
		newAccount.setLevel(0);
		newAccount.setInAmount(8d);
		newAccount.setOutAmount(0d);
		newAccount.setGivingAmount(8d);
		accountMapper.insert(newAccount);

		return PytheResult.ok(customer);
	}

	@Override
	public PytheResult registerCheck(String parameters) {
		JSONObject customerInformation = JSONObject.parseObject(parameters);
		Integer type = customerInformation.getInteger("type");
		VCustomerExample vCustomerExample = new VCustomerExample();
		String openId = customerInformation.getString("openId");
		vCustomerExample.createCriteria().andOpenIdEqualTo(openId);
//		if (type == 0) {
//		} 
//		else {
//			String phonenum = customerInformation.getString("phoneNum").trim();
//			vCustomerExample.createCriteria().andPhoneNumEqualTo(phonenum);
//		}
		List<VCustomer> customers = vCustomerMapper.selectByExample(vCustomerExample);
		// 返回用户数据
		if (customers.isEmpty()) {
			return PytheResult.build(400, "尚无该客户信息");
		} else {
			return PytheResult.ok(customers.get(0));
		}
	}

	@Override
	public PytheResult selectPersonalImformationByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		VCustomerExample example = new VCustomerExample();
		example.createCriteria().andCustomerIdEqualTo(customerId);
		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		if (!customerList.isEmpty()) {
			System.out.println("====================================> normal update !!!");
			return PytheResult.ok(customerList.get(0));
		}
		
		return PytheResult.build(400, "该用户不存在");
		
	}

	
	
	@Override
	public PytheResult wxSessionRequest(String code) {

		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WX_APPID + "&secret=" + WX_APPSECRET
				+ "&js_code=" + code + "&grant_type=authorization_code";
		String prePayResult = HttpClientUtil.doGet(url, null);
		String session_id = FactoryUtils.getUUID();
		String session_key = JSONObject.parseObject(prePayResult).getString("session_key");
		String openid = JSONObject.parseObject(prePayResult).getString("openid");

		TblSession session = new TblSession();
		session.setSessionId(session_id);
		session.setSessionKey(session_key);
		session.setOpenId(openid);
		sessionMapper.insert(session);

		JSONObject packet = new JSONObject();
		packet.put("session_id", session_id);
		packet.put("openid", openid);
		packet.put("session_key", session_key);
		// System.out.println(prePayResult);
		// {"status":200,"msg":"OK","data":"
		// {\"session_key\":\"G+m8VhyQqd6xZvHB0xPCHA==\",\"expires_in\":7200,\"openid\":\"oU6Xr0Iddiof1I7YFsioFTkwmJiU\"}"}
		return PytheResult.ok(packet);
	}

	@Override
	public PytheResult receiveGift(String parameters) {

		JSONObject params = JSONObject.parseObject(parameters);
		Long customerId = params.getLong("customerId");
		Long couponId = params.getLong("couponId");
		String couponCode = params.getString("couponCode").trim();
		String dealerId = params.getString("dealerId");

		TblCoupon coupon = couponMapper.selectByPrimaryKey(couponId);
		if (coupon.getCode().equals(couponCode) && coupon.getCustomerId().equals(customerId)) {
			// 使用赠品券
			coupon.setDealerId(dealerId);
			coupon.setStatus(1);
			coupon.setUseTime(new Date());
			couponMapper.updateByPrimaryKey(coupon);
			return PytheResult.ok("赠品券已使用");
		} else {
			return PytheResult.build(400, "券码错误");
		}

	}

	@Override
	public PytheResult selectCustomerByPhoneNum(String parameters) {
		// TODO Auto-generated method stub
		JSONObject params = JSONObject.parseObject(parameters);
		String phoneNum = params.getString("phoneNum").trim();
		VCustomerExample vCustomerExample = new VCustomerExample();
		vCustomerExample.createCriteria().andPhoneNumEqualTo(phoneNum);
		
		List<VCustomer> customerList = vCustomerMapper.selectByExample(vCustomerExample);
		
		//System.out.println("===========>customerList"+ customerList.size());
		if (customerList.isEmpty()) {
			return PytheResult.build(400, "该手机号用户不存在");
		}
		
		VCustomer customer = customerList.get(0);
		if (customer.getStartTime()==null) {
			return PytheResult.build(400, "该车已经成功结束计费，不需重复");
		}
		
		JSONObject object =new JSONObject();
		object.put("start_time",DateUtils.formatTime(customer.getStartTime()));
		
		return PytheResult.ok(object);
	}



}
