package com.pythe.rest.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblBagRecordMapper;
import com.pythe.mapper.TblBillMapper;
import com.pythe.mapper.TblStoreMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblBagRecord;
import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblStore;
import com.pythe.pojo.TblStoreExample;
import com.pythe.rest.service.BagService;

@Service
public class BagServiceImpl implements BagService {
	@Autowired
	private TblStoreMapper storeMapper;

	@Autowired
	private TblBagRecordMapper bagRecordMapper;
	
	@Autowired
	private TblAccountMapper accountMapper;
	
	
	@Autowired
	private TblBillMapper billMapper;
	
	
	// BILL
	@Value("${BILL_GIVE_TYPE}")
	private Integer BILL_GIVE_TYPE;
	
	// BILL
	@Value("${GIVE_BAG_MONEY}")
	private Double GIVE_BAG_MONEY;
	
	// BILL
	@Value("${PAY_STATUS}")
	private Integer PAY_STATUS;
	

	@Override
	public PytheResult maiBag(String parameters) {
		// TODO Auto-generated method stub
		JSONObject information = JSONObject.parseObject(parameters);
		Long customerId = information.getLong("customerId");
		String dealerId = information.getString("dealerId");
		String out_trade_no = information.getString("out_trade_no");
		String prepay_id = information.getString("prepay_id");
		
		TblStore store = null;
		// 更新卡券使用情况
		TblStoreExample example = new TblStoreExample();
		example.createCriteria().andDealerIdEqualTo(dealerId);
		List<TblStore> storeList = storeMapper.selectByExample(example);
		if (storeList.isEmpty()) {
			PytheResult.build(400, "该用户暂无权限");
		}
		store = storeList.get(0);
		store.setOutBagNum(store.getOutBagNum() + 1);
		storeMapper.updateByPrimaryKey(store);
		
		// 更新表单数据
		TblBagRecord bagRecord = new TblBagRecord();
		bagRecord.setCustomerId(customerId);
		bagRecord.setDealerId(dealerId);
		bagRecord.setType(1);
		bagRecord.setBuyTime(new Date());
		bagRecord.setStoreId(store.getId());
		bagRecordMapper.insert(bagRecord);
		
		//更新账单记录
		final TblAccount account = accountMapper.selectByPrimaryKey(customerId);
		// 先更新账单
		TblBill bill = new TblBill();
		bill.setId(FactoryUtils.getUUID());
		bill.setAmount(GIVE_BAG_MONEY);
		bill.setType(BILL_GIVE_TYPE);
		bill.setOutTradeNo(out_trade_no);
		bill.setPrepayId(prepay_id);
		bill.setStatus(PAY_STATUS);
		bill.setTime(new Date());
		bill.setCustomerId(customerId);
		billMapper.insert(bill);

		// 更新账户
		account.setAmount(account.getAmount() + GIVE_BAG_MONEY);
		account.setInAmount(account.getInAmount() +GIVE_BAG_MONEY);
		account.setGivingAmount(account.getGivingAmount() +GIVE_BAG_MONEY);
		accountMapper.updateByPrimaryKey(account);
		return PytheResult.ok("记录成功");
	}


	@Override
	public PytheResult selectStoreLocation(String storeId) {
		// TODO Auto-generated method stub
		TblStore store = storeMapper.selectByPrimaryKey(storeId);
		return PytheResult.ok(store);
	}

}
