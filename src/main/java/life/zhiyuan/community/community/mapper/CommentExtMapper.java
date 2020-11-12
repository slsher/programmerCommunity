package life.zhiyuan.community.community.mapper;


import life.zhiyuan.community.community.dto.CommentQueryDTO;
import life.zhiyuan.community.community.dto.QuestionQueryDTO;
import life.zhiyuan.community.community.model.Comment;
import life.zhiyuan.community.community.model.Question;

import java.util.List;


public interface CommentExtMapper {

    int incCommentCount(Comment comment);
    int deleteByCommentId(Comment comment);
    int deleteByCommentator(Comment comment);
    int deleteByParentId(Comment comment);
    Integer countBySearch(CommentQueryDTO commentQueryDTO);
    List<Comment> selectBySearch(CommentQueryDTO commentQueryDTO);
}