package com.pythe.mapper;

import com.pythe.pojo.VCar;
import com.pythe.pojo.VCarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VCarMapper {
    int countByExample(VCarExample example);

    int deleteByExample(VCarExample example);

    int insert(VCar record);

    int insertSelective(VCar record);

    List<VCar> selectByExample(VCarExample example);

    int updateByExampleSelective(@Param("record") VCar record, @Param("example") VCarExample example);

    int updateByExample(@Param("record") VCar record, @Param("example") VCarExample example);
}