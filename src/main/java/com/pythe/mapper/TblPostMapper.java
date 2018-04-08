package com.pythe.mapper;

import com.pythe.pojo.TblPost;
import com.pythe.pojo.TblPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblPostMapper {
    int countByExample(TblPostExample example);

    int deleteByExample(TblPostExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblPost record);

    int insertSelective(TblPost record);

    List<TblPost> selectByExample(TblPostExample example);

    TblPost selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblPost record, @Param("example") TblPostExample example);

    int updateByExample(@Param("record") TblPost record, @Param("example") TblPostExample example);

    int updateByPrimaryKeySelective(TblPost record);

    int updateByPrimaryKey(TblPost record);
}