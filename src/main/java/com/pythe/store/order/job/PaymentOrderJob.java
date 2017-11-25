package com.pythe.store.order.job;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSONObject;
import com.pythe.common.utils.HttpClientUtil;
import com.pythe.mapper.TblTimerMapper;



/**
 * 参照applicationContext-scheduler.xml
 * 每两分钟执行一次上传任务
 * 
 */
public class PaymentOrderJob extends QuartzJobBean {
	
	@Value("${FTP_TRANSPORT_URL}")
	private String FTP_TRANSPORT_URL;

	@Value("${FTP_TRANSPORT_LOCAL_FILEPATH}")
	private String FTP_TRANSPORT_LOCAL_FILEPATH;
	
	@Value("${FTP_TRANSPORT_REMOTE_FILEPATH}")
	private String FTP_TRANSPORT_REMOTE_FILEPATH;
	
	@Value("${FTP_TRANSPORT_FILENAME}")
	private String FTP_TRANSPORT_FILENAME;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap()
                .get("applicationContext");
        
//        时间参数，当前时间向前推2天
//        System.out.println("======================>"+(new DateTime().minusDays(1).toDate()));
//        applicationContext.getBean(TblTimerMapper.class).paymentOrderScan(new DateTime().minusDays(1).toDate());
        
       String URL = FTP_TRANSPORT_URL; 
       JSONObject parameters = new JSONObject();
       parameters.put("localFilePath", FTP_TRANSPORT_LOCAL_FILEPATH);
       parameters.put("fileName", FTP_TRANSPORT_FILENAME);
       parameters.put("remoteFilePath", FTP_TRANSPORT_REMOTE_FILEPATH);
       
       String result = HttpClientUtil.doPostJson(URL, parameters.toJSONString());
       System.out.println("pythe-order result : " + result); 
        
    }

}
