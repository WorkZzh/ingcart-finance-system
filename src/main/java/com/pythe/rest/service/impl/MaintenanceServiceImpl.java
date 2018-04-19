package com.pythe.rest.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
import com.pythe.common.utils.JsonUtils;
import com.pythe.mapper.TblCarMapper;
import com.pythe.mapper.TblCatalogMapper;
import com.pythe.mapper.TblDistributionMapper;
import com.pythe.mapper.TblMaintenanceMapper;
import com.pythe.mapper.VAcountRecordMapper;
import com.pythe.mapper.VMaintenanceMapper;
import com.pythe.mapper.VOperatorMapper;
import com.pythe.mapper.VOperatorRecordMapper;
import com.pythe.pojo.TblCar;
import com.pythe.pojo.TblCarExample;
import com.pythe.pojo.TblCatalog;
import com.pythe.pojo.TblCatalogExample;
import com.pythe.pojo.TblDistribution;
import com.pythe.pojo.TblDistributionExample;
import com.pythe.pojo.TblMaintenance;
import com.pythe.pojo.TblMaintenanceExample;
import com.pythe.pojo.VAcountRecord;
import com.pythe.pojo.VAcountRecordExample;
import com.pythe.pojo.VMaintenance;
import com.pythe.pojo.VMaintenanceExample;
import com.pythe.pojo.VOperator;
import com.pythe.pojo.VOperatorExample;
import com.pythe.rest.service.MaintenanceService;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

	@Autowired
	private TblMaintenanceMapper maintenanceMapper;

	@Autowired
	private VAcountRecordMapper aCountRecordMapper;

	@Autowired
	private TblCarMapper carMapper;

	@Autowired
	private VOperatorMapper VOperatorMapper;

	@Autowired
	private TblCatalogMapper tblCatalogMapper;

	@Autowired
	private TblDistributionMapper tblDistributionMapper;

	@Autowired
	private TblCatalogMapper catalogMapper;

	@Autowired
	private VOperatorRecordMapper vOperatorRecordMapper;

	@Autowired
	private VMaintenanceMapper vMaintenanceMapper;

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

	@Override
	public PytheResult callRepair(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum");
		JSONArray typeStr = information.getJSONArray("type");
		Double longitude = information.getDouble("longitude");
		Double latitude = information.getDouble("latitude");
		Long qrId = information.getLong("qrId");
		String annotation = information.getString("annotation");
		String level = information.getString("level");
		// 查询该权限的所有车辆
		ArrayList<String> list = new ArrayList<String>();
		TblDistributionExample tblDistributionExample = new TblDistributionExample();
		com.pythe.pojo.TblDistributionExample.Criteria criteria = tblDistributionExample.createCriteria();
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
			criteria.andLevelIn(list);
		}

		List<TblDistribution> distributions = tblDistributionMapper.selectByExampleWithBLOBs(tblDistributionExample);
		if (distributions.isEmpty()) {
			return PytheResult.build(400, "园区没有投放该车辆");
		}
		List<Long> listcars = new ArrayList<Long>();
		for (TblDistribution tblDistribution : distributions) {
			if (tblDistribution.getCarIds() != null) {
				List<Long> listTemp = JsonUtils.jsonToList(tblDistribution.getCarIds(), long.class);
				listcars.addAll(listTemp);
			}
		}
		if (!listcars.contains(qrId)) {
			return PytheResult.build(400, "管理员权限不够");
		}

		// 该车已经被报修成功
		TblMaintenanceExample example3 = new TblMaintenanceExample();
		example3.createCriteria().andQrIdEqualTo(qrId);
		List<TblMaintenance> maintenanceList = maintenanceMapper.selectByExampleWithBLOBs(example3);
		if (!maintenanceList.isEmpty()) {
			return PytheResult.build(400, "该车已被报修过，已排工作人员前往维修");
		}

		// 看看车牌号是否存在
		TblCarExample example = new TblCarExample();
		example.createCriteria().andQrIdEqualTo(qrId);
		List<TblCar> carList = carMapper.selectByExample(example);
		if (carList.isEmpty()) {
			return PytheResult.build(400, "输入车牌号错误");
		}
		// 更新小车状态
		TblCar car = carList.get(0);
		car.setStatus(CAR_MAINTENCE_STATUS);
		carMapper.updateByPrimaryKey(car);
		// 添加维修记录
		TblMaintenance record = new TblMaintenance();
		record.setQrId(qrId);
		record.setLongitude(longitude);
		record.setLatitude(latitude);
		record.setType(JsonUtils.objectToJson(typeStr));
		record.setAnnotation(annotation);
		record.setCallTime(new Date());
		record.setStatus(0);
		record.setPhoneNum(phoneNum);
		maintenanceMapper.insert(record);

		return PytheResult.ok("谢谢反馈");
	}

	@Override
	public PytheResult selectTripBillByCustomerId(Long customerId, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		VAcountRecordExample example = new VAcountRecordExample();
		example.createCriteria().andCustomerIdEqualTo(customerId);
		List<VAcountRecord> result = aCountRecordMapper.selectByExample(example);

		if (result.isEmpty()) {
			PytheResult.build(400, "暂无任何行程记录");
		}

		String key = null;
		LinkedList<JSONObject> list = null;
		List<String> date = new LinkedList<String>();

		LinkedHashMap<String, LinkedList<JSONObject>> map = new LinkedHashMap<String, LinkedList<JSONObject>>();
		for (VAcountRecord vAcountRecord : result) {
			JSONObject json = new JSONObject();
			key = DateUtils.formatDate(vAcountRecord.getStopTime());
			// json.put("duration",
			// DateUtils.formatDateToHour2Minute(vAcountRecord.getStartTime())
			// +"-"+DateUtils.formatDateToHour2Minute(vAcountRecord.getStopTime()));
			json.put("duration", "行程结束");
			json.put("billId", vAcountRecord.getBillId());
			// json.put("minute",
			// DateUtils.minusForPartHour(vAcountRecord.getStopTime(),vAcountRecord.getStartTime()));
			json.put("minute", "1");
			json.put("amount", vAcountRecord.getAmount());
			if (!map.containsKey(key)) {
				list = new LinkedList<JSONObject>();
				list.add(json);
				map.put(key, list);
				date.add(key);
			} else {
				list = map.get(key);
				list.add(json);
				map.put(key, list);
			}
		}
		JSONObject o = new JSONObject();
		o.put("historyDate", date);
		o.put("record", map);
		return PytheResult.ok(o);
	}

	@Override
	public PytheResult recordMaintenance(String parameter) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameter);
		Long qrId = information.getLong("qr_id");

		TblMaintenanceExample example = new TblMaintenanceExample();
		example.createCriteria().andQrIdEqualTo(qrId);
		List<TblMaintenance> maintenanceList = maintenanceMapper.selectByExampleWithBLOBs(example);

		if (maintenanceList.isEmpty()) {
			return PytheResult.build(400, "无需重复录入");
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
		car.setUser(null);
		carMapper.updateByPrimaryKey(car);

		return PytheResult.ok("录入成功");
	}

	@Override
	public PytheResult selectMaintenanceByLevel(String level, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		// 获取该权限下的所有车辆
		ArrayList<String> list = new ArrayList<String>();
		TblDistributionExample tblDistributionExample = new TblDistributionExample();
		com.pythe.pojo.TblDistributionExample.Criteria criteria = tblDistributionExample.createCriteria();
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
			criteria.andLevelIn(list);
		}

		List<TblDistribution> distributions = tblDistributionMapper.selectByExampleWithBLOBs(tblDistributionExample);
		if (distributions.isEmpty()) {
			return PytheResult.build(400, "园区没有投放车辆");
		}
		List<Long> listcars = new ArrayList<Long>();
		for (TblDistribution tblDistribution : distributions) {
			if (tblDistribution.getCarIds() != null) {
				List<Long> listTemp = JsonUtils.jsonToList(tblDistribution.getCarIds(), long.class);
				listcars.addAll(listTemp);
			}
		}
		// 查询报修记录
		PageHelper.startPage(pageNum, pageSize);
		VMaintenanceExample vMaintenanceExample = new VMaintenanceExample();
		vMaintenanceExample.createCriteria().andQrIdIn(listcars);
		List<VMaintenance> vMaintenances = vMaintenanceMapper.selectByExampleWithBLOBs(vMaintenanceExample);
		if (!vMaintenances.isEmpty()) {
			return PytheResult.ok(vMaintenances);
		}
		return PytheResult.build(400, "暂无报修记录");

	}

	@Override
	public PytheResult deleteMaintenanceStatusByLevel(String level, Long qrId) {
		// TODO Auto-generated method stub
		// 获取该权限下的所有车辆
		ArrayList<String> list = new ArrayList<String>();
		TblDistributionExample tblDistributionExample = new TblDistributionExample();
		com.pythe.pojo.TblDistributionExample.Criteria criteria = tblDistributionExample.createCriteria();
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
			criteria.andLevelIn(list);
		}

		List<TblDistribution> distributions = tblDistributionMapper.selectByExampleWithBLOBs(tblDistributionExample);
		if (distributions.isEmpty()) {
			return PytheResult.build(400, "园区没有投放车辆");
		}
		List<Long> listcars = new ArrayList<Long>();
		for (TblDistribution tblDistribution : distributions) {
			if (tblDistribution.getCarIds() != null) {
				List<Long> listTemp = JsonUtils.jsonToList(tblDistribution.getCarIds(), long.class);
				listcars.addAll(listTemp);
			}
		}
		if (!listcars.contains(qrId)) {
			return PytheResult.build(400, "管理员权限不够");
		}
		// 删除报修记录
		TblMaintenanceExample tblMaintenanceExample = new TblMaintenanceExample();
		tblMaintenanceExample.createCriteria().andQrIdEqualTo(qrId);
		List<TblMaintenance> maintenanceList = maintenanceMapper.selectByExampleWithBLOBs(tblMaintenanceExample);
		if (maintenanceList.isEmpty()) {
			return PytheResult.build(400, "该车没有被报修");
		}
		maintenanceMapper.deleteByPrimaryKey(maintenanceList.get(0).getId());
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
	public PytheResult updateFixedPointForCarByLevel(String parameters) {
		// TODO Auto-generated method stub
		// 关联景区和车
		JSONObject information = JSONObject.parseObject(parameters);
		Long qrId = information.getLong("qrId");
		String areaId = information.getString("areaId");
		String level = information.getString("level");
		TblDistributionExample tblDistributionExample = new TblDistributionExample();
		tblDistributionExample.createCriteria().andLevelEqualTo(level).andIdEqualTo(areaId);
		List<TblDistribution> distributions = tblDistributionMapper.selectByExampleWithBLOBs(tblDistributionExample);
		if (distributions.isEmpty()) {
			return PytheResult.build(400, "输入有误或权限不够");
		}
		List<Long> list = new ArrayList<Long>();
		if (distributions.get(0).getCarIds() != null) {
			list = JsonUtils.jsonToList(distributions.get(0).getCarIds(), Long.class);
		}

		if (list.contains(qrId)) {
			return PytheResult.build(400, "该车编号已录入，无需再录入");
		}

		list.add(qrId);
		distributions.get(0).setCarIds(JsonUtils.objectToJson(list));
		tblDistributionMapper.updateByPrimaryKeyWithBLOBs(distributions.get(0));

		// 给车定级
		TblCarExample example = new TblCarExample();
		example.createCriteria().andQrIdEqualTo(qrId);
		List<TblCar> carList = carMapper.selectByExample(example);
		if (carList.isEmpty()) {
			return PytheResult.build(400, "车编号未录入或输入错误");
		}
		TblCar car = carList.get(0);
		car.setDescription(distributions.get(0).getLevel());
		carMapper.updateByPrimaryKey(car);
		return PytheResult.ok("关联成功");
	}

}
