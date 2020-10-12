package life.zhiyuan.community.community.controller;

import life.zhiyuan.community.community.dto.QuestionDTO;
import life.zhiyuan.community.community.mapper.QuestionMapper;
import life.zhiyuan.community.community.mapper.UserMapper;
import life.zhiyuan.community.community.model.Question;
import life.zhiyuan.community.community.model.User;
import life.zhiyuan.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhuzhiwen by 2020/9/25 9:50
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        //获取cookie 获取cookie中的token 然后在获取session
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        //写一个获取到列表的方法 questionMapper针对Question表的
        // 不仅带有基本的question信息也带有用户的信息 跳转前需要去查
        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions",questionList);
        //查询列表数据
        //调转到index页面
        return "index";
    }
}
