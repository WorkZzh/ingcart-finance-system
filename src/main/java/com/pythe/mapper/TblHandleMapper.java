package com.pythe.mapper;

import com.pythe.pojo.TblHandle;
import com.pythe.pojo.TblHandleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblHandleMapper {
    int countByExample(TblHandleExample example);

    int deleteByExample(TblHandleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblHandle record);

    int insertSelective(TblHandle record);

    List<TblHandle> selectByExample(TblHandleExample example);

    TblHandle selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblHandle record, @Param("example") TblHandleExample example);

    int updateByExample(@Param("record") TblHandle record, @Param("example") TblHandleExample example);

    int updateByPrimaryKeySelective(TblHandle record);

    int updateByPrimaryKey(TblHandle record);
}