package com.pythe.rest.service;

import com.pythe.common.pojo.PytheResult;

public interface TeasurerService {
	PytheResult selectTeasurerList(String parameters);

	PytheResult deleteTeasurer(String parameters);

	PytheResult insertLevelOneTeasurer(String parameters);

	PytheResult insertLevelTwoTeasurer(String parameters);

	PytheResult loginByPassowrd(String parameters);

	PytheResult loginByMessage(String parameters);

	PytheResult updatePassword(String parameters);

	PytheResult selectLevelByName(String parameters);
	
	PytheResult selectTeasurerRocordList(String parameters);

}
