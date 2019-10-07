package com.houb.mapper;

import com.houb.pojo.SmbmsUser;
import com.houb.pojo.SmbmsUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmbmsUserMapper {
    int countByExample(SmbmsUserExample example);

    int deleteByExample(SmbmsUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmbmsUser record);

    int insertSelective(SmbmsUser record);

    List<SmbmsUser> selectByExample(SmbmsUserExample example);

    SmbmsUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmbmsUser record, @Param("example") SmbmsUserExample example);

    int updateByExample(@Param("record") SmbmsUser record, @Param("example") SmbmsUserExample example);

    int updateByPrimaryKeySelective(SmbmsUser record);

    int updateByPrimaryKey(SmbmsUser record);
}