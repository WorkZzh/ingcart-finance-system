package com.pythe.mapper;

import com.pythe.pojo.TblHoldRecord;
import com.pythe.pojo.TblHoldRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblHoldRecordMapper {
    int countByExample(TblHoldRecordExample example);

    int deleteByExample(TblHoldRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblHoldRecord record);

    int insertSelective(TblHoldRecord record);

    List<TblHoldRecord> selectByExample(TblHoldRecordExample example);

    TblHoldRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblHoldRecord record, @Param("example") TblHoldRecordExample example);

    int updateByExample(@Param("record") TblHoldRecord record, @Param("example") TblHoldRecordExample example);

    int updateByPrimaryKeySelective(TblHoldRecord record);

    int updateByPrimaryKey(TblHoldRecord record);
}