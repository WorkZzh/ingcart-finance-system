package com.pythe.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pythe.pojo.TblTimer;
import com.pythe.pojo.TblTimerExample;

public interface TblTimerMapper {
    int countByExample(TblTimerExample example);

    int deleteByExample(TblTimerExample example);

    int deleteByPrimaryKey(String timeid);

    int insert(TblTimer record);

    int insertSelective(TblTimer record);

    List<TblTimer> selectByExample(TblTimerExample example);

    TblTimer selectByPrimaryKey(String timeid);

    int updateByExampleSelective(@Param("record") TblTimer record, @Param("example") TblTimerExample example);

    int updateByExample(@Param("record") TblTimer record, @Param("example") TblTimerExample example);

    int updateByPrimaryKeySelective(TblTimer record);

    int updateByPrimaryKey(TblTimer record);

	void paymentOrderScan(Date date);
}