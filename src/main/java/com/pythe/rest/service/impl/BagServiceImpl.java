package com.pythe.rest.service.impl;

import java.util.Date;
import java.util.HashMap;
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
import com.pythe.mapper.TblStoreMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblBagRecord;
import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblCombo;
import com.pythe.pojo.TblComboExample;
import com.pythe.pojo.TblCustomer;
import com.pythe.pojo.TblStore;
import com.pythe.pojo.TblStoreExample;
import com.pythe.rest.service.BagService;

@Service
public class BagServiceImpl implements BagService {
	
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
	public PytheResult maiBag(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		Long customerId = information.getLong("customerId");
		String dealerId = information.getString("dealerId");
		String out_trade_no = information.getString("out_trade_no");
		String prepay_id = information.getString("prepay_id");
		
		TblStore store = null;
		// 更新卡券使用情况
		TblStoreExample example = new TblStoreExample();
		example.createCriteria().andDealerIdEqualTo(dealerId);
		List<TblStore> storeList = storeMapper.selectByExample(example);
		if (storeList.isEmpty()) {
			PytheResult.build(400, "该用户暂无权限");
		}
		store = storeList.get(0);
		store.setOutBagNum(store.getOutBagNum() + 1);
		storeMapper.updateByPrimaryKey(store);
		
		// 更新表单数据
		TblBagRecord bagRecord = new TblBagRecord();
		bagRecord.setCustomerId(customerId);
		bagRecord.setDealerId(dealerId);
		bagRecord.setType(1);
		bagRecord.setBuyTime(new Date());
		bagRecord.setStoreId(store.getId());
		bagRecordMapper.insert(bagRecord);
		
		//更新账单记录
		final TblAccount account = accountMapper.selectByPrimaryKey(customerId);
		// 先更新账单
		TblBill bill = new TblBill();
		bill.setId(FactoryUtils.getUUID());
		bill.setAmount(GIVE_BAG_MONEY);
		bill.setType(BILL_GIVE_TYPE);
		bill.setOutTradeNo(out_trade_no);
		bill.setPrepayId(prepay_id);
		bill.setStatus(PAY_STATUS);
		bill.setTime(new Date());
		bill.setCustomerId(customerId);
		billMapper.insert(bill);

		// 更新账户
		account.setAmount(account.getAmount() + GIVE_BAG_MONEY);
		account.setInAmount(account.getInAmount() +GIVE_BAG_MONEY);
		account.setGivingAmount(account.getGivingAmount() +GIVE_BAG_MONEY);
		accountMapper.updateByPrimaryKey(account);
		
		
		//向消费用户发送服务通知
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" + "&appid=" + WX_APPID
				+"&secret=" + WX_APPSECRET;
		String result = HttpClientUtil.doGet(url, null);
		System.out.println(result);
		String access_token = JSONObject.parseObject(result).getString("access_token");
		//System.out.println("=====================>"+access_token);
		
		JSONObject notifyParameters = new JSONObject();
		
		Map<String, Object> datas = new HashMap<String, Object>();

		Map<String, String> keyValue1 = new HashMap<String, String>();
		keyValue1.put("value", "￥38.00");
		datas.put("keyword1", keyValue1);
		
		Map<String, String> keyValue2 = new HashMap<String, String>();
		keyValue2.put("value", dealerMapper.selectByPrimaryKey(dealerId).getName());
		datas.put("keyword2", keyValue2);
		
		Map<String, String> keyValue3 = new HashMap<String, String>();
		keyValue3.put("value", "价值￥68的垫套，￥20预存费用");
		datas.put("keyword3", keyValue3);
		
		Map<String, String> keyValue4 = new HashMap<String, String>();
		keyValue4.put("value", bill.getTime().toString());
		datas.put("keyword4", keyValue4);
		
		notifyParameters.put("touser", customerMapper.selectByPrimaryKey(customerId).getOpenId());
		notifyParameters.put("template_id",GIFT_NOTIFY_TEMPLATE_ID);
		notifyParameters.put("form_id", bill.getPrepayId());
		notifyParameters.put("page", "pages/index/index");
		notifyParameters.put("data", datas);
		notifyParameters.put("emphasis_keyword", "keyword1.DATA");
		
		String xw_url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+access_token;
		String str = HttpClientUtil.doPostJson(xw_url, JsonUtils.objectToJson(notifyParameters));
		
		//System.out.println("===================> notify template result: " + str);
		
		return PytheResult.ok("记录成功");
	}


	@Override
	public PytheResult selectStoreLocation(String storeId) {
		// TODO Auto-generated method stub
		TblStore store = storeMapper.selectByPrimaryKey(storeId);
		return PytheResult.ok(store);
	}


	@Override
	public PytheResult selectComboDetailList() {
		// TODO Auto-generated method stub
		TblComboExample example = new TblComboExample();
		example.createCriteria().andStatusEqualTo(1);
		List<TblCombo> comboList = comboMapper.selectByExample(example);
		if (comboList.isEmpty()) {
			PytheResult.build(400, "暂无优惠套餐");
		}
		return PytheResult.ok(comboList);
	}



}
