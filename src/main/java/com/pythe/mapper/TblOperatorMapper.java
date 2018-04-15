package com.pythe.mapper;

import com.pythe.pojo.TblOperator;
import com.pythe.pojo.TblOperatorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblOperatorMapper {
    int countByExample(TblOperatorExample example);

    int deleteByExample(TblOperatorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblOperator record);

    int insertSelective(TblOperator record);

    List<TblOperator> selectByExample(TblOperatorExample example);

    TblOperator selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblOperator record, @Param("example") TblOperatorExample example);

    int updateByExample(@Param("record") TblOperator record, @Param("example") TblOperatorExample example);

    int updateByPrimaryKeySelective(TblOperator record);

    int updateByPrimaryKey(TblOperator record);
}