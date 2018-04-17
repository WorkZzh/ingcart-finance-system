package com.pythe.rest.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.mchange.v2.lang.Coerce;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.DateUtils;
import com.pythe.common.utils.ExcelExport;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.common.utils.JsonUtils;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblCarMapper;
import com.pythe.mapper.TblCatalogMapper;
import com.pythe.mapper.TblCustomerMapper;
import com.pythe.mapper.TblDistributionMapper;
import com.pythe.mapper.TblMaintenanceMapper;
import com.pythe.mapper.TblOperatorMapper;
import com.pythe.mapper.TblPriceMapper;
import com.pythe.mapper.TblTeasurerMapper;
import com.pythe.mapper.TblVersionMapper;
import com.pythe.mapper.VCatalogMapper;
import com.pythe.mapper.VCustomerMapper;
import com.pythe.mapper.VOperatorMapper;
import com.pythe.mapper.VOperatorRecordMapper;
import com.pythe.mapper.VRecordBillMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblCar;
import com.pythe.pojo.TblCarExample;
import com.pythe.pojo.TblCatalog;
import com.pythe.pojo.TblCatalogExample;
import com.pythe.pojo.TblCustomer;
import com.pythe.pojo.TblCustomerExample;
import com.pythe.pojo.TblDistribution;
import com.pythe.pojo.TblDistributionExample;
import com.pythe.pojo.TblMaintenance;
import com.pythe.pojo.TblMaintenanceExample;
import com.pythe.pojo.TblOperator;
import com.pythe.pojo.TblOperatorExample;
import com.pythe.pojo.TblPrice;
import com.pythe.pojo.TblPriceExample;
import com.pythe.pojo.TblTeasurer;
import com.pythe.pojo.TblTeasurerExample;
import com.pythe.pojo.TblVersion;
import com.pythe.pojo.VCatalog;
import com.pythe.pojo.VCatalogExample;
import com.pythe.pojo.VCustomer;
import com.pythe.pojo.VCustomerExample;
import com.pythe.pojo.VCustomerExample.Criteria;
import com.pythe.pojo.VOperator;
import com.pythe.pojo.VOperatorExample;
import com.pythe.pojo.VOperatorRecord;
import com.pythe.pojo.VOperatorRecordExample;
import com.pythe.pojo.VRecordBill;
import com.pythe.pojo.VRecordBillExample;
import com.pythe.rest.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {

	@Value("${CAR_FREE_STATUS}")
	private Integer CAR_FREE_STATUS;

	@Value("${TOP_COMPANY_ID}")
	private String TOP_COMPANY_ID;

	@Value("${SECOND_CODE}")
	private Integer SECOND_CODE;

	@Value("${TOP_CODE}")
	private Integer TOP_CODE;

	@Value("${THREE_CODE}")
	private Integer THREE_CODE;

	@Value("${WEIXIN_REGISTER_TYPE}")
	private Integer WEIXIN_REGISTER_TYPE;

	@Value("${TEASURER_SECOND_LEVEL}")
	private Integer TEASURER_SECOND_LEVEL;

	@Value("${TOP_MANGER_LEVEL}")
	private Integer TOP_MANGER_LEVEL;

	@Value("${INGCART_MANAGER_LEVEL}")
	private Integer INGCART_MANAGER_LEVEL;

	@Value("${GROUP_MANAGER_LEVEL}")
	private Integer GROUP_MANAGER_LEVEL;

	@Value("${COMPANY_MANAGER_LEVEL}")
	private Integer COMPANY_MANAGER_LEVEL;

	@Value("${PART_MANGER_LEVEL}")
	private Integer PART_MANGER_LEVEL;

	@Autowired
	private TblVersionMapper versionMapper;

	@Autowired
	private VCustomerMapper vCustomerMapper;

	@Autowired
	private TblCarMapper carMapper;

	@Autowired
	private TblPriceMapper priceMapper;

	@Autowired
	private TblTeasurerMapper teasurerMapper;

	@Autowired
	private TblOperatorMapper operatorMapper;

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

	@Autowired
	private TblCatalogMapper catalogMapper;

	@Autowired
	private VCatalogMapper vCatalogMapper;

	@Autowired
	private VOperatorRecordMapper vOperatorRecordMapper;

	@Autowired
	private VOperatorMapper vOperatorMapper;

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
		if (record == null) {
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

		if (version == null) {
			PytheResult.build(400, "apikey不合法");
		}

		return PytheResult.ok(version);
	}

	@Override
	public PytheResult countCarCondition(String level, Integer pageNum, Integer pageSize) {

		VCustomerExample example = new VCustomerExample();
		Criteria criteria = example.createCriteria();
		criteria.andCarStatusEqualTo(1);
		ArrayList<String> list = new ArrayList<String>();
		if (!"0".equals(level)) {
			TblCatalogExample example2 = new TblCatalogExample();
			example2.createCriteria().andHigherLevelIdEqualTo(level);
			List<TblCatalog> catalogList = catalogMapper.selectByExample(example2);
			if (!catalogList.isEmpty()) {
				for (TblCatalog tblCatalog : catalogList) {
					list.add(tblCatalog.getId());
				}
			} else {
				list.add(level);
			}
			criteria.andDescriptionIn(list);
		}

		PageHelper.startPage(pageNum, pageSize);

		List<VCustomer> customerList = vCustomerMapper.selectByExample(example);

		int size = vCustomerMapper.countByExample(example);
		JSONArray arr = new JSONArray();
		JSONObject json = new JSONObject();

		if (!customerList.isEmpty()) {
			json.put("size", size);
			for (VCustomer vCustomer : customerList) {
				JSONObject json2 = new JSONObject();
				json2.put("start_time", DateUtils.getMonthDay(vCustomer.getStartTime()));
				json2.put("phone_num", vCustomer.getPhoneNum());
				json2.put("car_code", vCustomer.getQrId());
				json2.put("level", vCustomer.getLevel());
				arr.add(json2);
			}
			json.put("user", arr);
		} else {
			json.put("size", 0);
			json.put("user", null);
		}

		return PytheResult.ok(json);
	}

	@Override
	public PytheResult updateFixedPointForCar(String parameters) {
		// 关联景区和车
		JSONObject information = JSONObject.parseObject(parameters);
		Long qrId = information.getLong("qrId");
		String areaId = information.getString("areaId");
		TblDistribution distribution = distributionMapper.selectByPrimaryKey(areaId);

		List<Long> list = null;
		if (null == distribution.getCarIds()) {
			list = new LinkedList<Long>();
		} else {
			list = JsonUtils.jsonToList(distribution.getCarIds(), Long.class);
			if (list.contains(qrId)) {
				return PytheResult.build(400, "该车编号已录入，无需再录入");
			}
		}
		list.add(qrId);
		distribution.setCarIds(JsonUtils.objectToJson(list));
		distributionMapper.updateByPrimaryKeyWithBLOBs(distribution);

		// 给车定级
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
		// 删除关联
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
		Double price = information.getDouble("price");
		Double giving = information.getDouble("giving");
		String higherLevelId = information.getString("higherLevelId");

		TblDistributionExample example = new TblDistributionExample();
		example.createCriteria().andNameEqualTo(name);
		List<TblDistribution> diList = distributionMapper.selectByExample(example);
		if (!diList.isEmpty()) {
			return PytheResult.build(400, "已经创建成功，不需重复创建");
		}

		// 插入具体景区和其收费标准
		// level对应的是具体园区的钱
		String level = FactoryUtils.getUUID();
		TblPrice record = new TblPrice();
		record.setLevel(level);
		record.setPrice(price);
		record.setGiving(giving);
		LinkedList<String> list = new LinkedList<String>();
		list.add("1、该景区当日用车封顶" + price.intValue() + "元");
		list.add("2、开锁需要余额不少于" + price.intValue() + "元");
		if (!giving.equals(0d)) {
			list.add("3、指定点还车享" + giving.intValue() + "元返款优惠");
		}
		record.setAnnotation(JsonUtils.objectToJson(list));
		priceMapper.insert(record);

		// 插入景区
		TblDistribution distribution = new TblDistribution();
		distribution.setCity(city);
		distribution.setId(FactoryUtils.getUUID());
		distribution.setLevel(level);
		distribution.setCreated(new Date());
		distribution.setName(name);
		distributionMapper.insert(distribution);

		// 关联目录树
		TblCatalog catalog = new TblCatalog();
		catalog.setId(level);
		catalog.setHigherLevelId(higherLevelId);
		catalog.setCode(THREE_CODE);
		catalog.setName(name);
		catalogMapper.insert(catalog);

		return PytheResult.ok("创建成功");
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
		JSONArray arr = new JSONArray();
		JSONObject json = new JSONObject();

		if (!customerList.isEmpty()) {
			json.put("size", size);
			for (VCustomer vCustomer : customerList) {
				JSONObject json2 = new JSONObject();
				json2.put("start_time", DateUtils.formatTime(vCustomer.getStartTime()));
				json2.put("phone_num", vCustomer.getPhoneNum());
				json2.put("car_code", vCustomer.getQrId());
				json2.put("level", vCustomer.getLevel());
				arr.add(json2);
			}
			json.put("user", arr);
		} else {
			json.put("size", 0);
			json.put("user", null);
		}
		return PytheResult.ok(json);
	}

	@Override
	public PytheResult selectAllAreaByCity(String city, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		TblDistributionExample example = new TblDistributionExample();
		example.createCriteria().andCityEqualTo(city);
		List<TblDistribution> diList = distributionMapper.selectByExampleWithBLOBs(example);
		return PytheResult.ok(diList);
	}

	@Override
	public PytheResult selectAllCity() {
		// TODO Auto-generated method stub
		List<TblDistribution> distributionList = distributionMapper.selectAllCity();
		if (distributionList.isEmpty()) {
			return PytheResult.build(400, "暂无景区所在的城市");
		} else {
			return PytheResult.ok(distributionList);
		}
	}

	@Override
	public PytheResult selectMaintennanceCondition(Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		TblMaintenanceExample example = new TblMaintenanceExample();
		List<TblMaintenance> maintenanceList = maintenanceMapper.selectByExampleWithBLOBs(example);
		if (maintenanceList.isEmpty()) {
			return PytheResult.build(400, "暂无客户维修反馈");
		}

		for (TblMaintenance tblMaintenance : maintenanceList) {
			List<String> type = JsonUtils.jsonToList(tblMaintenance.getType(), String.class);
			String str = "";
			for (String string : type) {
				str = str + string + " ";
			}
			tblMaintenance.setType(str);
			/*
			 * tblMaintenance.setTime(DateUtils.formatTime(tblMaintenance.
			 * getCallTime()));
			 */
			tblMaintenance.setCallTime(null);
		}
		return PytheResult.ok(maintenanceList);
	}

	@Override
	public PytheResult selectPriceLevel() {
		// TODO Auto-generated method stub
		TblPriceExample example = new TblPriceExample();
		List<TblPrice> priceList = priceMapper.selectByExample(example);
		return PytheResult.ok(priceList);
	}

	@Override
	public PytheResult updateMaintenanceStatus(Long id) {
		// TODO Auto-generated method stub
		TblMaintenance maintenance = maintenanceMapper.selectByPrimaryKey(id);
		maintenance.setStatus(1);
		maintenanceMapper.updateByPrimaryKeyWithBLOBs(maintenance);
		return PytheResult.ok("修改成功");
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

		TblCar car = cars.get(0);
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

		// 看看车牌号是否存在
		TblCarExample example2 = new TblCarExample();
		example2.createCriteria().andQrIdEqualTo(qrId);

		List<TblCar> carList = carMapper.selectByExample(example2);

		TblCar car = carList.get(0);

		// 改变报修的状态码
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
			if (results.isEmpty()) {
				return PytheResult.build(400, "无此用户", phoneNum);
			} else {
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

		String level = params.getString("level");
		String time = params.getString("time");

		/*
		 * 视图v_record_bill
		 */
		VRecordBillExample recordBillExample = new VRecordBillExample();
		com.pythe.pojo.VRecordBillExample.Criteria cretria2 = recordBillExample.createCriteria();

		/*
		 * high_level_id不为0的id集合
		 */
		ArrayList<String> list = new ArrayList<String>();
		if (!"0".equals(level)) {
			TblCatalogExample example2 = new TblCatalogExample();
			example2.createCriteria().andHigherLevelIdEqualTo(level);
			List<TblCatalog> catalogList = catalogMapper.selectByExample(example2);
			if (!catalogList.isEmpty()) {
				for (TblCatalog tblCatalog : catalogList) {
					list.add(tblCatalog.getId());
				}
			} else {
				list.add(level);
			}
			cretria2.andLevelIn(list);
		}

		/*
		 * 查第一天和最后一天的记录
		 */
		if (!"0".equals(time)) {
			cretria2.andTimeBetween(DateUtils.parseTime(DateUtils.getDay(time, 0) + " 01:00:00"),
					DateUtils.parseTime(DateUtils.getDay(time, 1) + " 23:50:00"));
		}

		/*
		 * 分页
		 */
		Integer pageNum = params.getInteger("pageNum");
		Integer pageSize = params.getInteger("pageSize");
		if (pageNum == null || pageSize == null) {
			pageNum = 1;
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);

		/*
		 * 查询出视图中的集合
		 */
		List<VRecordBill> recordBills = recordBillMapper.selectByExample(recordBillExample);

		/*
		 * 设置返回集合
		 */
		List<JSONObject> results = new LinkedList<JSONObject>();
		Integer type = null;
		for (VRecordBill vRecordBill : recordBills) {
			JSONObject result = new JSONObject();
			result.put("car_number", vRecordBill.getQrId());
			result.put("phone_number",
					vRecordBill.getPhoneNum().substring(0, 3) + "????" + vRecordBill.getPhoneNum().substring(7));
			result.put("distribution_name", vRecordBill.getDistributionName());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			result.put("date", dateFormat.format(vRecordBill.getTime()));
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			result.put("start_time", timeFormat.format(vRecordBill.getStartTime()));
			result.put("stop_time", timeFormat.format(vRecordBill.getStopTime()));
			result.put("sum", vRecordBill.getSum());
			type = vRecordBill.getType();
			if (type.equals(BILL_PAY_TYPE)) {
				result.put("typeName", "非定点还");
			} else if (type.equals(PART_REFUND_TYPE)) {
				result.put("typeName", "定点还车");
			} else if (type.equals(TOTAL_REFUND_TYPE)) {
				result.put("typeName", "全额退款");
			} else if (type.equals(TEST_PAY_TYPE)) {
				result.put("typeName", "交易测试");
			} else {
				result.put("typeName", "未知交易");
			}
			results.add(result);
		}

		return PytheResult.ok(results);
	}

	@Override
	public PytheResult selectOneLevel(Long managerId) {
		// TODO Auto-generated method stub
		// id得换一下，t_表示财务 ， y_表示运营
		TblOperator manager = operatorMapper.selectByPrimaryKey(managerId);

		List<VCatalog> catalogList = null;
		VCatalogExample example2 = new VCatalogExample();
		if (manager.getCatalogId().equals("0")) {
			example2.createCriteria().andC2IdEqualTo(manager.getCatalogId());
			catalogList = vCatalogMapper.selectByExample(example2);
			for (VCatalog vCatalog : catalogList) {
				vCatalog.setC2Name(null);
				vCatalog.setC2Id(null);
			}
		} else {
			example2.createCriteria().andC1IdEqualTo(manager.getCatalogId());
			catalogList = vCatalogMapper.selectByExample(example2);
			if (manager.getLevel() < GROUP_MANAGER_LEVEL) {
				for (VCatalog vCatalog : catalogList) {
					vCatalog.setC1Id(vCatalog.getC2Id());
					vCatalog.setC1Name(vCatalog.getC2Name());
					vCatalog.setC2Name(null);
					vCatalog.setC2Id(null);
				}
			} else {
				for (VCatalog vCatalog : catalogList) {
					vCatalog.setC2Name(null);
					vCatalog.setC2Id(null);
				}
			}
		}
		return PytheResult.ok(catalogList);
	}

	@Override
	public PytheResult selectSumByTime(String parameters) {
		// TODO Auto-generated method stub
		JSONObject params = JSONObject.parseObject(parameters);

		String level = params.getString("level");
		String time = params.getString("time");

		ArrayList<String> list = new ArrayList<String>();
		if (!"0".equals(level)) {
			TblCatalogExample example2 = new TblCatalogExample();
			example2.createCriteria().andHigherLevelIdEqualTo(level);
			List<TblCatalog> catalogList = catalogMapper.selectByExample(example2);
			if (!catalogList.isEmpty()) {
				for (TblCatalog tblCatalog : catalogList) {
					list.add(tblCatalog.getId());
				}
			} else {
				list.add(level);
			}
		} else {
			list = null;
		}

		if ("0".equals(time)) {
			time = null;
		}

		VRecordBill recordLists = recordBillMapper.selectSumByTime(list, time);
		recordLists.setFrequency(recordLists.getQrId());
		recordLists.setQrId(null);

		return PytheResult.ok(recordLists);
	}

	@Override
	@Transactional
	public PytheResult insertGroup(String parameters) {
		// TODO Auto-generated method stub
		JSONObject params = JSONObject.parseObject(parameters);

		String name = params.getString("name");
		String phoneNum = params.getString("phoneNum");
		Integer type = params.getInteger("type");
		phoneNum = phoneNum.trim();
		// 验证该电话是否已经加入是这家公司的最高管理员，如果是不允许添加
		if (isExistPhoneInManger(phoneNum)) {
			return PytheResult.build(400, "不允许重复添加，如需更改，请联系开发人员");
		}

		TblCatalog record = new TblCatalog();
		String catalogId = FactoryUtils.getUUID();
		record.setId(catalogId);
		record.setName(name);

		record.setHigherLevelId(TOP_COMPANY_ID);
		record.setCode(SECOND_CODE);
		catalogMapper.insert(record);

		// level为3就具有添加园区功能
		insertOperator(phoneNum, type, catalogId, GROUP_MANAGER_LEVEL);

		// 为了测试，而返回的参数，其实目录树上就有
		JSONObject j = new JSONObject();
		j.put("higherLevelId", catalogId);
		return PytheResult.build(200, "创建成功", j);
	}

	@Override
	public PytheResult selectTwoLevel(String c1_id, Integer level, String catalog_id) {
		// TODO Auto-generated method stub
		List<TblCatalog> cataList = null;
		TblCatalogExample example = new TblCatalogExample();
		// level为1，2时候就是属于一个园区
		if (1 == level || 2 == level) {
			example.createCriteria().andIdEqualTo(catalog_id);
		} else {
			example.createCriteria().andHigherLevelIdEqualTo(c1_id);
		}
		cataList = catalogMapper.selectByExample(example);
		return PytheResult.ok(cataList);
	}

	@Override
	public PytheResult selectTeasurerOneLevel(Long managerId) {
		TblTeasurer manager = teasurerMapper.selectByPrimaryKey(managerId);
		List<VCatalog> catalogList = null;
		VCatalogExample example2 = new VCatalogExample();
		if (manager.getCatalogId().equals("0")) {
			example2.createCriteria().andC2IdEqualTo(manager.getCatalogId());
			catalogList = vCatalogMapper.selectByExample(example2);
			for (VCatalog vCatalog : catalogList) {
				vCatalog.setC2Name(null);
				vCatalog.setC2Id(null);
			}
		} else {
			example2.createCriteria().andC1IdEqualTo(manager.getCatalogId());
			catalogList = vCatalogMapper.selectByExample(example2);
			if (manager.getLevel() < TEASURER_SECOND_LEVEL) {
				for (VCatalog vCatalog : catalogList) {
					vCatalog.setC1Id(vCatalog.getC2Id());
					vCatalog.setC1Name(vCatalog.getC2Name());
					vCatalog.setC2Name(null);
					vCatalog.setC2Id(null);
				}
			} else {
				for (VCatalog vCatalog : catalogList) {
					vCatalog.setC2Name(null);
					vCatalog.setC2Id(null);
				}
			}
		}
		return PytheResult.ok(catalogList);
	}

	@Override
	public PytheResult queryRecordBillByTimes(String parameters) {
		JSONObject params = JSONObject.parseObject(parameters);

		String level = params.getString("level");
		String startTime = params.getString("startTime");
		String endTime = params.getString("endTime");
		/*
		 * 视图v_record_bill
		 */
		VRecordBillExample recordBillExample = new VRecordBillExample();
		com.pythe.pojo.VRecordBillExample.Criteria cretria2 = recordBillExample.createCriteria();

		/*
		 * high_level_id不为0的id集合
		 */
		ArrayList<String> list = new ArrayList<String>();
		if (!"0".equals(level)) {
			TblCatalogExample example2 = new TblCatalogExample();
			example2.createCriteria().andHigherLevelIdEqualTo(level);
			List<TblCatalog> catalogList = catalogMapper.selectByExample(example2);
			if (!catalogList.isEmpty()) {
				for (TblCatalog tblCatalog : catalogList) {
					list.add(tblCatalog.getId());
				}
			} else {
				list.add(level);
			}
			cretria2.andLevelIn(list);
		}

		/*
		 * 查第一天和最后一天的记录
		 */
		if (!"0".equals(startTime) && !"0".equals(endTime)) {
			cretria2.andTimeBetween(DateUtils.parseTime(startTime + " 01:00:00"),
					DateUtils.parseTime(endTime + " 23:50:00"));
		}

		/*
		 * 分页
		 */
		Integer pageNum = params.getInteger("pageNum");
		Integer pageSize = params.getInteger("pageSize");
		if (pageNum == null || pageSize == null) {
			pageNum = 1;
			pageSize = 10;
		}
		PageHelper.startPage(pageNum, pageSize);

		/*
		 * 查询出视图中的集合
		 */
		List<VRecordBill> recordBills = recordBillMapper.selectByExample(recordBillExample);

		/*
		 * 设置返回集合
		 */
		List<JSONObject> results = new LinkedList<JSONObject>();
		Integer type = null;
		for (VRecordBill vRecordBill : recordBills) {
			JSONObject result = new JSONObject();
			result.put("car_number", vRecordBill.getQrId());
			result.put("phone_number",
					vRecordBill.getPhoneNum().substring(0, 3) + "????" + vRecordBill.getPhoneNum().substring(7));
			result.put("distribution_name", vRecordBill.getDistributionName());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			result.put("date", dateFormat.format(vRecordBill.getTime()));
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			result.put("start_time", timeFormat.format(vRecordBill.getStartTime()));
			result.put("stop_time", timeFormat.format(vRecordBill.getStopTime()));
			result.put("sum", vRecordBill.getSum());
			type = vRecordBill.getType();
			if (type.equals(BILL_PAY_TYPE)) {
				result.put("typeName", "非定点还");
			} else if (type.equals(PART_REFUND_TYPE)) {
				result.put("typeName", "定点还车");
			} else if (type.equals(TOTAL_REFUND_TYPE)) {
				result.put("typeName", "全额退款");
			} else if (type.equals(TEST_PAY_TYPE)) {
				result.put("typeName", "交易测试");
			} else {
				result.put("typeName", "未知交易");
			}
			results.add(result);
		}

		return PytheResult.ok(results);
	}

	@Override
	public PytheResult selectSumByTimes(String parameters) {
		// TODO Auto-generated method stub
		JSONObject params = JSONObject.parseObject(parameters);

		String level = params.getString("level");
		String startTime = params.getString("startTime") + " 01:00:00";
		String endTime = params.getString("endTime") + " 23:50:00";
		ArrayList<String> list = new ArrayList<String>();
		if (!"0".equals(level)) {
			TblCatalogExample example2 = new TblCatalogExample();
			example2.createCriteria().andHigherLevelIdEqualTo(level);
			List<TblCatalog> catalogList = catalogMapper.selectByExample(example2);
			if (!catalogList.isEmpty()) {
				for (TblCatalog tblCatalog : catalogList) {
					list.add(tblCatalog.getId());
				}
			} else {
				list.add(level);
			}
		} else {
			list = null;
		}

		if ("0".equals(startTime)) {
			startTime = null;
		}
		if ("0".equals(endTime)) {
			endTime = null;
		}
		VRecordBill recordLists = recordBillMapper.selectSumByTimes(list, startTime, endTime);
		recordLists.setFrequency(recordLists.getQrId());
		recordLists.setQrId(null);

		return PytheResult.ok(recordLists);
	}

	public PytheResult downloadByTime(String parameters) {
		// TODO Auto-generated method stub
		JSONObject params = JSONObject.parseObject(parameters);

		String level = params.getString("level");
		String time = params.getString("time");

		/*
		 * 视图v_record_bill
		 */
		VRecordBillExample recordBillExample = new VRecordBillExample();
		com.pythe.pojo.VRecordBillExample.Criteria cretria2 = recordBillExample.createCriteria();

		/*
		 * high_level_id不为0的id集合
		 */
		ArrayList<String> list = new ArrayList<String>();
		if (!"0".equals(level)) {
			TblCatalogExample example2 = new TblCatalogExample();
			example2.createCriteria().andHigherLevelIdEqualTo(level);
			List<TblCatalog> catalogList = catalogMapper.selectByExample(example2);
			if (!catalogList.isEmpty()) {
				for (TblCatalog tblCatalog : catalogList) {
					list.add(tblCatalog.getId());
				}
			} else {
				list.add(level);
			}
			cretria2.andLevelIn(list);
		}

		/*
		 * 查第一天和最后一天的记录
		 */
		if (!"0".equals(time)) {
			cretria2.andTimeBetween(DateUtils.parseTime(DateUtils.getDay(time, 0) + " 01:00:00"),
					DateUtils.parseTime(DateUtils.getDay(time, 1) + " 23:50:00"));
		}

		/*
		 * 查询出视图中的集合
		 */
		List<VRecordBill> recordBills = recordBillMapper.selectByExample(recordBillExample);

		/*
		 * 设置返回集合
		 */
		List<JSONObject> results = new LinkedList<JSONObject>();
		Integer type = null;
		for (VRecordBill vRecordBill : recordBills) {
			JSONObject result = new JSONObject();
			result.put("car_number", vRecordBill.getQrId());
			result.put("phone_number",
					vRecordBill.getPhoneNum().substring(0, 3) + "????" + vRecordBill.getPhoneNum().substring(7));
			result.put("distribution_name", vRecordBill.getDistributionName());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			result.put("date", dateFormat.format(vRecordBill.getTime()));
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			result.put("start_time", timeFormat.format(vRecordBill.getStartTime()));
			result.put("stop_time", timeFormat.format(vRecordBill.getStopTime()));
			result.put("sum", vRecordBill.getSum());
			type = vRecordBill.getType();
			if (type.equals(BILL_PAY_TYPE)) {
				result.put("typeName", "非定点还");
			} else if (type.equals(PART_REFUND_TYPE)) {
				result.put("typeName", "定点还车");
			} else if (type.equals(TOTAL_REFUND_TYPE)) {
				result.put("typeName", "全额退款");
			} else if (type.equals(TEST_PAY_TYPE)) {
				result.put("typeName", "交易测试");
			} else {
				result.put("typeName", "未知交易");
			}
			results.add(result);
		}
		String retUrl = ExcelExport.export(results);

		JSONObject json = new JSONObject();
		json.put("retUrl", retUrl);
		return PytheResult.build(200, "请求成功", json);
	}

	@Override
	public PytheResult selectTeasurerTwoLevel(String c1_id, Integer level, String catalog_id) {
		// TODO Auto-generated method stub
		List<TblCatalog> cataList = null;
		TblCatalogExample example = new TblCatalogExample();
		// level为1，2时候就是属于一个园区
		if (1 == level) {
			example.createCriteria().andIdEqualTo(catalog_id);
		} else {
			example.createCriteria().andHigherLevelIdEqualTo(c1_id);
		}
		cataList = catalogMapper.selectByExample(example);
		return PytheResult.ok(cataList);

	}

	public PytheResult selectSumByYear(String parameters) {
		// TODO Auto-generated method stub
		JSONObject params = JSONObject.parseObject(parameters);

		String level = params.getString("level");
		String year = params.getString("year");
		ArrayList<String> list = new ArrayList<String>();
		if (!"0".equals(level)) {
			TblCatalogExample example2 = new TblCatalogExample();
			example2.createCriteria().andHigherLevelIdEqualTo(level);
			List<TblCatalog> catalogList = catalogMapper.selectByExample(example2);
			if (!catalogList.isEmpty()) {
				for (TblCatalog tblCatalog : catalogList) {
					list.add(tblCatalog.getId());
				}
			} else {
				list.add(level);
			}
		} else {
			list = null;
		}

		if ("0".equals(year)) {
			year = null;
		}

		List<VRecordBill> recordLists = recordBillMapper.selectSumByYear(list, year);

		return PytheResult.ok(recordLists);
	}

	@Override
	public PytheResult selectOperatorCondition(String level, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<String>();
		VOperatorRecordExample example = new VOperatorRecordExample();
		com.pythe.pojo.VOperatorRecordExample.Criteria criteria = example.createCriteria();
		if (!"0".equals(level)) {
			TblCatalogExample example2 = new TblCatalogExample();
			example2.createCriteria().andHigherLevelIdEqualTo(level);
			List<TblCatalog> catalogList = catalogMapper.selectByExample(example2);
			if (!catalogList.isEmpty()) {
				for (TblCatalog tblCatalog : catalogList) {
					list.add(tblCatalog.getId());
				}
			} else {
				list.add(level);
			}
			// 在这里填上园区信息判断条件
			criteria.andDescriptionIn(list);
		}

		PageHelper.startPage(pageNum, pageSize);
		List<VOperatorRecord> operatorList = vOperatorRecordMapper.selectByExample(example);
		for (VOperatorRecord voperator : operatorList) {
			// 1为行程结束
			if (voperator.getStatus().equals(1)) {
				DateUtils.minusForPartHour(voperator.getStopTime(), voperator.getStartTime());
			} else {
				DateUtils.minusForPartHour(new Date(), voperator.getStartTime());
			}
		}

		if (!operatorList.isEmpty()) {
			return PytheResult.ok(operatorList);
		}
		return PytheResult.build(400, "暂无记录");
	}

	// 重构管理者的方法
	private void insertOperator(String phoneNum, Integer type, String catalogId, Integer level) {
		TblOperator operator = new TblOperator();
		operator.setCatalogId(catalogId);
		operator.setPhoneNum(phoneNum);
		operator.setLevel(level);
		operator.setStatus(1);
		operator.setName(phoneNum.substring(0, 3) + "••••" + phoneNum.substring(7));
		operator.setCreated(new Date());
		operator.setType(type);
		operatorMapper.insert(operator);
	}

	// 重构
	private Boolean isExistPhoneInManger(String phoneNum) {
		// 验证该电话是否已经加入是这家公司的管理员，如果是不允许添加
		TblOperatorExample example = new TblOperatorExample();
		example.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<TblOperator> operatorList = operatorMapper.selectByExample(example);
		if (operatorList.isEmpty())
			return false;
		return true;
	}

	@Override
	public PytheResult insertOperator(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum");
		Integer type = information.getInteger("type");
		String catalogId = information.getString("catalogId");

		if (isExistPhoneInManger(phoneNum)) {
			return PytheResult.build(400, "不允许重复添加，如需更改，请联系开发人员");
		}
		insertOperator(phoneNum, type, catalogId, PART_MANGER_LEVEL);
		return PytheResult.build(400, "该用户已经是管理员");
	}

	@Override
	public PytheResult insertOperatorManager(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum");
		Integer type = information.getInteger("type");
		String catalogId = information.getString("catalogId");
		// 验证该电话是否已经加入是这家公司的管理员，如果是不允许添加
		if (isExistPhoneInManger(phoneNum)) {
			return PytheResult.build(400, "不允许重复添加，如需更改，请联系开发人员");
		}
		insertOperator(phoneNum, type, catalogId, COMPANY_MANAGER_LEVEL);
		return PytheResult.build(200, "添加成功");
	}

	@Override
	public PytheResult insertIngcartManage(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum");
		Integer type = information.getInteger("type");
		String catalogId = information.getString("catalogId");
		// 验证该电话是否已经加入是这家公司的管理员，如果是不允许添加
		if (isExistPhoneInManger(phoneNum)) {
			return PytheResult.build(400, "不允许重复添加，如需更改，请联系开发人员");
		}
		insertOperator(phoneNum, type, catalogId, INGCART_MANAGER_LEVEL);
		return PytheResult.build(200, "添加成功");
	}

	@Override
	public PytheResult selectAddOperatorRecord(String level, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		VOperatorExample example = new VOperatorExample();
		List<VOperator> result = null;
		if (!level.equals("0")) {
			// 查看level的 code是几层，code=2 查c2,code=3查c1
			Integer code = catalogMapper.selectByPrimaryKey(level).getCode();
			if (code == THREE_CODE) {
				example.createCriteria().andC1IdEqualTo(level);
			} else {
				example.createCriteria().andC2IdEqualTo(level);
			}
			// 将用户数据返回
			result = vOperatorMapper.selectByExample(example);
			return PytheResult.ok(result);
		}

		// 当为4，5级时候什么都可以看
		result = vOperatorMapper.selectByExample(example);
		;
		return PytheResult.ok(result);
	}

	public PytheResult deleteOperator(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum");
		Integer status_level = information.getInteger("level");
		// 通过手机号查看管理员等级
		TblOperatorExample tblOperatorExample = new TblOperatorExample();
		tblOperatorExample.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<TblOperator> tblOperators = operatorMapper.selectByExample(tblOperatorExample);
		if (tblOperators.isEmpty()) {
			return PytheResult.build(400, "没有此管理员，请核实后删除");
		}
		int level = tblOperators.get(0).getLevel();
		if (level >= status_level) {
			return PytheResult.build(400, "权限不够，无法删除该等级管理员");
		}
		operatorMapper.deleteByPrimaryKey(tblOperators.get(0).getId());
		return PytheResult.build(200, "删除成功");
	}

}
