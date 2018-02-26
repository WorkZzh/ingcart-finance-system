package com.pythe.mapper;

import com.pythe.pojo.TblPrice;
import com.pythe.pojo.TblPriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblPriceMapper {
    int countByExample(TblPriceExample example);

    int deleteByExample(TblPriceExample example);

    int deleteByPrimaryKey(String level);

    int insert(TblPrice record);

    int insertSelective(TblPrice record);

    List<TblPrice> selectByExample(TblPriceExample example);

    TblPrice selectByPrimaryKey(String level);

    int updateByExampleSelective(@Param("record") TblPrice record, @Param("example") TblPriceExample example);

    int updateByExample(@Param("record") TblPrice record, @Param("example") TblPriceExample example);

    int updateByPrimaryKeySelective(TblPrice record);

    int updateByPrimaryKey(TblPrice record);
}