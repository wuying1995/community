package com.example.demo.controller;

import com.example.demo.dto.QuestionDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action, Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

        User user = (User) request.getSession().getAttribute("user");

//        if (user == null) {
//            return "redirect:/";
//        }


        System.out.println(user);
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {

            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

       // questionService.list(user.getId(), page, size);

        List<QuestionDto> questionDtoList = questionService.list();
        model.addAttribute("questions", questionDtoList);


        return "profile";
    }
}
