package com.pythe.rest.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.common.utils.HttpClientUtil;
import com.pythe.common.utils.JsonUtils;
import com.pythe.common.utils.Xml2JsonUtil;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblCustomerMapper;
import com.pythe.mapper.TblSessionMapper;
import com.pythe.mapper.VCustomerMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblCustomer;
import com.pythe.pojo.TblCustomerExample;
import com.pythe.pojo.TblSession;
import com.pythe.pojo.VCustomer;
import com.pythe.pojo.VCustomerExample;
import com.pythe.rest.service.CustomerService;


import org.joda.time.DateTime;

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

	@Override
	public PytheResult register(String parameters) {
		
		// 用户
		JSONObject customerInformation = JSONObject.parseObject(parameters);
		
		String openId = customerInformation.getString("openId").trim();
		String unionId = customerInformation.getString("unionId").trim();
		String name = customerInformation.getString("name").trim();
		String phoneNum = customerInformation.getString("phoneNum").trim();
		String verificationCode = customerInformation.getString("verificationCode").trim();
		
		TblCustomer newCustomer = new TblCustomer();
		newCustomer.setOpenId(openId);
		newCustomer.setUnionId(unionId);
		newCustomer.setName(name);
		newCustomer.setPhoneNum(phoneNum);
		customerMapper.insert(newCustomer);
		
		TblAccount newAccount = new TblAccount();
		newAccount.setCustomerId(newCustomer.getId());
		newAccount.setAmount(0d);
		newAccount.setLevel(0);
		newAccount.setInAmount(0d);
		newAccount.setOutAmount(0d);
		accountMapper.insert(newAccount);
		
		customerMapper.updateByPrimaryKey(newCustomer);
		
		VCustomerExample vCustomerExample = new VCustomerExample();
		vCustomerExample.createCriteria().andCustomerIdEqualTo(newCustomer.getId());
		List<VCustomer> customers = vCustomerMapper.selectByExample(vCustomerExample);
		
		return PytheResult.ok(customers.get(0));
	}

	@Override
	public PytheResult registerCheck(String parameters) {

		JSONObject customerInformation = JSONObject.parseObject(parameters);
		
		String openId = customerInformation.getString("openId").trim();
		String unionId = customerInformation.getString("unionId").trim();
		
		VCustomerExample vCustomerExample = new VCustomerExample();
		vCustomerExample.createCriteria().andOpenIdEqualTo(openId).andUnionIdEqualTo(unionId);
		List<VCustomer> customers = vCustomerMapper.selectByExample(vCustomerExample);
		if(customers.isEmpty())
		{
			return PytheResult.build(400, "尚未有此客户信息");
		}
		else
		{
			return PytheResult.ok(customers.get(0));
		}
		
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
		packet.put("session_id",session_id);
		packet.put("openid",openid);
		packet.put("session_key",session_key);
		// System.out.println(prePayResult);
		// {"status":200,"msg":"OK","data":"
		// {\"session_key\":\"G+m8VhyQqd6xZvHB0xPCHA==\",\"expires_in\":7200,\"openid\":\"oU6Xr0Iddiof1I7YFsioFTkwmJiU\"}"}
		return PytheResult.ok(packet);
	}

	@Override
	public PytheResult selectPersonalImformationByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		VCustomerExample example = new VCustomerExample();
		example.createCriteria().andCustomerIdEqualTo(customerId);
		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		if (!customerList.isEmpty()) {
			return PytheResult.ok(customerList.get(0));
		}
		
		return PytheResult.build(400, "该用户不存在");
		
	}


}
