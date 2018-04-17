package com.pythe.mapper;

import com.pythe.pojo.VRecord;
import com.pythe.pojo.VRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VRecordMapper {
    int countByExample(VRecordExample example);

    int deleteByExample(VRecordExample example);

    int insert(VRecord record);

    int insertSelective(VRecord record);

    List<VRecord> selectByExample(VRecordExample example);

    int updateByExampleSelective(@Param("record") VRecord record, @Param("example") VRecordExample example);

    int updateByExample(@Param("record") VRecord record, @Param("example") VRecordExample example);
}