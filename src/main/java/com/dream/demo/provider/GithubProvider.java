package com.dream.demo.provider;

import com.alibaba.fastjson.JSON;
import com.dream.demo.dto.AccessTokenDTO;
import com.dream.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;
        }

    }
    public GithubUser getUser(String accessToken)  {
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        System.out.println("https://api.github.com/user?access_token="+accessToken);
        try{
        Response response =client.newCall(request).execute();
        String string =response.body().string();
        GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
        return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
