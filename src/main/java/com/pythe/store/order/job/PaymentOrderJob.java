package com.pythe.store.order.job;

import java.util.Date;
import java.util.List;

import javax.swing.event.CaretListener;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pythe.common.utils.DateUtils;
import com.pythe.common.utils.FactoryUtils;
import com.pythe.mapper.TblAccountMapper;
import com.pythe.mapper.TblBillMapper;
import com.pythe.mapper.TblCarMapper;
import com.pythe.mapper.TblHoldRecordMapper;
import com.pythe.mapper.TblRecordMapper;
import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblCar;
import com.pythe.pojo.TblCarExample;
import com.pythe.pojo.TblHoldRecord;
import com.pythe.pojo.TblHoldRecordExample;
import com.pythe.pojo.TblRecord;



/**
 * 参照applicationContext-scheduler.xml
 * 每一分钟执行一次上传任务
 * 
 */
public class PaymentOrderJob extends QuartzJobBean {
	

	
	
	private static Integer EACH_HOUR_PRICE = 5;
	
	
	private static Integer BILL_PAY_TYPE = 1;
	private static Integer NOT_PAY_STATUS = 0;
	private static Integer PAY_TYPE = 1;
	private static Integer CAR_FREE_STATUS = 0;
	
	

	

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("========>开始自动检测,是否忘记关锁");
    	
    	final ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap()
                .get("applicationContext");
        
       TblCarExample carExample = new TblCarExample();
       carExample.createCriteria().andStatusEqualTo(4).andEndtimeLessThanOrEqualTo(new DateTime().minusMinutes(1).toDate());
       List<TblCar> carList = applicationContext.getBean(TblCarMapper.class).selectByExample(carExample);
       
       if (!carList.isEmpty()) {
	    	   for (TblCar car: carList) {
	    		
	    		   final String  recordId  = car.getRecordid();


	    			// 更新停止时间和停止位置和记录用的钱
	    			int time = DateUtils.minusForPartHour(car.getEndtime(), car.getStarttime());
	    			Double amount = null;
	    			if (time % 30 == 0) {
	    				amount = Math.floor(time / 30) * EACH_HOUR_PRICE;
	    			} else {
	    				amount = (Math.floor(time / 30) + 1) * EACH_HOUR_PRICE;
	    			}
	    			
	    			final String billId = FactoryUtils.getUUID();
	    			new Thread() {
	    				@Override
	    				public void run() {
	    					TblRecord line = applicationContext.getBean(TblRecordMapper.class).selectByPrimaryKey(recordId);
	    					line.setStopTime(new Date());
	    					line.setBillId(billId);
	    					applicationContext.getBean(TblRecordMapper.class).updateByPrimaryKey(line);
	    				}
	    			}.start();

	    			
	    			final Long customerId = car.getUser();
	    			// 生成账单
	    			final TblAccount account = applicationContext.getBean(TblAccountMapper.class).selectByPrimaryKey(customerId);
	    			account.setAmount(account.getAmount() - amount);
	    			account.setOutAmount(account.getOutAmount() - amount);
	    			applicationContext.getBean(TblAccountMapper.class).updateByPrimaryKey(account);

	    			// 更新车的位置和使用情况和结束时间
	    			//让车处于空闲状态，让后续的人可以使用
	    			car.setStatus(CAR_FREE_STATUS);
	    			//将停止的时间作为最终时间更新
	    			car.setUser(null);
	    			applicationContext.getBean(TblCarMapper.class).updateByPrimaryKey(car);
	    			
	    			// 更新流水
					final TblBill bill = new TblBill();
					bill.setId(billId);
					bill.setRecordId(recordId);
					bill.setAmount(amount);
					bill.setType(BILL_PAY_TYPE);
					bill.setCustomerId(customerId);
					bill.setTime(new Date());
					bill.setRecordId(recordId);
					if (account.getAmount() < EACH_HOUR_PRICE) {
						bill.setStatus(NOT_PAY_STATUS);
					} else {
						bill.setStatus(PAY_TYPE);
					}
	    			new Thread() {
	    				@Override
	    				public void run() {
	    					applicationContext.getBean(TblBillMapper.class).insert(bill);
	    				}
	    			}.start();
	    			
	    			


				}
	    }
        
    }

}
