package com.pythe.rest.service.impl;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.pythe.common.pojo.PytheResult;
import com.pythe.mapper.TblCatalogMapper;
import com.pythe.mapper.TblTeasurerMapper;
import com.pythe.mapper.TblTeasurerRecordMapper;
import com.pythe.mapper.TblVerificationMapper;
import com.pythe.mapper.VTeasurerMapper;
import com.pythe.mapper.VTeasurerRecordMapper;
import com.pythe.pojo.TblCatalog;
import com.pythe.pojo.TblCatalogExample;
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

	@Override
	public PytheResult selectTeasurerList(String parameters) {
		// TODO Auto-generated method stub
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
		JSONObject information = JSONObject.parseObject(parameters);
		String phoneNum = information.getString("phoneNum").trim();
		TblTeasurerExample tblTeasurerExample = new TblTeasurerExample();
		tblTeasurerExample.createCriteria().andPhoneNumEqualTo(phoneNum);
		List<TblTeasurer> tblTeasurerList = tblTeasurerMapper.selectByExample(tblTeasurerExample);
		if (tblTeasurerList.size() == 0) {
			return PytheResult.ok("该手机号不存在");
		}
		TblTeasurerRecord tblTeasurerRecord = new TblTeasurerRecord();
		tblTeasurerRecord.setCatalogId(tblTeasurerList.get(0).getCatalogId());
		tblTeasurerRecord.setCreated(tblTeasurerList.get(0).getCreated());
		tblTeasurerRecord.setId(tblTeasurerList.get(0).getId());
		tblTeasurerRecord.setLevel(tblTeasurerList.get(0).getLevel());
		tblTeasurerRecord.setName(tblTeasurerList.get(0).getName());
		tblTeasurerRecord.setPassword(tblTeasurerList.get(0).getPassword());
		tblTeasurerRecord.setPhoneNum(tblTeasurerList.get(0).getPhoneNum());
		tblTeasurerRecord.setType(tblTeasurerList.get(0).getType());
		tblTeasurerRecordMapper.insertSelective(tblTeasurerRecord);
		tblTeasurerMapper.deleteByPrimaryKey(tblTeasurerList.get(0).getId());
		return PytheResult.ok("财务人员删除成功");
	}

	@Override
	public PytheResult insertLevelOneTeasurer(String parameters) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
	public PytheResult selectLevelByName(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String name = information.getString("name");
		TblCatalogExample tblCatalogExample = new TblCatalogExample();
		tblCatalogExample.createCriteria().andNameEqualTo(name);
		List<TblCatalog> tblCatalogList = TblCatalogMapper.selectByExample(tblCatalogExample);
		JSONObject json = new JSONObject();
		json.put("level", tblCatalogList.get(0).getId());
		return PytheResult.build(200, "查询成功", json);
	}

	@Override
	public PytheResult selectTeasurerRocordList(String parameters) {
		// TODO Auto-generated method stub
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
	public PytheResult selectNameByLevel(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		String level = information.getString("level");
		TblCatalogExample tblCatalogExample = new TblCatalogExample();
		tblCatalogExample.createCriteria().andIdEqualTo(level);
		List<TblCatalog> tblCatalogList = TblCatalogMapper.selectByExample(tblCatalogExample);
		JSONObject json = new JSONObject();
		json.put("name", tblCatalogList.get(0).getName());
		return PytheResult.build(200, "查询成功", json);
	}

}
