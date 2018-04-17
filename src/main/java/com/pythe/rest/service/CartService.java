package com.pythe.rest.service;

import com.pythe.common.pojo.PytheResult;

public interface CartService {


	PytheResult unlock(String parameters);

	PytheResult lock(String parameters);

	PytheResult selectCouponByCustomerId(Long customerId);


	PytheResult selectCartPositionByMap(Double longitude, Double latitude);

	PytheResult computeFee(String parameters);

	PytheResult holdCartByCustomerId(String parameters);


	PytheResult selectSaveRestTimeByCustomerId(Long customerId);

	PytheResult selectUseCarTimeByCarId(String carId);

	PytheResult deleteAppointmentByCustomerId(Long customerId);

	PytheResult unLockEncodeByCartId(String parameters);

	PytheResult recordDeviceInfo(String parameters);

	PytheResult qrToMac(String parameters);

	PytheResult prepareUnlock(Long customerId, String carId);

	String bluetoothEncrypt(String parameter);

	String bluetoothDecrypt(String parameter);

	PytheResult managerLock(String parameters);

	PytheResult macSwitchKey(String parameters);

	PytheResult prepareUnlockGyQrId(Long customerId, Long qrId,Integer type, String code);

	PytheResult unlockFalseReset(String parameters);

	PytheResult urgentUnlock(String parameters);

	PytheResult urgentUnlockByClient(String parameters);

	PytheResult manageUrgentRefund(String parameters);

	void autoLock();

	PytheResult lockHold(String parameters);

	PytheResult customerUrgentLock(String parameters);

	PytheResult checkLockStatusEncode(String parameters);

	PytheResult updateCustomerAccount();


	PytheResult refundByTopManager(String parameters);

	PytheResult testRestRefund(String parameters);

	PytheResult refundToTripByManager(String parameters);

	PytheResult refundUnconditionally(String parameters);

	PytheResult transferCar(String parameters);

	PytheResult manageUnlock(String parameters);


}
