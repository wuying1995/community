package com.example.demo.controller;


import com.example.demo.dto.CommentCreateDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.QuestionDto;
import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.service.CommentService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;


//    @GetMapping("/question/{id}")
//    private String question(@PathVariable(name = "id") Long id,
//                            Model model) {
//
//        QuestionDto questionDto = questionService.getById(id);
//
//        questionService.incView(id);
//
//
//        List<CommentDTO> commentCreateDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
//        model.addAttribute("question", questionDto);
//
//        model.addAttribute("comments", commentCreateDTOS);
//
//        return "question";
//
//    }


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") String id, Model model) {
        Long questionId = null;
        try {
            questionId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }
        QuestionDto questionDTO = questionService.getById(questionId);

        //List<QuestionDto> relatedQuestions = questionService.selectRelated(questionDTO);

        List<CommentDTO> comments = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(questionId);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        //model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
