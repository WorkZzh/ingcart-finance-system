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
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblBillMapper;
import com.pythe.mapper.TblCarMapper;
import com.pythe.mapper.TblCustomerMapper;
import com.pythe.mapper.TblHoldRecordMapper;
import com.pythe.mapper.TblRecordMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblCar;
import com.pythe.pojo.TblCarExample;
import com.pythe.pojo.TblCustomer;
import com.pythe.pojo.TblCustomerExample;
import com.pythe.pojo.TblHoldRecord;
import com.pythe.pojo.TblRecord;
import com.pythe.pojo.TblRecordExample;
import com.pythe.rest.service.CartService;
import com.pythe.rest.service.PayService;

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

	@Value("${CAR_MAINTENCE_STATUS}")
	private Integer CAR_MAINTENCE_STATUS;
	
	//BILL
	@Value("${BILL_CHARGE_TYPE}")
	private Integer BILL_CHARGE_TYPE;

	@Value("${BILL_PAY_TYPE}")
	private Integer BILL_PAY_TYPE;

	@Autowired
	private TblRecordMapper recordMapper;

	@Autowired
	private TblBillMapper billMapper;

	@Autowired
	private TblAccountMapper accountMapper;

	@Autowired
	private TblCarMapper carMapper;

	@Autowired
	private TblHoldRecordMapper holdRecordMapper;

	@Override
	public PytheResult unloke(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		final Long customerId = information.getLong("customerId");
		final String carId = information.getString("carId");
		final Double longitude = information.getDouble("longitude");
		final Double latitude = information.getDouble("latitude");

		// 看看用户消费情况
		TblAccount account = accountMapper.selectByPrimaryKey(customerId);
		if (account.getAmount() <= 0) {
			return PytheResult.build(400, "余额不足前往充值");
		}

		// 看看车的状态
		TblCar car = carMapper.selectByPrimaryKey(carId);
		if (car == null) {
			PytheResult.ok("该车信息尚未录入，暂无法使用");
		}

		Integer status = car.getStatus();
		switch (status) {
		case 1: {
			return PytheResult.build(400, "该车正在使用中");
		}
		case 2: {
			// 是否是该用户继续使用
			// 不是

			if (car.getUser() != null) {
				return PytheResult.build(400, "该车已被保留");
			}
			// 是，解除状态，将状态变为使用状态
			car.setLatitude(latitude);
			car.setLongitude(longitude);
			car.setUser(customerId);
			car.setStatus(CAR_USER_STATUS);
			carMapper.updateByPrimaryKey(car);
			return PytheResult.ok("开锁成功");
		}
		case 3: {
			return PytheResult.build(400, "该车需要维修");
		}
		}

		// 先更新车的时间和使用状态
		car.setLatitude(latitude);
		car.setLongitude(longitude);
		car.setStarttime(new Date());
		car.setUser(customerId);
		car.setStatus(CAR_USER_STATUS);
		carMapper.updateByPrimaryKey(car);

		// 记录登记
		// 在运行时候先，推荐完要将原来的推荐信号给制空
		final String recordId = FactoryUtils.getUUID();

		new Thread() {
			@Override
			public void run() {
				TblRecord record = new TblRecord();
				record.setId(recordId);
				record.setCustomerId(customerId);
				record.setLongitdeStart(longitude);
				record.setLatitudeStart(latitude);
				record.setCarId(carId);
				record.setStartTime(new Date());
				recordMapper.insert(record);
			}
		}.start();

		JSONObject object = new JSONObject();
		object.put("recordId", recordId);
		return PytheResult.build(200, "开锁成功", object);

	}

	@Override
	public PytheResult loke(String parameters) {
	      //更新记录位置
		JSONObject information = JSONObject.parseObject(parameters);
		final Double longitude = information.getDouble("longitude");
		final Double latitude = information.getDouble("latitude");
		String carId = information.getString("carId");
		final String recordId = information.getString("recordId");
		
		//车信息
		TblCar car = carMapper.selectByPrimaryKey(carId);
		car.setLatitude(latitude);
		car.setLongitude(longitude);
		car.setUser(null);
		car.setStatus(CAR_FREE_STATUS);
		car.setEndtime(new Date());
		carMapper.updateByPrimaryKey(car);
		
		//更新记录信息
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
	public PytheResult selectCartPositionByMap(Integer longitude, Integer latitude) {
		// TODO Auto-generated method stub
		TblCarExample example = new TblCarExample();
		example.createCriteria().andLatitudeGreaterThanOrEqualTo(latitude-0.1).andLatitudeLessThanOrEqualTo(latitude+0.1)
		.andLongitudeGreaterThan(longitude-0.1).andLongitudeLessThanOrEqualTo(longitude+0.1);
		List<TblCar> carList = carMapper.selectByExample(example);
		if (!carList.isEmpty()) {
			return PytheResult.ok(carList);
		}
		return PytheResult.build(300, "附近无婴儿车");
	}

	@Override
	public PytheResult computeFee(String parameters) {
		JSONObject information = JSONObject.parseObject(parameters);
		final String recordId = information.getString("recordId");
		String carId = information.getString("carId");
		final Long customerId = information.getLong("customerId");

		// 更新车的位置和使用情况和结束时间
		//让车处于空闲状态，让后续的人可以使用
		TblCar car = carMapper.selectByPrimaryKey(carId);
		car.setStatus(CAR_FREE_STATUS);
		car.setEndtime(new Date());
		carMapper.updateByPrimaryKey(car);

		// 更新停止时间和停止位置和记录用的钱
		final double amount = EACH_HOUR_PRICE * DateUtils.minusForPartHour(car.getEndtime(), car.getStarttime());
		
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
		new Thread() {
			@Override
			public void run() {
				TblBill bill = new TblBill();
				bill.setId(billId);
				bill.setRecordId(recordId);
				bill.setAmount(amount);
				bill.setType(BILL_PAY_TYPE);
				bill.setCustomerId(customerId);
				bill.setTime(new Date());
				bill.setRecordId(recordId);
				
				if (account.getAmount() < 0) {
					bill.setStatus(NOT_PAY_STATUS);
				} else {
					bill.setStatus(PAY_TYPE);
				}
				billMapper.insert(bill);
			}
		}.start();

		// 更新扣费是否成功
		if (account.getAmount() > 0) {
			return PytheResult.ok("支付成功");
		} else {
			return PytheResult.build(300, "余额不足，前往充值");
		}
	}

	@Override
	public PytheResult holdCartByCustomerId(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String carId = information.getString("carId");
		final Long customerId = information.getLong("customerId");
		Integer appointmentTime = information.getInteger("appointmentTime");
		
		TblCar car = new TblCar();
		car.setId(carId);
		car.setStatus(CAR_SAVE_STATUS);
		carMapper.updateByPrimaryKeySelective(car);

		
		// 插入保留锁的信息，方便自动化程序检测是否有超时
		TblHoldRecord record = new TblHoldRecord();
		record.setCarId(carId);
		record.setCustomerId(customerId);
		record.setHoldStartTime(new Date());
		record.setHoldStopTime(new DateTime().plusMinutes(appointmentTime).toDate());
		holdRecordMapper.insert(record);
		return PytheResult.ok("保留成功");
	}



}
