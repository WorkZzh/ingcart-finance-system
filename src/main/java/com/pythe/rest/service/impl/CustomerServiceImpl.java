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

	@Value("${INGCART_MANAGE_MNP_APPID}")
	private String INGCART_MANAGE_MNP_APPID;

	@Value("${INGCART_MANAGE_MNP_APPSECRET}")
	private String INGCART_MANAGE_MNP_APPSECRET;

	@Value("${WX_KEY}")
	private String WX_KEY;

	@Value("${WX_MCH_ID}")
	private String WX_MCH_ID;

	@Value("${INGCART_CUSTOMER_TYPE}")
	private Integer INGCART_CUSTOMER_TYPE;

	@Value("${INGCART_MANAGER_TYPE}")
	private Integer INGCART_MANAGER_TYPE;

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
		String phoneNum = customerInformation.getString("phoneNum").trim();
		Integer type = customerInformation.getInteger("type");
		if (null!=customerInformation.getString("verificationCode")) {
			String verificationCode = customerInformation.getString("verificationCode");

			TblVerificationExample example = new TblVerificationExample();
			example.createCriteria().andPhoneNumEqualTo(phoneNum)
					.andTimeGreaterThanOrEqualTo(new DateTime().minusMinutes(1).toDate());
			List<TblVerification> verificationList = verificationMapper.selectByExample(example);
			// 验证码对象不存在：是因为之前没有插入或者超时导致的
			if (verificationList.size() == 0) {
				return PytheResult.build(400, "验证码错误或过期");
			}

			TblVerification verification_info = verificationList.get(0);
			// 验证码不正确
			if (!verification_info.getVerificationCode().equals(verificationCode)) {
				return PytheResult.build(400, "验证码错误或过期");
			}
		}
		
		
		String openId = customerInformation.getString("openId");
		String unionId = customerInformation.getString("unionId");
		
		VCustomerExample example5 = new VCustomerExample();
		example5.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<VCustomer> customerList2 = vCustomerMapper.selectByExample(example5);
		
		// 如果用户不存在
		if (customerList2.isEmpty()) {
			// 允许注册
			TblCustomer newCustomer = new TblCustomer();
			newCustomer.setUnionId(unionId);
			newCustomer.setXcxOpenId(openId);
			newCustomer.setOpenId(FactoryUtils.getUUID());
			newCustomer.setLevel(0);
			newCustomer.setName(phoneNum.substring(0, 3) + "••••" + phoneNum.substring(7));
			newCustomer.setPhoneNum(phoneNum);
			newCustomer.setCreated(new Date());
			newCustomer.setType(type);
			customerMapper.insert(newCustomer);

			VCustomerExample example2 = new VCustomerExample();
			example2.createCriteria().andPhoneNumEqualTo(phoneNum);
			List<VCustomer> customers = vCustomerMapper.selectByExample(example2);
			VCustomer customer = customers.get(0);

			TblAccount newAccount = new TblAccount();
			newAccount.setCustomerId(customer.getCustomerId());
			newAccount.setAmount(0d);
			newAccount.setLevel(0);
			newAccount.setInAmount(0d);
			newAccount.setOutAmount(0d);
			newAccount.setGivingAmount(0d);
			accountMapper.insert(newAccount);
			
			return PytheResult.ok(customer);
			// return PytheResult.build(202, "抱歉，该小程序暂供管理员使用，如需使用请下载APP");
		}
		
		//用户存在
		VCustomer customer = customerList2.get(0);

		//说明在APP和管理员工具上注册过，若需要免密码登录，需要加上xcxopenId
		//如果存在都会去更新openId
//			if (customer.getLevel()>=1 || customer.getType()>=1  ) {
//				TblCustomer record = new TblCustomer();
//				record.setXcxOpenId(openId);
//				TblCustomerExample example3 = new TblCustomerExample();
//				example3.createCriteria().andPhoneNumEqualTo(phoneNum);
//				customerMapper.updateByExampleSelective(record, example3);
//			}
		
			TblCustomer record = new TblCustomer();
			record.setXcxOpenId(openId);
			TblCustomerExample example3 = new TblCustomerExample();
			example3.createCriteria().andPhoneNumEqualTo(phoneNum);
			customerMapper.updateByExampleSelective(record, example3);
		
			return PytheResult.ok(customer);
	}

	@Override
	public PytheResult registerCheck(String parameters) {
		JSONObject customerInformation = JSONObject.parseObject(parameters);
		Integer type = customerInformation.getInteger("type");
		VCustomerExample vCustomerExample = new VCustomerExample();
		String openId = customerInformation.getString("openId");
		vCustomerExample.createCriteria().andXcxOpenIdEqualTo(openId);
		// if (type == 0) {
		// }
		// else {
		// String phonenum = customerInformation.getString("phoneNum").trim();
		// vCustomerExample.createCriteria().andPhoneNumEqualTo(phonenum);
		// }
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
			//System.out.println("====================================> normal update !!!");
			return PytheResult.ok(customerList.get(0));
		}
		return PytheResult.build(400, "该用户不存在");
	}

	@Override
	public PytheResult wxSessionRequest(String code, Integer userType) {
		String url = null;
		if (userType.equals(INGCART_MANAGER_TYPE)) {
			url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + INGCART_MANAGE_MNP_APPID + "&secret="
					+ INGCART_MANAGE_MNP_APPSECRET + "&js_code=" + code + "&grant_type=authorization_code";
		}
		if (userType.equals(INGCART_CUSTOMER_TYPE)) {
			url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WX_APPID + "&secret=" + WX_APPSECRET
					+ "&js_code=" + code + "&grant_type=authorization_code";
		}
		//System.out.println("========================================> js_code !!! " + url);
		String prePayResult = HttpClientUtil.doGet(url, null);
		//System.out.println("========================================> get openid !!! " + prePayResult);
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
	public PytheResult selectCustomerByPhoneNum(String parameters) {
		// TODO Auto-generated method stub
		JSONObject params = JSONObject.parseObject(parameters);
		String phoneNum = params.getString("phoneNum").trim();
		VCustomerExample example= new VCustomerExample();
	
		if (phoneNum.length() == 11) {
			example.createCriteria().andPhoneNumEqualTo(phoneNum);
		} else {
			example.createCriteria().andQrIdEqualTo(Long.valueOf(phoneNum));
		}

		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);

		// System.out.println("===========>customerList"+ customerList.size());
		if (customerList.isEmpty()) {
			return PytheResult.build(400, "该手机号用户不存在");
		}

		VCustomer customer = customerList.get(0);
		if (customer.getStartTime() == null) {
			return PytheResult.build(400, "该车已经成功结束计费，不需重复");
		}

		JSONObject object = new JSONObject();
		object.put("start_time", DateUtils.formatTime(customer.getStartTime()));

		return PytheResult.ok(object);
	}

	@Override
	public PytheResult registerByManager(String parameters) {
		// 用户
		JSONObject customerInformation = JSONObject.parseObject(parameters);
		String phoneNum = customerInformation.getString("phoneNum").trim();
		Integer type = customerInformation.getInteger("type");
		
		if (null!=customerInformation.getString("verificationCode")) {
			String verificationCode = customerInformation.getString("verificationCode");
			TblVerificationExample example = new TblVerificationExample();
			example.createCriteria().andPhoneNumEqualTo(phoneNum)
					.andTimeGreaterThanOrEqualTo(new DateTime().minusMinutes(1).toDate());
			List<TblVerification> verificationList = verificationMapper.selectByExample(example);
			// 验证码对象不存在：是因为之前没有插入或者超时导致的
			if (verificationList.size() == 0) {
				return PytheResult.build(400, "验证码错误或过期");
			}

			TblVerification verification_info = verificationList.get(0);
			// 验证码不正确
			if (!verification_info.getVerificationCode().equals(verificationCode)) {
				return PytheResult.build(400, "验证码错误或过期");
			}
		}

		String openId = null;

		// 判断用户是否存在
		openId = customerInformation.getString("openId");
		//unionId = customerInformation.getString("unionId");
		VCustomerExample example5 = new VCustomerExample();
		example5.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<VCustomer> customerList2 = vCustomerMapper.selectByExample(example5);

		// 用户不存在或者权限不够
		if (customerList2.isEmpty()) {
			return PytheResult.build(400, "仅供管理员，如需使用请换用婴咖出行小程序");
		}

		VCustomer customer = customerList2.get(0);

		// 是否权限符合
		if (customer.getLevel() >= 1) {
			TblCustomer record = new TblCustomer();
			record.setOpenId(openId);
			record.setCreated(new Date());
			TblCustomerExample example3 = new TblCustomerExample();
			example3.createCriteria().andPhoneNumEqualTo(phoneNum);
			customerMapper.updateByExampleSelective(record, example3);

			TblAccount account = accountMapper.selectByPrimaryKey(customer.getCustomerId());
			//分为两种验证情况
			if (account!=null) {
				account.setAmount(0d);
				account.setInAmount(0d);
				account.setLevel(customer.getLevel());
				accountMapper.updateByPrimaryKey(account);
				return PytheResult.ok("管理员验证成功");
			}
			TblAccount newAccount = new TblAccount();
			newAccount.setCustomerId(customerList2.get(0).getCustomerId());
			newAccount.setAmount(0d);
			newAccount.setLevel(customer.getLevel());
			newAccount.setInAmount(0d);
			newAccount.setOutAmount(0d);
			newAccount.setGivingAmount(0d);
			accountMapper.insert(newAccount);
			return PytheResult.ok("管理员验证成功");
		} else {
			return PytheResult.build(400, "仅供管理员，如需使用请换用婴咖出行小程序");
		}
	}

	@Override
	public PytheResult registerCheckByManger(String parameters) {
		JSONObject Information = JSONObject.parseObject(parameters);
		Integer type = Information.getInteger("type");
		VCustomerExample vCustomerExample = new VCustomerExample();
		String openId = Information.getString("openId");
		vCustomerExample.createCriteria().andOpenIdEqualTo(openId).andLevelGreaterThanOrEqualTo(1);
		List<VCustomer> customers = vCustomerMapper.selectByExample(vCustomerExample);
		// 返回用户数据
		if (customers.isEmpty()) {
			return PytheResult.build(400, "尚无该管理员信息");
		} else {
			return PytheResult.ok(customers.get(0));
		}
	}

	@Override
	public PytheResult Loginout(String parameters) {
		// TODO Auto-generated method stub
		JSONObject Information = JSONObject.parseObject(parameters);
		String phoneNum = Information.getString("phoneNum");
		TblCustomerExample example =new TblCustomerExample();
		example.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<TblCustomer> list = customerMapper.selectByExample(example);
		
		TblCustomer customer = list.get(0);
		
		customer.setXcxOpenId(null);
		customerMapper.updateByPrimaryKey(customer);
		return PytheResult.ok("退出成功");
	}

	

}
