package life.zhiyuan.community.community.dto;

import life.zhiyuan.community.community.model.User;
import lombok.Data;

/**
 * Created by zhuzhiwen by 2020/10/18 20:06
 */
@Data
public class UserDTO {
    private Long id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String bio;
    private String avatarUrl;
    private User user;
}
