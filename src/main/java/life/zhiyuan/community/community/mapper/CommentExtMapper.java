package life.zhiyuan.community.community.mapper;


import life.zhiyuan.community.community.model.Comment;


public interface CommentExtMapper {

    int incCommentCount(Comment comment);
    int deleteByCommentId(Comment comment);
    int deleteByCommentator(Comment comment);
    int deleteByParentId(Comment comment);
}