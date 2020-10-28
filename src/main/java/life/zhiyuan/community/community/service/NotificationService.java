package life.zhiyuan.community.community.service;

import life.zhiyuan.community.community.dto.NotificationDTO;
import life.zhiyuan.community.community.dto.PaginationDTO;
import life.zhiyuan.community.community.enums.NotificationStatusEnum;
import life.zhiyuan.community.community.enums.NotificationTypeEnum;
import life.zhiyuan.community.community.exception.CustomizeErrorCode;
import life.zhiyuan.community.community.exception.CustomizeException;
import life.zhiyuan.community.community.mapper.NotificationExtMapper;
import life.zhiyuan.community.community.mapper.NotificationMapper;
import life.zhiyuan.community.community.mapper.UserMapper;
import life.zhiyuan.community.community.model.Notification;
import life.zhiyuan.community.community.model.NotificationExample;
import life.zhiyuan.community.community.model.User;
import life.zhiyuan.community.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhuzhiwen by 2020/10/21 16:22
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationExtMapper notificationExtMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        Integer totalPage;
        if (totalCount % size == 0) {
            // 如果等于0
            totalPage = totalCount / size;
        } else {
            // 如果不等于0
            totalPage = totalCount / size + 1;
        }


        //没有页数的处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);
        // 计算页面公式 size*(page-1)
        Integer offset = size * (page - 1);
        //QuestionService里面查询Question 同时循环查询user 赋值
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }


        paginationDTO.setData(notificationDTOS);
        return paginationDTO;

    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FALL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }

    public void deleteByNotification(Long id) {
        Notification notification=new Notification();
        notification.setId(id);
        notificationExtMapper.deleteByNotificationId(notification);

    }
    public void deleteByNotifier(Long id) {
        Notification notification=new Notification();
        notification.setNotifier(id);
        notificationExtMapper.deleteByNotifier(notification);

    }
}
