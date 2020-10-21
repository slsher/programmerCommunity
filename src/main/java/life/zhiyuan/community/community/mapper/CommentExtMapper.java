package life.zhiyuan.community.community.mapper;

import life.zhiyuan.community.community.model.Comment;


public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}