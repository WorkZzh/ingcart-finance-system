package com.pythe.mapper;

import com.pythe.pojo.VOperatorRecord;
import com.pythe.pojo.VOperatorRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VOperatorRecordMapper {
    int countByExample(VOperatorRecordExample example);

    int deleteByExample(VOperatorRecordExample example);

    int insert(VOperatorRecord record);

    int insertSelective(VOperatorRecord record);

    List<VOperatorRecord> selectByExample(VOperatorRecordExample example);

    int updateByExampleSelective(@Param("record") VOperatorRecord record, @Param("example") VOperatorRecordExample example);

    int updateByExample(@Param("record") VOperatorRecord record, @Param("example") VOperatorRecordExample example);
}