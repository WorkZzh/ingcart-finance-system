package com.pythe.mapper;

import com.pythe.pojo.TblP;
import com.pythe.pojo.TblPExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblPMapper {
    int countByExample(TblPExample example);

    int deleteByExample(TblPExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblP record);

    int insertSelective(TblP record);

    List<TblP> selectByExample(TblPExample example);

    TblP selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblP record, @Param("example") TblPExample example);

    int updateByExample(@Param("record") TblP record, @Param("example") TblPExample example);

    int updateByPrimaryKeySelective(TblP record);

    int updateByPrimaryKey(TblP record);
}