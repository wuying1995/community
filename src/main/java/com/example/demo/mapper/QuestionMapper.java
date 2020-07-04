package com.example.demo.mapper;


import com.example.demo.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag)values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag}) ")
    default void create(Question question) {

    }

    @Select("select * from question")
    List<Question> List();

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);


    @Update("update question set title =#{title},description=#{description},gmt_modified=#{gmtModified}")
    void update(Question question);
}
