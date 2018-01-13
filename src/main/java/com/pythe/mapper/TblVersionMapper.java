package com.pythe.mapper;

import com.pythe.pojo.TblVersion;
import com.pythe.pojo.TblVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblVersionMapper {
    int countByExample(TblVersionExample example);

    int deleteByExample(TblVersionExample example);

    int deleteByPrimaryKey(String apikey);

    int insert(TblVersion record);

    int insertSelective(TblVersion record);

    List<TblVersion> selectByExampleWithBLOBs(TblVersionExample example);

    List<TblVersion> selectByExample(TblVersionExample example);

    TblVersion selectByPrimaryKey(String apikey);

    int updateByExampleSelective(@Param("record") TblVersion record, @Param("example") TblVersionExample example);

    int updateByExampleWithBLOBs(@Param("record") TblVersion record, @Param("example") TblVersionExample example);

    int updateByExample(@Param("record") TblVersion record, @Param("example") TblVersionExample example);

    int updateByPrimaryKeySelective(TblVersion record);

    int updateByPrimaryKeyWithBLOBs(TblVersion record);

    int updateByPrimaryKey(TblVersion record);
}