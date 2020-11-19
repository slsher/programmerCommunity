package life.zhiyuan.community.community.controller;

import life.zhiyuan.community.community.cache.HotTagCache;
import life.zhiyuan.community.community.dto.PaginationDTO;
import life.zhiyuan.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by zhuzhiwen by 2020/9/25 9:50
 */
@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(Model model,
                        //计算页数 接受传入的参数
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "8") Integer size,
                        @RequestParam(name = "search",required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "sort", required = false) String sort) {
        //获取cookie 获取cookie中的token 然后在获取session

        //写一个获取到列表的方法 questionMapper针对Question表的
        // 不仅带有基本的question信息也带有用户的信息 跳转前需要去查
        // 方法返回参数传入的数据
        PaginationDTO pagination = questionService.list(search, tag, sort, page, size);
        List<String> tags = hotTagCache.getHots();
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);
        model.addAttribute("sort", sort);
        model.addAttribute("tags", tags);
        //查询列表数据
        //调转到index页面
        return "index";
    }
}
