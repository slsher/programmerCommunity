package life.zhiyuan.community.community.mapper;

import life.zhiyuan.community.community.dto.CommentDTO;
import life.zhiyuan.community.community.model.Comment;

import java.util.List;
import java.util.Map;


public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}