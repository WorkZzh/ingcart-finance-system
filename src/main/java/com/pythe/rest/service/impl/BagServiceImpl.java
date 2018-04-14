package com.pythe.rest.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblBagRecordMapper;
import com.pythe.mapper.TblBillMapper;
import com.pythe.mapper.TblComboMapper;
import com.pythe.mapper.TblStoreMapper;
import com.pythe.mapper.VCouponMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblBagRecord;
import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblCombo;
import com.pythe.pojo.TblComboExample;
import com.pythe.pojo.TblStore;
import com.pythe.pojo.TblStoreExample;
import com.pythe.pojo.VCoupon;
import com.pythe.pojo.VCouponExample;
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
	private VCouponMapper  couponMapper;

	@Autowired
	private TblBagRecordMapper bagRecordMapper;


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

		JSONObject information = JSONObject.parseObject(parameters);
		Long customerId = information.getLong("customerId");
		String storeId = information.getString("storeId");
		String out_trade_no = information.getString("out_trade_no");
		String prepay_id = information.getString("prepay_id");

		TblStoreExample example = new TblStoreExample();
		example.createCriteria().andLocationNameEqualTo(storeId);
		
    	List<TblStore> storeList = storeMapper.selectByExample(example);
		
    	if (storeList.isEmpty()) {
			return PytheResult.build(400, "不属布套购买点，购买虽谨慎");
		}
		
    	//更新布套数量
		TblStore store = storeList.get(0);
    	store.setBagNum(store.getBagNum()-1);
    	store.setOutBagNum(store.getOutBagNum()+1);
    	storeMapper.updateByPrimaryKey(store);
    	
		// 更新卡券使用情况
		// store.setBagNum(store.getBagNum()-1);
		// store.setOutBagNum(store.getOutBagNum() + 1);
//		storeMapper.updateByPrimaryKey(store);

		// 更新表单数据
		TblBagRecord bagRecord = new TblBagRecord();
		bagRecord.setCustomerId(customerId);
		// bagRecord.setDealerId(dealerId);
		// bagRecord.setType(1);
		bagRecord.setBuyTime(new Date());
		bagRecord.setStoreId(store.getId());
		bagRecordMapper.insert(bagRecord);

		// 更新账单记录
//		TblAccount account = accountMapper.selectByPrimaryKey(customerId);
		// //先查是否已付款
		// TblBillExample billExample = new TblBillExample();
		// billExample.createCriteria().andOutTradeNoEqualTo(out_trade_no).andStatusEqualTo(PAY_STATUS);
		// List<TblBill> payBills = billMapper.selectByExample(billExample);
		// if(payBills.isEmpty())
		// {
		// return PytheResult.build(500, "尚未付款");
		// }

		// 再更新账单
//		TblBill bill = new TblBill();
//		bill.setId(FactoryUtils.getUUID());
//		bill.setAmount(GIVE_BAG_MONEY);
//		bill.setType(BILL_GIVE_TYPE);
//		bill.setOutTradeNo(out_trade_no);
//		bill.setPrepayId(prepay_id);
//		bill.setStatus(PAY_STATUS);
//		bill.setTime(new Date());
//		bill.setCustomerId(customerId);
//		billMapper.insert(bill);

		// 更新账户
//		account.setAmount(account.getAmount() + GIVE_BAG_MONEY);
//		account.setInAmount(account.getInAmount() + GIVE_BAG_MONEY);
//		account.setGivingAmount(account.getGivingAmount() + GIVE_BAG_MONEY);
//		accountMapper.updateByPrimaryKey(account);

		// 向消费用户发送服务通知
		// String url =
		// "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
		// + "&appid=" + WX_APPID
		// +"&secret=" + WX_APPSECRET;
		// String result = HttpClientUtil.doGet(url, null);
		// System.out.println(result);
		// String access_token =
		// JSONObject.parseObject(result).getString("access_token");
		// //System.out.println("=====================>"+access_token);
		//
		// JSONObject notifyParameters = new JSONObject();
		//
		// Map<String, Object> datas = new HashMap<String, Object>();
		//
		// Map<String, String> keyValue1 = new HashMap<String, String>();
		// keyValue1.put("value", "￥38.00");
		// datas.put("keyword1", keyValue1);
		//
		// Map<String, String> keyValue2 = new HashMap<String, String>();
		// keyValue2.put("value",
		// dealerMapper.selectByPrimaryKey(dealerId).getName());
		// datas.put("keyword2", keyValue2);
		//
		// Map<String, String> keyValue3 = new HashMap<String, String>();
		// keyValue3.put("value", "价值￥68的垫套，￥20预存费用");
		// datas.put("keyword3", keyValue3);
		//
		// Map<String, String> keyValue4 = new HashMap<String, String>();
		// keyValue4.put("value", bill.getTime().toString());
		// datas.put("keyword4", keyValue4);
		//
		// notifyParameters.put("touser",
		// customerMapper.selectByPrimaryKey(customerId).getOpenId());
		// notifyParameters.put("template_id",GIFT_NOTIFY_TEMPLATE_ID);
		// notifyParameters.put("form_id", bill.getPrepayId());
		// notifyParameters.put("page", "pages/index/index");
		// notifyParameters.put("data", datas);
		// notifyParameters.put("emphasis_keyword", "keyword1.DATA");
		//
		// String xw_url =
		// "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+access_token;
		// String str = HttpClientUtil.doPostJson(xw_url,
		// JsonUtils.objectToJson(notifyParameters));

		// System.out.println("===================> notify template result: " +
		// str);
		// JSONObject purchaseInfo = new JSONObject();
		// purchaseInfo.put("finishTime", System.currentTimeMillis());
		// purchaseInfo.put("bill", payBills.get(0));

		return PytheResult.ok("布套购买成功");
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
		example.createCriteria().andStatusGreaterThanOrEqualTo(1);
		List<TblCombo> comboList = comboMapper.selectByExample(example);
		if (comboList.isEmpty()) {
			PytheResult.build(400, "暂无优惠套餐");
		}
		return PytheResult.ok(comboList);
	}

	@Override
	public PytheResult insertStore(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String location_name = information.getString("location_name");

		TblStoreExample example = new TblStoreExample();
		example.createCriteria().andLocationNameEqualTo(location_name);
		List<TblStore> storeList = storeMapper.selectByExample(example);
		
		if (storeList.size()>0) {
			return PytheResult.build(400, "该店铺已经添加过");
		}

		String id = FactoryUtils.getUUID();
		String name = information.getString("name");
		String description = information.getString("description");
		String store_hours = information.getString("store_hours");
		Double longitude = information.getDouble("longitude");
		Double latitude = information.getDouble("latitude");
		String dealer = information.getString("dealer");
		TblStore store = new TblStore();

		store.setId(id);
		store.setDescription(description);
		store.setName(name);
		store.setStoreHours(store_hours);
		store.setCreated(new Date());
		store.setLocationName(location_name);
		store.setLatitude(latitude);
		store.setLongitude(longitude);
		store.setBagNum(0);
		store.setInBagNum(0);
		store.setOutBagNum(0);
		store.setDealer(dealer);
		//营业状态
		store.setStatus(1);
		storeMapper.insert(store);

		return PytheResult.ok("店铺添加成功");
	}

	@Override
	public PytheResult updateStoreGoods(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String location_name = information.getString("location_name");
		Integer bag_num = information.getInteger("bag_num");
		TblStoreExample example = new TblStoreExample();
		example.createCriteria().andLocationNameEqualTo(location_name);
		List<TblStore> storeList = storeMapper.selectByExample(example);
		if (storeList.isEmpty()) {
			PytheResult.build(400, "该店不存在");
		}
		
		TblStore store = storeList.get(0);
		store.setBagNum(store.getBagNum()+bag_num);
		store.setInBagNum(store.getInBagNum()+ bag_num);
		storeMapper.updateByPrimaryKey(store);
		
		JSONObject json = new JSONObject();
		json.put("total_bag_num", store.getBagNum());
		json.put("add_bag_num", bag_num);
		return PytheResult.ok(json);
	}

	@Override
	public PytheResult selectStoreBag(String code) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		code= code.trim();
		TblStoreExample example =new TblStoreExample();
		example.createCriteria().andLocationNameEqualTo(code);
		List<TblStore> storeList = storeMapper.selectByExample(example);
		if (storeList.isEmpty()) {
			return PytheResult.build(400, "该店铺不存在,请填写正确店铺编码信息");
		}
		return PytheResult.ok(storeList.get(0));
	}

	@Override
	public PytheResult deleteBagStore(String code) {
		// TODO Auto-generated method stub
		code= code.trim();
		TblStoreExample example =new TblStoreExample();
		example.createCriteria().andLocationNameEqualTo(code);
		List<TblStore> storeList = storeMapper.selectByExample(example);
		if (storeList.isEmpty()) {
			return PytheResult.build(400, "该店铺不存在,请填写正确店铺编码信息");
		}
		storeMapper.deleteByPrimaryKey(storeList.get(0).getId());
		return PytheResult.ok(code+"店铺信息删除成功");
	}

	@Override
	public PytheResult selectAllStore() {
		// TODO Auto-generated method stub
		TblStoreExample example =new TblStoreExample();
		List<TblStore> storeList = storeMapper.selectByExample(example);
		return PytheResult.ok(storeList);
	}

	@Override
	public PytheResult selectCoupon(Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		VCouponExample example =new VCouponExample();
		List<VCoupon> coupon = couponMapper.selectByExample(example);
		if (coupon.isEmpty()) {
			return PytheResult.build(400, "暂无优惠券");
		}
		return PytheResult.ok(coupon);
	}
	
	
	
	

}
