package com.pythe.mapper;

import com.pythe.pojo.TblCombo;
import com.pythe.pojo.TblComboExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblComboMapper {
    int countByExample(TblComboExample example);

    int deleteByExample(TblComboExample example);

    int deleteByPrimaryKey(Long comboId);

    int insert(TblCombo record);

    int insertSelective(TblCombo record);

    List<TblCombo> selectByExample(TblComboExample example);

    TblCombo selectByPrimaryKey(Long comboId);

    int updateByExampleSelective(@Param("record") TblCombo record, @Param("example") TblComboExample example);

    int updateByExample(@Param("record") TblCombo record, @Param("example") TblComboExample example);

    int updateByPrimaryKeySelective(TblCombo record);

    int updateByPrimaryKey(TblCombo record);
}