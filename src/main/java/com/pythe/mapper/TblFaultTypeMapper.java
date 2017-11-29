package com.pythe.mapper;

import com.pythe.pojo.TblFaultType;
import com.pythe.pojo.TblFaultTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblFaultTypeMapper {
    int countByExample(TblFaultTypeExample example);

    int deleteByExample(TblFaultTypeExample example);

    int deleteByPrimaryKey(Integer type);

    int insert(TblFaultType record);

    int insertSelective(TblFaultType record);

    List<TblFaultType> selectByExample(TblFaultTypeExample example);

    TblFaultType selectByPrimaryKey(Integer type);

    int updateByExampleSelective(@Param("record") TblFaultType record, @Param("example") TblFaultTypeExample example);

    int updateByExample(@Param("record") TblFaultType record, @Param("example") TblFaultTypeExample example);

    int updateByPrimaryKeySelective(TblFaultType record);

    int updateByPrimaryKey(TblFaultType record);
}