package com.pythe.rest.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.CookieUtils;
import com.pythe.common.utils.JWTUtils;
import com.pythe.common.utils.JsonUtils;
import com.pythe.mapper.TblCarMapper;
import com.pythe.mapper.TblCatalogMapper;
import com.pythe.mapper.TblDistributionMapper;
import com.pythe.mapper.TblTeasurerMapper;
import com.pythe.mapper.TblTeasurerRecordMapper;
import com.pythe.mapper.TblVerificationMapper;
import com.pythe.mapper.VTeasurerMapper;
import com.pythe.mapper.VTeasurerRecordMapper;
import com.pythe.pojo.TblCar;
import com.pythe.pojo.TblCarExample;
import com.pythe.pojo.TblCatalog;
import com.pythe.pojo.TblCatalogExample;
import com.pythe.pojo.TblDistribution;
import com.pythe.pojo.TblDistributionExample;
import com.pythe.pojo.TblTeasurer;
import com.pythe.pojo.TblTeasurerExample;
import com.pythe.pojo.TblTeasurerRecord;
import com.pythe.pojo.TblTeasurerRecordExample;
import com.pythe.pojo.TblVerification;
import com.pythe.pojo.TblVerificationExample;
import com.pythe.pojo.VTeasurer;
import com.pythe.pojo.VTeasurerExample;
import com.pythe.pojo.VTeasurerRecord;
import com.pythe.pojo.VTeasurerRecordExample;
import com.pythe.rest.service.TeasurerService;

@Service
public class TeasurerServiceImpl implements TeasurerService {
	@Autowired
	private TblTeasurerMapper tblTeasurerMapper;
	@Autowired
	private VTeasurerMapper vTeasurerMapper;
	@Autowired
	private TblTeasurerRecordMapper tblTeasurerRecordMapper;
	@Autowired
	private VTeasurerRecordMapper vTeasurerRecordMapper;
	@Autowired
	private TblCatalogMapper TblCatalogMapper;
	@Autowired
	private TblVerificationMapper verificationMapper;
	@Autowired
	private TblCatalogMapper catalogMapper;
	@Autowired
	private TblDistributionMapper distributionMapper;
	@Autowired
	private TblCarMapper carMapper;
	@Autowired
	private HttpSession session;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@Override
	public PytheResult selectTeasurerList(String parameters) {
		// TODO Auto-generated method stub
		String tokenMsg = JWTUtils.IngcartTokenOperation(session, request, response);
		if (tokenMsg.equals("登录凭证过期")) {
			return PytheResult.build(400, "登录凭证过期");
		}
		JSONObject params = JSONObject.parseObject(parameters);
		Integer pageNum = params.getInteger("pageNum");
		Integer pageSize = params.getInteger("pageSize");
		TblTeasurerExample tblTeasurerExample = new TblTeasurerExample();
		int count = tblTeasurerMapper.countByExample(tblTeasurerExample);
		if (pageNum == null || pageSize == null) {
			pageNum = 1;
			pageSize = count > 10 ? 10 : count;
		}
		PageHelper.startPage(pageNum, pageSize);
		VTeasurerExample vTeasurerExample = new VTeasurerExample();
		// 三级财务不显示
		vTeasurerExample.createCriteria().andLevelNotEqualTo(3);
		List<VTeasurer> vTeasurerList = vTeasurerMapper.selectByExample(vTeasurerExample);
		return PytheResult.ok(vTeasurerList);
	}

	@Override
	public PytheResult deleteTeasurer(String parameters) {
		// TODO Auto-generated method stub
		String tokenMsg = JWTUtils.IngcartTokenOperation(session, request, response);
		if (tokenMsg.equals("登录凭证过期")) {
			return PytheResult.build(400, "登录凭证过期");
		}
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		String note = information.getString("note");
		System.out.println(note);
		TblTeasurerExample tblTeasurerExample = new TblTeasurerExample();
		tblTeasurerExample.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<TblTeasurer> tblTeasurerList = tblTeasurerMapper.selectByExample(tblTeasurerExample);
		if (tblTeasurerList.size() == 0) {
			return PytheResult.ok("该手机号不存在");
		}
		TblTeasurerRecord tblTeasurerRecord = new TblTeasurerRecord();
		tblTeasurerRecord.setCatalogId(tblTeasurerList.get(0).getCatalogId());
		tblTeasurerRecord.setCreated(new Date());
		tblTeasurerRecord.setId(tblTeasurerList.get(0).getId());
		tblTeasurerRecord.setLevel(tblTeasurerList.get(0).getLevel());
		tblTeasurerRecord.setName(tblTeasurerList.get(0).getName());
		tblTeasurerRecord.setPassword(tblTeasurerList.get(0).getPassword());
		tblTeasurerRecord.setPhoneNum(tblTeasurerList.get(0).getPhoneNum());
		tblTeasurerRecord.setType(tblTeasurerList.get(0).getType());
		tblTeasurerRecord.setNote(note);
		tblTeasurerRecordMapper.insertSelective(tblTeasurerRecord);
		tblTeasurerMapper.deleteByPrimaryKey(tblTeasurerList.get(0).getId());
		return PytheResult.ok("财务人员删除成功");
	}

	@Override
	public PytheResult insertLevelOneTeasurer(String parameters) {
		// TODO Auto-generated method stub
		String tokenMsg = JWTUtils.IngcartTokenOperation(session, request, response);
		if (tokenMsg.equals("登录凭证过期")) {
			return PytheResult.build(400, "登录凭证过期");
		}
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum");
		/*
		 * 由c1Name确定catalog_id
		 */
		String name = information.getString("name");
		TblCatalogExample tblCatalogExample = new TblCatalogExample();
		tblCatalogExample.createCriteria().andNameEqualTo(name);
		List<TblCatalog> tblCatalogList = TblCatalogMapper.selectByExample(tblCatalogExample);
		String catalogId = tblCatalogList.get(0).getId();

		String password = String.valueOf((phoneNum.substring(7)));
		TblTeasurerExample tblTeasurerExample = new TblTeasurerExample();
		tblTeasurerExample.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<TblTeasurer> tblTeasurerList = tblTeasurerMapper.selectByExample(tblTeasurerExample);
		if (tblTeasurerList.size() > 0) {
			return PytheResult.ok("该手机号已经存在");
		}
		TblTeasurer teasurer = new TblTeasurer();
		teasurer.setCreated(new Date());
		/*
		 * web端type默认为3 一级财务的level为1
		 */
		teasurer.setType(3);
		teasurer.setLevel(1);
		teasurer.setName((phoneNum.substring(0, 3) + "****" + phoneNum.substring(7)));
		teasurer.setPhoneNum(phoneNum);
		teasurer.setPassword(password);
		teasurer.setCatalogId(catalogId);
		tblTeasurerMapper.insertSelective(teasurer);
		return PytheResult.ok("一级财务添加成功");
	}

	@Override
	public PytheResult insertLevelTwoTeasurer(String parameters) {
		// TODO Auto-generated method stub
		String tokenMsg = JWTUtils.IngcartTokenOperation(session, request, response);
		if (tokenMsg.equals("登录凭证过期")) {
			return PytheResult.build(400, "登录凭证过期");
		}
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		/*
		 * 由c1Name确定catalog_id
		 */
		String name = information.getString("name");
		TblCatalogExample tblCatalogExample = new TblCatalogExample();
		tblCatalogExample.createCriteria().andNameEqualTo(name);
		List<TblCatalog> tblCatalogList = TblCatalogMapper.selectByExample(tblCatalogExample);
		String catalogId = tblCatalogList.get(0).getId();

		String password = String.valueOf((phoneNum.substring(7)));
		TblTeasurerExample tblTeasurerExample = new TblTeasurerExample();
		tblTeasurerExample.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<TblTeasurer> tblTeasurerList = tblTeasurerMapper.selectByExample(tblTeasurerExample);
		if (tblTeasurerList.size() > 0) {
			return PytheResult.ok("该手机号已经存在");
		}
		TblTeasurer teasurer = new TblTeasurer();
		teasurer.setCreated(new Date());
		/*
		 * web端type默认为3 二级财务的level为2
		 */
		teasurer.setType(3);
		teasurer.setLevel(2);
		teasurer.setName((phoneNum.substring(0, 3) + "****" + phoneNum.substring(7)));
		teasurer.setPhoneNum(phoneNum);
		teasurer.setPassword(password);
		teasurer.setCatalogId(catalogId);
		tblTeasurerMapper.insertSelective(teasurer);
		return PytheResult.ok("二级财务添加成功");
	}

	@Override
	public PytheResult loginByPassowrd(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String name = information.getString("name").trim();
		String password = information.getString("password");
		TblTeasurerExample tblTeasurerExample = new TblTeasurerExample();
		tblTeasurerExample.createCriteria().andPhoneNumEqualTo(name).andPasswordEqualTo(password);
		List<TblTeasurer> tblTeasurerList = tblTeasurerMapper.selectByExample(tblTeasurerExample);
		if (tblTeasurerList.size() == 0) {
			return PytheResult.ok("用户名或密码错误");
		}

		String ingcartToken = "";
		try {
			ingcartToken = JWTUtils.createToken(name, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CookieUtils.setCookie(request, response, "ingcartToken", ingcartToken);
		session.setAttribute("ingcartToken", ingcartToken);

		JSONObject json = new JSONObject();
		json.put("catalogId", tblTeasurerList.get(0).getCatalogId());
		json.put("id", tblTeasurerList.get(0).getId());
		json.put("level", tblTeasurerList.get(0).getLevel());
		return PytheResult.build(200, "登录成功", json);
	}

	@Override
	public PytheResult loginByMessage(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		String verificationCode = information.getString("verificationCode");
		TblTeasurerExample tblTeasurerExample = new TblTeasurerExample();
		tblTeasurerExample.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<TblTeasurer> tblTeasurerList = tblTeasurerMapper.selectByExample(tblTeasurerExample);
		if (tblTeasurerList.size() == 0) {
			return PytheResult.ok("该手机号不存在");
		}
		if (null != verificationCode) {
			TblVerificationExample example = new TblVerificationExample();
			example.createCriteria().andPhoneNumEqualTo(phoneNum)
					.andTimeGreaterThanOrEqualTo(new DateTime().minusMinutes(1).toDate());
			List<TblVerification> verificationList = verificationMapper.selectByExample(example);
			// 验证码对象不存在：是因为之前没有插入或者超时导致的
			if (verificationList.size() == 0) {
				return PytheResult.build(400, "验证码错误或过期");
			}

			TblVerification verification_info = verificationList.get(0);
			// 验证码不正确
			if (!verification_info.getVerificationCode().equals(verificationCode)) {
				return PytheResult.build(400, "验证码错误或过期");
			}
		}
		// token
		TblTeasurerExample tblTeasurerExample2 = new TblTeasurerExample();
		tblTeasurerExample2.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<TblTeasurer> tblTeasurer = tblTeasurerMapper.selectByExample(tblTeasurerExample2);
		String ingcartToken = "";
		try {
			ingcartToken = JWTUtils.createToken(phoneNum, tblTeasurer.get(0).getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CookieUtils.setCookie(request, response, "ingcartToken", ingcartToken);
		session.setAttribute("ingcartToken", ingcartToken);

		JSONObject json = new JSONObject();
		json.put("catalogId", tblTeasurerList.get(0).getCatalogId());
		json.put("id", tblTeasurerList.get(0).getId());
		json.put("level", tblTeasurerList.get(0).getLevel());
		return PytheResult.build(200, "登录成功", json);
	}

	@Override
	public PytheResult updatePassword(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		String newPassord = information.getString("newPassord");
		String verificationCode = information.getString("verificationCode");
		TblTeasurerExample tblTeasurerExample = new TblTeasurerExample();
		tblTeasurerExample.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<TblTeasurer> tblTeasurerList = tblTeasurerMapper.selectByExample(tblTeasurerExample);
		if (tblTeasurerList.size() == 0) {
			return PytheResult.ok("该手机号不存在");
		}
		if (null != verificationCode) {
			TblVerificationExample example = new TblVerificationExample();
			example.createCriteria().andPhoneNumEqualTo(phoneNum)
					.andTimeGreaterThanOrEqualTo(new DateTime().minusMinutes(1).toDate());
			List<TblVerification> verificationList = verificationMapper.selectByExample(example);
			// 验证码对象不存在：是因为之前没有插入或者超时导致的
			if (verificationList.size() == 0) {
				return PytheResult.build(400, "验证码错误或过期");
			}

			TblVerification verification_info = verificationList.get(0);
			// 验证码不正确
			if (!verification_info.getVerificationCode().equals(verificationCode)) {
				return PytheResult.build(400, "验证码错误或过期");
			}
		}
		TblTeasurer tblTeasurer = new TblTeasurer();
		tblTeasurer.setId(tblTeasurerList.get(0).getId());
		tblTeasurer.setPassword(newPassord);
		tblTeasurerMapper.updateByPrimaryKeySelective(tblTeasurer);
		return PytheResult.ok("密码修改成功");
	}

	@Override
	public PytheResult selectTeasurerRocordList(String parameters) {
		// TODO Auto-generated method stub
		String tokenMsg = JWTUtils.IngcartTokenOperation(session, request, response);
		if (tokenMsg.equals("登录凭证过期")) {
			return PytheResult.build(400, "登录凭证过期");
		}
		JSONObject params = JSONObject.parseObject(parameters);
		Integer pageNum = params.getInteger("pageNum");
		Integer pageSize = params.getInteger("pageSize");
		TblTeasurerRecordExample tblTeasurerRecordExample = new TblTeasurerRecordExample();
		int count = tblTeasurerRecordMapper.countByExample(tblTeasurerRecordExample);
		if (pageNum == null || pageSize == null) {
			pageNum = 1;
			pageSize = count > 10 ? 10 : count;
		}
		PageHelper.startPage(pageNum, pageSize);
		VTeasurerRecordExample vTeasurerRecordExample = new VTeasurerRecordExample();
		// 三级财务不显示
		vTeasurerRecordExample.createCriteria().andLevelNotEqualTo(3);
		List<VTeasurerRecord> vTeasurerRecordList = vTeasurerRecordMapper.selectByExample(vTeasurerRecordExample);
		return PytheResult.ok(vTeasurerRecordList);
	}

	@Override
	public PytheResult selectTeasurerById(Long id) {
		// TODO Auto-generated method stub
		String tokenMsg = JWTUtils.IngcartTokenOperation(session, request, response);
		if (tokenMsg.equals("登录凭证过期")) {
			return PytheResult.build(400, "登录凭证过期");
		}
		TblTeasurer tblTeasurers = tblTeasurerMapper.selectByPrimaryKey(id);
		return PytheResult.ok(tblTeasurers.getPhoneNum());

	}

	@Override
	public PytheResult selectCarsByLevel(String level, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		String tokenMsg = JWTUtils.IngcartTokenOperation(session, request, response);
		if (tokenMsg.equals("登录凭证过期")) {
			return PytheResult.build(400, "登录凭证过期");
		}
		TblDistributionExample tblDistributionExample = new TblDistributionExample();
		com.pythe.pojo.TblDistributionExample.Criteria cretria2 = tblDistributionExample.createCriteria();
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
		} else {
			list = null;
		}
		List<TblDistribution> distributions = distributionMapper.selectByExampleWithBLOBs(tblDistributionExample);
		if (distributions.isEmpty()) {
			return PytheResult.build(400, "景区没有投放车辆");
		}
		List<Long> listcars = new ArrayList<Long>();
		List<JSONObject> resultJsons = new ArrayList<JSONObject>();
		for (TblDistribution tblDistribution : distributions) {
			if (tblDistribution.getCarIds() != null) {
				List<Long> listTemp = JsonUtils.jsonToList(tblDistribution.getCarIds(), Long.class);
				listcars.addAll(listTemp);

			}
		}
		TblCarExample tblCarExample = new TblCarExample();
		tblCarExample.createCriteria().andQrIdIn(listcars);
		PageHelper.startPage(pageNum, pageSize);
		List<TblCar> cars = carMapper.selectByExample(tblCarExample);
		for (TblCar tblCar : cars) {
			JSONObject carjson = new JSONObject();
			carjson.put("car_number", tblCar.getQrId());
			if (tblCar.getStatus() == 0) {
				carjson.put("status", "空闲");
			} else if (tblCar.getStatus() == 1) {
				carjson.put("status", "使用");
			} else if (tblCar.getStatus() == 2) {
				carjson.put("status", "保留");
			} else if (tblCar.getStatus() == 3) {
				carjson.put("status", "故障");
			}
			for (TblDistribution tblDistribution : distributions) {
				if (tblDistribution.getCarIds() != null) {
					List<Long> listTemp = JsonUtils.jsonToList(tblDistribution.getCarIds(), Long.class);
					for (int i = 0; i < listTemp.size(); i++) {
						if (listTemp.get(i).equals(tblCar.getQrId())) {
							carjson.put("city", tblDistribution.getCity());
							carjson.put("level", tblDistribution.getLevel());
							carjson.put("name", tblDistribution.getName());
						}
					}

				}
			}
			resultJsons.add(carjson);
		}
		return PytheResult.build(200, "请求成功", resultJsons);
	}

}
