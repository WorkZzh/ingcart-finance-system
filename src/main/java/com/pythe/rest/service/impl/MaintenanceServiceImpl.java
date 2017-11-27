package com.pythe.rest.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.DateUtils;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.mapper.TblMaintenanceMapper;
import com.pythe.mapper.VAcountRecordMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblMaintenance;
import com.pythe.pojo.TblRecord;
import com.pythe.pojo.VAcountRecord;
import com.pythe.pojo.VAcountRecordExample;
import com.pythe.rest.service.MaintenanceService;


@Service
public class MaintenanceServiceImpl implements MaintenanceService{

	
	@Autowired
	private TblMaintenanceMapper maintenanceMapper;
	
	@Autowired
	private VAcountRecordMapper aCountRecordMapper;
	
	@Override
	public PytheResult callRepair(String parameters) {
		// TODO Auto-generated method stub
		
		
		JSONObject information = JSONObject.parseObject(parameters);
		Long customerId = information.getLong("customerId");
		Integer type = information.getInteger("type");
		Double longitude = information.getDouble("longitude");
		Double latitude = information.getDouble("latitude");
		String carId = information.getString("carId");
		String description = information.getString("description");
		
		TblMaintenance record =new TblMaintenance();
		record.setCustomerId(customerId);
		record.setCarId(carId);
		record.setLongitude(longitude);
		record.setLatitude(latitude);
		record.setType(type);
		record.setDescription(description);
		record.setCallTime(new Date());
		record.setStatus(0);
		
		maintenanceMapper.insert(record);
		
		return PytheResult.ok("报修成功");
	}

	@Override
	public PytheResult selectTripBillByCustomerId(Long customerId,Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		VAcountRecordExample example =new VAcountRecordExample();
		example.createCriteria().andCustomerIdEqualTo(customerId);
		List<VAcountRecord> result = aCountRecordMapper.selectByExample(example);
		
		if (result.isEmpty()) {
			PytheResult.ok("暂无任何行程记录");
		}
		
		
		String key =null;
		LinkedList<VAcountRecord> list  =null;
		
		LinkedHashMap<String,LinkedList<VAcountRecord>> map = new LinkedHashMap<String,LinkedList<VAcountRecord>>();
		for (VAcountRecord vAcountRecord : result) {
			key = DateUtils.formatDate(vAcountRecord.getStopTime());
			
			vAcountRecord.setRecordId(DateUtils.formatDateToHour2Minute(vAcountRecord.getStartTime()) +"-"+DateUtils.formatDateToHour2Minute(vAcountRecord.getStopTime()));
			if (!map.containsKey(key)) {
				list = new LinkedList<VAcountRecord>();
				
				list.add(vAcountRecord);
				map.put(key, list);
			}else{
				list = map.get(key);
				list.add(vAcountRecord);
				map.put(key, list);
			}
		}
		return PytheResult.ok(map);
	}

	

}
