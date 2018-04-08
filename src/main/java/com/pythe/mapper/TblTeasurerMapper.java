package com.pythe.mapper;

import com.pythe.pojo.TblTeasurer;
import com.pythe.pojo.TblTeasurerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblTeasurerMapper {
    int countByExample(TblTeasurerExample example);

    int deleteByExample(TblTeasurerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblTeasurer record);

    int insertSelective(TblTeasurer record);

    List<TblTeasurer> selectByExample(TblTeasurerExample example);

    TblTeasurer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblTeasurer record, @Param("example") TblTeasurerExample example);

    int updateByExample(@Param("record") TblTeasurer record, @Param("example") TblTeasurerExample example);

    int updateByPrimaryKeySelective(TblTeasurer record);

    int updateByPrimaryKey(TblTeasurer record);
}