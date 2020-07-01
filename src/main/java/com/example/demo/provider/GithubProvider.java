package com.example.demo.provider;


import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class GithubProvider {


    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id=" + accessTokenDTO.getClient_id() + "&client_secret=" + accessTokenDTO.getClient_secret() + "&code=" + accessTokenDTO.getCode() + "&redirect_uri=" + accessTokenDTO.getRedirect_uri() + "&state=" + accessTokenDTO.getState())
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            log.error("getAccessToken error,{}", accessTokenDTO, e);
        }
        return null;
    }

    //hutool


//    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
//
//        String url = "https://github.com/login/oauth/access_token?client_id=" + accessTokenDTO.getClient_id() + "&client_secret=" + accessTokenDTO.getClient_secret() + "&code=" + accessTokenDTO.getCode() + "&redirect_uri=" + accessTokenDTO.getRedirect_uri() + "&state=" + accessTokenDTO.getState();
//
//        //链式构建请求
//        String result2 = HttpRequest.post(url)
//                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                .body(JSON.toJSONString(accessTokenDTO))//表单内容
//                .timeout(20000)//超时，毫秒
//                .execute()
//                .body();
//
//        String token = result2.split("&")[0].split("=")[1];
//
//        return token;
//
//
//    }
//

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
            log.error("getUser error,{}", accessToken, e);
        }
        return null;
    }
}
