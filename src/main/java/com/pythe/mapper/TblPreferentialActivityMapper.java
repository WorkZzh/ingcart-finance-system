package com.pythe.mapper;

import com.pythe.pojo.TblPreferentialActivity;
import com.pythe.pojo.TblPreferentialActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblPreferentialActivityMapper {
    int countByExample(TblPreferentialActivityExample example);

    int deleteByExample(TblPreferentialActivityExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblPreferentialActivity record);

    int insertSelective(TblPreferentialActivity record);

    List<TblPreferentialActivity> selectByExample(TblPreferentialActivityExample example);

    TblPreferentialActivity selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblPreferentialActivity record, @Param("example") TblPreferentialActivityExample example);

    int updateByExample(@Param("record") TblPreferentialActivity record, @Param("example") TblPreferentialActivityExample example);

    int updateByPrimaryKeySelective(TblPreferentialActivity record);

    int updateByPrimaryKey(TblPreferentialActivity record);
}