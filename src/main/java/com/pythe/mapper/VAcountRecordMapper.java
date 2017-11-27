package com.pythe.mapper;

import com.pythe.pojo.VAcountRecord;
import com.pythe.pojo.VAcountRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VAcountRecordMapper {
    int countByExample(VAcountRecordExample example);

    int deleteByExample(VAcountRecordExample example);

    int insert(VAcountRecord record);

    int insertSelective(VAcountRecord record);

    List<VAcountRecord> selectByExample(VAcountRecordExample example);

    int updateByExampleSelective(@Param("record") VAcountRecord record, @Param("example") VAcountRecordExample example);

    int updateByExample(@Param("record") VAcountRecord record, @Param("example") VAcountRecordExample example);
}