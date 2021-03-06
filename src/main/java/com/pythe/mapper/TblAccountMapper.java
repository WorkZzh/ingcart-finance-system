package com.pythe.mapper;

import com.pythe.pojo.TblAccount;
import com.pythe.pojo.TblAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblAccountMapper {
    int countByExample(TblAccountExample example);

    int deleteByExample(TblAccountExample example);

    int deleteByPrimaryKey(Long customerId);

    int insert(TblAccount record);

    int insertSelective(TblAccount record);

    List<TblAccount> selectByExample(TblAccountExample example);

    TblAccount selectByPrimaryKey(Long customerId);

    int updateByExampleSelective(@Param("record") TblAccount record, @Param("example") TblAccountExample example);

    int updateByExample(@Param("record") TblAccount record, @Param("example") TblAccountExample example);

    int updateByPrimaryKeySelective(TblAccount record);

    int updateByPrimaryKey(TblAccount record);
}