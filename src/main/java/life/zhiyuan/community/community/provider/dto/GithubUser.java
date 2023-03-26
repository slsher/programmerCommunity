package life.zhiyuan.community.community.provider.dto;

import lombok.Data;

/**
 * @program: programmerCommunity
 * @description:
 * @author: zhuzhiwen
 * @date: 2023/03/26 22:23
 **/
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
