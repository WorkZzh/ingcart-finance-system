package com.pythe.mapper;

import com.pythe.pojo.VDistribution;
import com.pythe.pojo.VDistributionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VDistributionMapper {
    int countByExample(VDistributionExample example);

    int deleteByExample(VDistributionExample example);

    int insert(VDistribution record);

    int insertSelective(VDistribution record);

    List<VDistribution> selectByExample(VDistributionExample example);

    int updateByExampleSelective(@Param("record") VDistribution record, @Param("example") VDistributionExample example);

    int updateByExample(@Param("record") VDistribution record, @Param("example") VDistributionExample example);
}