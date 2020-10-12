package life.zhiyuan.community.community.service;

import life.zhiyuan.community.community.dto.QuestionDTO;
import life.zhiyuan.community.community.mapper.QuestionMapper;
import life.zhiyuan.community.community.mapper.UserMapper;
import life.zhiyuan.community.community.model.Question;
import life.zhiyuan.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuzhiwen by 2020/10/10 23:06
 */
@Service
public class QuestionService {
    @Autowired
    public QuestionMapper questionMapper;
    @Autowired
    public UserMapper userMapper;

    public List<QuestionDTO> list() {
        //QuestionService里面查询Question 同时循环查询user 赋值
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user= userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);


        }
        return questionDTOList;
    }
}
