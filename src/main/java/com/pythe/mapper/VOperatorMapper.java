package com.pythe.mapper;

import com.pythe.pojo.VOperator;
import com.pythe.pojo.VOperatorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VOperatorMapper {
    int countByExample(VOperatorExample example);

    int deleteByExample(VOperatorExample example);

    int insert(VOperator record);

    int insertSelective(VOperator record);

    List<VOperator> selectByExample(VOperatorExample example);

    int updateByExampleSelective(@Param("record") VOperator record, @Param("example") VOperatorExample example);

    int updateByExample(@Param("record") VOperator record, @Param("example") VOperatorExample example);
}