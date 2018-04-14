package com.pythe.mapper;

import com.pythe.pojo.VTeasurerRecord;
import com.pythe.pojo.VTeasurerRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VTeasurerRecordMapper {
    int countByExample(VTeasurerRecordExample example);

    int deleteByExample(VTeasurerRecordExample example);

    int insert(VTeasurerRecord record);

    int insertSelective(VTeasurerRecord record);

    List<VTeasurerRecord> selectByExample(VTeasurerRecordExample example);

    int updateByExampleSelective(@Param("record") VTeasurerRecord record, @Param("example") VTeasurerRecordExample example);

    int updateByExample(@Param("record") VTeasurerRecord record, @Param("example") VTeasurerRecordExample example);
}