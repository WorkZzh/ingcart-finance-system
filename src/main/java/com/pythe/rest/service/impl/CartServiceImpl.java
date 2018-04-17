package com.pythe.rest.service.impl;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.dev.RecordLister;
import org.bouncycastle.asn1.esf.RevocationValues;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.DateUtils;
import com.pythe.common.utils.DecodeUtils;
import com.pythe.common.utils.EncodeUtils;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.common.utils.HttpClientUtil;
import com.pythe.common.utils.JsonUtils;
import com.pythe.common.utils.NumberUtils;
import com.pythe.common.utils.StringUtils;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblBillMapper;
import com.pythe.mapper.TblCarMapper;
import com.pythe.mapper.TblCouponMapper;
import com.pythe.mapper.TblCustomerMapper;
import com.pythe.mapper.TblHoldRecordMapper;
import com.pythe.mapper.TblOperatorRecordMapper;
import com.pythe.mapper.TblPriceMapper;
import com.pythe.mapper.TblRecordMapper;
import com.pythe.mapper.TblStoreMapper;
import com.pythe.mapper.VAcountRecordMapper;
import com.pythe.mapper.VCouponMapper;
import com.pythe.mapper.VCustomerMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblAccountExample;
import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblBillExample;
import com.pythe.pojo.TblCar;
import com.pythe.pojo.TblCarExample;
import com.pythe.pojo.TblCoupon;
import com.pythe.pojo.TblCouponExample;
import com.pythe.pojo.TblCustomer;
import com.pythe.pojo.TblHoldRecord;
import com.pythe.pojo.TblHoldRecordExample;
import com.pythe.pojo.TblOperatorRecord;
import com.pythe.pojo.TblPrice;
import com.pythe.pojo.TblRecord;
import com.pythe.pojo.TblRecordExample;
import com.pythe.pojo.TblStore;
import com.pythe.pojo.TblStoreExample;
import com.pythe.pojo.VAcountRecord;
import com.pythe.pojo.VAcountRecordExample;
import com.pythe.pojo.VCoupon;
import com.pythe.pojo.VCouponExample;
import com.pythe.pojo.VCustomer;
import com.pythe.pojo.VCustomerExample;
import com.pythe.rest.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	// 权限有2种
	private Integer DEVELOPER_LEVEL;

	@Value("${TEST_PAY_TYPE}")
	private Integer TEST_PAY_TYPE;

	@Value("${EACH_HOUR_PRICE}")
	private Integer EACH_HOUR_PRICE;

	@Value("${NOT_PAY_STATUS}")
	private Integer NOT_PAY_STATUS;

	@Value("${PAY_STATUS}")
	private Integer PAY_STATUS;

	@Value("${PAY_TYPE}")
	private Integer PAY_TYPE;

	@Value("${GRADE_FEE}")
	private String GRADE_FEE;

	// CAR
	@Value("${CAR_FREE_STATUS}")
	private Integer CAR_FREE_STATUS;

	@Value("${CAR_USER_STATUS}")
	private Integer CAR_USER_STATUS;

	@Value("${CAR_SAVE_STATUS}")
	private Integer CAR_SAVE_STATUS;

	@Value("${CAR_LOCK_STATUS}")
	private Integer CAR_LOCK_STATUS;

	@Value("${CAR_MAINTENCE_STATUS}")
	private Integer CAR_MAINTENCE_STATUS;

	// BILL
	@Value("${BILL_CHARGE_TYPE}")
	private Integer BILL_CHARGE_TYPE;

	@Value("${PART_REFUND_TYPE}")
	private Integer PART_REFUND_TYPE;

	@Value("${TOTAL_REFUND_TYPE}")
	private Integer TOTAL_REFUND_TYPE;

	@Value("${AUTO_PAY_TYPE}")
	private Integer AUTO_PAY_TYPE;

	@Value("${BILL_PAY_TYPE}")
	private Integer BILL_PAY_TYPE;

	@Value("${WX_APPID}")
	private String WX_APPID;

	@Value("${WX_APPSECRET}")
	private String WX_APPSECRET;

	@Value("${WX_NOTIFY_PAY_TEMPLATE_ID}")
	private String WX_NOTIFY_PAY_TEMPLATE_ID;

	@Value("${GIFT_NOTIFY_TEMPLATE_ID}")
	private String GIFT_NOTIFY_TEMPLATE_ID;

	@Value("${NOTIFY_PAY_TEMPLATE_ID}")
	private String NOTIFY_PAY_TEMPLATE_ID;

	@Value("${WX_KEY}")
	private String WX_KEY;

	@Value("${WX_MCH_ID}")
	private String WX_MCH_ID;

	@Value("${WX_PAY_CONFIRM_NOTIFY_URL}")
	private String WX_PAY_CONFIRM_NOTIFY_URL;
	
	
	@Value("${RECORD_USER_STATUS}")
	private Integer RECORD_USER_STATUS;

	@Value("${RECORD_END_STATUS}")
	private Integer RECORD_END_STATUS;

	
	
	@Autowired
	private TblOperatorRecordMapper operatorRecordMapper;

	@Autowired
	private VCustomerMapper vCustomerMapper;

	@Autowired
	private TblCustomerMapper customerMapper;

	@Autowired
	private TblCouponMapper couponMapper;

	@Autowired
	private VCouponMapper vCouponMapper;

	@Autowired
	private TblRecordMapper recordMapper;

	@Autowired
	private TblPriceMapper priceMapper;

	@Autowired
	private TblStoreMapper storeMapper;

	@Autowired
	private TblBillMapper billMapper;

	@Autowired
	private TblAccountMapper accountMapper;

	@Autowired
	private TblCarMapper carMapper;

	@Autowired
	private TblHoldRecordMapper holdRecordMapper;

	@Autowired
	private VAcountRecordMapper acountRecordMapper;

	@Override
	public PytheResult unlock(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		Long customerId = information.getLong("customerId");
		String carId = information.getString("carId");
		Double longitude = information.getDouble("longitude");
		Double latitude = information.getDouble("latitude");
		String recordId = FactoryUtils.getUUID();
		String code = null;
		// 开锁成功后才来更新这个的。
		if (null != information.getDouble("code")) {
			code = information.getString("code");
			TblCouponExample couponExample = new TblCouponExample();
			couponExample.createCriteria().andStatusNotEqualTo(0).andCodeEqualTo(code);
			TblCoupon coupon = couponMapper.selectByExample(couponExample).get(0);
			coupon.setStatus(1);
			couponMapper.updateByPrimaryKey(coupon);
		}

		// 1、车有被保留，能成功的情况。
		TblCar car = carMapper.selectByPrimaryKey(carId);

		/**
		 * 临时使用，在开锁时候，依然可以继续扫描开锁
		 */
		if (1 == car.getStatus()) {
			return PytheResult.ok("开锁成功");
		}

		if (2 == car.getStatus()) {
			// 是，解除状态，将状态变为使用状态
			car.setLatitude(latitude);
			car.setLongitude(longitude);
			// car.setUser(customerId);
			// car.setRecordid(recordId); 因为第一次开锁时候就已经记录的该车属于那条记录
			car.setStatus(CAR_USER_STATUS);
			carMapper.updateByPrimaryKey(car);

			// 预约保留，将锁的计时器释放掉
			// TblHoldRecordExample example = new TblHoldRecordExample();
			// example.createCriteria().andCustomerIdEqualTo(customerId).andCarIdEqualTo(carId).andStatusEqualTo(0);
			// TblHoldRecord holdRecord = new TblHoldRecord();
			// holdRecord.setStatus(1);
			// holdRecordMapper.updateByExampleSelective(holdRecord, example);
			return PytheResult.ok("开锁成功");
		}

		// 2、车没有被保留，能成功的情况。
		// 先更新车的时间和使用状态
		car.setLatitude(latitude);
		car.setLongitude(longitude);
		car.setStarttime(new Date());
		car.setUser(customerId);
		car.setRecordid(recordId);
		car.setStatus(CAR_USER_STATUS);
		carMapper.updateByPrimaryKey(car);
		// 记录登记
		// 在运行时候先，推荐完要将原来的推荐信号给制空
		TblRecord record = new TblRecord();
		record.setId(recordId);
		record.setCustomerId(customerId);
		record.setLongitdeStart(longitude);
		record.setLatitudeStart(latitude);
		record.setCarId(carId);
		//同时更新一下行程账单，方便统计
		record.setStatus(RECORD_USER_STATUS);
		record.setStartTime(new Date());
		recordMapper.insert(record);
		JSONObject object = new JSONObject();
		object.put("recordId", recordId);
		return PytheResult.build(200, "开锁成功", object);
	}

	@Override
	public PytheResult lock(String parameters) {
		// 更新记录位置
		JSONObject information = JSONObject.parseObject(parameters);
		Double longitude = information.getDouble("longitude");
		Double latitude = information.getDouble("latitude");
		String carId = information.getString("carId");
		String recordId = information.getString("recordId");

		// 车信息
		TblCar car = carMapper.selectByPrimaryKey(carId);
		car.setLatitude(latitude);
		car.setLongitude(longitude);
		// car.setUser(null);
		car.setStatus(CAR_LOCK_STATUS);
		car.setEndtime(new Date());
		carMapper.updateByPrimaryKey(car);

		// 更新记录信息
		TblRecord line = recordMapper.selectByPrimaryKey(recordId);
		line.setLongitudeStop(longitude);
		line.setLatitudeStop(latitude);
		line.setStopTime(new Date());
		recordMapper.updateByPrimaryKey(line);

		return PytheResult.ok("关锁成功");
	}

	@Override
	public PytheResult selectCouponByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PytheResult selectCartPositionByMap(Double longitude, Double latitude) {
		// TODO Auto-generated method stub
		LinkedList<JSONObject> list = new LinkedList<JSONObject>();

		TblCarExample example = new TblCarExample();
		example.createCriteria().andLatitudeGreaterThanOrEqualTo(latitude - 1)
				.andLatitudeLessThanOrEqualTo(latitude + 1).andLongitudeGreaterThan(longitude - 1)
				.andLongitudeLessThanOrEqualTo(longitude + 1).andStatusEqualTo(CAR_FREE_STATUS);
		List<TblCar> carList = carMapper.selectByExample(example);
		for (TblCar car : carList) {
			JSONObject json = new JSONObject();
			json.put("id", car.getId());
			json.put("latitude", car.getLatitude());
			json.put("longitude", car.getLongitude());
			json.put("type", 0);
			list.add(json);
		}
		TblStoreExample storeExample = new TblStoreExample();
		storeExample.createCriteria().andLatitudeGreaterThanOrEqualTo(latitude - 1)
				.andLatitudeLessThanOrEqualTo(latitude + 1).andLongitudeGreaterThan(longitude - 1)
				.andLongitudeLessThanOrEqualTo(longitude + 1);

		List<TblStore> storeList = storeMapper.selectByExample(storeExample);
		for (TblStore store : storeList) {
			JSONObject json = new JSONObject();
			json.put("id", store.getId());
			json.put("latitude", store.getLatitude());
			json.put("longitude", store.getLongitude());
			json.put("type", 1);
			list.add(json);
		}

		if (!list.isEmpty()) {
			return PytheResult.ok(list);
		}
		return PytheResult.build(300, "附近无婴儿车");

	}

	@Override
	public PytheResult computeFee(String parameters) {
		JSONObject information = JSONObject.parseObject(parameters);
		String recordId = information.getString("recordId");
		String carId = information.getString("carId");
		Long customerId = information.getLong("customerId");

		// final String formId = information.getString("formId");

		// 更新车的位置和使用情况和结束时间
		// 让车处于空闲状态，让后续的人可以使用

		TblCar car = carMapper.selectByPrimaryKey(carId);
		// 更新停止时间和停止位置和记录用的钱
		int time = DateUtils.minusForPartHour(new Date(), car.getStarttime());
		Double amount = null;
		// 处理Bug如果钱是负数就说明用户报的时间不对
		if (time < 0) {
			return PytheResult.build(400, "时间不合法，结束时间不能小于开始时间");
		}

		int tmp = time - 2;
		if (tmp > 0) {
			// 前2分钟不算钱,所以这里要减去2
			if (time % 30 == 0) {
				amount = Math.floor(tmp / 30);
			} else {
				amount = (Math.floor(tmp / 30) + 1);
			}
		} else {
			amount = 0d;
		}
		amount = EACH_HOUR_PRICE * amount;

		car.setStatus(CAR_FREE_STATUS);
		car.setEndtime(null);
		car.setUser(null);
		car.setStarttime(null);
		carMapper.updateByPrimaryKey(car);

		String billId = FactoryUtils.getUUID();
		TblRecord line = recordMapper.selectByPrimaryKey(recordId);
		line.setStopTime(new Date());
		line.setBillId(billId);
		recordMapper.updateByPrimaryKey(line);

		// 生成账单
		TblAccount account = accountMapper.selectByPrimaryKey(customerId);
		account.setAmount(account.getAmount() - amount);
		account.setOutAmount(account.getOutAmount() - amount);
		accountMapper.updateByPrimaryKey(account);

		// 更新流水
		TblBill bill = new TblBill();
		bill.setId(billId);
		bill.setRecordId(recordId);
		bill.setAmount(amount);
		bill.setType(BILL_PAY_TYPE);
		bill.setCustomerId(customerId);
		bill.setTime(new Date());
		bill.setRecordId(recordId);
		// new Thread() {
		// @Override
		// public void run() {
		if (account.getAmount() < EACH_HOUR_PRICE) {
			bill.setStatus(NOT_PAY_STATUS);
		} else {
			bill.setStatus(PAY_TYPE);
		}
		billMapper.insert(bill);
		// }
		// }.start();

		// // 微信服务通知推送支付结果
		// String url =
		// "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
		// +
		// "&appid=" + WX_APPID
		// + "&secret=" + WX_APPSECRET;
		// String result = HttpClientUtil.doGet(url, null);
		// String access_token =
		// JSONObject.parseObject(result).getString("access_token");
		// //System.out.println("notify===========>" + access_token);
		// //
		// https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
		//
		// JSONObject notifyParameters = new JSONObject();
		//
		// Map<String, Object> datas = new HashMap<String, Object>();
		//
		// Map<String, String> keyValue1 = new HashMap<String, String>();
		// keyValue1.put("value", "￥" + String.valueOf(bill.getAmount()));
		// datas.put("keyword1", keyValue1);
		//
		// Map<String, String> keyValue2 = new HashMap<String, String>();
		// keyValue2.put("value", "总费用￥" + String.valueOf(bill.getAmount()) +
		// "，优惠￥"
		// + String.valueOf(0d));
		// datas.put("keyword2", keyValue2);
		//
		// Map<String, String> keyValue3 = new HashMap<String, String>();
		// keyValue3.put("value", String.valueOf(time) + "分钟");
		// datas.put("keyword3", keyValue3);
		//
		// notifyParameters.put("touser",
		// customerMapper.selectByPrimaryKey(customerId).getOpenId());
		// notifyParameters.put("template_id", NOTIFY_PAY_TEMPLATE_ID);
		// notifyParameters.put("form_id", formId);
		// notifyParameters.put("page", "pages/index/index");
		// notifyParameters.put("data", datas);
		// notifyParameters.put("emphasis_keyword", "keyword1.DATA");
		//
		// String params_json = JsonUtils.objectToJson(notifyParameters);
		// System.out.println("### params to post =========================> " +
		// params_json);

		// String xw_url =
		// "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="
		// + access_token;
		// String str = HttpClientUtil.doPostJson(xw_url, params_json);

		// System.out.println("!!!!!=======================>push pay info: " +
		// str);
		// 微信服务通知推送支付结果

		// 看看更新后的账单是否为正数，如果是，证明扣费成功
		JSONObject json = new JSONObject();
		json.put("price", amount.intValue());
		json.put("time", time);
		json.put("amount", account.getAmount());
		if (account.getAmount() > 0) {
			return PytheResult.build(200, "支付成功", json);
		} else {

			return PytheResult.build(300, "余额不足，前往充值", json);
		}
	}

	@Override
	public PytheResult holdCartByCustomerId(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String carId = information.getString("carId");
		final Long customerId = information.getLong("customerId");
		Integer appointmentTime = information.getInteger("appointmentTime");
		String recordId = information.getString("recordId");

		// 保留的话，让该用户继续霸占这个锁
		TblCar car = new TblCar();
		car.setId(carId);
		car.setUser(customerId);
		car.setStatus(CAR_SAVE_STATUS);
		car.setEndtime(null);
		carMapper.updateByPrimaryKeySelective(car);

		// 插入保留锁的信息，方便自动化程序检测是否有超时
		TblHoldRecord record = new TblHoldRecord();
		record.setCarId(carId);
		record.setCustomerId(customerId);
		record.setRecordId(recordId);
		record.setHoldStartTime(new Date());

		// 保留车
		record.setStatus(0);
		record.setHoldStopTime(new DateTime().plusMinutes(appointmentTime).toDate());
		holdRecordMapper.insert(record);
		return PytheResult.ok("保留成功");
	}

	@Override
	public PytheResult selectSaveRestTimeByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		// 将锁的计时器释放掉
		TblHoldRecordExample example = new TblHoldRecordExample();
		example.createCriteria().andCustomerIdEqualTo(customerId).andStatusEqualTo(0);
		// holdrecordList 进入计时界面时候，是不可能为空的
		TblHoldRecord holdrecord = holdRecordMapper.selectByExample(example).get(0);
		// 说明用户已经计时完成
		Date stopTime = holdrecord.getHoldStopTime();
		Date currentTime = new Date();

		if (currentTime.after(stopTime)) {
			// 取消预约
			holdrecord.setStatus(1);
			holdRecordMapper.updateByPrimaryKey(holdrecord);

			String carId = holdrecord.getCarId();
			// 更新车的位置和使用情况和结束时间(将车设为空闲，并是否释放用户绑定)
			TblCar car = carMapper.selectByPrimaryKey(carId);
			car.setStatus(CAR_FREE_STATUS);

			// 将停止的时间作为最终时间更新
			car.setEndtime(currentTime);
			car.setUser(null);

			// 更新停止时间和停止位置和记录用的钱
			int time = DateUtils.minusForPartHour(car.getEndtime(), car.getStarttime());

			Double amount = null;
			// 处理Bug如果钱是负数就说明用户报的时间不对
			if (time < 0) {
				return PytheResult.build(400, "时间不合法，结束时间不能小于开始时间");
			}

			int tmp = time - 2;
			if (tmp > 0) {
				// 前2分钟不算钱,所以这里要减去2
				if (time % 30 == 0) {
					amount = Math.floor(tmp / 30);
				} else {
					amount = (Math.floor(tmp / 30) + 1);
				}
			} else {
				amount = 0d;
			}
			amount = EACH_HOUR_PRICE * amount;

			car.setStarttime(null);
			car.setEndtime(null);
			carMapper.updateByPrimaryKey(car);

			String billId = FactoryUtils.getUUID();

			String recordId = holdrecord.getRecordId();

			// new Thread() {
			// @Override
			// public void run() {
			TblRecord line = recordMapper.selectByPrimaryKey(recordId);
			line.setStopTime(new Date());
			line.setBillId(billId);
			recordMapper.updateByPrimaryKey(line);
			// }
			// }.start();

			// 生成账单
			final TblAccount account = accountMapper.selectByPrimaryKey(customerId);
			account.setAmount(account.getAmount() - amount);
			account.setOutAmount(account.getOutAmount() - amount);
			accountMapper.updateByPrimaryKey(account);

			// 更新流水
			final TblBill bill = new TblBill();
			bill.setId(billId);
			bill.setRecordId(recordId);
			bill.setAmount(amount);
			bill.setType(BILL_PAY_TYPE);
			bill.setCustomerId(customerId);
			bill.setTime(new Date());
			bill.setRecordId(recordId);

			new Thread() {
				@Override
				public void run() {
					if (account.getAmount() < EACH_HOUR_PRICE) {
						bill.setStatus(NOT_PAY_STATUS);
					} else {
						bill.setStatus(PAY_TYPE);
					}
					billMapper.insert(bill);
				}
			}.start();

			// 看看更新后的账单是否为正数，如果是，证明扣费成功
			if (account.getAmount() > EACH_HOUR_PRICE) {
				JSONObject json = new JSONObject();
				json.put("price", amount.intValue());
				json.put("time", time);
				return PytheResult.build(200, "预约过期，自动结算", json);
			} else {
				return PytheResult.build(300, "余额不足，前往充值", account.getAmount());
			}

		}

		// 如果没有就返回时间
		long time = stopTime.getTime() - currentTime.getTime();
		JSONObject json = new JSONObject();
		json.put("time", time / 1000 / 60);
		return PytheResult.ok(json);
	}

	@Override
	public PytheResult selectUseCarTimeByCarId(String carId) {
		// 说明用户已经计时完成
		Date currentTime = new Date();
		VCustomerExample example = new VCustomerExample();
		example.createCriteria().andCarIdEqualTo(carId);
		List<VCustomer> list = vCustomerMapper.selectByExample(example);
		if (list.isEmpty()) {
			return PytheResult.build(400, "已结束行程");
		}
		VCustomer car = list.get(0);

		Date startTime = car.getStartTime();
		long time = currentTime.getTime() - startTime.getTime();
		JSONObject json = new JSONObject();

		json.put("time", DateUtils.toHourMinute(time / 1000l / 60l));
		return PytheResult.ok(json);
	}

	@Override
	public PytheResult deleteAppointmentByCustomerId(Long customerId) {
		// 将锁的计时器释放掉
		TblHoldRecordExample example = new TblHoldRecordExample();
		example.createCriteria().andCustomerIdEqualTo(customerId).andStatusEqualTo(0);
		// holdrecordList 进入计时界面时候，是不可能为空的
		TblHoldRecord holdrecord = holdRecordMapper.selectByExample(example).get(0);
		// 说明用户已经计时完成
		Date stopTime = holdrecord.getHoldStopTime();
		Date currentTime = new Date();

		// 取消预约
		holdrecord.setStatus(1);
		holdRecordMapper.updateByPrimaryKey(holdrecord);

		String carId = holdrecord.getCarId();
		// 更新车的位置和使用情况和结束时间(将车设为空闲，并是否释放用户绑定)
		TblCar car = carMapper.selectByPrimaryKey(carId);
		car.setStatus(CAR_FREE_STATUS);

		// 将停止的时间作为最终时间更新
		car.setEndtime(currentTime);

		// 更新停止时间和停止位置和记录用的钱
		int time = DateUtils.minusForPartHour(car.getEndtime(), car.getStarttime());
		Double amount = null;
		// 处理Bug如果钱是负数就说明用户报的时间不对
		if (time < 0) {
			return PytheResult.build(400, "时间不合法，结束时间不能小于开始时间");
		}

		int tmp = time - 2;
		if (tmp > 0) {
			// 前2分钟不算钱,所以这里要减去2
			if (time % 30 == 0) {
				amount = Math.floor(tmp / 30);
			} else {
				amount = (Math.floor(tmp / 30) + 1);
			}
		} else {
			amount = 0d;
		}
		amount = EACH_HOUR_PRICE * amount;

		car.setEndtime(null);
		car.setStarttime(null);
		car.setUser(null);
		carMapper.updateByPrimaryKey(car);
		final String billId = FactoryUtils.getUUID();
		final String recordId = holdrecord.getRecordId();

		// 更新记录
		new Thread() {
			@Override
			public void run() {
				TblRecord line = recordMapper.selectByPrimaryKey(recordId);
				line.setStopTime(new Date());
				line.setBillId(billId);
				recordMapper.updateByPrimaryKey(line);
			}
		}.start();

		// 生成账单
		final TblAccount account = accountMapper.selectByPrimaryKey(customerId);
		account.setAmount(account.getAmount() - amount);
		account.setOutAmount(account.getOutAmount() - amount);
		accountMapper.updateByPrimaryKey(account);

		// 更新流水
		final TblBill bill = new TblBill();
		bill.setId(billId);
		bill.setRecordId(recordId);
		bill.setAmount(amount);
		bill.setType(BILL_PAY_TYPE);
		bill.setCustomerId(customerId);
		bill.setTime(new Date());
		bill.setRecordId(recordId);

		new Thread() {
			@Override
			public void run() {
				if (account.getAmount() < EACH_HOUR_PRICE) {
					bill.setStatus(NOT_PAY_STATUS);
				} else {
					bill.setStatus(PAY_TYPE);
				}
				billMapper.insert(bill);
			}
		}.start();

		// 看看更新后的账单是否为正数，如果是，证明扣费成功
		if (account.getAmount() > EACH_HOUR_PRICE) {
			JSONObject json = new JSONObject();
			json.put("price", amount.intValue());
			json.put("time", time);
			return PytheResult.build(200, "支付成功", json);
		} else {
			return PytheResult.build(300, "余额不足，前往充值", account.getAmount());
		}

	}

	@Override
	public PytheResult unLockEncodeByCartId(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String carId = information.getString("carId");
		String token = information.getString("token");

		TblCar car = carMapper.selectByPrimaryKey(carId);

		byte head[] = { 05, 01, 06 };
		byte passwordBytes[] = NumberUtils.parseHexArray2ByteArray(car.getLockPassword().split(","));
		byte tokenBytes[] = NumberUtils.parseHexStr2Byte(token);
		byte[] s = new byte[passwordBytes.length + tokenBytes.length];
		System.arraycopy(passwordBytes, 0, s, 0, passwordBytes.length);
		System.arraycopy(tokenBytes, 0, s, passwordBytes.length, tokenBytes.length);

		try {
			byte[] sSrc = new byte[head.length + s.length + 3];
			System.arraycopy(head, 0, sSrc, 0, head.length);
			System.arraycopy(s, 0, sSrc, head.length, s.length);
			System.out.println(
					"============================> unlock frame: " + NumberUtils.parseByteArray2HexArray(sSrc));
			SecretKeySpec skeySpec = new SecretKeySpec(NumberUtils.parseHexArray2ByteArray(car.getLockKey().split(",")),
					"AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc);
			String encryptedStr = new String(Base64.encodeBase64(encrypted));

			return PytheResult.ok(encryptedStr);
		} catch (Exception ex) {
			System.out.println("==================> exception: " + ex);
			return PytheResult.build(400, "异常错误，解密失败");
		}

	}

	@Override
	public PytheResult recordDeviceInfo(String parameters) {

		JSONObject information = JSONObject.parseObject(parameters);
		String carId = information.getString("carId");
		String deviceId = information.getString("deviceId");

		TblCar car = carMapper.selectByPrimaryKey(carId);
		if (car != null) {
			car.setDeviceId(deviceId);
			carMapper.updateByPrimaryKey(car);
			return PytheResult.ok(car);
		} else {
			return PytheResult.build(400, "查无此车");
		}
	}

	@Override
	public PytheResult qrToMac(String parameters) {
		JSONObject information = JSONObject.parseObject(parameters);
		Long qrId = information.getLong("qrId");

		TblCarExample carExample = new TblCarExample();
		carExample.createCriteria().andQrIdEqualTo(qrId);
		List<TblCar> cars = carMapper.selectByExample(carExample);

		if (cars.isEmpty()) {
			return PytheResult.build(400, "查无此车");
		} else {
			return PytheResult.ok(cars.get(0));
		}

	}

	@Override
	public PytheResult prepareUnlock(Long customerId, String carId) {

		// 判断该用户是否占用了某台车
		TblCarExample example = new TblCarExample();
		example.createCriteria().andUserEqualTo(customerId);
		List<TblCar> carList = carMapper.selectByExample(example);
		if (!carList.isEmpty()) {
			return PytheResult.build(202, "抱歉，一个帐号只能使用一台车");
		}

		// 看看用户消费情况
		TblAccount account = accountMapper.selectByPrimaryKey(customerId);

		if (account.getAmount() < EACH_HOUR_PRICE) {
			return PytheResult.build(300, "余额不足前往充值", account.getAmount());
		}

		// 看看车的状态
		TblCar car = carMapper.selectByPrimaryKey(carId);

		if (car == null) {
			PytheResult.build(100, "该车信息尚未录入，暂无法使用");
		}

		Integer status = car.getStatus();
		switch (status) {
		case 1: {
			return PytheResult.build(400, "此车正在使用中");
		}
		case 2: {
			// 是否是该用户继续使用
			if (car.getUser() != customerId) {
				return PytheResult.build(500, "抱歉，该车已被其他用户预约保留");
			}
			break;
		}
		case 3: {
			return PytheResult.build(600, "此车有故障，请换车扫码");
		}
		}
		return PytheResult.ok("车安全检测通过，请放心使用");
	}

	@Override
	public String bluetoothEncrypt(String parameter) {

		JSONObject j = JSONObject.parseObject(parameter);
		String id = j.getString("carId");
		String content = j.getString("content");

		TblCar car = carMapper.selectByPrimaryKey(id);
		String encryptedStr = EncodeUtils.bluetoothEncrypt(content, car.getLockKey());

		return encryptedStr;
	}

	@Override
	public String bluetoothDecrypt(String parameter) {
		JSONObject j = JSONObject.parseObject(parameter);
		String id = j.getString("carId");
		String content = j.getString("content");

		TblCar car = carMapper.selectByPrimaryKey(id);
		String decryptedStr = DecodeUtils.bluetoothDecrypt(content, car.getLockKey());
		return decryptedStr;
	}

	@Override
	public PytheResult managerLock(String parameters) {
		// 更新记录位置
		JSONObject information = JSONObject.parseObject(parameters);
		final Double longitude = information.getDouble("longitude");
		final Double latitude = information.getDouble("latitude");
		String carId = information.getString("carId");
		final String recordId = information.getString("recordId");

		// 车信息
		TblCar car = carMapper.selectByPrimaryKey(carId);
		car.setLatitude(latitude);
		car.setLongitude(longitude);
		car.setUser(null);
		car.setStatus(CAR_FREE_STATUS);
		car.setEndtime(new Date());
		carMapper.updateByPrimaryKey(car);

		// 更新记录信息
		new Thread() {
			@Override
			public void run() {
				TblRecord line = recordMapper.selectByPrimaryKey(recordId);
				line.setLongitudeStop(longitude);
				line.setLatitudeStop(latitude);
				line.setStopTime(new Date());
				recordMapper.updateByPrimaryKey(line);
			}
		}.start();
		return PytheResult.ok("关锁成功");
	}

	@Override
	public PytheResult macSwitchKey(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String id = information.getString("mac_id");

		TblCar car = carMapper.selectByPrimaryKey(id);

		if (car == null) {
			return PytheResult.build(400, "查无此车");
		} else {
			return PytheResult.ok(car);
		}
	}

	@Override
	public PytheResult prepareUnlockGyQrId(Long customerId, Long qrId, Integer type, String code) {
		// 判断该用户是否占用了某台车
		TblCar car = null;
		TblCarExample example = new TblCarExample();
		example.createCriteria().andUserEqualTo(customerId);
		List<TblCar> carList = carMapper.selectByExample(example);

		if (!carList.isEmpty()) {
			car = carList.get(0);
			// System.out.println("=============>qrId"+qrId);
			// System.out.println("=============>car.getQrId()"+car.getQrId());
			// 说明有车了还霸占了另外一台车
			if (!qrId.equals(car.getQrId())) {
				// System.out.println("=============>qrId"+qrId);
				// System.out.println("=============>car.getQrId()"+car.getQrId());
				return PytheResult.build(202, "抱歉，一个帐号只能使用一台车");
			}
		}

		// 看看用户消费情况
		TblAccount account = accountMapper.selectByPrimaryKey(customerId);

		// 看看车的状态
		TblCarExample example2 = new TblCarExample();
		example2.createCriteria().andQrIdEqualTo(qrId);
		List<TblCar> carList2 = carMapper.selectByExample(example2);

		if (carList2.isEmpty()) {
			return PytheResult.build(100, "该车信息尚未录入，暂无法使用");
		}
		car = carList2.get(0);

		// 检测该园区是否符合收费标准
		String level = null;
		if (null == car.getDescription()) {
			level = GRADE_FEE;
		} else {
			level = car.getDescription();
		}
		TblPrice price = priceMapper.selectByPrimaryKey(level);

		Integer status = car.getStatus();
		switch (status) {
		case 1: {
			// return PytheResult.build(400, "此车正在使用中");
			// 说明是同一个用户在使用车，所以还是可以让他开
			if (!car.getUser().equals(customerId)) {
				return PytheResult.build(400, "该车已被其他用户预约使用中");
			} else {
				break;
			}
		}
		case 2: {
			// 是否是该用户继续使用
			if (car.getUser() != customerId) {
				return PytheResult.build(500, "抱歉，该车已被其他用户预约保留");
			}
			break;
		}
		case 3: {
			return PytheResult.build(600, "此车有故障，请换车扫码");
		}
		}

		// 判断是否是优惠券开锁
		if (type.equals(1)) {
			VCouponExample couponExample = new VCouponExample();
			couponExample.createCriteria().andStatusNotEqualTo(0).andCodeEqualTo(code);
			List<VCoupon> list = vCouponMapper.selectByExample(couponExample);
			if (list.isEmpty()) {
				return PytheResult.build(400, "该优惠券已使用");
			}
			VCoupon coupon = list.get(0);

			if (price.getPrice() > coupon.getAmount()) {
				JSONObject json = new JSONObject();
				json.put("price", price.getPrice() - coupon.getAmount());
				json.put("annotation", JsonUtils.jsonToList(price.getAnnotation(), String.class));
				return PytheResult.build(300, "去付款", json);
			} else {
				return PytheResult.build(200, "车安全检测通过，请放心使用", car.getId());
			}
		}

		// 先判断车是否有问题，在判断是否钱够？
		if (account.getAmount() < price.getPrice()) {
			JSONObject json = new JSONObject();
			// json.put("amount", account.getAmount());
			json.put("price", price.getPrice());
			json.put("annotation", JsonUtils.jsonToList(price.getAnnotation(), String.class));
			// return PytheResult.build(300, "余额不满足此次旅程费用，请前往充值", json);
			return PytheResult.build(300, "去付款", json);
		}
		return PytheResult.build(200, "车安全检测通过，请放心使用", car.getId());
	}

	@Override
	public PytheResult unlockFalseReset(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		Long qrId = information.getLong("qrId");
		final String recordId = information.getString("recordId");

		// 车信息
		TblCarExample example = new TblCarExample();
		example.createCriteria().andQrIdEqualTo(qrId);
		List<TblCar> carList = carMapper.selectByExample(example);
		if (carList.isEmpty()) {
			PytheResult.build(400, "该车不存在");
		}

		TblCar car = carList.get(0);
		car.setStatus(CAR_FREE_STATUS);
		car.setUser(null);
		carMapper.updateByPrimaryKey(car);
		recordMapper.deleteByPrimaryKey(recordId);
		return PytheResult.ok("重置成功");
	}

	@Override
	public PytheResult urgentUnlock(String parameters) {

		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		String date = information.getString("date");

		final Date date_ = DateUtils.parseTime(date);
		// 让车处于空闲状态，让后续的人可以使用
		VCustomerExample example = new VCustomerExample();

		if (phoneNum.length() == 11) {
			example.createCriteria().andPhoneNumEqualTo(phoneNum);
		} else {
			example.createCriteria().andQrIdEqualTo(Long.valueOf(phoneNum));
		}

		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		if (customerList.isEmpty()) {
			return PytheResult.build(400, "该用户不存在");
		}

		VCustomer customer = customerList.get(0);
		Long customerId = customer.getCustomerId();
		TblCar car = null;
		String carId = customer.getCarId();
		int time = 0;
		Double amount = null;
		if (null != carId) {
			car = carMapper.selectByPrimaryKey(carId);
			// 更新停止时间和停止位置和记录用的钱
			time = DateUtils.minusForPartHour(date_, car.getStarttime());

			// 处理Bug如果钱是负数就说明用户报的时间不对
			if (time < 0) {
				return PytheResult.build(400, "时间不合法，结束时间不能小于开始时间");
			}

			int tmp = time - 2;
			if (tmp > 0) {
				// 前2分钟不算钱,所以这里要减去2
				if (time % 30 == 0) {
					amount = Math.floor(tmp / 30);
				} else {
					amount = (Math.floor(tmp / 30) + 1);
				}
			} else {
				amount = 0d;
			}

			amount = EACH_HOUR_PRICE * amount;

			car.setId(customer.getCarId());
			car.setStatus(CAR_FREE_STATUS);
			car.setEndtime(null);
			car.setUser(null);
			carMapper.updateByPrimaryKey(car);
		} else {
			return PytheResult.build(400, "暂无占用任何车，无需释放");
		}

		// 查看某用户的最近行车记录
		TblRecord record = recordMapper.selectPreRecordPrimaryKey(customerId, carId);
		final String recordId = record.getId();
		final String billId = FactoryUtils.getUUID();
		new Thread() {
			@Override
			public void run() {
				TblRecord line = recordMapper.selectByPrimaryKey(recordId);
				line.setStopTime(date_);
				line.setBillId(billId);
				recordMapper.updateByPrimaryKey(line);
			}
		}.start();

		// 生成账单
		final TblAccount account = accountMapper.selectByPrimaryKey(customerId);
		account.setAmount(account.getAmount() - amount);
		account.setOutAmount(account.getOutAmount() - amount);
		accountMapper.updateByPrimaryKey(account);

		// 更新流水
		final TblBill bill = new TblBill();
		bill.setId(billId);
		bill.setRecordId(recordId);
		bill.setAmount(amount);
		bill.setType(BILL_PAY_TYPE);
		bill.setCustomerId(customerId);
		bill.setTime(new Date());
		bill.setRecordId(recordId);
		new Thread() {
			@Override
			public void run() {
				if (account.getAmount() < EACH_HOUR_PRICE) {
					bill.setStatus(NOT_PAY_STATUS);
				} else {
					bill.setStatus(PAY_TYPE);
				}
				billMapper.insert(bill);
			}
		}.start();

		// 看看更新后的账单是否为正数，如果是，证明扣费成功
		JSONObject json = new JSONObject();
		json.put("price", amount.intValue());
		json.put("time", time);
		json.put("amount", account.getAmount());
		if (account.getAmount() > 0) {
			return PytheResult.build(200, "结算成功", json);
		} else {
			return PytheResult.build(300, "余额不足，前往充值", json);
		}
	}

	@Override
	public PytheResult urgentUnlockByClient(String parameters) {
		// TODO Auto-generated method stub
		// 更新记录位置
		JSONObject information = JSONObject.parseObject(parameters);
		JSONObject param1 = new JSONObject();
		param1.put("longitude", information.getDouble("longitude"));
		param1.put("latitude", information.getDouble("latitude"));
		param1.put("carId", information.getString("carId"));
		param1.put("recordId", information.getString("recordId"));
		param1.put("customerId", information.getLong("customerId"));
		parameters = JsonUtils.objectToJson(param1);
		lock(parameters);
		return computeFee(parameters);
	}

	@Override
	@Transactional
	public PytheResult manageUrgentRefund(String parameters) {
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		// String date = information.getString("date");
		Long managerId = information.getLong("managerId");

		// final Date date_ = DateUtils.parseTime(date);
		Date date_ = new Date();
		// 让车处于空闲状态，让后续的人可以使用
		VCustomerExample example = new VCustomerExample();

		if (phoneNum.length() == 11) {
			example.createCriteria().andPhoneNumEqualTo(phoneNum);
		} else {
			example.createCriteria().andQrIdEqualTo(Long.valueOf(phoneNum));
		}

		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		if (customerList.isEmpty()) {
			return PytheResult.build(400, "该用户不存在");
		}

		VCustomer customer = customerList.get(0);
		Long customerId = customer.getCustomerId();
		TblCar car = null;
		String carId = customer.getCarId();
		// 用于返回给用户使用
		// int gString = customer.getGiving().intValue() ;
		String giving = String.valueOf((customer.getGiving().intValue() * 100));
		int time = 0;
		Double amount = null;
		Double givingAmount = 0d;
		int tmp = 0;
		if (null != carId) {
			car = carMapper.selectByPrimaryKey(carId);
			// 更新停止时间和停止位置和记录用的钱
			time = DateUtils.minusForPartHour(date_, car.getStarttime());
			// 处理Bug如果钱是负数就说明用户报的时间不对
			if (time < 0) {
				return PytheResult.build(400, "时间不合法，结束时间不能小于开始时间");
			}

			// 前面10分钟不要钱
			tmp = time - 10;

			// 找到用户
			if (tmp > 0) {
				amount = customer.getPrice();
				givingAmount = customer.getGiving();
				// 前2分钟不算钱,所以这里要减去2
				// if (time % 30 == 0) {
				// amount = Math.floor(tmp / 30);
				// } else {
				// amount = (Math.floor(tmp / 30) + 1);
				// }
			} else {
				amount = 0d;
			}
			// 释放车
			car.setId(customer.getCarId());
			car.setStatus(CAR_FREE_STATUS);
			car.setEndtime(null);
			car.setUser(null);
			car.setStarttime(null);
			car.setRecordid(null);
			carMapper.updateByPrimaryKey(car);
		} else {
			return PytheResult.build(400, "无法停止计费，暂无占用任何车");
		}

		// 查看某用户的最近行车记录
		String recordId = customer.getRecordId();
		TblRecord record = recordMapper.selectByPrimaryKey(recordId);
		String billId = FactoryUtils.getUUID();
		record.setStatus(RECORD_END_STATUS);
		record.setStopTime(date_);
		record.setBillId(billId);
		recordMapper.updateByPrimaryKey(record);

		// 更新流水
		// 即使是人工，这20元也一定给，但是人工给用户退款。
		final TblBill bill = new TblBill();
		bill.setId(billId);
		bill.setRecordId(recordId);
		bill.setAmount(amount);
		bill.setRefundAmount(givingAmount);
		bill.setGivingAmount(0d);

		if (DEVELOPER_LEVEL.equals(customer.getLevel())) {
			bill.setType(TEST_PAY_TYPE);
		} else {
			bill.setType(PART_REFUND_TYPE);
		}

		bill.setCustomerId(customerId);
		bill.setTime(new Date());
		bill.setRecordId(recordId);
		bill.setManagerId(managerId);

		// 生成账单
		TblAccount account = accountMapper.selectByPrimaryKey(customerId);
		account.setAmount(account.getAmount() - amount);
		account.setOutAmount(account.getOutAmount() - amount);

		// 如果giving 为0 就直接返回回去就行，不用再微信退款请求
		if ("0".equals(giving)) {
			// 直接扣
			accountMapper.updateByPrimaryKey(account);
			// 如果是没有退，就不更新腾讯的退款订单号
			bill.setStatus(PAY_TYPE);
			billMapper.insert(bill);
			JSONObject json = new JSONObject();
			json.put("price", amount.intValue());
			json.put("time", time);
			json.put("amount", account.getAmount());
			return PytheResult.build(200, "结算成功", json);
		}

		// 退回用户20元现金
		TblBillExample example2 = new TblBillExample();
		example2.createCriteria().andStatusEqualTo(1).andTypeEqualTo(BILL_CHARGE_TYPE).andCustomerIdEqualTo(customerId);
		example2.setOrderByClause("time DESC");
		List<TblBill> billList = billMapper.selectByExample(example2);
		if (!billList.isEmpty()) {
			TblBill bi = billList.get(0);

			String str = refundByOrderInWX(bi.getOutTradeNo(), String.valueOf((bi.getAmount().intValue() * 100)),
					giving);
			// System.out.println("============>"+str);
			if (str.indexOf("SUCCESS") != -1 && !str.contains("订单已全额退款") && !str.contains("累计退款金额大于支付金额")) {

				// 注意： 退回20后，这单要记录这张单已经退回20，目的是为了未来能退回剩的余额
				bi.setRefundAmount(bi.getRefundAmount() + givingAmount);
				billMapper.updateByPrimaryKey(bi);

				// 支付成功后再扣
				accountMapper.updateByPrimaryKey(account);

				// 退款成功
				bill.setOutTradeNo(bi.getOutTradeNo());
				bill.setPrepayId(bi.getPrepayId());
				bill.setStatus(PAY_TYPE);
				billMapper.insert(bill);

				// 看看更新后的账单是否为正数，如果是，证明扣费成功
				JSONObject json = new JSONObject();
				json.put("price", amount.intValue());
				if (tmp > 0) {
					json.put("time", "1天");
				} else {
					json.put("time", time);
				}
				json.put("amount", account.getAmount());
				return PytheResult.build(200, "结算成功", json);
			}
		}

		// 这种情况是，因为退款不成功,所以退款金额为0
		bill.setStatus(NOT_PAY_STATUS);
		billMapper.insert(bill);

		return PytheResult.build(400, "退款失败，具体原因，请咨询开发人员");
	}

	private String refundByOrderInWX(String out_trade_no, String total_fee, String refund_fee) {

		String appid = WX_APPID;// appid
		String mch_id = WX_MCH_ID;// 微信支付商户号
		String nonce_str = FactoryUtils.getUUID();// 随机码
		String sign = "";
		String out_refund_no = FactoryUtils.getUUID();
		String op_user_id = WX_MCH_ID;
		// sign
		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", mch_id);
		params.put("nonce_str", nonce_str);
		params.put("out_trade_no", out_trade_no);
		params.put("out_refund_no", out_refund_no);
		params.put("out_trade_no", out_trade_no);
		params.put("total_fee", total_fee);
		params.put("refund_fee", refund_fee);
		params.put("op_user_id", op_user_id);

		// 1第一次签名
		sign = FactoryUtils.getSign(params, WX_KEY);
		// 参数xml化
		String xmlParams = FactoryUtils.parseString2Xml(params, sign);
		// 判断返回码
		String str = "";
		String xw_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		try {
			// resource/apiclient_cert.p12
			// System.out.println("===============>我我我"+this.getClass().getClassLoader().getResource("resource/apiclient_cert.p12").getPath());
			str = HttpClientUtil.doPostXmlSafely(xw_url, xmlParams, mch_id,
					new File(this.getClass().getClassLoader().getResource("resource/apiclient_cert.p12").getPath()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;
	}

	@Override
	//@Transactional
	public void autoLock() {
		// TODO Auto-generated method stub
		Date date_ = DateUtils.parseTime(DateUtils.getTodayDate() + " 23:50:00");
		VCustomerExample example = new VCustomerExample();
		example.createCriteria().andCarStatusEqualTo(CAR_USER_STATUS);
		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);

		for (VCustomer customer : customerList) {
			Long customerId = customer.getCustomerId();
			TblCar car = null;
			String carId = customer.getCarId();
			Double amount = null;

			// 因为用户在使用所以车一定是存在的
			car = carMapper.selectByPrimaryKey(carId);
			// 更新停止时间和停止位置和记录用的钱
			amount = customer.getPrice();

			// 查看某用户的最近行车记录
			TblRecord record = recordMapper.selectByPrimaryKey(customer.getRecordId());
			String recordId = record.getId();
			String billId = FactoryUtils.getUUID();
			TblRecord line = recordMapper.selectByPrimaryKey(recordId);
			line.setStopTime(date_);
			line.setBillId(billId);
			recordMapper.updateByPrimaryKey(line);

			// 更新车的信息
			car.setId(customer.getCarId());
			car.setStatus(CAR_FREE_STATUS);
			car.setStarttime(null);
			car.setEndtime(null);
			car.setUser(null);
			car.setRecordid(null);
			carMapper.updateByPrimaryKey(car);

			// 生成账单
			TblAccount account = accountMapper.selectByPrimaryKey(customerId);
			account.setAmount(account.getAmount() - amount);
			account.setOutAmount(account.getOutAmount() - amount);
			accountMapper.updateByPrimaryKey(account);
			
			
			// 更新流水
			TblBill bill = new TblBill();
			bill.setId(billId);
			bill.setRecordId(recordId);
			bill.setAmount(amount);
			bill.setGivingAmount(0d);
			bill.setRefundAmount(0d);
			if (DEVELOPER_LEVEL.equals(customer.getLevel())) {
				bill.setType(TEST_PAY_TYPE);
			} else {
				bill.setType(AUTO_PAY_TYPE);
			}

			bill.setCustomerId(customerId);
			bill.setTime(new Date());
			bill.setRecordId(recordId);

			bill.setStatus(PAY_TYPE);
			billMapper.insert(bill);
		}

		// 2、自动退款 （当天充值，并且当天无行程）
//		Date start = DateUtils.parseTime(DateUtils.getTodayDate() + " 23:59:00");
//		Date end = DateUtils.parseTime(DateUtils.getTodayDate() + " 01:00:00");
//
//		TblBillExample example2 = new TblBillExample();
//		example2.createCriteria().andTimeBetween(start, end).andTypeEqualTo(BILL_CHARGE_TYPE).andStatusEqualTo(1);
//		List<TblBill> billList = billMapper.selectByExample(example2);
//		// 说明今天有充值
//		Map<Long, TblBill> cMap = new LinkedHashMap<Long, TblBill>();
//		// 有充值
//		List<Long> cList = new LinkedList<Long>();
//		// 有行程的用户
//		List<Long> yList = new LinkedList<Long>();
//		if (!billList.isEmpty()) {
//			// 看充值的，无行程给他们退款。
//			for (TblBill bill : billList) {
//				cList.add(bill.getCustomerId());
//				cMap.put(bill.getCustomerId(), bill);
//			}
//			TblRecordExample recordExample = new TblRecordExample();
//			recordExample.createCriteria().andStartTimeBetween(start, end).andCustomerIdIn(cList);
//			List<TblRecord> recordList = recordMapper.selectByExample(recordExample);
//			for (TblRecord tblRecord : recordList) {
//				yList.add(tblRecord.getCustomerId());
//			}
//
//			cList.removeAll(yList);
//
//			// 退款
//			for (Long target : cList) {
//				TblBill bi = cMap.get(target);
//				Double p = bi.getAmount() * 100;
//				Double refund = bi.getRefundAmount() * 100;
//				Double rest = p - refund;
//
//				String str = refundByOrderInWX(bi.getOutTradeNo(), String.valueOf(p.intValue()),
//						String.valueOf(rest.intValue()));
//
//				if (str.indexOf("SUCCESS") != -1 && !str.contains("订单已全额退款") && !str.contains("累计退款金额大于支付金额")) {
//
//					TblAccount account = accountMapper.selectByPrimaryKey(bi.getCustomerId());
//
//					account.setAmount(account.getAmount() - bi.getAmount());
//					account.setOutAmount(account.getOutAmount() - bi.getAmount());
//					accountMapper.updateByPrimaryKey(account);
//
//					bi.setRefundAmount(bi.getRefundAmount() + bi.getAmount());
//					billMapper.updateByPrimaryKey(bi);
//				}
//			}
//		}

	}

	@Override
	public PytheResult lockHold(String parameters) {
		JSONObject information = JSONObject.parseObject(parameters);
		String carId = information.getString("carId");
		Long customerId = information.getLong("customerId");
		Double longitude = information.getDouble("longitude");
		Double latitude = information.getDouble("latitude");

		// 保留的话，让该用户继续霸占这个锁
		TblCar car = new TblCar();
		car.setId(carId);
		car.setUser(customerId);
		car.setStatus(CAR_SAVE_STATUS);
		car.setEndtime(null);
		car.setLatitude(latitude);
		car.setLongitude(longitude);
		carMapper.updateByPrimaryKeySelective(car);

		return PytheResult.ok("小婴为亲保留一日，祝你旅途愉快");
	}

	@Override
	@Transactional
	public PytheResult customerUrgentLock(String parameters) {
		// TODO Auto-generated method stub
		// 更新记录位置
		JSONObject information = JSONObject.parseObject(parameters);
		Double longitude = information.getDouble("longitude");
		Double latitude = information.getDouble("latitude");
		Long customerId = information.getLong("customerId");
		// String formId = information.getString("formId");

		// 结算计费
		Date date_ = new Date();
		// 让车处于空闲状态，让后续的人可以使用
		VCustomerExample example = new VCustomerExample();

		example.createCriteria().andCustomerIdEqualTo(customerId);
		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		if (customerList.isEmpty()) {
			return PytheResult.build(400, "该用户不存在");
		}
		VCustomer customer = customerList.get(0);

		String carId = null;
		if (null != customer.getCarId()) {
			carId = customer.getCarId();
		} else {
			return PytheResult.build(400, "该车已结束过，无需重复点击");
		}
		String recordId = information.getString("recordId");

		TblCar car = null;
		// 车信息
		// TblCar car = carMapper.selectByPrimaryKey(carId);
		// car.setLatitude(latitude);
		// car.setLongitude(longitude);
		// // car.setUser(null);
		// car.setStatus(CAR_LOCK_STATUS);
		// car.setEndtime(new Date());
		// carMapper.updateByPrimaryKey(car);

		int time = 0;
		Double amount = null;
		Double givingAmount = 0d;

		car = carMapper.selectByPrimaryKey(carId);
		// 更新停止时间和停止位置和记录用的钱
		time = DateUtils.minusForPartHour(date_, car.getStarttime());
		// 处理Bug如果钱是负数就说明用户报的时间不对
		if (time < 0) {
			return PytheResult.build(400, "时间不合法，结束时间不能小于开始时间");
		}

		// 前面10分钟不要钱
		int tmp = time;

		if (tmp >= 0) {
			amount = customer.getPrice();
		} else {
			amount = 0d;
		}
		// amount = EACH_HOUR_PRICE* amount;
		car.setId(customer.getCarId());
		car.setStatus(CAR_FREE_STATUS);
		car.setLatitude(latitude);
		car.setLongitude(longitude);
		car.setEndtime(null);
		car.setUser(null);
		car.setStarttime(null);
		carMapper.updateByPrimaryKey(car);

		// 查看某用户的最近行车记录
		// TblRecord record = recordMapper.selectPreRecordPrimaryKey(customerId,
		// carId);
		TblRecord record = recordMapper.selectByPrimaryKey(recordId);
		String billId = FactoryUtils.getUUID();
		record.setStopTime(date_);
		record.setBillId(billId);
		record.setStatus(RECORD_END_STATUS);
		recordMapper.updateByPrimaryKey(record);

		// 生成账单
		final TblAccount account = accountMapper.selectByPrimaryKey(customerId);
		account.setAmount(account.getAmount() - amount);
		account.setOutAmount(account.getOutAmount() - amount);
		accountMapper.updateByPrimaryKey(account);

		// 更新流水
		final TblBill bill = new TblBill();
		bill.setId(billId);
		bill.setRecordId(recordId);
		bill.setAmount(amount);
		bill.setGivingAmount(0d);
		bill.setRefundAmount(0d);
		if (DEVELOPER_LEVEL.equals(customer.getLevel())) {
			bill.setType(TEST_PAY_TYPE);
		} else {
			bill.setType(BILL_PAY_TYPE);
		}
		bill.setCustomerId(customerId);
		bill.setTime(new Date());
		bill.setRecordId(recordId);

		if (account.getAmount() < amount) {
			bill.setStatus(NOT_PAY_STATUS);
		} else {
			bill.setStatus(PAY_TYPE);
		}
		billMapper.insert(bill);

		// 微信服务通知推送支付结果
		// String url =
		// "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
		// + "&appid=" + WX_APPID
		// + "&secret=" + WX_APPSECRET;
		// String result = HttpClientUtil.doGet(url, null);
		// String access_token =
		// JSONObject.parseObject(result).getString("access_token");
		// // System.out.println("notify===========>" + access_token);
		// //
		// https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
		//
		// JSONObject notifyParameters = new JSONObject();
		//
		// Map<String, Object> datas = new HashMap<String, Object>();
		//
		// Map<String, String> keyValue1 = new HashMap<String, String>();
		// keyValue1.put("value", "￥" + String.valueOf(amount-givingAmount));
		// datas.put("keyword1", keyValue1);
		//
		// Map<String, String> keyValue2 = new HashMap<String, String>();
		// keyValue2.put("value", "总费用￥" + String.valueOf(amount) + "，优惠￥" +
		// String.valueOf(givingAmount));
		// datas.put("keyword2", keyValue2);
		//
		// Map<String, String> keyValue3 = new HashMap<String, String>();
		// // keyValue3.put("value", String.valueOf(time) + "分钟");
		// if (tmp > 0) {
		// keyValue3.put("value", "1天");
		// } else {
		// keyValue3.put("value", String.valueOf(time) + "分钟");
		// }
		//
		// datas.put("keyword3", keyValue3);
		//
		// notifyParameters.put("touser", customer.getXcxOpenId());
		// notifyParameters.put("template_id", NOTIFY_PAY_TEMPLATE_ID);
		// notifyParameters.put("form_id", formId);
		// notifyParameters.put("page", "pages/index/index");
		// notifyParameters.put("data", datas);
		// notifyParameters.put("emphasis_keyword", "keyword1.DATA");
		//
		// String params_json = JsonUtils.objectToJson(notifyParameters);
		// // System.out.println("### params to post =========================>
		// " +
		// // params_json);
		//
		// String xw_url =
		// "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="
		// + access_token;
		// String str = HttpClientUtil.doPostJson(xw_url, params_json);
		//
		// System.out.println("!!!!!=======================>push pay info: " +
		// str);

		// 如果giving 为0 就直接返回回去就行，不用再微信退款请求
		JSONObject json = new JSONObject();
		json.put("price", amount.intValue());
		// json.put("time", time);
		if (tmp > 0) {
			json.put("time", "1天");
		} else {
			json.put("time", time);
		}
		json.put("amount", account.getAmount());
		return PytheResult.build(200, "该车成功结束,祝你旅途愉快", json);
	}

	@Override
	public PytheResult checkLockStatusEncode(String parameters) {
		JSONObject information = JSONObject.parseObject(parameters);
		String carId = information.getString("carId");
		String token = information.getString("token");

		TblCar car = carMapper.selectByPrimaryKey(carId);

		byte head[] = { 0x05, 0x0E, 0x01, 0x01 };
		byte passwordBytes[] = NumberUtils.parseHexArray2ByteArray(car.getLockPassword().split(","));
		byte tokenBytes[] = NumberUtils.parseHexStr2Byte(token);
		byte[] s = new byte[tokenBytes.length];
		System.arraycopy(tokenBytes, 0, s, 0, tokenBytes.length);

		try {
			byte[] sSrc = new byte[head.length + s.length + 8];
			System.arraycopy(head, 0, sSrc, 0, head.length);
			System.arraycopy(s, 0, sSrc, head.length, s.length);
			System.out.println("============================> check lock status frame: "
					+ NumberUtils.parseByteArray2HexArray(sSrc));
			SecretKeySpec skeySpec = new SecretKeySpec(NumberUtils.parseHexArray2ByteArray(car.getLockKey().split(",")),
					"AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc);
			String encryptedStr = new String(Base64.encodeBase64(encrypted));

			return PytheResult.ok(encryptedStr);
		} catch (Exception ex) {
			System.out.println("==================> exception: " + ex);
			return PytheResult.build(400, "异常错误，解密失败");
		}

	}

	@Override
	public PytheResult updateCustomerAccount() {
		// TODO Auto-generated method stub
		TblRecordExample example = new TblRecordExample();
		example.createCriteria().andStartTimeBetween(DateUtils.parseTime("2018-03-03 01:01:13"),
				DateUtils.parseTime("2018-03-04 23:15:59"));
		List<TblRecord> recordList = recordMapper.selectByExample(example);
		TblAccountExample aexample = new TblAccountExample();
		List<TblAccount> accountList = accountMapper.selectByExample(aexample);

		for (TblRecord tblRecord : recordList) {
			for (TblAccount tblAccount : accountList) {
				if (tblRecord.getCustomerId().equals(tblAccount.getCustomerId()) && tblAccount.getLevel() != 1l) {
					tblAccount.setAmount(0d);
					accountMapper.updateByPrimaryKey(tblAccount);
					break;
				}
			}
		}

		return PytheResult.ok("更新成功");
	}


	@Override
	public PytheResult refundByTopManager(String parameters) {
		// 该功能用于解决充值不用车情况
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		Long managerId = information.getLong("managerId");
		// 没得选择，就是全额退款
		// Double refund = information.getDouble("refund");

		// 判断管理员权限
		TblCustomer manager = customerMapper.selectByPrimaryKey(managerId);

		if (manager.getLevel() <= 0) {
			return PytheResult.build(400, "权限不够");
		}

		// 判断用户使用情况
		VCustomerExample example = new VCustomerExample();
		example.createCriteria().andPhoneNumEqualTo(phoneNum);

		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		if (customerList.isEmpty()) {
			return PytheResult.build(400, "该用户不存在");
		}

		VCustomer customer = customerList.get(0);
		Long customerId = customer.getCustomerId();

		// 车必须已经释放，才能退（无行程退款）
		if (customer.getCarId() != null) {
			return PytheResult.build(400, "用户未结束用车，为保证数据正常，请使用停止计费功能");
		}

		// 改为无论用户是否停车，都可以退钱
		// 如果还在车上就不给退款
		if (null != customer.getCarId()) {
			return PytheResult.build(400, "抱歉，请结束用车后，才可退款");
		}

		// 用于返回给用户使用
		// 生成账单
		TblAccount account = accountMapper.selectByPrimaryKey(customerId);

		// if (account.getAmount() < refund) {
		// return PytheResult.build(400, "不合法退款：累计退款金额大于支付金额");
		// }

		// 用户在车上，更新并退款。用户不在车上，只退款，不更新数据
		TblBillExample example2 = new TblBillExample();
		example2.createCriteria().andTypeEqualTo(BILL_CHARGE_TYPE).andStatusEqualTo(1).andCustomerIdEqualTo(customerId);
		example2.setOrderByClause("time DESC");
		List<TblBill> billList = billMapper.selectByExample(example2);
		if (!billList.isEmpty()) {
			// 更新bill退款订单号
			TblBill bi = billList.get(0);
			Double bi_amount = bi.getAmount() * 100;
			String refundMoney = String.valueOf(bi_amount.intValue());
			// 说明用户现有金额小于退款
			if (account.getAmount() < bi.getAmount()) {
				return PytheResult.build(400, "不合法退款：用户现有金额小于退款金额");
			}
			String total = String.valueOf(bi_amount.intValue());
			// System.out.println("============>"+bi_amount.intValue());
			String str = refundByOrderInWX(bi.getOutTradeNo(), total, refundMoney);
			// System.out.println("=====================>bi"+bi.getPrepayId() +"
			// "+bi.getOutTradeNo() + " "+bi.getType());
			// System.out.println("===================>" + str);
			if (str.indexOf("SUCCESS") != -1 && !str.contains("订单已全额退款") && !str.contains("累计退款金额大于支付金额")) {

				account.setAmount(account.getAmount() - bi.getAmount());
				account.setOutAmount(account.getOutAmount() - bi.getAmount());
				accountMapper.updateByPrimaryKey(account);
				// 看看更新后的账单是否为正数，如果是，证明扣费成功
				JSONObject json = new JSONObject();
				json.put("price", bi.getAmount());
				json.put("amount", account.getAmount());
				return PytheResult.build(200, "退款成功", json);
				// 如果用户在车上才更新数据
				// if (null!=customer.getCarId()) {
				// TblBill bill = new TblBill();
				// String billId = FactoryUtils.getUUID();
				// String recordId = customer.getRecordId();
				// //更新记录
				// TblRecord line = recordMapper.selectByPrimaryKey(recordId);
				// line.setStopTime(new Date());
				// line.setBillId(billId);
				// recordMapper.updateByPrimaryKey(line);
				// // 更新退款流水
				// bill.setId(billId);
				// bill.setAmount(customer.getPrice());
				// bill.setRefundAmount(customer.getGiving());
				// bill.setGivingAmount(0d);
				// if (customer.getPrice()>customer.getGiving()) {
				// bill.setType(PART_REFUND_TYPE);
				// }else{
				// bill.setType(TOTAL_REFUND_TYPE);
				// }
				// bill.setCustomerId(customerId);
				// bill.setTime(new Date());
				// bill.setStatus(PAY_TYPE);
				// bill.setPrepayId(bi.getPrepayId());
				// bill.setOutTradeNo(bi.getOutTradeNo());
				// billMapper.insert(bill);
				//
				// account.setAmount(account.getAmount() - refund);
				// account.setOutAmount(account.getOutAmount() - refund);
				// accountMapper.updateByPrimaryKey(account);
				// }
			} else {
				return PytheResult.build(400, "退款失败,请联系客服人员");
			}
		}

		// 退款失败要有条数据
		return PytheResult.build(400, "退款失败,请联系客服人员");
	}

	@Override
	public PytheResult testRestRefund(String parameters) {
		// 该功能用于解决充值不用车情况
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		Long managerId = information.getLong("managerId");

		// 没得选择，就是全额退款
		// Double refund = information.getDouble("refund");

		// 判断管理员权限
		TblCustomer manager = customerMapper.selectByPrimaryKey(managerId);

		if (manager.getLevel() <= 0) {
			return PytheResult.build(400, "权限不够");
		}

		// 判断用户使用情况
		VCustomerExample example = new VCustomerExample();
		example.createCriteria().andPhoneNumEqualTo(phoneNum);

		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		if (customerList.isEmpty()) {
			return PytheResult.build(400, "该用户不存在");
		}

		VCustomer customer = customerList.get(0);
		Long customerId = customer.getCustomerId();

		// 车必须已经释放，才能退（无行程退款）
		if (customer.getCarId() != null) {
			return PytheResult.build(400, "用户未结束用车，为保证数据正常，请使用停止计费功能");
		}

		// 改为无论用户是否停车，都可以退钱
		// 如果还在车上就不给退款
		if (null != customer.getCarId()) {
			return PytheResult.build(400, "抱歉，请结束用车后，才可退款");
		}

		// 用于返回给用户使用
		// 生成账单
		TblAccount account = accountMapper.selectByPrimaryKey(customerId);

		// if (account.getAmount() < refund) {
		// return PytheResult.build(400, "不合法退款：累计退款金额大于支付金额");
		// }

		// 用户在车上，更新并退款。用户不在车上，只退款，不更新数据
		TblBillExample example2 = new TblBillExample();
		example2.createCriteria().andTypeEqualTo(BILL_CHARGE_TYPE).andStatusEqualTo(1).andCustomerIdEqualTo(customerId);
		example2.setOrderByClause("time DESC");
		List<TblBill> billList = billMapper.selectByExample(example2);
		if (!billList.isEmpty()) {
			// 更新bill退款订单号
			TblBill bi = billList.get(0);
			Double bi_amount = bi.getAmount() * 100;
			Double refund_amount = bi.getRefundAmount() * 100;
			Double rest_amount = bi_amount - refund_amount;
			String total = String.valueOf(bi_amount.intValue());
			// System.out.println("============>"+bi_amount.intValue());
			// System.out.println("=====================>bi"+bi.getOutTradeNo());
			String str = refundByOrderInWX(bi.getOutTradeNo(), total, String.valueOf(rest_amount.intValue()));

			// System.out.println("===================>" + str);
			if (str.indexOf("SUCCESS") != -1 && !str.contains("订单已全额退款") && !str.contains("累计退款金额大于支付金额")) {

				// 注意： 退回20后，这单要记录这张单已经退回20，目的是为了未来能退回剩的余额
				bi.setRefundAmount(bi.getRefundAmount() + (rest_amount / 100));
				billMapper.updateByPrimaryKey(bi);

				// 看看更新后的账单是否为正数，如果是，证明扣费成功
				JSONObject json = new JSONObject();
				json.put("price", bi.getAmount());
				json.put("amount", account.getAmount());
				return PytheResult.build(200, "退款成功", json);
			} else {
				return PytheResult.build(400, "退款失败,请联系客服人员");
			}
		}

		// 退款失败要有条数据
		return PytheResult.build(400, "退款失败,请联系客服人员");
	}

	@Override
	@Transactional
	public PytheResult refundToTripByManager(String parameters) {
		// TODO Auto-generated method stub

		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		// String date = information.getString("date");
		Long managerId = information.getLong("managerId");

		// final Date date_ = DateUtils.parseTime(date);
		String today = DateUtils.getTodayDate();
		// 让车处于空闲状态，让后续的人可以使用
		VCustomerExample example = new VCustomerExample();

		if (phoneNum.length() == 11) {
			example.createCriteria().andPhoneNumEqualTo(phoneNum);
		} else {
			example.createCriteria().andQrIdEqualTo(Long.valueOf(phoneNum));
		}

		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		if (customerList.isEmpty()) {
			return PytheResult.build(400, "该用户不存在");
		}

		VCustomer customer = customerList.get(0);
		Long customerId = customer.getCustomerId();
		String carId = null;
		if (customer.getCarId() == null) {
			return PytheResult.build(400, "该车不在用车中，无法全额退款");
		} else {
			carId = customer.getCarId();
		}

		TblCar car = carMapper.selectByPrimaryKey(carId);
		String recordId = customer.getRecordId();
		TblRecord record = recordMapper.selectByPrimaryKey(recordId);
		String startTime = DateUtils.formatDate(record.getStartTime());
		if (!startTime.equals(DateUtils.getTodayDate())) {
			return PytheResult.build(400, "当天无行程，无法全额退款");
		}

		// 释放车
		car.setId(customer.getCarId());
		car.setStatus(CAR_FREE_STATUS);
		car.setEndtime(null);
		car.setUser(null);
		car.setStarttime(null);
		car.setRecordid(null);
		carMapper.updateByPrimaryKey(car);

		// 查看某用户的最近行车记录
		String billId = FactoryUtils.getUUID();
		record.setStopTime(new Date());
		record.setBillId(billId);
		record.setStatus(RECORD_END_STATUS);
		recordMapper.updateByPrimaryKey(record);

		// 用户要退的钱
		Double price = customer.getPrice();

		// 更新流水
		// 即使是人工，这20元也一定给，但是人工给用户退款。
		final TblBill bill = new TblBill();
		bill.setId(billId);
		bill.setRecordId(recordId);
		bill.setAmount(price);
		bill.setRefundAmount(price);
		bill.setGivingAmount(0d);

		if (DEVELOPER_LEVEL.equals(customer.getLevel())) {
			bill.setType(TEST_PAY_TYPE);
		} else {
			bill.setType(TOTAL_REFUND_TYPE);
		}

		bill.setCustomerId(customerId);
		bill.setTime(new Date());
		bill.setRecordId(recordId);
		bill.setManagerId(managerId);

		// 生成账单
		TblAccount account = accountMapper.selectByPrimaryKey(customerId);

		// 退回用户20元现金
		TblBillExample example2 = new TblBillExample();
		example2.createCriteria().andCustomerIdEqualTo(customerId).andTypeEqualTo(BILL_CHARGE_TYPE).andStatusEqualTo(1);
		example2.setOrderByClause("time DESC");
		List<TblBill> billList = billMapper.selectByExample(example2);
		if (!billList.isEmpty()) {
			TblBill bi = billList.get(0);

			Double p = price * 100;
			String price_str = String.valueOf(p.intValue());
			String str = refundByOrderInWX(bi.getOutTradeNo(), price_str, price_str);
			if (str.indexOf("SUCCESS") != -1 && !str.contains("订单已全额退款") && !str.contains("累计退款金额大于支付金额")) {
				// 注意： 退回20后，这单要记录这张单已经退回20，目的是为了未来能退回剩的余额
				bi.setRefundAmount(price);
				billMapper.updateByPrimaryKey(bi);

				// 支付成功后再扣
				accountMapper.updateByPrimaryKey(account);

				// 退款成功
				bill.setOutTradeNo(bi.getOutTradeNo());
				bill.setPrepayId(bi.getPrepayId());
				bill.setStatus(PAY_TYPE);
				billMapper.insert(bill);

				// 看看更新后的账单是否为正数，如果是，证明扣费成功
				JSONObject json = new JSONObject();
				json.put("price", price);
				return PytheResult.build(200, "结算成功", json);
			}
		}
		// 这种情况是，因为退款不成功,所以退款金额为0
		bill.setStatus(NOT_PAY_STATUS);
		billMapper.insert(bill);
		return PytheResult.build(400, "退款失败，具体原因，请咨询Ingcart出行");

	}

	@Override
	public PytheResult refundUnconditionally(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		// String date = information.getString("date");
		Long managerId = information.getLong("managerId");

		// final Date date_ = DateUtils.parseTime(date);
		String today = DateUtils.getTodayDate();
		// 让车处于空闲状态，让后续的人可以使用
		VCustomerExample example = new VCustomerExample();

		if (phoneNum.length() == 11) {
			example.createCriteria().andPhoneNumEqualTo(phoneNum);
		} else {
			example.createCriteria().andQrIdEqualTo(Long.valueOf(phoneNum));
		}

		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		if (customerList.isEmpty()) {
			return PytheResult.build(400, "该用户不存在");
		}

		VCustomer customer = customerList.get(0);
		Long customerId = customer.getCustomerId();
		String carId = null;
		if (customer.getCarId() != null) {
			return PytheResult.build(400, "退款失败，该车行程未结束，请结束后重试");
		}

		VAcountRecordExample acountRecordExample = new VAcountRecordExample();
		acountRecordExample.createCriteria().andCustomerIdEqualTo(customerId);
		List<VAcountRecord> accountRecordList = acountRecordMapper.selectByExample(acountRecordExample);
		if (accountRecordList.isEmpty()) {
			return PytheResult.build(400, "无行程退款，请选用全额退款（无行程）退款");
		}

		// 退回用户20元现金
		TblBillExample example2 = new TblBillExample();
		example2.createCriteria().andCustomerIdEqualTo(customerId).andTypeEqualTo(BILL_CHARGE_TYPE).andStatusEqualTo(1);
		example2.setOrderByClause("time DESC");
		List<TblBill> billList = billMapper.selectByExample(example2);
		if (!billList.isEmpty()) {
			TblBill bi = billList.get(0);
			Double price = bi.getAmount() * 100;
			Double refund = bi.getRefundAmount() * 100;
			Double rest = price - refund;
			if (rest.equals(0d)) {
				return PytheResult.build(400, "已全额退款");
			}

			String rest_str = String.valueOf(rest.intValue());
			String price_str = String.valueOf(price.intValue());
			String str = refundByOrderInWX(bi.getOutTradeNo(), price_str, rest_str);
			if (str.indexOf("SUCCESS") != -1 && !str.contains("订单已全额退款") && !str.contains("累计退款金额大于支付金额")) {

				// 退款成功后清0
				TblAccount account = accountMapper.selectByPrimaryKey(customerId);
				account.setOutAmount(account.getOutAmount() + account.getAmount());
				account.setAmount(0d);
				accountMapper.updateByPrimaryKey(account);

				VAcountRecord accountRecord = accountRecordList.get(0);
				// 修正之前的使用记录
				String billId = accountRecord.getBillId();
				TblBill bill = new TblBill();
				bill.setId(billId);
				bill.setRefundAmount(bill.getRefundAmount() + rest / 100d);
				billMapper.updateByPrimaryKeySelective(bill);
				billMapper.updateByPrimaryKey(bi);

				// 退款成功
				bill.setOutTradeNo(bi.getOutTradeNo());
				bill.setPrepayId(bi.getPrepayId());
				bill.setStatus(PAY_TYPE);
				if (DEVELOPER_LEVEL.equals(customer.getLevel())) {
					bill.setType(TEST_PAY_TYPE);
				} else {
					bill.setType(TOTAL_REFUND_TYPE);
				}
				billMapper.insert(bill);

				// 看看更新后的账单是否为正数，如果是，证明扣费成功
				JSONObject json = new JSONObject();
				json.put("price", price);
				return PytheResult.build(200, "结算成功", json);
			}
		}
		return PytheResult.build(400, "退款失败，具体原因，请咨询Ingcart出行");
	}

	private int giveCoupon(Long managerId, Double price) {
		// 送用户一张券
		TblCoupon coupon = new TblCoupon();
		// 产生不重复的为8位随机数
		String code = null;
		for (int i = 0; i <= 100; i++) {
			code = StringUtils.getStringRandom(8);
			TblCouponExample couponExample = new TblCouponExample();
			couponExample.createCriteria().andStatusNotEqualTo(0).andCodeEqualTo(code);
			List<TblCoupon> couponList = couponMapper.selectByExample(couponExample);
			if (couponList.isEmpty()) {
				break;
			}
		}

		coupon.setCode(code);
		coupon.setStopTime(DateUtils.parseTime(DateUtils.getTodayDate() + " 23:00:00"));
		coupon.setStatus(0);
		coupon.setManagerId(managerId);
		coupon.setType(0);
		coupon.setAmount(price);
		coupon.setStartTime(new Date());
		return couponMapper.insert(coupon);
	}
	
	
	

	@Override
	public PytheResult transferCar(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		// String date = information.getString("date");
		Long managerId = information.getLong("managerId");

		// final Date date_ = DateUtils.parseTime(date);
		Date date_ = new Date();
		// 让车处于空闲状态，让后续的人可以使用
		VCustomerExample example = new VCustomerExample();

		if (phoneNum.length() == 11) {
			example.createCriteria().andPhoneNumEqualTo(phoneNum);
		} else {
			example.createCriteria().andQrIdEqualTo(Long.valueOf(phoneNum));
		}

		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		if (customerList.isEmpty()) {
			return PytheResult.build(400, "该用户不存在");
		}

		VCustomer customer = customerList.get(0);
		Long customerId = customer.getCustomerId();

		if (null == customer.getCarStatus()) {
			return PytheResult.build(400, "无占用任何车辆,故无法换车");
		}

		// 维修换车，不扣钱。
		String recordId = customer.getRecordId();
		if (3 == customer.getCarStatus()) {
			// 删除之前的用车记录
			recordMapper.deleteByPrimaryKey(recordId);
			giveCoupon(managerId, customer.getPrice() - customer.getGiving());
			return PytheResult.ok("优惠券赠送成功");
		}

		TblCar car = carMapper.selectByPrimaryKey(customer.getCarId());
		// 如下是用户结束用车，退还押金，并赠送券
		String giving = String.valueOf((customer.getGiving().intValue() * 100));
		Double amount = customer.getPrice();
		Double givingAmount = customer.getGiving();

		car.setId(customer.getCarId());
		car.setStatus(CAR_FREE_STATUS);
		car.setEndtime(null);
		car.setUser(null);
		car.setStarttime(null);
		car.setRecordid(null);
		carMapper.updateByPrimaryKey(car);

		// 查看某用户的最近行车记录
		TblRecord record = recordMapper.selectByPrimaryKey(recordId);
		String billId = FactoryUtils.getUUID();
		record.setStopTime(date_);
		record.setBillId(billId);
		record.setStatus(RECORD_END_STATUS);
		recordMapper.updateByPrimaryKey(record);

		// 更新流水
		// 即使是人工，这20元也一定给，但是人工给用户退款。
		final TblBill bill = new TblBill();
		bill.setId(billId);
		bill.setRecordId(recordId);
		bill.setAmount(amount);
		bill.setRefundAmount(givingAmount);
		bill.setGivingAmount(0d);

		if (DEVELOPER_LEVEL.equals(customer.getLevel())) {
			bill.setType(TEST_PAY_TYPE);
		} else {
			bill.setType(PART_REFUND_TYPE);
		}

		bill.setCustomerId(customerId);
		bill.setTime(new Date());
		bill.setRecordId(recordId);
		bill.setManagerId(managerId);

		// 生成账单
		TblAccount account = accountMapper.selectByPrimaryKey(customerId);

		account.setAmount(account.getAmount() - amount);
		account.setOutAmount(account.getOutAmount() - amount);

		// 如果giving 为0 就直接返回回去就行，不用再微信退款请求
		if ("0".equals(giving)) {
			// 直接扣
			accountMapper.updateByPrimaryKey(account);
			// 如果是没有退，就不更新腾讯的退款订单号
			bill.setStatus(PAY_TYPE);
			billMapper.insert(bill);

			// 送一张优惠券
			giveCoupon(managerId, customer.getPrice() - customer.getGiving());
			return PytheResult.ok("优惠券赠送成功");
		}

		// 退回用户20元现金
		TblBillExample example2 = new TblBillExample();
		example2.createCriteria().andStatusEqualTo(1).andTypeEqualTo(BILL_CHARGE_TYPE).andCustomerIdEqualTo(customerId);
		example2.setOrderByClause("time DESC");
		List<TblBill> billList = billMapper.selectByExample(example2);
		if (!billList.isEmpty()) {
			TblBill bi = billList.get(0);

			String str = refundByOrderInWX(bi.getOutTradeNo(), String.valueOf((bi.getAmount().intValue() * 100)),
					giving);
			// System.out.println("============>"+str);
			if (str.indexOf("SUCCESS") != -1 && !str.contains("订单已全额退款") && !str.contains("累计退款金额大于支付金额")) {

				// 注意： 退回20后，这单要记录这张单已经退回20，目的是为了未来能退回剩的余额
				bi.setRefundAmount(bi.getRefundAmount() + givingAmount);
				billMapper.updateByPrimaryKey(bi);

				// 支付成功后再扣
				accountMapper.updateByPrimaryKey(account);

				// 退款成功
				bill.setOutTradeNo(bi.getOutTradeNo());
				bill.setPrepayId(bi.getPrepayId());
				bill.setStatus(PAY_TYPE);
				billMapper.insert(bill);

				// 送一张优惠券
				giveCoupon(managerId, customer.getPrice() - customer.getGiving());
				return PytheResult.ok("优惠券赠送成功");
			}
		}

		// 这种情况是，因为退款不成功,优惠券没有，并没有扣除任何金额
		bill.setStatus(NOT_PAY_STATUS);
		billMapper.insert(bill);

		return PytheResult.build(400, "优惠券已赠送，但退款失败，具体原因，请咨询开发人员");

	}

	@Override
	public PytheResult manageUnlock(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		Long managerId = information.getLong("managerId");
		Long qrId = information.getLong("qrId");
		Double longitude = information.getDouble("longitude");
		Double latitude = information.getDouble("latitude");
		String recordId = FactoryUtils.getUUID();

		TblOperatorRecord record =new TblOperatorRecord();
		record.setId(recordId);
		record.setLatitudeStart(latitude);
		record.setLongitdeStart(longitude);
		record.setOperatorId(managerId);
		//车使用中
		record.setStatus(RECORD_USER_STATUS);
		record.setQrId(qrId);
		record.setStartTime(new Date());
		operatorRecordMapper.insert(record);
		
		return PytheResult.ok("开锁成功");
	}
	
	
	
	
	
	
}
