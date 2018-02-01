package com.pythe.mapper;

import com.pythe.pojo.TblRecord;
import com.pythe.pojo.TblRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblRecordMapper {
    int countByExample(TblRecordExample example);

    int deleteByExample(TblRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblRecord record);

    int insertSelective(TblRecord record);

    List<TblRecord> selectByExample(TblRecordExample example);

    TblRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblRecord record, @Param("example") TblRecordExample example);

    int updateByExample(@Param("record") TblRecord record, @Param("example") TblRecordExample example);

    int updateByPrimaryKeySelective(TblRecord record);

    int updateByPrimaryKey(TblRecord record);
    
    TblRecord selectPreRecordPrimaryKey(Long customer_id,String car_id);
}