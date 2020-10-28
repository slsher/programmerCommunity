package life.zhiyuan.community.community.service;

import life.zhiyuan.community.community.dto.PaginationDTO;
import life.zhiyuan.community.community.dto.QuestionDTO;
import life.zhiyuan.community.community.dto.QuestionQueryDTO;
import life.zhiyuan.community.community.exception.CustomizeErrorCode;
import life.zhiyuan.community.community.exception.CustomizeException;
import life.zhiyuan.community.community.mapper.QuestionExtMapper;
import life.zhiyuan.community.community.mapper.QuestionMapper;
import life.zhiyuan.community.community.mapper.UserMapper;
import life.zhiyuan.community.community.model.Question;
import life.zhiyuan.community.community.model.QuestionExample;
import life.zhiyuan.community.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhuzhiwen by 2020/10/10 23:06
 */
@Service
public class QuestionService {
    @Autowired
    public QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;


    @Autowired
    public UserMapper userMapper;

    public PaginationDTO AdminList(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
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
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(String search,Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search=Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        PaginationDTO paginationDTO = new PaginationDTO();

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount =  questionExtMapper.countBySearch(questionQueryDTO);

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

        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
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
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);

        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        } else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void inView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public void deleteByQuestionId(Long id){
        Question question=new Question();
        question.setId(id);
        questionExtMapper.deleteByQuestionId(question);
    }
    public void  deleteByCreator(long id){
        Question question=new Question();
        question.setCreator(id);
        questionExtMapper.deleteByCreator(question);
    }
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS=questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());

        return questionDTOS;
    }
}
