package life.zhiyuan.community.community.controller;

import life.zhiyuan.community.community.dto.CommentDTO;
import life.zhiyuan.community.community.dto.PaginationDTO;
import life.zhiyuan.community.community.dto.QuestionDTO;
import life.zhiyuan.community.community.enums.CommentTypeEnum;
import life.zhiyuan.community.community.model.User;
import life.zhiyuan.community.community.service.CommentService;
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
 * Created by zhuzhiwen by 2020/10/24 9:31
 */
@Controller
public class AdminController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private NotificationService notificationService;
    @GetMapping("/admin")
    public String index(Model model,
                        HttpServletRequest request,
                        //计算页数 接受传入的参数
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "search", required = false) String search) {

        User user = (User) request.getSession().getAttribute("user");
        PaginationDTO pagination = questionService.AdminList(user.getId(), page, size);


        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);

        PaginationDTO paginationDTO=notificationService.list(user.getId(), page, size);
        Long unreadCount=notificationService.unreadCount(user.getId());
        model.addAttribute("section", "replies");
        model.addAttribute("paginations", paginationDTO);
        model.addAttribute("sectionName", "通知中心");

        return "admin";
    }

}
