package com.pythe.mapper;

import com.pythe.pojo.TblTmpCar;
import com.pythe.pojo.TblTmpCarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblTmpCarMapper {
    int countByExample(TblTmpCarExample example);

    int deleteByExample(TblTmpCarExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblTmpCar record);

    int insertSelective(TblTmpCar record);

    List<TblTmpCar> selectByExample(TblTmpCarExample example);

    TblTmpCar selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblTmpCar record, @Param("example") TblTmpCarExample example);

    int updateByExample(@Param("record") TblTmpCar record, @Param("example") TblTmpCarExample example);

    int updateByPrimaryKeySelective(TblTmpCar record);

    int updateByPrimaryKey(TblTmpCar record);
}