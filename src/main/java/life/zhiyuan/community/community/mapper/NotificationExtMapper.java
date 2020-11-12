package life.zhiyuan.community.community.mapper;

import life.zhiyuan.community.community.dto.NotificationQueryDTO;
import life.zhiyuan.community.community.dto.QuestionQueryDTO;
import life.zhiyuan.community.community.model.Notification;
import life.zhiyuan.community.community.model.NotificationExample;
import life.zhiyuan.community.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface NotificationExtMapper {
    int deleteByNotificationId(Notification notification);

    int deleteByNotifier(Notification notification);

    Integer countBySearch(NotificationQueryDTO notificationQueryDTO);
    List<Notification> selectBySearch(NotificationQueryDTO notificationQueryDTO);
}