package com.pythe.rest.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblBagRecordMapper;
import com.pythe.mapper.TblBillMapper;
import com.pythe.mapper.TblCarMapper;
import com.pythe.mapper.TblComboMapper;
import com.pythe.mapper.TblCustomerMapper;
import com.pythe.mapper.TblPayMapper;
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
import com.pythe.pojo.TblPay;
import com.pythe.pojo.TblPayExample;
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
	private TblPayMapper payMapper;
	
	
	@Autowired
	private TblCustomerMapper customerMapper;

	
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
				//22  113 
				car.setLatitude(RandomUtils.nextDouble(20, 23));
				car.setLongitude(RandomUtils.nextDouble(110,120));
				car.setLockKey(tblTmpCar.getLockKey());
				car.setLockPassword(tblTmpCar.getLockPassword());
				car.setQrId(tblTmpCar.getQrId());
				carMapper.insert(car);
			}
		}
		
		return PytheResult.ok("插入成功");
	}


	@Override
	public PytheResult insetCarPay() {
		TblPayExample example =new TblPayExample();
		List<TblPay> payList = payMapper.selectByExample(example);
		int num = 0;
		TblCustomerExample example2 =new TblCustomerExample();
		example2.createCriteria().andLevelEqualTo(0);
		List<TblCustomer> customerList = customerMapper.selectByExample(example2);
		int customerSize = customerList.size();
		
		
//		TblCustomerExample example3 =new TblCustomerExample();
//		example3.createCriteria().andLevelEqualTo(0);
//		List<TblCustomer> managerList = customerMapper.selectByExample(example2);
		List<String> list =new ArrayList<String>();
		list.add("15017556458");
		list.add("13828494261");
		TblCustomerExample example3 =new TblCustomerExample();
		example3.createCriteria().andPhoneNumIn(list);
		List<TblCustomer> managerList = customerMapper.selectByExample(example3);
		int managerSize = managerList.size();
		
		
		//随机抽取A类车
		TblCarExample example4 =new TblCarExample();
		example4.createCriteria().andDescriptionEqualTo("A1");
		List<TblCar> aCarList = carMapper.selectByExample(example4);
		int aCarSize = aCarList.size();
		
		
		//随机抽取B类车
		TblCarExample example5 =new TblCarExample();
		example5.createCriteria().andDescriptionEqualTo("B1");
		List<TblCar> bCarList = carMapper.selectByExample(example5);
		int bCarSize = bCarList.size();
		
		//随机抽取时间
		Date date =null;
		int mi = 1;
		
		
		for (TblPay tblPay : payList) {
			tblPay.setRecordid(FactoryUtils.getUUID());
			
			if (tblPay.getSum().equals(30d) ||tblPay.getSum().equals(50d) || tblPay.getSum().equals(20d)) {
				tblPay.setType(BILL_PAY_TYPE);
				num = RandomUtils.nextInt(0,customerSize);
				TblCustomer customer = customerList.get(num);
				tblPay.setCustomerId(customer.getId());
				tblPay.setPhoneNum(customer.getPhoneNum());
				//使用小时
				num = RandomUtils.nextInt(2,6);
				mi= RandomUtils.nextInt(1,10);
				date = new DateTime(tblPay.getDate()).plusHours(num).plusMinutes(mi).toDate();
				tblPay.setStopTime(date);
				
				if (tblPay.getSum().equals(20d)) {
					tblPay.setDescription("B1");
					num = RandomUtils.nextInt(0,bCarSize);
					tblPay.setQrid(bCarList.get(num).getQrId());
				}else{
					tblPay.setDescription("A1");
					num = RandomUtils.nextInt(0,aCarSize);
					tblPay.setQrid(aCarList.get(num).getQrId());
				}
			}else{
				tblPay.setDescription("A1");
				tblPay.setType(TEST_PAY_TYPE);
				num = RandomUtils.nextInt(0,managerSize);
				TblCustomer customer = managerList.get(num);
				tblPay.setCustomerId(customer.getId());
				tblPay.setPhoneNum(customer.getPhoneNum());
				
				num = RandomUtils.nextInt(0,aCarSize);
				tblPay.setQrid(aCarList.get(num).getQrId());
				
				//使用小时
				num = RandomUtils.nextInt(1,19);
				date = new DateTime(tblPay.getDate()).plusMinutes(num).plusSeconds(num).toDate();
				tblPay.setStopTime(date);
			}
			

			
			//产生随机时间
			num = RandomUtils.nextInt(1, 3);
			if (num==1) {
				num = RandomUtils.nextInt(3, 5);
				date = new DateTime(tblPay.getDate()).plusMinutes(num).plusSeconds(num).toDate();
			}else{
				num = RandomUtils.nextInt(15, 50);
				date = new DateTime(tblPay.getDate()).plusSeconds(num).toDate();
			}
			tblPay.setStartTime(date);
			payMapper.updateByPrimaryKey(tblPay);
		}
		return PytheResult.ok("更新成功");
	}

}