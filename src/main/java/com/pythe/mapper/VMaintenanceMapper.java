package com.pythe.mapper;

import com.pythe.pojo.VMaintenance;
import com.pythe.pojo.VMaintenanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VMaintenanceMapper {
    int countByExample(VMaintenanceExample example);

    int deleteByExample(VMaintenanceExample example);

    int insert(VMaintenance record);

    int insertSelective(VMaintenance record);

    List<VMaintenance> selectByExampleWithBLOBs(VMaintenanceExample example);

    List<VMaintenance> selectByExample(VMaintenanceExample example);

    int updateByExampleSelective(@Param("record") VMaintenance record, @Param("example") VMaintenanceExample example);

    int updateByExampleWithBLOBs(@Param("record") VMaintenance record, @Param("example") VMaintenanceExample example);

    int updateByExample(@Param("record") VMaintenance record, @Param("example") VMaintenanceExample example);
}