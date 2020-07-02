package com.example.demo.controller;

import com.example.demo.dto.QuestionDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String index(Model model) {


        List<QuestionDto> questionDtoList = questionService.list();
        model.addAttribute("questions", questionDtoList);

        return "index";
    }


}
