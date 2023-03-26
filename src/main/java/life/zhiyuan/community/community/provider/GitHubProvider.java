package life.zhiyuan.community.community.provider;

import com.alibaba.fastjson.JSON;
import life.zhiyuan.community.community.dto.AccessTokenDTO;
import life.zhiyuan.community.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by zhuzhiwen by 2020/9/27 20:32
 */
@Component
@Slf4j
public class GitHubProvider {
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String strs = response.body().string();
            String token = strs.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            log.error("getAccessToken error,{}", accessTokenDTO, e);
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();
        try  {
            Response response = client.newCall(request).execute();
            String strs = response.body().string();
            GithubUser githubUser = JSON.parseObject(strs, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            log.error("getUser error,{}", accessToken, e);
        }
        return null;
    }
}
