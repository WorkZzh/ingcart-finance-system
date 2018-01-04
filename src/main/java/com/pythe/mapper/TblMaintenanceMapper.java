package com.pythe.mapper;

import com.pythe.pojo.TblMaintenance;
import com.pythe.pojo.TblMaintenanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblMaintenanceMapper {
    int countByExample(TblMaintenanceExample example);

    int deleteByExample(TblMaintenanceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblMaintenance record);

    int insertSelective(TblMaintenance record);

    List<TblMaintenance> selectByExampleWithBLOBs(TblMaintenanceExample example);

    List<TblMaintenance> selectByExample(TblMaintenanceExample example);

    TblMaintenance selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblMaintenance record, @Param("example") TblMaintenanceExample example);

    int updateByExampleWithBLOBs(@Param("record") TblMaintenance record, @Param("example") TblMaintenanceExample example);

    int updateByExample(@Param("record") TblMaintenance record, @Param("example") TblMaintenanceExample example);

    int updateByPrimaryKeySelective(TblMaintenance record);

    int updateByPrimaryKeyWithBLOBs(TblMaintenance record);

    int updateByPrimaryKey(TblMaintenance record);
}