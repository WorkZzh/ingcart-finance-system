package com.pythe.mapper;

import com.pythe.pojo.VCustomer;
import com.pythe.pojo.VCustomerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VCustomerMapper {
    int countByExample(VCustomerExample example);

    int deleteByExample(VCustomerExample example);

    int insert(VCustomer record);

    int insertSelective(VCustomer record);

    List<VCustomer> selectByExample(VCustomerExample example);

    int updateByExampleSelective(@Param("record") VCustomer record, @Param("example") VCustomerExample example);

    int updateByExample(@Param("record") VCustomer record, @Param("example") VCustomerExample example);
}