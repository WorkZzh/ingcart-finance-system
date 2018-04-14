package com.pythe.mapper;

import com.pythe.pojo.VTeasurer;
import com.pythe.pojo.VTeasurerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VTeasurerMapper {
    int countByExample(VTeasurerExample example);

    int deleteByExample(VTeasurerExample example);

    int insert(VTeasurer record);

    int insertSelective(VTeasurer record);

    List<VTeasurer> selectByExample(VTeasurerExample example);

    int updateByExampleSelective(@Param("record") VTeasurer record, @Param("example") VTeasurerExample example);

    int updateByExample(@Param("record") VTeasurer record, @Param("example") VTeasurerExample example);
}