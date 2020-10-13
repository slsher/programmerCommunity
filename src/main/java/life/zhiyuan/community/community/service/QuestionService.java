package life.zhiyuan.community.community.service;

import life.zhiyuan.community.community.dto.PaginationDTO;
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

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);
        //没有页数的处理
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        // 计算页面公式 size*(page-1)
        Integer offset = size * (page - 1);
        //QuestionService里面查询Question 同时循环查询user 赋值
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);


        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
