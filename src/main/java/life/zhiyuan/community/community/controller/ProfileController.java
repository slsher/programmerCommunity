package life.zhiyuan.community.community.controller;

import life.zhiyuan.community.community.dto.NotificationDTO;
import life.zhiyuan.community.community.dto.PaginationDTO;
import life.zhiyuan.community.community.mapper.UserMapper;
import life.zhiyuan.community.community.model.Notification;
import life.zhiyuan.community.community.model.User;
import life.zhiyuan.community.community.service.NotificationService;
import life.zhiyuan.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhuzhiwen by 2020/10/13 21:46
 */
@Controller
public class ProfileController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(
            HttpServletRequest request,
            //计算页数 接受传入的参数
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @PathVariable(name = "action") String action,
            Model model) {
        //获取cookie 获取cookie中的token 然后在获取session

        User user = (User) request.getSession().getAttribute("user");

        if (user==null){
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("replies".equals(action)) {
            PaginationDTO paginationDTO=notificationService.list(user.getId(), page, size);
            Long unreadCount=notificationService.unreadCount(user.getId());
            model.addAttribute("section", "replies");
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("unreadCount", unreadCount);
            model.addAttribute("sectionName", "最新回复");
        }

        return "profile";
    }
}
