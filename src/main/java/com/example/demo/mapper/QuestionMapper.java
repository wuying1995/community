package com.example.demo.mapper;


import com.example.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag)values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag}) ")
    default void create(Question question){

    }

    @Select("select * from question")
    List<Question> List();

}
