package com.pythe.mapper;

import com.pythe.pojo.TblDistribution;
import com.pythe.pojo.TblDistributionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblDistributionMapper {
    int countByExample(TblDistributionExample example);

    int deleteByExample(TblDistributionExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblDistribution record);

    int insertSelective(TblDistribution record);

    List<TblDistribution> selectByExampleWithBLOBs(TblDistributionExample example);

    List<TblDistribution> selectByExample(TblDistributionExample example);

    TblDistribution selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblDistribution record, @Param("example") TblDistributionExample example);

    int updateByExampleWithBLOBs(@Param("record") TblDistribution record, @Param("example") TblDistributionExample example);

    int updateByExample(@Param("record") TblDistribution record, @Param("example") TblDistributionExample example);

    int updateByPrimaryKeySelective(TblDistribution record);

    int updateByPrimaryKeyWithBLOBs(TblDistribution record);

    int updateByPrimaryKey(TblDistribution record);
    
	List<TblDistribution> selectAllCity();
}