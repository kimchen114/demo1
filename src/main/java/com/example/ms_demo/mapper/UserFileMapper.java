package com.example.ms_demo.mapper;

import com.example.ms_demo.domain.UserFile;
import com.example.ms_demo.domain.UserFileCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserFileMapper {
    long countByExample(UserFileCriteria example);

    int deleteByExample(UserFileCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(UserFile record);

    int insertSelective(UserFile record);

    List<UserFile> selectByExample(UserFileCriteria example);

    UserFile selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserFile record, @Param("example") UserFileCriteria example);

    int updateByExample(@Param("record") UserFile record, @Param("example") UserFileCriteria example);

    int updateByPrimaryKeySelective(UserFile record);

    int updateByPrimaryKey(UserFile record);
}