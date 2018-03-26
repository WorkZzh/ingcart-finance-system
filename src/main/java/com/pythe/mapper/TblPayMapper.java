package com.pythe.mapper;

import com.pythe.pojo.TblPay;
import com.pythe.pojo.TblPayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblPayMapper {
    int countByExample(TblPayExample example);

    int deleteByExample(TblPayExample example);

    int insert(TblPay record);

    int insertSelective(TblPay record);

    List<TblPay> selectByExample(TblPayExample example);

    int updateByExampleSelective(@Param("record") TblPay record, @Param("example") TblPayExample example);

    int updateByExample(@Param("record") TblPay record, @Param("example") TblPayExample example);
}