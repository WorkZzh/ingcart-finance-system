package com.pythe.mapper;

import com.pythe.pojo.TblBagRecord;
import com.pythe.pojo.TblBagRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblBagRecordMapper {
    int countByExample(TblBagRecordExample example);

    int deleteByExample(TblBagRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblBagRecord record);

    int insertSelective(TblBagRecord record);

    List<TblBagRecord> selectByExample(TblBagRecordExample example);

    TblBagRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblBagRecord record, @Param("example") TblBagRecordExample example);

    int updateByExample(@Param("record") TblBagRecord record, @Param("example") TblBagRecordExample example);

    int updateByPrimaryKeySelective(TblBagRecord record);

    int updateByPrimaryKey(TblBagRecord record);
}