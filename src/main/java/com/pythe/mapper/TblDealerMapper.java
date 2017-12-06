package com.pythe.mapper;

import com.pythe.pojo.TblDealer;
import com.pythe.pojo.TblDealerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblDealerMapper {
    int countByExample(TblDealerExample example);

    int deleteByExample(TblDealerExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblDealer record);

    int insertSelective(TblDealer record);

    List<TblDealer> selectByExample(TblDealerExample example);

    TblDealer selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblDealer record, @Param("example") TblDealerExample example);

    int updateByExample(@Param("record") TblDealer record, @Param("example") TblDealerExample example);

    int updateByPrimaryKeySelective(TblDealer record);

    int updateByPrimaryKey(TblDealer record);
}