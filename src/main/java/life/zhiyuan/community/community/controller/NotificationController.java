package life.zhiyuan.community.community.controller;

import life.zhiyuan.community.community.dto.NotificationDTO;
import life.zhiyuan.community.community.dto.PaginationDTO;
import life.zhiyuan.community.community.enums.NotificationTypeEnum;
import life.zhiyuan.community.community.model.Notification;
import life.zhiyuan.community.community.model.User;
import life.zhiyuan.community.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuzhiwen by 2020/10/21 19:35
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(
            HttpServletRequest request,
            @PathVariable(name = "id") Long id
    ) {
        //获取cookie 获取cookie中的token 然后在获取session

        User user = (User) request.getSession().getAttribute("user");

        //没有登陆直接跳转到首页
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (NotificationTypeEnum.REPLAY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLAY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
