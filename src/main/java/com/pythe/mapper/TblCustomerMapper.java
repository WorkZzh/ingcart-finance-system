package com.pythe.mapper;

import com.pythe.pojo.TblCustomer;
import com.pythe.pojo.TblCustomerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblCustomerMapper {
    int countByExample(TblCustomerExample example);

    int deleteByExample(TblCustomerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblCustomer record);

    int insertSelective(TblCustomer record);

    List<TblCustomer> selectByExample(TblCustomerExample example);

    TblCustomer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblCustomer record, @Param("example") TblCustomerExample example);

    int updateByExample(@Param("record") TblCustomer record, @Param("example") TblCustomerExample example);

    int updateByPrimaryKeySelective(TblCustomer record);

    int updateByPrimaryKey(TblCustomer record);
}