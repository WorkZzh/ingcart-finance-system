package com.pythe.mapper;

import com.pythe.pojo.TblCatalog;
import com.pythe.pojo.TblCatalogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblCatalogMapper {
    int countByExample(TblCatalogExample example);

    int deleteByExample(TblCatalogExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblCatalog record);

    int insertSelective(TblCatalog record);

    List<TblCatalog> selectByExample(TblCatalogExample example);

    TblCatalog selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblCatalog record, @Param("example") TblCatalogExample example);

    int updateByExample(@Param("record") TblCatalog record, @Param("example") TblCatalogExample example);

    int updateByPrimaryKeySelective(TblCatalog record);

    int updateByPrimaryKey(TblCatalog record);
}