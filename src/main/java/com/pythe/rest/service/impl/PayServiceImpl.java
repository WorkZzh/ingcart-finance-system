package com.pythe.rest.service.impl;

import java.io.File;
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
import com.pythe.mapper.TblComboMapper;
import com.pythe.mapper.TblCouponMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblBillExample;
import com.pythe.pojo.TblCombo;
import com.pythe.pojo.TblCoupon;
import com.pythe.rest.service.PayService;

@Service
public class PayServiceImpl implements PayService {

	
	// BILL
	@Value("${BILL_CHARGE_TYPE}")
	private Integer BILL_CHARGE_TYPE;
	
	
	@Value("${BILL_GIVE_TYPE}")
	private Integer BILL_GIVE_TYPE;
	

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

	@Value("${WX_PAY_CONFIRM_NOTIFY_URL}")
	private String WX_PAY_CONFIRM_NOTIFY_URL;
	
	
	@Value("${APP_PAY_CONFIRM_NOTIFY_URL}")
	private String APP_PAY_CONFIRM_NOTIFY_URL;
	
	

	@Value("${APP_APPID}")
	private String APP_APPID;

	@Value("${APP_APPSECRET}")
	private String APP_APPSECRET;

	@Value("${APP_MCH_ID}")
	private String APP_MCH_ID;

	@Value("${GIFT_COUPONS_THRESHOLD}")
	private double GIFT_COUPONS_THRESHOLD;
	
	
	@Value("${HIDDLE_CHARE_STATUS}")
	private Integer HIDDLE_CHARE_STATUS;
	
	
	

	@Autowired
	private TblAccountMapper accountMapper;
	
	
	@Autowired
	private TblBillMapper billMapper;

	
	@Autowired
	private TblComboMapper comboMapper;

	@Override
	public PytheResult chargeForAccount(String prepayment_imforamtion) throws Exception {

		// TODO Auto-generated method stub
		String appid = WX_APPID;// appid
		String mch_id = WX_MCH_ID;// 微信支付商户号
		String nonce_str = FactoryUtils.getUUID();// 随机码

		prepayment_imforamtion = DecodeUtils.decode(prepayment_imforamtion);
		JSONObject json = JSONObject.parseObject(prepayment_imforamtion);

		Long customerId = json.getLong("customerId");
		Double giving = json.getDouble("giving");

		String body = json.getString("body");// 商品描述
		String out_trade_no = System.currentTimeMillis() + "" + new java.util.Random().nextInt(8);// 商品订单号
		String product_id = FactoryUtils.getUUID();// 商品编号
		Double a = json.getDouble("total_fee") * 100;
		String total_fee = a.intValue() + "";// 总金额
		// String time_start = getCurrTime();// 交易起始时间(订单生成时间非必须)
		String trade_type = json.getString("trade_type");// 公众号支付
		String notify_url = WX_PAY_CONFIRM_NOTIFY_URL;// 回调函数
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

		// 插入账单，设置未确定支付结果状态，等待微信支付回调确认再更新
		TblBill bill = new TblBill();
		bill.setId(FactoryUtils.getUUID());
		bill.setAmount(json.getDouble("total_fee"));
		bill.setType(BILL_CHARGE_TYPE);
		bill.setOutTradeNo(out_trade_no);
		bill.setPrepayId(prepay_id);
		bill.setGivingAmount(giving);
		bill.setStatus(NOT_PAY_STATUS);
		bill.setTime(new Date());
		bill.setCustomerId(customerId);
		billMapper.insert(bill);

		return PytheResult.ok(strJson.toString());
	}

	@Override
	public PytheResult wxChargeConfirm(String parameters) throws Exception {
		JSONObject strJson = Xml2JsonUtil.xml2Json(parameters);
		String appId = strJson.getString("appid");
		String mchId = strJson.getString("mch_id");
		String outTradeNo = strJson.getString("out_trade_no");
		String resultCode = strJson.getString("result_code");
		String returnCode = strJson.getString("return_code");
		String totalFee = strJson.getString("total_fee");

		if (returnCode.equals("SUCCESS") && resultCode.equals("SUCCESS")) {
			System.out.println("============================> accept return !!! " + returnCode + " && " + resultCode);
			if (appId.equals(WX_APPID) && mchId.equals(WX_MCH_ID)) {
				System.out.println("=============================> match !!!!!!!!!!!!");
				TblBillExample billExample = new TblBillExample();
				billExample.createCriteria().andOutTradeNoEqualTo(outTradeNo);
				TblBill originalBill = billMapper.selectByExample(billExample).get(0);
				if (originalBill.getStatus().equals(NOT_PAY_STATUS)) {
					//System.out.println("============================> charge fee !!! " + totalFee);
					originalBill.setStatus(PAY_STATUS);
					billMapper.updateByPrimaryKey(originalBill);

					// 更新充值支付账单状态后，继续更新账户信息、优惠券信息等等
					Double inAmount = originalBill.getAmount();
					Double givingAmount = originalBill.getGivingAmount();
					TblAccount account = accountMapper.selectByPrimaryKey(originalBill.getCustomerId());

					//System.out.println("====================>givingAmount"+ givingAmount);
					account.setGivingAmount(account.getGivingAmount() + givingAmount);

					account.setAmount(account.getAmount() + inAmount + givingAmount);
					account.setInAmount(account.getInAmount() + inAmount + givingAmount );
					accountMapper.updateByPrimaryKey(account);
					
					SortedMap<String, String> params = new TreeMap<String, String>();
					params.put("return_code", "SUCCESS");
					params.put("return_msg", "OK");
					String xmlParams = FactoryUtils.parseString2Xml(params);
					
					return null;
				}
			}
		}

		return null;
	}

	@Override
	public PytheResult chargeForAccountInApp(String prepayment_imforamtion) throws Exception {
		// TODO Auto-generated method stub
		String appid = APP_APPID;// appid
		String mch_id = APP_MCH_ID;// 微信支付商户号
		String nonce_str = FactoryUtils.getUUID();// 随机码

		// prepayment_imforamtion = DecodeUtils.decode(prepayment_imforamtion);
		JSONObject json = JSONObject.parseObject(prepayment_imforamtion);
		Long customerId = json.getLong("customerId");
		Long comboId = json.getLong("comboId");
		
		TblCombo combo = comboMapper.selectByPrimaryKey(comboId);
		
		Double givingAmount = combo.getGiving();
		
		//获取支付的类型的状态
		Integer status  = combo.getStatus();
		
		String body = json.getString("body");// 商品描述
		String out_trade_no = System.currentTimeMillis() + "" + new java.util.Random().nextInt(8);// 商品订单号
		String product_id = FactoryUtils.getUUID();// 商品编号
		Double a = json.getDouble("total_fee") * 100;
		String total_fee = a.intValue() + "";// 总金额
		String spbill_create_ip = json.getString("spbill_create_ip");

		// String time_start = getCurrTime();// 交易起始时间(订单生成时间非必须)
		String trade_type = json.getString("trade_type");// 公众号支付
		String notify_url = APP_PAY_CONFIRM_NOTIFY_URL;// 回调函数
		// String sessionId
		// =JSONObject.parseObject(prepayment_imforamtion).getString("sessionId");

		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put("appid", appid);
		params.put("body", body);// 商品描述
		params.put("mch_id", mch_id);
		params.put("nonce_str", nonce_str);
		params.put("notify_url", notify_url);

		params.put("out_trade_no", out_trade_no);
		params.put("product_id", product_id);
		params.put("spbill_create_ip", spbill_create_ip);
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

		String prepay_id = strJson.getString("prepay_id");
		String signr = strJson.getString("sign");
		String nonce_str2 = FactoryUtils.getUUID();
		String signType = "MD5";
		SortedMap<String, String> params2 = new TreeMap<String, String>();

		String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);

		params2.put("appid", appid);
		params2.put("noncestr", nonce_str2);
		params2.put("package", "Sign=WXPay");
		params2.put("partnerid", mch_id);
		params2.put("prepayid", prepay_id);
		params2.put("timestamp", timeStamp);
		params2.put("sign", signr);

		String signB = "";// 签名(该签名本应使用微信商户平台的API证书中的密匙key,但此处使用的是微信公众号的密匙APP_SECRET)
		signB = FactoryUtils.getSign(params2, WX_KEY);

		JSONObject returnObject = new JSONObject();
		returnObject.put("prepay_id", prepay_id);
		returnObject.put("paySign", signB);
		returnObject.put("timeStamp", timeStamp);
		returnObject.put("nonceStr", nonce_str2);
		returnObject.put("out_trade_no", out_trade_no);

		// 插入账单，设置未确定支付结果状态，等待微信支付回调确认再更新
		TblBill bill = new TblBill();
		bill.setId(FactoryUtils.getUUID());
		
		if (1 == status) {
			bill.setAmount(0d);
			bill.setType(BILL_GIVE_TYPE);
			//System.out.println("=============>"+ bill.getAmount());
		}else{
			bill.setAmount(json.getDouble("total_fee"));
			bill.setType(BILL_CHARGE_TYPE);
		}
		
		bill.setOutTradeNo(out_trade_no);
		bill.setPrepayId(prepay_id);
		bill.setStatus(NOT_PAY_STATUS);
		bill.setTime(new Date());
		bill.setGivingAmount(givingAmount);
		bill.setCustomerId(customerId);
		billMapper.insert(bill);

		return PytheResult.ok(returnObject);
	}

	@Override
	public PytheResult wxChargeConfirmInApp(String parameters) throws Exception {
		
		
		JSONObject strJson = Xml2JsonUtil.xml2Json(parameters);
		String appId = strJson.getString("appid");
		String mchId = strJson.getString("mch_id");
		String outTradeNo = strJson.getString("out_trade_no");
		String resultCode = strJson.getString("result_code");
		String returnCode = strJson.getString("return_code");
		String totalFee = strJson.getString("total_fee");

		if (returnCode.equals("SUCCESS") && resultCode.equals("SUCCESS")) {
			System.out.println("============================> accept return !!! " + returnCode + " && " + resultCode);
			if (appId.equals(APP_APPID) && mchId.equals(APP_MCH_ID)) {
				System.out.println("=============================> match !!!!!!!!!!!!");
				TblBillExample billExample = new TblBillExample();
				billExample.createCriteria().andOutTradeNoEqualTo(outTradeNo);
				TblBill originalBill = billMapper.selectByExample(billExample).get(0);
				if (originalBill.getStatus().equals(NOT_PAY_STATUS)) {
					//System.out.println("============================> charge fee !!! " + totalFee);
					originalBill.setStatus(PAY_STATUS);
					billMapper.updateByPrimaryKey(originalBill);

					// 更新充值支付账单状态后，继续更新账户信息、优惠券信息等等
					Double inAmount = originalBill.getAmount();
					Double givingAmount = originalBill.getGivingAmount();
					TblAccount account = accountMapper.selectByPrimaryKey(originalBill.getCustomerId());

					//System.out.println("====================>givingAmount"+ givingAmount);
					account.setGivingAmount(account.getGivingAmount() + givingAmount);
//					if (10 == inAmount) {
//						GIVING_ACCOUNT = 2d;
//						inAmount = inAmount + GIVING_ACCOUNT;
//						
//					} else if (20 == inAmount) {
//						GIVING_ACCOUNT = 8d;
//						inAmount = inAmount + GIVING_ACCOUNT;
//						account.setGivingAmount(account.getGivingAmount() + GIVING_ACCOUNT);
//					} else if (50 == inAmount) {
//						GIVING_ACCOUNT = 28d;
//						inAmount = inAmount + GIVING_ACCOUNT;
//						account.setGivingAmount(account.getGivingAmount() + GIVING_ACCOUNT);
//					} else if (100 == inAmount) {
//						GIVING_ACCOUNT = 68d;
//						inAmount = inAmount + GIVING_ACCOUNT;
//						account.setGivingAmount(account.getGivingAmount() + GIVING_ACCOUNT);
//					}

					account.setAmount(account.getAmount() + inAmount + givingAmount);
					account.setInAmount(account.getInAmount() + inAmount + givingAmount );
					accountMapper.updateByPrimaryKey(account);
					
					SortedMap<String, String> params = new TreeMap<String, String>();
					params.put("return_code", "SUCCESS");
					params.put("return_msg", "OK");
					String xmlParams = FactoryUtils.parseString2Xml(params);
					
					return null;
				}
			}
		}

		return null;
	}

	@Override
	public PytheResult hiddleCharge() {
		// TODO Auto-generated method stub
		
		JSONObject json = new JSONObject();
		json.put("status", HIDDLE_CHARE_STATUS);
		return PytheResult.ok(json);
	}

	@Override
	public String wxOrderQueryInApp(String parameters) {
		String appid = APP_APPID;// appid
		String mch_id = APP_MCH_ID;// 微信支付商户号
		String nonce_str = FactoryUtils.getUUID();// 随机码
		

		JSONObject json = JSONObject.parseObject(parameters);

		String out_trade_no = json.getString("out_trade_no");//订单号
		
		

		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", mch_id);
		params.put("nonce_str", nonce_str);
		params.put("out_trade_no", out_trade_no);
		
		

		String sign = "";// 签名(该签名本应使用微信商户平台的API证书中的密匙key,但此处使用的是微信公众号的密匙APP_SECRET)
		sign = FactoryUtils.getSign(params, WX_KEY);
		// 参数xml化
		String xmlParams = FactoryUtils.parseString2Xml(params, sign);
		
		// 判断返回码
		String str = "";
		String xw_url = "https://api.mch.weixin.qq.com/pay/orderquery";
		str = HttpClientUtil.doPostJson(xw_url, xmlParams);
		
		System.out.println("=======================================> order query : " + str);
		
		return (str);
	}
	
	
	
	@Override
	public PytheResult refundByOrderInWX(String url) {

		JSONObject json = JSONObject.parseObject(url);
		
		String appid = WX_APPID;// appid
		String mch_id = WX_MCH_ID;// 微信支付商户号
		String nonce_str = FactoryUtils.getUUID();// 随机码
		String sign = "";
		String out_trade_no  = json.getString("out_trade_no");
		String out_refund_no = FactoryUtils.getUUID();
		String total_fee = json.getString("total_fee");
		String refund_fee = json.getString("refund_fee");
		String op_user_id = WX_MCH_ID;
		 //sign
		 SortedMap<String, String> params = new TreeMap<String, String>();
		 params.put("appid", appid);
		 params.put("mch_id", mch_id);
		 params.put("nonce_str", nonce_str);
		 params.put("out_trade_no", out_trade_no);
		 params.put("out_refund_no", out_refund_no);
		 params.put("out_trade_no", out_trade_no);
		 params.put("total_fee", total_fee);
		 params.put("refund_fee", refund_fee);
		 params.put("op_user_id", op_user_id);

		// 1第一次签名
		sign = FactoryUtils.getSign(params, WX_KEY);
		// 参数xml化
		String xmlParams = FactoryUtils.parseString2Xml(params, sign);
		// 判断返回码
		String str = "";
		String xw_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		try {
			//resource/apiclient_cert.p12
			//System.out.println("===============>我我我"+this.getClass().getClassLoader().getResource("resource/apiclient_cert.p12").getPath());
			str = HttpClientUtil.doPostXmlSafely(xw_url, xmlParams, mch_id,
					new File(this.getClass().getClassLoader().getResource("resource/apiclient_cert.p12").getPath()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return PytheResult.ok(str);
	}

}
