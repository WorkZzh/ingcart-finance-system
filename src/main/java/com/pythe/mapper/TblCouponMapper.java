package com.pythe.mapper;

import com.pythe.pojo.TblCoupon;
import com.pythe.pojo.TblCouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblCouponMapper {
    int countByExample(TblCouponExample example);

    int deleteByExample(TblCouponExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TblCoupon record);

    int insertSelective(TblCoupon record);

    List<TblCoupon> selectByExample(TblCouponExample example);

    TblCoupon selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TblCoupon record, @Param("example") TblCouponExample example);

    int updateByExample(@Param("record") TblCoupon record, @Param("example") TblCouponExample example);

    int updateByPrimaryKeySelective(TblCoupon record);

    int updateByPrimaryKey(TblCoupon record);
}