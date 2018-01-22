package com.pythe.rest.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.common.utils.HttpClientUtil;
import com.pythe.common.utils.JsonUtils;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblBagRecordMapper;
import com.pythe.mapper.TblBillMapper;
import com.pythe.mapper.TblComboMapper;
import com.pythe.mapper.TblCustomerMapper;
import com.pythe.mapper.TblDealerMapper;
import com.pythe.mapper.TblPreferentialActivityMapper;
import com.pythe.mapper.TblStoreMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblBagRecord;
import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblBillExample;
import com.pythe.pojo.TblCombo;
import com.pythe.pojo.TblComboExample;
import com.pythe.pojo.TblCustomer;
import com.pythe.pojo.TblDealer;
import com.pythe.pojo.TblPreferentialActivity;
import com.pythe.pojo.TblPreferentialActivityExample;
import com.pythe.pojo.TblStore;
import com.pythe.pojo.TblStoreExample;
import com.pythe.rest.service.ActivityService;
import com.pythe.rest.service.BagService;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	@Value("${WX_APPID}")
	private String WX_APPID;

	@Value("${WX_APPSECRET}")
	private String WX_APPSECRET;
	
	@Value("${GIFT_NOTIFY_TEMPLATE_ID}")
	private String GIFT_NOTIFY_TEMPLATE_ID;
	
	@Autowired
	private TblStoreMapper storeMapper;

	@Autowired
	private TblBagRecordMapper bagRecordMapper;
	
	@Autowired
	private TblAccountMapper accountMapper;
	
	@Autowired
	private TblCustomerMapper customerMapper;
	
	@Autowired
	private TblBillMapper billMapper;
	
	@Autowired
	private TblDealerMapper dealerMapper;
	
	@Autowired
	private TblComboMapper comboMapper;
	
	@Autowired
	private TblPreferentialActivityMapper activityMapper;
	
	
	// BILL
	@Value("${BILL_GIVE_TYPE}")
	private Integer BILL_GIVE_TYPE;
	
	// BILL
	@Value("${GIVE_BAG_MONEY}")
	private Double GIVE_BAG_MONEY;
	
	// BILL
	@Value("${PAY_STATUS}")
	private Integer PAY_STATUS;
	

	@Override
	public PytheResult queryActivity(String parameters) {
		
		JSONObject information = JSONObject.parseObject(parameters);
		Long customerId = information.getLong("customerId");
		Double longitude = information.getDouble("longitude");
		Double latitude = information.getDouble("latitude");
		
		//客户身份和地理位置作条件过滤，返回当前处于未过期的活动
		TblPreferentialActivityExample activityExample = new TblPreferentialActivityExample();
		activityExample.createCriteria()
		.andStopTimeGreaterThan(new Date())
		;
		List<TblPreferentialActivity> activities = activityMapper.selectByExample(activityExample);
		
		if(activities.isEmpty())
		{
			return PytheResult.build(400, "暂无优惠活动");
		}
		else
		{
			List<JSONObject> results = new LinkedList<JSONObject>();
			for (TblPreferentialActivity activity : activities) {
				DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd"); 
				JSONObject result = new JSONObject();
				result.put("id", activity.getId());
				result.put("name", activity.getName());
				result.put("content", 
						sdf.format(activity.getStartTime())
						+ "至"
						+sdf.format(activity.getStopTime())
						+ "期间 "
						+ activity.getContent());
				result.put("startTime", sdf.format(activity.getStartTime()));
				result.put("stopTime", sdf.format(activity.getStopTime()));
				result.put("image", activity.getImage());
				results.add(result);
			}
			return PytheResult.ok(results);
		}
		
	}
	

	
}
