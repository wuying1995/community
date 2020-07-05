package com.example.demo.mapper;

import com.example.demo.model.Question;


public interface QuestionExtMapper {
    int incView(Question record);


    int incCommentCount(Question question);
}