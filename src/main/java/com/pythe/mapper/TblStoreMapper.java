package com.pythe.mapper;

import com.pythe.pojo.TblStore;
import com.pythe.pojo.TblStoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblStoreMapper {
    int countByExample(TblStoreExample example);

    int deleteByExample(TblStoreExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblStore record);

    int insertSelective(TblStore record);

    List<TblStore> selectByExample(TblStoreExample example);

    TblStore selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblStore record, @Param("example") TblStoreExample example);

    int updateByExample(@Param("record") TblStore record, @Param("example") TblStoreExample example);

    int updateByPrimaryKeySelective(TblStore record);

    int updateByPrimaryKey(TblStore record);
}