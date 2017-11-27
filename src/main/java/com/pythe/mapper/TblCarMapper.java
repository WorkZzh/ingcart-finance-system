package com.pythe.mapper;

import com.pythe.pojo.TblCar;
import com.pythe.pojo.TblCarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblCarMapper {
    int countByExample(TblCarExample example);

    int deleteByExample(TblCarExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblCar record);

    int insertSelective(TblCar record);

    List<TblCar> selectByExample(TblCarExample example);

    TblCar selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblCar record, @Param("example") TblCarExample example);

    int updateByExample(@Param("record") TblCar record, @Param("example") TblCarExample example);

    int updateByPrimaryKeySelective(TblCar record);

    int updateByPrimaryKey(TblCar record);
}