package com.pythe.mapper;

import com.pythe.pojo.VRecordBill;
import com.pythe.pojo.VRecordBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VRecordBillMapper {
    int countByExample(VRecordBillExample example);

    int deleteByExample(VRecordBillExample example);

    int insert(VRecordBill record);

    int insertSelective(VRecordBill record);

    List<VRecordBill> selectByExample(VRecordBillExample example);

    int updateByExampleSelective(@Param("record") VRecordBill record, @Param("example") VRecordBillExample example);

    int updateByExample(@Param("record") VRecordBill record, @Param("example") VRecordBillExample example);
    
    VRecordBill selectSumByTime(@Param("levels") List<String> levels, @Param("time") String time);
    
}