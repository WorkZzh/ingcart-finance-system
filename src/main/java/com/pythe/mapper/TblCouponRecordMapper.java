package com.pythe.mapper;

import com.pythe.pojo.TblCouponRecord;
import com.pythe.pojo.TblCouponRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblCouponRecordMapper {
    int countByExample(TblCouponRecordExample example);

    int deleteByExample(TblCouponRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblCouponRecord record);

    int insertSelective(TblCouponRecord record);

    List<TblCouponRecord> selectByExample(TblCouponRecordExample example);

    TblCouponRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblCouponRecord record, @Param("example") TblCouponRecordExample example);

    int updateByExample(@Param("record") TblCouponRecord record, @Param("example") TblCouponRecordExample example);

    int updateByPrimaryKeySelective(TblCouponRecord record);

    int updateByPrimaryKey(TblCouponRecord record);
}