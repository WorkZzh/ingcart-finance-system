package com.pythe.mapper;

import com.pythe.pojo.TblOperatorRecord;
import com.pythe.pojo.TblOperatorRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblOperatorRecordMapper {
    int countByExample(TblOperatorRecordExample example);

    int deleteByExample(TblOperatorRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblOperatorRecord record);

    int insertSelective(TblOperatorRecord record);

    List<TblOperatorRecord> selectByExample(TblOperatorRecordExample example);

    TblOperatorRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblOperatorRecord record, @Param("example") TblOperatorRecordExample example);

    int updateByExample(@Param("record") TblOperatorRecord record, @Param("example") TblOperatorRecordExample example);

    int updateByPrimaryKeySelective(TblOperatorRecord record);

    int updateByPrimaryKey(TblOperatorRecord record);
}