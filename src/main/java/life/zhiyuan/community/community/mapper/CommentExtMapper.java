package life.zhiyuan.community.community.mapper;

import life.zhiyuan.community.community.model.Comment;
import life.zhiyuan.community.community.model.CommentExample;
import life.zhiyuan.community.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}