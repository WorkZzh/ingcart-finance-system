package com.pythe.rest.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.DateUtils;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.common.utils.JsonUtils;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblCarMapper;
import com.pythe.mapper.TblCustomerMapper;
import com.pythe.mapper.TblDistributionMapper;
import com.pythe.mapper.TblMaintenanceMapper;
import com.pythe.mapper.TblPriceMapper;
import com.pythe.mapper.TblVersionMapper;
import com.pythe.mapper.VCustomerMapper;
import com.pythe.mapper.VRecordBillMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblCar;
import com.pythe.pojo.TblCarExample;
import com.pythe.pojo.TblCustomer;
import com.pythe.pojo.TblCustomerExample;
import com.pythe.pojo.TblDistribution;
import com.pythe.pojo.TblDistributionExample;
import com.pythe.pojo.TblMaintenance;
import com.pythe.pojo.TblMaintenanceExample;
import com.pythe.pojo.TblPrice;
import com.pythe.pojo.TblPriceExample;
import com.pythe.pojo.TblStore;
import com.pythe.pojo.TblStoreExample;
import com.pythe.pojo.TblVersion;
import com.pythe.pojo.VCustomer;
import com.pythe.pojo.VCustomerExample;
import com.pythe.pojo.VRecordBill;
import com.pythe.pojo.VRecordBillExample;
import com.pythe.rest.service.ManagerService;



@Service
public class ManagerServiceImpl implements ManagerService{
	
	
	@Value("${CAR_FREE_STATUS}")
	private Integer CAR_FREE_STATUS;
	
	@Autowired
	private TblVersionMapper versionMapper;
	
	@Autowired
	private VCustomerMapper vCustomerMapper;
	
	@Autowired
	private TblCarMapper carMapper;
	
	@Autowired
	private TblPriceMapper priceMapper;
	
	@Autowired
	private TblDistributionMapper distributionMapper;
	
	@Autowired
	private TblMaintenanceMapper maintenanceMapper;
	
	@Autowired
	private TblCustomerMapper customerMapper;
	
	@Autowired
	private TblAccountMapper accountMapper;
	
	@Autowired
	private VRecordBillMapper recordBillMapper;

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
		example.setOrderByClause("qr_id asc");
		example.createCriteria().andCarStatusEqualTo(1);
		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		
		int size = vCustomerMapper.countByExample(example);
		JSONArray arr =new JSONArray();
		JSONObject json =new JSONObject();
		
		if (!customerList.isEmpty()) {
			json.put("size",size);
			for (VCustomer vCustomer : customerList) {
				JSONObject json2 = new JSONObject();
				json2.put("start_time",DateUtils.formatTime(vCustomer.getStartTime()));
				json2.put("phone_num", vCustomer.getPhoneNum());
				json2.put("car_code", vCustomer.getQrId());
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

	@Override
	public PytheResult updateFixedPointForCar(String parameters) {
		//关联景区和车
		JSONObject information = JSONObject.parseObject(parameters);
		Long qrId = information.getLong("qrId");
		String areaId = information.getString("areaId");
		TblDistribution distribution = distributionMapper.selectByPrimaryKey(areaId);
		
		List<Long> list = null;
		if (null == distribution.getCarIds()) {
			list = new  LinkedList<Long>();
		}else{
			list = JsonUtils.jsonToList(distribution.getCarIds(), Long.class);
			if (list.contains(qrId)) {
				return PytheResult.build(400, "该车编号已录入，无需再录入");
			}
		}
		list.add(qrId);
		distribution.setCarIds(JsonUtils.objectToJson(list));
		distributionMapper.updateByPrimaryKeyWithBLOBs(distribution);
		
		//给车定级
		TblCarExample example = new TblCarExample();
		example.createCriteria().andQrIdEqualTo(qrId);
		List<TblCar> carList = carMapper.selectByExample(example);
		if (carList.isEmpty()) {
			return PytheResult.build(400, "车编号未录入或输入错误");
		}
		TblCar car = carList.get(0);
		car.setDescription(distribution.getLevel());
		carMapper.updateByPrimaryKey(car);
		return PytheResult.ok("关联成功");
	}

	@Override
	public PytheResult deleteFixedPointForCar(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		Long qrId = information.getLong("qrId");
		String areaId = information.getString("areaId");
		//删除关联
		TblDistribution distribution = distributionMapper.selectByPrimaryKey(areaId);
		List<Long> list = JsonUtils.jsonToList(distribution.getCarIds(), Long.class);
		list.remove(qrId);
		distribution.setCarIds(JsonUtils.objectToJson(list));
		distributionMapper.updateByPrimaryKeyWithBLOBs(distribution);
		
		// 车信息
		TblCarExample example = new TblCarExample();
		example.createCriteria().andQrIdEqualTo(qrId);
		List<TblCar> carList = carMapper.selectByExample(example);
		if (carList.isEmpty()) {
			PytheResult.build(400, "该车数据未录入，请联系管理员");
		}

		TblCar car = carList.get(0);
		car.setDescription("A");
		carMapper.updateByPrimaryKey(car);
		
		return PytheResult.ok("删除成功");
	}

	
	@Override
	public PytheResult insertAttraction(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String city = information.getString("city");
		String name = information.getString("name");
		String level = information.getString("level");
		TblDistribution distribution =new TblDistribution();
		distribution.setCity(city);
		distribution.setId(FactoryUtils.getUUID());
		distribution.setLevel(level);
		distribution.setCreated(new Date());
		distribution.setName(name);
		distributionMapper.insert(distribution);
		return PytheResult.ok("景区信息创建成功");
	}

	@Override
	public PytheResult selectCarAttraction(String parameters) {
		
		JSONObject information = JSONObject.parseObject(parameters);
		List<Long> carIds = information.getJSONArray("carIds").toJavaList(Long.class);
		Integer pageNum = information.getInteger("pageNum");
		Integer pageSize = information.getInteger("pageSize");
		
		PageHelper.startPage(pageNum, pageSize);
		VCustomerExample example = new VCustomerExample();
		example.createCriteria().andCarStatusEqualTo(1).andQrIdIn(carIds);
		
		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);
		
		int size = vCustomerMapper.countByExample(example);
		JSONArray arr =new JSONArray();
		JSONObject json =new JSONObject();
		
		if (!customerList.isEmpty()) {
			json.put("size",size);
			for (VCustomer vCustomer : customerList) {
				JSONObject json2 = new JSONObject();
				json2.put("start_time",DateUtils.formatTime(vCustomer.getStartTime()));
				json2.put("phone_num", vCustomer.getPhoneNum());
				json2.put("car_code", vCustomer.getQrId());
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


	@Override
	public PytheResult selectAllAreaByCity(String city, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		TblDistributionExample example =new TblDistributionExample();
		example.createCriteria().andCityEqualTo(city);
		List<TblDistribution> diList = distributionMapper.selectByExampleWithBLOBs(example);
		return PytheResult.ok(diList);
	}

	@Override
	public PytheResult selectAllCity() {
		// TODO Auto-generated method stub
		List<TblDistribution> distributionList = distributionMapper.selectAllCity();
		if (distributionList.isEmpty()) {
			return PytheResult.build(400,"暂无景区所在的城市");
		}else{
			return PytheResult.ok(distributionList);	
		}
	}

	@Override
	public PytheResult selectMaintennanceCondition(Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		TblMaintenanceExample example =new TblMaintenanceExample();
		List<TblMaintenance> maintenanceList = maintenanceMapper.selectByExampleWithBLOBs(example);
		if (maintenanceList.isEmpty()) {
			return PytheResult.build(400, "暂无客户维修反馈");
		}
		
		for (TblMaintenance tblMaintenance : maintenanceList) {
			List<String> type = JsonUtils.jsonToList(tblMaintenance.getType(), String.class);
			String str = "";
			for (String string : type) {
				str = str +string+" ";
			}
			tblMaintenance.setType(str);
			tblMaintenance.setTime(DateUtils.formatTime(tblMaintenance.getCallTime()));
			tblMaintenance.setCallTime(null);
		}
		return PytheResult.ok(maintenanceList);
	}
	
	
	

	@Override
	public PytheResult selectPriceLevel() {
		// TODO Auto-generated method stub
		TblPriceExample example =new TblPriceExample();
		List<TblPrice> priceList = priceMapper.selectByExample(example);
		return PytheResult.ok(priceList);
	}

	@Override
	public PytheResult updateMaintenanceStatus(Long id ) {
		// TODO Auto-generated method stub
		TblMaintenance maintenance = maintenanceMapper.selectByPrimaryKey(id);
		maintenance.setStatus(1);
		maintenanceMapper.updateByPrimaryKeyWithBLOBs(maintenance);
		return PytheResult.ok("修改成功");
	}

	@Override
	public PytheResult insertManager(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum");
		VCustomerExample example5 = new VCustomerExample();
		example5.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<VCustomer> customerList = vCustomerMapper.selectByExample(example5);
		
		if (customerList.isEmpty()) {
			TblCustomer newCustomer = new TblCustomer();
			newCustomer.setLevel(1);
			newCustomer.setPhoneNum(phoneNum);
			newCustomer.setCreated(new Date());
			newCustomer.setType(0);
			customerMapper.insert(newCustomer);
			return PytheResult.ok("添加成功");
		}
		
		VCustomer customer = customerList.get(0);
		if (1!=customer.getLevel() && 2!=customer.getLevel()) {
			TblCustomer newCustomer = new TblCustomer();
			newCustomer.setId(customer.getCustomerId());
			newCustomer.setLevel(1);
			newCustomer.setType(0);
			newCustomer.setCreated(new Date());
			customerMapper.updateByPrimaryKeySelective(newCustomer);
			
			
			
			return PytheResult.ok("添加成功");
		}else{
			return PytheResult.build(400, "已添加，无需重复添加");
		}
		
		
		
		
		
		
		
		

		
		
	}

	@Override
	public PytheResult updateLocation(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		Long qrId = information.getLong("qrId");
		Double longitude = information.getDouble("longitude");
		Double latitude = information.getDouble("latitude");
		
		TblCarExample carExample = new TblCarExample();
		carExample.createCriteria().andQrIdEqualTo(qrId);
		List<TblCar> cars = carMapper.selectByExample(carExample);
		
		if (cars.isEmpty()) {
			PytheResult.build(400, "车信息不存在或编码输入不正确");
		}
		
		TblCar car =cars.get(0);
		car.setLatitude(latitude);
		car.setLongitude(longitude);
		// car.setRecordid(recordId); 因为第一次开锁时候就已经记录的该车属于那条记录
		carMapper.updateByPrimaryKey(car);
		
		return PytheResult.ok("更新成功");
	}

	@Override
	public PytheResult deleteMaintenanceStatus(Long qrId) {
		// TODO Auto-generated method stub
		
		TblMaintenanceExample example = new TblMaintenanceExample();
		example.createCriteria().andQrIdEqualTo(qrId);
		List<TblMaintenance> maintenanceList = maintenanceMapper.selectByExampleWithBLOBs(example);
		
		if (maintenanceList.isEmpty()) {
			return PytheResult.build(400, "该车没有被报修");
		}
		
		TblMaintenance maintenance = maintenanceList.get(0);
		maintenanceMapper.deleteByPrimaryKey(maintenance.getId());
		
		//看看车牌号是否存在
		TblCarExample example2  =  new TblCarExample();
		example2.createCriteria().andQrIdEqualTo(qrId);
		
		List<TblCar> carList = carMapper.selectByExample(example2);

		TblCar car = carList.get(0);
		
		//改变报修的状态码
		car.setStatus(CAR_FREE_STATUS);
		carMapper.updateByPrimaryKey(car);
		
		return PytheResult.ok("解修成功");
	}

	@Override
	public PytheResult zeroCleanAccount(String parameters) {
		
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNumStr = information.getString("phoneNum");
		List<Long> phoneNums = JsonUtils.jsonToList(phoneNumStr, Long.class);
		
		TblCustomerExample customerExample = new TblCustomerExample();
		for (Long phoneNum : phoneNums) {
			
			customerExample.createCriteria().andPhoneNumEqualTo(phoneNum.toString());
			List<TblCustomer> results = customerMapper.selectByExample(customerExample);
			if(results.isEmpty())
			{
				return PytheResult.build(400, "无此用户", phoneNum);
			}
			else
			{
				TblCustomer customer = results.get(0);
				TblAccount account = accountMapper.selectByPrimaryKey(customer.getId());
				account.setAmount(0d);
				accountMapper.updateByPrimaryKeySelective(account);
			}
			customerExample.clear();
		}
		
		return PytheResult.ok("已全部清零");
	}

	@Override
	public PytheResult queryRecordBill(String parameters) {
		JSONObject params = JSONObject.parseObject(parameters);

		Integer pageNum = params.getInteger("pageNum");
		Integer pageSize = params.getInteger("pageSize");
		if (pageNum == null || pageSize == null) {
			pageNum = 1;
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);
		
		VRecordBillExample recordBillExample = new VRecordBillExample();
		recordBillExample.createCriteria();
		List<VRecordBill> recordBills = recordBillMapper.selectByExample(recordBillExample);
		
		List<JSONObject> results = new LinkedList<JSONObject>();
		
		for (VRecordBill vRecordBill : recordBills) {
			JSONObject result = new JSONObject();
			result.put("car_number", vRecordBill.getQrId());
			result.put("phone_number", vRecordBill.getPhoneNum());
			result.put("distribution_name", vRecordBill.getDistributionName());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			result.put("date", dateFormat.format(vRecordBill.getTime()));
			SimpleDateFormat  timeFormat= new SimpleDateFormat("HH:mm:ss");
			result.put("start_time", timeFormat.format(vRecordBill.getStartTime()));
			result.put("stop_time", timeFormat.format(vRecordBill.getStopTime()));
			result.put("amount", vRecordBill.getAmount());
			results.add(result);
		}
		
		return PytheResult.ok(results);
	}


	
	

	


	

}
