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

	PytheResult prepareUnlockGyQrId(Long customerId, Long qrId);


}
