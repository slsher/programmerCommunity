package life.zhiyuan.community.community.dto;

import life.zhiyuan.community.community.model.User;
import lombok.Data;

/**
 * Created by zhuzhiwen by 2020/10/18 20:06
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}
