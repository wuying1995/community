package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@PropertySource({"classpath:application.properties"})
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;


    @Value("${Redirect_uri}")
    private String Redirect_uri;
    @Value("${Client_id}")
    private String Client_id;
    @Value("${Client_secret}")
    private String Client_secret;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {

        AccessTokenDTO accessTokenDto = new AccessTokenDTO();
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(Redirect_uri);
        accessTokenDto.setClient_id(Client_id);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_secret(Client_secret);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());

        if (githubUser != null&& githubUser.getName()!=null) {
            User user1 = new User();

            String token= UUID.randomUUID().toString();
            user1.setToken(token);
            user1.setName(githubUser.getName());
            user1.setAccountId(String.valueOf(githubUser.getId()));
            user1.setAvatarUrl(githubUser.getAvatar_url());
            //userMapper.insert(user1);

            userService.createOrUpdate(user1);
           // request.getSession().setAttribute("user",githubUser);

            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        }else {
            return "redirect:/";
        }


    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}

//session : 账户
//cookie:银行卡

//CMOND+option+V 抽取变量