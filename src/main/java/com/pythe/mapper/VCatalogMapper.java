package com.pythe.mapper;

import com.pythe.pojo.VCatalog;
import com.pythe.pojo.VCatalogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VCatalogMapper {
    int countByExample(VCatalogExample example);

    int deleteByExample(VCatalogExample example);

    int insert(VCatalog record);

    int insertSelective(VCatalog record);

    List<VCatalog> selectByExample(VCatalogExample example);

    int updateByExampleSelective(@Param("record") VCatalog record, @Param("example") VCatalogExample example);

    int updateByExample(@Param("record") VCatalog record, @Param("example") VCatalogExample example);
}