package life.zhiyuan.community.community.mapper;

import life.zhiyuan.community.community.model.Notification;
import life.zhiyuan.community.community.model.NotificationExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface NotificationExtMapper {
    int deleteByNotificationId(Notification notification);
}