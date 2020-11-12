package life.zhiyuan.community.community.dto;

import life.zhiyuan.community.community.model.User;
import lombok.Data;

/**
 * Created by zhuzhiwen by 2020/10/21 16:17
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
    private User user;
}
