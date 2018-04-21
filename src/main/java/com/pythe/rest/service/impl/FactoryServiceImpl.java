package com.pythe.rest.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.DateUtils;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.common.utils.JsonUtils;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblBagRecordMapper;
import com.pythe.mapper.TblBillMapper;
import com.pythe.mapper.TblCarMapper;
import com.pythe.mapper.TblComboMapper;
import com.pythe.mapper.TblCustomerMapper;
import com.pythe.mapper.TblDistributionMapper;
import com.pythe.mapper.TblOperatorMapper;
import com.pythe.mapper.TblPMapper;
import com.pythe.mapper.TblPayMapper;
import com.pythe.mapper.TblRecordMapper;
import com.pythe.mapper.TblStoreMapper;
import com.pythe.mapper.TblTmpCarMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblBagRecord;
import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblCar;
import com.pythe.pojo.TblCarExample;
import com.pythe.pojo.TblCombo;
import com.pythe.pojo.TblComboExample;
import com.pythe.pojo.TblCustomer;
import com.pythe.pojo.TblCustomerExample;
import com.pythe.pojo.TblDistributionExample;
import com.pythe.pojo.TblOperator;
import com.pythe.pojo.TblOperatorExample;
import com.pythe.pojo.TblP;
import com.pythe.pojo.TblPExample;
import com.pythe.pojo.TblPay;
import com.pythe.pojo.TblPayExample;
import com.pythe.pojo.TblRecord;
import com.pythe.pojo.TblRecordExample;
import com.pythe.pojo.TblStore;
import com.pythe.pojo.TblStoreExample;
import com.pythe.pojo.TblTmpCar;
import com.pythe.pojo.TblTmpCarExample;
import com.pythe.rest.service.BagService;
import com.pythe.rest.service.FactoryService;

@Service
public class FactoryServiceImpl implements FactoryService {

	@Autowired
	private TblTmpCarMapper tmpCarMapper;

	@Autowired
	private TblCarMapper carMapper;

	@Autowired
	private TblOperatorMapper operatorMapper;

	@Autowired
	private TblPMapper pMapper;

	@Autowired
	private TblDistributionMapper distributionMapper;

	@Autowired
	private TblPayMapper payMapper;

	@Autowired
	private TblRecordMapper recordMapper;

	@Autowired
	private TblCustomerMapper customerMapper;

	@Autowired
	private TblBillMapper billMapper;

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

	@Value("${TEST_PAY_TYPE}")
	private Integer TEST_PAY_TYPE;

	@Value("${PAY_STATUS}")
	private Integer PAY_STATUS;

	// BILL
	@Value("${BILL_GIVE_TYPE}")
	private Integer BILL_GIVE_TYPE;

	@Override
	public PytheResult insetRecordIncar(String storeId) {
		// TODO Auto-generated method stub
		TblTmpCarExample example = new TblTmpCarExample();
		List<TblTmpCar> tmpCarList = tmpCarMapper.selectByExample(example);

		TblCarExample carExample = new TblCarExample();
		List<TblCar> carList = carMapper.selectByExample(carExample);
		LinkedList<String> list = new LinkedList<>();
		for (TblCar tblCar : carList) {
			list.add(tblCar.getId());
		}

		for (TblTmpCar tblTmpCar : tmpCarList) {
			if (!list.contains(tblTmpCar.getId())) {
				TblCar car = new TblCar();
				car.setId(tblTmpCar.getId());
				// 22 113
				car.setLatitude(RandomUtils.nextDouble(20, 23));
				car.setLongitude(RandomUtils.nextDouble(110, 120));
				car.setLockKey(tblTmpCar.getLockKey());
				car.setLockPassword(tblTmpCar.getLockPassword());
				car.setQrId(tblTmpCar.getQrId());
				carMapper.insert(car);
			}
		}

		return PytheResult.ok("插入成功");
	}

	@Override
	@Transactional
	public PytheResult insetCarPay() {

		// 每次都要打乱用车的顺序

		// 用户数据
		int num = 0;
		TblCustomerExample example2 = new TblCustomerExample();
		example2.createCriteria().andLevelEqualTo(0).andIdLessThanOrEqualTo(2649l);
		
		example2.setOrderByClause("id asc");
		List<TblCustomer> customerList = customerMapper.selectByExample(example2);
		// 从那次开始有用户出现，
		int count = 0;

		TblCustomerExample example100 = new TblCustomerExample();
		example100.createCriteria().andLevelEqualTo(0).andIdGreaterThanOrEqualTo(2650l);
		List<TblCustomer> tmp = customerMapper.selectByExample(example100);
		int tmpSize = tmp.size();
		
		int customerSize = customerList.size();

		// 这里会有传递性得小心一点

		// 换成operator
		List<String> list = new ArrayList<String>();
		list.add("15017556458");
		list.add("13828494261");
		TblOperatorExample example3 = new TblOperatorExample();
		example3.createCriteria().andPhoneNumIn(list);
		List<TblOperator> managerList = operatorMapper.selectByExample(example3);
		int managerSize = managerList.size();

		// 插入
		TblRecordExample example6 = new TblRecordExample();
		List<TblRecord> recordList = recordMapper.selectByExample(example6);
		int recordSize = recordList.size();

		// 随机抽取A类车
		TblDistributionExample distributionExample = new TblDistributionExample();
		distributionExample.createCriteria().andLevelEqualTo("A1");
		List<Long> aIds = JsonUtils.jsonToList(
				distributionMapper.selectByExampleWithBLOBs(distributionExample).get(0).getCarIds(), Long.class);

		TblCarExample example4 = new TblCarExample();
		example4.createCriteria().andQrIdIn(aIds);
		List<TblCar> aCarList = carMapper.selectByExample(example4);
		int aCarSize = aCarList.size();

		// 随机抽取B类车
		TblDistributionExample distributionExample2 = new TblDistributionExample();
		distributionExample2.createCriteria().andLevelEqualTo("B1");
		List<Long> bIds = JsonUtils.jsonToList(
				distributionMapper.selectByExampleWithBLOBs(distributionExample2).get(0).getCarIds(), Long.class);

		TblCarExample example5 = new TblCarExample();
		example5.createCriteria().andQrIdIn(bIds);
		List<TblCar> bCarList = carMapper.selectByExample(example5);
		int bCarSize = bCarList.size();

		// 随机抽取时间
		Date date = null;
		int mi = 1;

		// 2018-03-10之前光明农场按小时收费和动物园 30元 3小时 20元 2小时 50元 是因为当初改变策略做的
		// ====================================>

//		List<String> sequenceDate = DateUtils.getSequenceDate("2018-02-01 00:00:00", "2018-04-10 23:59:59");
//		// 按每天来产生数据
//		for (String d : sequenceDate) {
//			// 每天都有新车
//			List<TblCar> a_tmp = aCarList;
//			List<TblCar> b_tmp = bCarList;
//
//			// 每次循环用的一台车
//			TblCar car = null;
//			// 用完情况下再说
//			TblPayExample example = new TblPayExample();
//			example.createCriteria().andStartTimeBetween(DateUtils.parseTime(d + " 00:00:00"),
//					DateUtils.parseTime(d + " 23:59:59"));
//			example.setOrderByClause("DATE ASC");
//			List<TblPay> payList = payMapper.selectByExample(example);
//			for (TblPay tblPay : payList) {
//
//				// 避免车号被用完
//				if (a_tmp.isEmpty()) {
//					TblCarExample example7 = new TblCarExample();
//					example7.createCriteria().andDescriptionEqualTo("A1").andQrIdNotIn(aIds);
//					a_tmp.addAll(carMapper.selectByExample(example7));
//				}
//
//				if (b_tmp.isEmpty()) {
//					TblCarExample example8 = new TblCarExample();
//					example8.createCriteria().andDescriptionEqualTo("B1").andQrIdNotIn(bIds);
//					b_tmp.addAll(carMapper.selectByExample(example8));
//				}
//
//				tblPay.setRecordid(FactoryUtils.getUUID());
//				tblPay.setBillId(FactoryUtils.getUUID());
//				num = RandomUtils.nextInt(0, recordSize);
//				TblRecord record = recordList.get(num);
//				for (int i = 0; i < 10; i++) {
//					if (record.getLatitudeStop() != null && record.getLongitdeStart() != null) {
//						tblPay.setLatitudeStart(record.getLatitudeStart());
//						tblPay.setLatitudeStop(record.getLatitudeStop());
//						tblPay.setLongitudeStart(record.getLongitdeStart());
//						tblPay.setLongitudeStop(record.getLongitudeStop());
//						break;
//					}
//				}
//				// 只有退款和支付 ，诡异情况为测试，或者是因为用户时候后，帐号上有余额
//				// 部分退款情况:（1）开发测试部分退款功能 （2）用不完或故障，部分退款用户 （3）定点还车 （用机器调节时间）
//				if (!tblPay.getRefundAmount().equals(0d) && tblPay.getAmount() > tblPay.getRefundAmount()) {
//					tblPay.setType(PART_REFUND_TYPE);
//					tblPay.setGivingAmount(0d);
//					// 更新结束时间
//					if (tblPay.getStopTime() == null) {
//						Double times = (tblPay.getAmount() + tblPay.getGivingAmount() - tblPay.getRefundAmount()) / 5;
//						Double time = Math.ceil(times);
//						num = RandomUtils.nextInt(0, 10);
//						date = new DateTime(tblPay.getStartTime()).plusMinutes(30 * time.intValue())
//								.plusSeconds(num * 13).toDate();
//						tblPay.setStopTime(date);
//					}
//				}
//				// 全额退款情况 （1）开发测试全额退款功能 （2）用车故障，全额退款
//				else if (tblPay.getSum().equals(0d)) {
//					tblPay.setType(TOTAL_REFUND_TYPE);
//					tblPay.setGivingAmount(0d);
//					// 更新结束时间
//					if (tblPay.getStopTime() == null) {
//						Double times = (tblPay.getAmount() + tblPay.getGivingAmount() - tblPay.getRefundAmount()) / 5;
//						Double time = Math.ceil(times);
//						num = RandomUtils.nextInt(0, 10);
//						int hour = RandomUtils.nextInt(0, 5);
//						if (hour > 1) {
//							hour = 0;
//						} else {
//							hour = 1;
//						}
//						date = new DateTime(tblPay.getStartTime()).plusMinutes(30 * time.intValue())
//								.plusSeconds(num * 13).toDate();
//						tblPay.setStopTime(date);
//					}
//
//				}
//				// 正常消费
//				else {
//					tblPay.setType(BILL_PAY_TYPE);
//					Double amount = tblPay.getAmount();
//					if (amount % 5 != 0) {
//						tblPay.setGivingAmount(5 - amount % 5);
//					} else {
//						tblPay.setGivingAmount(0d);
//					}
//					// 更新结束时间
//					if (tblPay.getStopTime() == null) {
//						Double times = (tblPay.getAmount() + tblPay.getGivingAmount() - tblPay.getRefundAmount()) / 5;
//						Double time = Math.ceil(times);
//						num = RandomUtils.nextInt(0, 15);
//						date = new DateTime(tblPay.getStartTime()).plusMinutes(30 * time.intValue()).plusSeconds(num)
//								.toDate();
//						tblPay.setStopTime(date);
//					}
//				}
//				// 分配车信息
//				num = RandomUtils.nextInt(0, 4);
//				if (num == 0 && !tblPay.getAmount().equals(50d)) {
//					car = b_tmp.get(0);
//					tblPay.setDescription(car.getDescription());
//					tblPay.setQrid(car.getQrId());
//					tblPay.setCarId(car.getId());
//					// 删除当天用过的，并洗牌一次
//					b_tmp.remove(0);
//					Collections.shuffle(b_tmp);
//				} else {
//					car = a_tmp.get(0);
//					tblPay.setDescription(car.getDescription());
//					tblPay.setQrid(car.getQrId());
//					tblPay.setCarId(car.getId());
//					// 删除当天用过的，并洗牌一次
//					a_tmp.remove(0);
//					Collections.shuffle(a_tmp);
//				}
//				// 更新用户信息 为测试
//				// 更新用户信息 为测试
//				if (tblPay.getAmount() <= 5) {
//					num = RandomUtils.nextInt(0, managerSize);
//					TblOperator customer = managerList.get(num);
//					tblPay.setCustomerId(customer.getId());
//					tblPay.setPhoneNum(customer.getPhoneNum());
//				} else if (tblPay.getAmount().equals(30) && tblPay.getRefundAmount() >= 20d) {
//					num = RandomUtils.nextInt(0, managerSize);
//					TblOperator customer = managerList.get(num);
//					tblPay.setCustomerId(customer.getId());
//					tblPay.setPhoneNum(customer.getPhoneNum());
//				} else {
//					// 客户会用完
//					num = RandomUtils.nextInt(0, 11);
//					count = count++;
//					if (count >= 100 || tmp.isEmpty()) {
//						if (num == 0 || tmp.isEmpty()) {
//							int tmpSize = customerSize;
//							num = RandomUtils.nextInt(0, 1450);
//							TblCustomer customer = customerList.get(num);
//							tblPay.setCustomerId(customer.getId());
//							tblPay.setPhoneNum(customer.getPhoneNum());
//						}else{
//							TblCustomer customer = tmp.get(0);
//							tblPay.setCustomerId(customer.getId());
//							tblPay.setPhoneNum(customer.getPhoneNum());
//							tmp.remove(0);
//						}
//					} else {
//						TblCustomer customer = tmp.get(0);
//						tblPay.setCustomerId(customer.getId());
//						tblPay.setPhoneNum(customer.getPhoneNum());
//						tmp.remove(0);
//					}
//				}
//				// 更新数据
//				payMapper.updateByPrimaryKey(tblPay);
//			}
//		}

		/**
		 * 第二种消费情况 3/10日的账 <=5情况为测试 =30 退20为非定点还车测试 >=10 不整除5为按小时，收费 amount =50
		 * refund_amount=0情况 非定点还车 100 20 忘记关锁，没有结束行程消费，消费
		 * （1）需要改用户序列ID
		 */
		List<String> sequenceDate = DateUtils.getSequenceDate("2018-04-11 00:00:00", "2018-04-19 23:59:59");
		// 按每天来产生数据
		for (String d : sequenceDate) {
			// 每天都有新车
			List<TblCar> a_tmp = aCarList;
			List<TblCar> b_tmp = bCarList;

			// 每次循环用的一台车
			TblCar car = null;
			TblPayExample example = new TblPayExample();
			example.createCriteria().andStartTimeBetween(DateUtils.parseTime(d + " 00:00:00"),
					DateUtils.parseTime(d + " 23:59:59"));
			example.setOrderByClause("DATE ASC");
			List<TblPay> payList = payMapper.selectByExample(example);
			for (TblPay tblPay : payList) {

				// 避免车号被用完
				if (a_tmp.isEmpty()) {
					TblCarExample example7 = new TblCarExample();
					example7.createCriteria().andDescriptionEqualTo("A1").andQrIdNotIn(aIds);
					a_tmp.addAll(carMapper.selectByExample(example7));
				}

				if (b_tmp.isEmpty()) {
					TblCarExample example8 = new TblCarExample();
					example8.createCriteria().andDescriptionEqualTo("B1").andQrIdNotIn(bIds);
					b_tmp.addAll(carMapper.selectByExample(example8));
				}

				tblPay.setRecordid(FactoryUtils.getUUID());
				tblPay.setBillId(FactoryUtils.getUUID());
				num = RandomUtils.nextInt(0, recordSize);
				TblRecord record = recordList.get(num);
				for (int i = 0; i < 10; i++) {
					if (record.getLatitudeStop() != null && record.getLongitdeStart() != null) {
						tblPay.setLatitudeStart(record.getLatitudeStart());
						tblPay.setLatitudeStop(record.getLatitudeStop());
						tblPay.setLongitudeStart(record.getLongitdeStart());
						tblPay.setLongitudeStop(record.getLongitudeStop());
						break;
					}
				}
				// 只有退款和支付 ，诡异情况为测试，或者是因为用户时候后，帐号上有余额
				// 部分退款情况:（1）开发测试部分退款功能 （2）用不完或故障，部分退款用户 （3）定点还车 （用机器调节时间）
				Date date_ = DateUtils.parseTime(DateUtils.formatDate(tblPay.getStartTime()) + " 23:50:00");
				if (!tblPay.getRefundAmount().equals(0d) && tblPay.getAmount() > tblPay.getRefundAmount()) {
					tblPay.setType(PART_REFUND_TYPE);
					tblPay.setGivingAmount(0d);
					// 更新结束时间
					if (tblPay.getAmount().equals(50d)) {
						// 结束时间不一定是按照计算的来
						Double times = (tblPay.getAmount() + tblPay.getGivingAmount() - tblPay.getRefundAmount()) / 5;
						Double time = Math.ceil(times);
						num = RandomUtils.nextInt(0, 4);
						date = new DateTime(tblPay.getStartTime()).plusMinutes(30 * (time.intValue() + num))
								.plusSeconds(num * num).toDate();
						tblPay.setStopTime(date);
					} else if (tblPay.getStopTime() == null) {
						Double times = (tblPay.getAmount() + tblPay.getGivingAmount() - tblPay.getRefundAmount()) / 5;
						Double time = Math.ceil(times);
						num = RandomUtils.nextInt(0, 10);
						date = new DateTime(tblPay.getStartTime()).plusMinutes(30 * time.intValue())
								.plusSeconds(num * 13).toDate();
						tblPay.setStopTime(date);
					}
				}
				// 全额退款情况 （1）开发测试全额退款功能 （2）用车故障，全额退款
				else if (tblPay.getSum().equals(0d)) {
					tblPay.setType(TOTAL_REFUND_TYPE);
					tblPay.setGivingAmount(0d);
					// 更新结束时间
					if (tblPay.getStopTime() == null) {
						Double times = (tblPay.getAmount() + tblPay.getGivingAmount() - tblPay.getRefundAmount()) / 5;
						Double time = Math.ceil(times);
						num = RandomUtils.nextInt(0, 10);
						int hour = RandomUtils.nextInt(0, 5);
						if (hour > 1) {
							hour = 0;
						} else {
							hour = 1;
						}
						date = new DateTime(tblPay.getStartTime()).plusMinutes(30 * time.intValue())
								.plusSeconds(num * 13).toDate();
						tblPay.setStopTime(date);
					}
				}
				// 正常消费（有2种情况，所以end_time不一样，判断是否有推回来）
				else {
					tblPay.setType(BILL_PAY_TYPE);
					tblPay.setGivingAmount(0d);
					// 更新结束时间
					if (tblPay.getAmount().equals(50d)) {
						tblPay.setStopTime(
								DateUtils.parseTime(DateUtils.formatDate(tblPay.getStartTime()) + " 23:50:00"));
					}
					// 其他情况
					else if (tblPay.getStopTime() == null) {
						Double times = (tblPay.getAmount() + tblPay.getGivingAmount() - tblPay.getRefundAmount()) / 5;
						Double time = Math.ceil(times);
						num = RandomUtils.nextInt(0, 15);
						date = new DateTime(tblPay.getStartTime()).plusMinutes(30 * time.intValue()).plusSeconds(num)
								.toDate();
						tblPay.setStopTime(date);
					}
				}
				// 分配车信息
				num = RandomUtils.nextInt(0, 4);
				if (tblPay.getAmount().equals(50d) || tblPay.getAmount() % 5 != 0) {
					car = a_tmp.get(0);
					tblPay.setDescription(car.getDescription());
					tblPay.setQrid(car.getQrId());
					tblPay.setCarId(car.getId());
					// 删除当天用过的，并洗牌一次
					a_tmp.remove(0);
					Collections.shuffle(a_tmp);
				}
				// A类型测试数据，机器给20，自己加30，这样能50退20
				else if (tblPay.getAmount().equals(30d) && tblPay.getRefundAmount() >= 20d) {
					car = a_tmp.get(0);
					tblPay.setDescription(car.getDescription());
					tblPay.setQrid(car.getQrId());
					tblPay.setCarId(car.getId());
					// 删除当天用过的，并洗牌一次
					a_tmp.remove(0);
					Collections.shuffle(a_tmp);
				} else {
					car = b_tmp.get(0);
					tblPay.setDescription(car.getDescription());
					tblPay.setQrid(car.getQrId());
					tblPay.setCarId(car.getId());
					// 删除当天用过的，并洗牌一次
					b_tmp.remove(0);
					Collections.shuffle(b_tmp);
				}
				
				// 更新用户信息 为测试
				if (tblPay.getAmount() <= 5) {
					num = RandomUtils.nextInt(0, managerSize);
					TblOperator customer = managerList.get(num);
					tblPay.setCustomerId(customer.getId());
					tblPay.setPhoneNum(customer.getPhoneNum());
				} else if (tblPay.getAmount().equals(30) && tblPay.getRefundAmount() >= 20d) {
					num = RandomUtils.nextInt(0, managerSize);
					TblOperator customer = managerList.get(num);
					tblPay.setCustomerId(customer.getId());
					tblPay.setPhoneNum(customer.getPhoneNum());
				} else {
					//客户回头率是 30份之一
					num = RandomUtils.nextInt(0, 30);
//					System.out.println("=================>num"+ num);
//					System.out.println("=================>tmp0"+tmp.size());
						if (num == 0 || tmp.isEmpty()) {
							num = RandomUtils.nextInt(30, 1700);
							TblCustomer customer = customerList.get(num);
							tblPay.setCustomerId(customer.getId());
							tblPay.setPhoneNum(customer.getPhoneNum());
						}
					else {
						//System.out.println("tmp==============>"+tmp.get(0).getId());
						TblCustomer customer = tmp.get(0);
						tblPay.setCustomerId(customer.getId());
						tblPay.setPhoneNum(customer.getPhoneNum());
						tmp.remove(0);
					}
				}
				// 更新数据
				payMapper.updateByPrimaryKey(tblPay);
			}
		}

		return PytheResult.ok("更新成功");

	}

	@Override
	public PytheResult insertBillRecord() {
		// TODO Auto-generated method stub
		TblPayExample example = new TblPayExample();
		List<TblPay> payList = payMapper.selectByExample(example);
		int num = 0;
		Date date = null;
		for (TblPay tblPay : payList) {
			TblBill bill = new TblBill();
			bill.setId(tblPay.getBillId());
			bill.setAmount(tblPay.getAmount());
			bill.setOutTradeNo(tblPay.getOrderNum());
			bill.setGivingAmount(tblPay.getGivingAmount());
			bill.setCustomerId(tblPay.getCustomerId());
			// bill.setOutTradeNo(String.valueOf(tblPay.getOrdernum()));
			bill.setStatus(PAY_STATUS);
			// num = RandomUtils.nextInt(1, 3);
			// date = new
			// DateTime(tblPay.getStartTime()).minusMinutes(num).minusSeconds(num).toDate();
			bill.setTime(tblPay.getDate());
			bill.setRecordId(tblPay.getRecordid());
			bill.setType(tblPay.getType());
			billMapper.insert(bill);

			TblRecord record = new TblRecord();
			record.setBillId(tblPay.getBillId());
			record.setCarId(tblPay.getCarId());
			record.setCustomerId(tblPay.getCustomerId());
			record.setLatitudeStart(tblPay.getLatitudeStart());
			record.setId(tblPay.getRecordid());
			record.setLatitudeStop(tblPay.getLatitudeStop());
			record.setLongitdeStart(tblPay.getLongitudeStart());
			record.setLongitudeStop(tblPay.getLongitudeStop());
			record.setStartTime(tblPay.getStartTime());
			record.setStopTime(tblPay.getStopTime());
			recordMapper.insert(record);
		}
		return PytheResult.ok("更新成功");
	}

	@Override
	public PytheResult groupByOrderNumSum() {
		// TODO Auto-generated method stub
		TblPExample p = new TblPExample();
		p.setOrderByClause("DATE ASC");
		List<TblP> payList = pMapper.selectByExample(p);
		Date date = null;
		int num = 1;
		for (TblP tblP : payList) {
			TblPayExample example = new TblPayExample();
			example.createCriteria().andOrderNumEqualTo(tblP.getOrderNum());

			List<TblPay> list = payMapper.selectByExample(example);
			if (list.isEmpty()) {
				TblPay pay = new TblPay();
				pay.setOrderNum(tblP.getOrderNum());
				pay.setCustomerTag(tblP.getCustomerTag());
				pay.setAmount(tblP.getAmount());
				pay.setRefundAmount(tblP.getRefundAmount());
				num = RandomUtils.nextInt(1, 4);
				date = new DateTime(tblP.getDate()).plusSeconds(num).toDate();
				pay.setDate(date);
				num = RandomUtils.nextInt(1, 5);
				date = new DateTime(pay.getDate()).plusMinutes(num).plusSeconds(num).toDate();
				pay.setStartTime(date);
				payMapper.insert(pay);
			} else {
				TblPay pay = list.get(0);
				pay.setAmount(pay.getAmount() + tblP.getAmount());
				pay.setRefundAmount(pay.getRefundAmount() + tblP.getRefundAmount());
				if (tblP.getRefundAmount() <= 0d) {
					num = RandomUtils.nextInt(1, 4);
					date = new DateTime(tblP.getDate()).plusSeconds(num).toDate();
					pay.setStopTime(date);
				}
				payMapper.updateByPrimaryKey(pay);
			}

		}
		return PytheResult.ok("计费成功");
	}

}