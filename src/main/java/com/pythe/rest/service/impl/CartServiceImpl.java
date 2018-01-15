package com.pythe.rest.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.DateUtils;
import com.pythe.common.utils.DecodeUtils;
import com.pythe.common.utils.EncodeUtils;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.common.utils.HttpClientUtil;
import com.pythe.common.utils.JsonUtils;
import com.pythe.common.utils.NumberUtils;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblBillMapper;
import com.pythe.mapper.TblCarMapper;
import com.pythe.mapper.TblCustomerMapper;
import com.pythe.mapper.TblHoldRecordMapper;
import com.pythe.mapper.TblRecordMapper;
import com.pythe.mapper.TblStoreMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblBillExample;
import com.pythe.pojo.TblCar;
import com.pythe.pojo.TblCarExample;
import com.pythe.pojo.TblCustomer;
import com.pythe.pojo.TblCustomerExample;
import com.pythe.pojo.TblHoldRecord;
import com.pythe.pojo.TblHoldRecordExample;
import com.pythe.pojo.TblRecord;
import com.pythe.pojo.TblRecordExample;
import com.pythe.pojo.TblStore;
import com.pythe.pojo.TblStoreExample;
import com.pythe.rest.service.CartService;
import com.pythe.rest.service.PayService;

import net.sf.jsqlparser.expression.JsonExpression;

@Service
public class CartServiceImpl implements CartService {

	@Value("${EACH_HOUR_PRICE}")
	private Integer EACH_HOUR_PRICE;

	@Value("${NOT_PAY_STATUS}")
	private Integer NOT_PAY_STATUS;

	@Value("${PAY_STATUS}")
	private Integer PAY_STATUS;

	@Value("${PAY_TYPE}")
	private Integer PAY_TYPE;

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

	@Autowired
	private TblCustomerMapper customerMapper;

	@Autowired
	private TblRecordMapper recordMapper;

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

	@Override
	public PytheResult unlock(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		final Long customerId = information.getLong("customerId");
		final String carId = information.getString("carId");
		final Double longitude = information.getDouble("longitude");
		final Double latitude = information.getDouble("latitude");
		final String recordId = FactoryUtils.getUUID();

		////1、车有被保留，能成功的情况。
		TblCar car = carMapper.selectByPrimaryKey(carId);

		if (2 == car.getStatus()) {
			// 是，解除状态，将状态变为使用状态
			car.setLatitude(latitude);
			car.setLongitude(longitude);
			car.setUser(customerId);
			// car.setRecordid(recordId); 因为第一次开锁时候就已经记录的该车属于那条记录
			car.setStatus(CAR_USER_STATUS);
			carMapper.updateByPrimaryKey(car);

			// 将锁的计时器释放掉
			TblHoldRecordExample example = new TblHoldRecordExample();
			example.createCriteria().andCustomerIdEqualTo(customerId).andCarIdEqualTo(carId).andStatusEqualTo(0);
			TblHoldRecord holdRecord = new TblHoldRecord();
			holdRecord.setStatus(1);
			holdRecordMapper.updateByExampleSelective(holdRecord, example);
			return PytheResult.ok("开锁成功");
		}
		
		//2、车没有被保留，能成功的情况。
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
		final Double longitude = information.getDouble("longitude");
		final Double latitude = information.getDouble("latitude");
		String carId = information.getString("carId");
		final String recordId = information.getString("recordId");

		// 车信息
		TblCar car = carMapper.selectByPrimaryKey(carId);
		car.setLatitude(latitude);
		car.setLongitude(longitude);
		// car.setUser(null);
		car.setStatus(CAR_LOCK_STATUS);
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
	public PytheResult selectCouponByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PytheResult selectCartPositionByMap(Double longitude, Double latitude) {
		// TODO Auto-generated method stub
		LinkedList<JSONObject> list = new LinkedList<JSONObject>();

		TblCarExample example = new TblCarExample();
		example.createCriteria().andLatitudeGreaterThanOrEqualTo(latitude - 2)
				.andLatitudeLessThanOrEqualTo(latitude + 2).andLongitudeGreaterThan(longitude - 2)
				.andLongitudeLessThanOrEqualTo(longitude + 2)
				.andStatusEqualTo(CAR_FREE_STATUS);
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
		storeExample.createCriteria().andLatitudeGreaterThanOrEqualTo(latitude - 2)
				.andLatitudeLessThanOrEqualTo(latitude + 2).andLongitudeGreaterThan(longitude - 2)
				.andLongitudeLessThanOrEqualTo(longitude + 2);

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
		final String recordId = information.getString("recordId");
		String carId = information.getString("carId");
		final Long customerId = information.getLong("customerId");

		final String formId = information.getString("formId");

		// 更新车的位置和使用情况和结束时间
		// 让车处于空闲状态，让后续的人可以使用
		TblCar car = carMapper.selectByPrimaryKey(carId);
		car.setStatus(CAR_FREE_STATUS);
		car.setEndtime(new Date());
		car.setUser(null);
		carMapper.updateByPrimaryKey(car);

		// 更新停止时间和停止位置和记录用的钱
		int time = DateUtils.minusForPartHour(car.getEndtime(), car.getStarttime());
		Double amount = null;
		if (time % 30 == 0) {
			amount = Math.floor(time / 30) * EACH_HOUR_PRICE;
		} else {
			amount = (Math.floor(time / 30) + 1) * EACH_HOUR_PRICE;
		}

		final String billId = FactoryUtils.getUUID();
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

		// 微信服务通知推送支付结果
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" + "&appid=" + WX_APPID
				+ "&secret=" + WX_APPSECRET;
		String result = HttpClientUtil.doGet(url, null);
		String access_token = JSONObject.parseObject(result).getString("access_token");
		//System.out.println("notify===========>" + access_token);
		// https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN

		JSONObject notifyParameters = new JSONObject();

		Map<String, Object> datas = new HashMap<String, Object>();

		Map<String, String> keyValue1 = new HashMap<String, String>();
		keyValue1.put("value", "￥" + String.valueOf(bill.getAmount()));
		datas.put("keyword1", keyValue1);

		Map<String, String> keyValue2 = new HashMap<String, String>();
		keyValue2.put("value", "总费用￥" + String.valueOf(bill.getAmount()) + "，优惠￥" + String.valueOf(0d));
		datas.put("keyword2", keyValue2);

		Map<String, String> keyValue3 = new HashMap<String, String>();
		keyValue3.put("value", String.valueOf(time) + "分钟");
		datas.put("keyword3", keyValue3);

		notifyParameters.put("touser", customerMapper.selectByPrimaryKey(customerId).getOpenId());
		notifyParameters.put("template_id", NOTIFY_PAY_TEMPLATE_ID);
		notifyParameters.put("form_id", formId);
		notifyParameters.put("page", "pages/index/index");
		notifyParameters.put("data", datas);
		notifyParameters.put("emphasis_keyword", "keyword1.DATA");

		String params_json = JsonUtils.objectToJson(notifyParameters);
		//System.out.println("### params to post =========================> " + params_json);

		String xw_url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + access_token;
		String str = HttpClientUtil.doPostJson(xw_url, params_json);

		//System.out.println("!!!!!=======================>push pay info: " + str);
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
			carMapper.updateByPrimaryKey(car);

			// 更新停止时间和停止位置和记录用的钱
			int time = DateUtils.minusForPartHour(car.getEndtime(), car.getStarttime());
			Double amount = null;
			if (time % 30 == 0) {
				amount = Math.floor(time / 30) * EACH_HOUR_PRICE;
			} else {
				amount = (Math.floor(time / 30) + 1) * EACH_HOUR_PRICE;
			}

			final String billId = FactoryUtils.getUUID();

			final String recordId = holdrecord.getRecordId();

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
		// TODO Auto-generated method stub
		// 说明用户已经计时完成
		Date currentTime = new Date();
		TblCar car = carMapper.selectByPrimaryKey(carId);
		Date startTime = car.getStarttime();
		long time = currentTime.getTime() - startTime.getTime();
		JSONObject json = new JSONObject();
		json.put("time", time / 1000 / 60);
		return PytheResult.ok(json);
	}
	
	
	
	

	@Override
	public PytheResult deleteAppointmentByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
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
		car.setUser(null);
		carMapper.updateByPrimaryKey(car);

		// 更新停止时间和停止位置和记录用的钱
		// 更新停止时间和停止位置和记录用的钱
		int time = DateUtils.minusForPartHour(car.getEndtime(), car.getStarttime());
		Double amount = null;
		if (time % 30 == 0) {
			amount = Math.floor(time / 30) * EACH_HOUR_PRICE;
		} else {
			amount = (Math.floor(time / 30) + 1) * EACH_HOUR_PRICE;
		}

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
			System.out.println("============================> unlock frame: " + NumberUtils.parseByteArray2HexArray(sSrc));
			SecretKeySpec skeySpec = new SecretKeySpec(NumberUtils.parseHexArray2ByteArray(car.getLockKey().split(",")), "AES");
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
		
		//判断该用户是否占用了某台车
		TblCarExample example = new TblCarExample();
		example.createCriteria().andUserEqualTo(customerId);
		List<TblCar> carList = carMapper.selectByExample(example);
		if (!carList.isEmpty()) {
			return PytheResult.build(202,"抱歉，一个帐号只能使用一台车");
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
				return PytheResult.build(500, "抱歉，该车已被预约保留");
			}
			break;
			// // 是，解除状态，将状态变为使用状态
			// car.setLatitude(latitude);
			// car.setLongitude(longitude);
			// car.setUser(customerId);
			// // car.setRecordid(recordId); 因为第一次开锁时候就已经记录的该车属于那条记录
			// car.setStatus(CAR_USER_STATUS);
			// carMapper.updateByPrimaryKey(car);
			//
			// // 将锁的计时器释放掉
			// TblHoldRecordExample example = new TblHoldRecordExample();
			// example.createCriteria().andCustomerIdEqualTo(customerId).andCarIdEqualTo(carId).andStatusEqualTo(0);
			// TblHoldRecord holdRecord = new TblHoldRecord();
			// holdRecord.setStatus(1);
			// holdRecordMapper.updateByExampleSelective(holdRecord, example);
			// return PytheResult.ok("开锁成功");
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

		if (car==null) {
			return PytheResult.build(400, "查无此车");
		} else {
			return PytheResult.ok(car);
		}
	}

	@Override
	public PytheResult prepareUnlockGyQrId(Long customerId, Long qrId) {
		//判断该用户是否占用了某台车
		TblCarExample example = new TblCarExample();
		example.createCriteria().andUserEqualTo(customerId);
		List<TblCar> carList = carMapper.selectByExample(example);
		if (!carList.isEmpty()) {
			return PytheResult.build(202,"抱歉，一个帐号只能使用一台车");
		}
		
		// 看看用户消费情况
		TblAccount account = accountMapper.selectByPrimaryKey(customerId);

		if (account.getAmount() < EACH_HOUR_PRICE) {
			return PytheResult.build(300, "余额不足前往充值", account.getAmount());
		}
		
		// 看看车的状态
		TblCarExample example2 = new TblCarExample();
		example2.createCriteria().andQrIdEqualTo(qrId);
		List<TblCar> carList2 = carMapper.selectByExample(example2);
		
		if (carList2.isEmpty()) {
			PytheResult.build(100, "该车信息尚未录入，暂无法使用");
		}
		TblCar car = carList2.get(0);

		Integer status = car.getStatus();
		switch (status) {
		case 1: {
			return PytheResult.build(400, "此车正在使用中");
		}
		case 2: {
			// 是否是该用户继续使用
			if (car.getUser() != customerId) {
				return PytheResult.build(500, "抱歉，该车已被预约保留");
			}
			break;
		}
		case 3: {
			return PytheResult.build(600, "此车有故障，请换车扫码");
		}
		}
		return PytheResult.ok("车安全检测通过，请放心使用");
	}
}
