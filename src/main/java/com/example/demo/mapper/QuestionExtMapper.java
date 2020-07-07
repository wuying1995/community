package com.example.demo.mapper;

import com.example.demo.model.Question;

import java.util.List;


public interface QuestionExtMapper {
    int incView(Question record);


    int incCommentCount(Question question);

    List<Question> selectRelated(Question question);
}