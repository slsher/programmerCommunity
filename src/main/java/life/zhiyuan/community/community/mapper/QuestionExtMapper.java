package life.zhiyuan.community.community.mapper;

import life.zhiyuan.community.community.dto.QuestionQueryDTO;
import life.zhiyuan.community.community.model.Question;


import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);

    int incCommentCount(Question record);

    int deleteByQuestionId(Question record);

    int deleteByCreator(Question question);

    List<Question> selectAdminRelated(Question question);

    Integer countByAdminSearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectByAdminSearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}