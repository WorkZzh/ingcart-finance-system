package com.pythe.rest.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
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
		for (TblPay tblPay : payList) {
			
		}
		return null;
	}

}