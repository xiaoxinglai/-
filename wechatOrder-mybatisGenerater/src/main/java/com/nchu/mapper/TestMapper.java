package com.nchu.mapper;

import com.nchu.pojo.DO.Test;
import java.util.List;

public interface TestMapper {
    int deleteByPrimaryKey(Integer testId);

    int insert(Test record);

    Test selectByPrimaryKey(Integer testId);

    List<Test> selectAll();

    int updateByPrimaryKey(Test record);
}