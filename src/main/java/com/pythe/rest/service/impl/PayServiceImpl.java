package com.pythe.rest.service.impl;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pythe.common.pojo.PytheResult;
import com.pythe.common.utils.DecodeUtils;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.common.utils.HttpClientUtil;
import com.pythe.common.utils.Xml2JsonUtil;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblBillMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblBill;
import com.pythe.rest.service.PayService;

@Service
public class PayServiceImpl implements PayService {

	// BILL
	@Value("${BILL_CHARGE_TYPE}")
	private Integer BILL_CHARGE_TYPE;

	@Value("${BILL_PAY_TYPE}")
	private Integer BILL_PAY_TYPE;

	@Value("${NOT_PAY_STATUS}")
	private Integer NOT_PAY_STATUS;

	@Value("${PAY_STATUS}")
	private Integer PAY_STATUS;

	@Value("${WX_APPID}")
	private String WX_APPID;

	@Value("${WX_APPSECRET}")
	private String WX_APPSECRET;

	@Value("${WX_KEY}")
	private String WX_KEY;

	@Value("${WX_MCH_ID}")
	private String WX_MCH_ID;


	@Autowired
	private TblAccountMapper accountMapper;

	@Autowired
	private TblBillMapper billMapper;

	@Override
	public PytheResult chargeForAccount(String prepayment_imforamtion) throws Exception{

		// TODO Auto-generated method stub
		String appid = WX_APPID;// appid
		String mch_id = WX_MCH_ID;// 微信支付商户号
		String nonce_str = FactoryUtils.getUUID();// 随机码
		
		prepayment_imforamtion = DecodeUtils.decode(prepayment_imforamtion);
		JSONObject json = JSONObject.parseObject(prepayment_imforamtion);
		String body = json.getString("body");// 商品描述
		String out_trade_no = System.currentTimeMillis() + "" + new java.util.Random().nextInt(8);// 商品订单号
		String product_id = FactoryUtils.getUUID();// 商品编号
		Double a = json.getDouble("total_fee")* 100;
		String total_fee = a.intValue()+ "";// 总金额
																	// 分
		// String time_start = getCurrTime();// 交易起始时间(订单生成时间非必须)
		String trade_type = json.getString("trade_type");// 公众号支付
		String notify_url = json.getString("notify_url");// 回调函数
		// String sessionId
		// =JSONObject.parseObject(prepayment_imforamtion).getString("sessionId");
		String openid = json.getString("openId");

		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put("appid", appid);
		params.put("body", body);// 商品描述
		params.put("mch_id", mch_id);
		params.put("nonce_str", nonce_str);
		params.put("notify_url", notify_url);
		params.put("openid", openid);
		params.put("out_trade_no", out_trade_no);
		params.put("product_id", product_id);
		params.put("total_fee", total_fee);
		params.put("trade_type", trade_type);

		// 1第一次签名
		String sign = "";// 签名(该签名本应使用微信商户平台的API证书中的密匙key,但此处使用的是微信公众号的密匙APP_SECRET)
		sign = FactoryUtils.getSign(params, WX_KEY);
		// 参数xml化
		String xmlParams = FactoryUtils.parseString2Xml(params, sign);
		// 判断返回码
		String str = "";
		String xw_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		str = HttpClientUtil.doPostJson(xw_url, xmlParams);

		// 2第二步签名
		JSONObject strJson = Xml2JsonUtil.xml2Json(str);
		// System.out.println("=======>strJson"+strJson.toString());
		// String return_code = strJson.getString("return_code");
		// String return_msg = strJson.getString("return_msg");
		String prepay_id = strJson.getString("prepay_id");
		String sign2 = strJson.getString("sign");
		String nonce_str2 = FactoryUtils.getUUID();
		String signType = "MD5";
		SortedMap<String, String> params2 = new TreeMap<String, String>();
		// String timeStamp = new
		// Timestamp(System.currentTimeMillis()).toString();
		String timeStamp = String.valueOf(System.currentTimeMillis());

		params2.put("appId", appid);
		// params2.put("mch_id", mch_id);
		params2.put("nonceStr", nonce_str2);
		// params2.put("openid", openid);
		// params2.put("prepay_id", prepay_id);
		params2.put("package", "prepay_id=" + prepay_id);
		params2.put("signType", signType);
		params2.put("timeStamp", timeStamp);
		// params2.put("return_code",return_code);
		// params2.put("return_msg", return_msg);
		// params2.put("trade_type", trade_type);
		String signB = "";// 签名(该签名本应使用微信商户平台的API证书中的密匙key,但此处使用的是微信公众号的密匙APP_SECRET)
		signB = FactoryUtils.getSign(params2, WX_KEY);
		System.out.println("======================>" + signB);

		// String prepay_id ="";
		// for (String s : str.split("\n"))
		// if (s.contains("prepay_id")) prepay_id =
		// s.substring((s.indexOf("CDATA")+6), (s.lastIndexOf("]")-1));
		//
		// JSONObject json = new JSONObject();
		// json.put("return_code", "SUCCESS");
		// json.put("return_msg", "OK");
		// json.put("prepay_id",prepay_id);
		// json.toString()
		// 2第一步签名

		// String nonceStr = FactoryUtils.getUUID();
		// String signType ="MD5";
		// JSONObject strJson = Xml2JsonUtil.xml2Json(str);
		// strJson.put("appId", appid);
		strJson.put("paySign", signB);
		strJson.put("timeStamp", timeStamp);
		strJson.put("nonceStr", nonce_str2);
		strJson.put("out_trade_no", out_trade_no);
		// strJson.put("package", "prepay_id="+prepay_id);
		// strJson.put("signType", "signType");

		return PytheResult.ok(strJson.toString());

	}

	@Override
	public PytheResult wxChargeConfirm(String parameters) throws Exception {
		// TODO Auto-generated method stub
		JSONObject customerInformation = JSONObject.parseObject(parameters);

		Long customerId = customerInformation.getLong("customerId");
		Double inAmount = customerInformation.getDouble("amount");
		String out_trade_no = customerInformation.getString("out_trade_no");
		String prepay_id = customerInformation.getString("prepay_id");
		// 入账

		// 先更新账单
		TblBill bill = new TblBill();
		bill.setId(FactoryUtils.getUUID());
		bill.setAmount(inAmount);
		bill.setType(BILL_CHARGE_TYPE);
		bill.setOutTradeNo(out_trade_no);
		bill.setPrepayId(prepay_id);
		bill.setStatus(PAY_STATUS);
		bill.setTime(new Date());
		bill.setCustomerId(customerId);
		billMapper.insert(bill);

		// 更新账户
		TblAccount account = accountMapper.selectByPrimaryKey(customerId);
		account.setAmount(account.getAmount() + inAmount);
		account.setInAmount(account.getInAmount() + inAmount);
		accountMapper.updateByPrimaryKey(account);
//		if (null != account) {
//			account.setAmount(account.getAmount() + inAmount);
//		account.setInAmount(account.getInAmount() + inAmount);
//		accountMapper.updateByPrimaryKey(account);
//		} else {
//			TblAccount record = new TblAccount();
//			record.setCustomerId(customerId);
//			record.setInAmount(inAmount);
//			// 是否判断tbl_bill是否是转出或者转入
//			record.setAmount(inAmount);
//			accountMapper.insert(record);
//		}

		return PytheResult.ok("充值成功");
	}

}
