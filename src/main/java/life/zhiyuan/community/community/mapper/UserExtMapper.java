package life.zhiyuan.community.community.mapper;


import life.zhiyuan.community.community.dto.CommentQueryDTO;
import life.zhiyuan.community.community.dto.UserQueryDTO;
import life.zhiyuan.community.community.model.Comment;
import life.zhiyuan.community.community.model.User;

import java.util.List;


public interface UserExtMapper {
    Integer countBySearch(UserQueryDTO userQueryDTO);
    List<User> selectBySearch(UserQueryDTO userQueryDTO);
}