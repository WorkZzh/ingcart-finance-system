package com.pythe.mapper;

import com.pythe.pojo.TblTeasurerRecord;
import com.pythe.pojo.TblTeasurerRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblTeasurerRecordMapper {
    int countByExample(TblTeasurerRecordExample example);

    int deleteByExample(TblTeasurerRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblTeasurerRecord record);

    int insertSelective(TblTeasurerRecord record);

    List<TblTeasurerRecord> selectByExample(TblTeasurerRecordExample example);

    TblTeasurerRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblTeasurerRecord record, @Param("example") TblTeasurerRecordExample example);

    int updateByExample(@Param("record") TblTeasurerRecord record, @Param("example") TblTeasurerRecordExample example);

    int updateByPrimaryKeySelective(TblTeasurerRecord record);

    int updateByPrimaryKey(TblTeasurerRecord record);
}