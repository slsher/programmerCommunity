package life.zhiyuan.community.community.controller;

import life.zhiyuan.community.community.cache.TagCache;
import life.zhiyuan.community.community.dto.CommentDTO;
import life.zhiyuan.community.community.dto.PaginationDTO;
import life.zhiyuan.community.community.dto.QuestionDTO;
import life.zhiyuan.community.community.dto.ResultDTO;
import life.zhiyuan.community.community.enums.CommentTypeEnum;
import life.zhiyuan.community.community.model.User;
import life.zhiyuan.community.community.service.CommentService;
import life.zhiyuan.community.community.service.NotificationService;
import life.zhiyuan.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String admin(){
        return "redirect:/admin/user";
    }
    @GetMapping("/admin/{action}")
    public String index(Model model,
                        HttpServletRequest request,
                        //计算页数 接受传入的参数
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @PathVariable(name = "action") String action) {

        User user = (User) request.getSession().getAttribute("user");


        model.addAttribute("search", search);


        if (!"slsher".equals(user.getName())) {
            return "redirect:/";
        }


        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
//            PaginationDTO pagination = questionService.AdminList(user.getId(), page, size);
            PaginationDTO pagination = questionService.list(search,page, size);
            model.addAttribute("pagination", pagination);
        } else if ("comments".equals(action)) {
            model.addAttribute("section", "comments");
            PaginationDTO paginationComment = commentService.AdminList(user.getId(), page, size);
            model.addAttribute("paginationComment", paginationComment);
        } else if ("notification".equals(action)) {
            model.addAttribute("section", "notification");
            PaginationDTO paginationNotification = notificationService.list(user.getId(), page, size);
            model.addAttribute("paginationNotification", paginationNotification);
        } else {
            model.addAttribute("section", "users");
        }

        return "admin";
    }

    @GetMapping(value = "/admin/{action}/{id}")
    public Object questionEdit(@PathVariable(name = "id") Long id,
                               @PathVariable(name = "action") String action) {

        if ("questions".equals(action)) {
            //删除帖子
            questionService.deleteByQuestionId(id);
            commentService.deleteByParentId(id);
        } else if ("comments".equals(action)) {
            //删除评论
            commentService.deleteByCommentId(id);
            commentService.deleteByParentId(id);
        } else if ("notification".equals(action)) {
            //删除通知
            notificationService.deleteByNotification(id);
        }else if ("users".equals(action)){
            questionService.deleteByCreator(id);
            notificationService.deleteByNotifier(id);
            commentService.deleteByCommentator(id);
        }
        return "redirect:/admin/" + action;
    }

}
