package com.pythe.rest.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.DateUtils;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.common.utils.JsonUtils;
import com.pythe.mapper.TblCarMapper;
import com.pythe.mapper.TblFaultTypeMapper;
import com.pythe.mapper.TblMaintenanceMapper;
import com.pythe.mapper.TblVersionMapper;
import com.pythe.mapper.VAcountRecordMapper;
import com.pythe.mapper.VCustomerMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblCar;
import com.pythe.pojo.TblCarExample;
import com.pythe.pojo.TblFaultType;
import com.pythe.pojo.TblFaultTypeExample;
import com.pythe.pojo.TblMaintenance;
import com.pythe.pojo.TblMaintenanceExample;
import com.pythe.pojo.TblRecord;
import com.pythe.pojo.TblVersion;
import com.pythe.pojo.VAcountRecord;
import com.pythe.pojo.VAcountRecordExample;
import com.pythe.pojo.VCustomer;
import com.pythe.pojo.VCustomerExample;
import com.pythe.rest.service.MaintenanceService;
import com.pythe.rest.service.ManagerService;



@Service
public class ManagerServiceImpl implements ManagerService{
	
	@Autowired
	private TblVersionMapper versionMapper;
	
	@Autowired
	private VCustomerMapper vCustomerMapper;

	@Override
	public PytheResult updateVersion(String parameters) {
		// TODO Auto-generated method stub
		JSONObject param = JSONObject.parseObject(parameters);

		String type = param.getString("type");
		String version = param.getString("version");
		String apikey = param.getString("apikey");
		String leader = param.getString("leader");
		String description = param.getString("description");
		
		TblVersion record = versionMapper.selectByPrimaryKey(apikey);
		if (record==null) {
			PytheResult.build(400, "apikey不合法");
		}
		
		record.setCreated(new Date());
		record.setLeader(leader);
		record.setType(type);
		record.setVersion(version);
		record.setDescription(description);
		versionMapper.updateByPrimaryKeyWithBLOBs(record);
		return PytheResult.ok("版本更新成功");
	}

	@Override
	public PytheResult selectVersion(String parameters) {
		// TODO Auto-generated method stub
		JSONObject param = JSONObject.parseObject(parameters);

		String apikey = param.getString("apikey");
		
		TblVersion version = versionMapper.selectByPrimaryKey(apikey);
		
		if (version==null) {
			PytheResult.build(400, "apikey不合法");
		}
		
		return PytheResult.ok(version);
	}



	@Override
	public PytheResult countCarCondition(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		VCustomerExample example = new VCustomerExample();
		example.createCriteria().andCarStatusEqualTo(1);
		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		JSONArray arr =new JSONArray();
		JSONObject json =new JSONObject();
		
		if (!customerList.isEmpty()) {
			json.put("size",customerList.size());
			for (VCustomer vCustomer : customerList) {
				JSONObject json2 = new JSONObject();
				json2.put("start_time",DateUtils.formatTime(vCustomer.getStartTime()));
				json2.put("phone_num", vCustomer.getPhoneNum());
				json2.put("level", vCustomer.getLevel());
				arr.add(json2);
			}
			json.put("user", arr);
		}else{
			json.put("size", 0);
			json.put("user", null);
		}
		
		return PytheResult.ok(json);
	}
	
	
	
	

	


	

}
