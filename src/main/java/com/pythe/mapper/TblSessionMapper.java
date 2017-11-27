package com.pythe.mapper;

import com.pythe.pojo.TblSession;
import com.pythe.pojo.TblSessionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblSessionMapper {
    int countByExample(TblSessionExample example);

    int deleteByExample(TblSessionExample example);

    int deleteByPrimaryKey(String sessionId);

    int insert(TblSession record);

    int insertSelective(TblSession record);

    List<TblSession> selectByExample(TblSessionExample example);

    TblSession selectByPrimaryKey(String sessionId);

    int updateByExampleSelective(@Param("record") TblSession record, @Param("example") TblSessionExample example);

    int updateByExample(@Param("record") TblSession record, @Param("example") TblSessionExample example);

    int updateByPrimaryKeySelective(TblSession record);

    int updateByPrimaryKey(TblSession record);
}