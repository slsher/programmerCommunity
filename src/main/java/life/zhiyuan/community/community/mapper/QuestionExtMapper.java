package life.zhiyuan.community.community.mapper;

import life.zhiyuan.community.community.dto.QuestionQueryDTO;
import life.zhiyuan.community.community.model.Question;
import life.zhiyuan.community.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);

    int incCommentCount(Question record);

    int deleteById(Question record);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}