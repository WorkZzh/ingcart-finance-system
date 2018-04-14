package com.pythe.mapper;

import com.pythe.pojo.VCoupon;
import com.pythe.pojo.VCouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VCouponMapper {
    int countByExample(VCouponExample example);

    int deleteByExample(VCouponExample example);

    int insert(VCoupon record);

    int insertSelective(VCoupon record);

    List<VCoupon> selectByExample(VCouponExample example);

    int updateByExampleSelective(@Param("record") VCoupon record, @Param("example") VCouponExample example);

    int updateByExample(@Param("record") VCoupon record, @Param("example") VCouponExample example);
}