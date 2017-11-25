package com.pythe.mapper;

import com.pythe.pojo.TblBill;
import com.pythe.pojo.TblBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblBillMapper {
    int countByExample(TblBillExample example);

    int deleteByExample(TblBillExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblBill record);

    int insertSelective(TblBill record);

    List<TblBill> selectByExample(TblBillExample example);

    TblBill selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblBill record, @Param("example") TblBillExample example);

    int updateByExample(@Param("record") TblBill record, @Param("example") TblBillExample example);

    int updateByPrimaryKeySelective(TblBill record);

    int updateByPrimaryKey(TblBill record);
}