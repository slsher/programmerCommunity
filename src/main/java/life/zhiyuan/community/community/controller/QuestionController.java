package life.zhiyuan.community.community.controller;

import life.zhiyuan.community.community.dto.QuestionDTO;
import life.zhiyuan.community.community.mapper.QuestionMapper;
import life.zhiyuan.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by zhuzhiwen by 2020/10/14 21:02
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Long id,
                           Model model){
        QuestionDTO questionDTO=questionService.getById(id);

        //累加阅读数
        questionService.inView(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
