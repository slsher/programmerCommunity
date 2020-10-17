package life.zhiyuan.community.community.controller;

import life.zhiyuan.community.community.dto.CommentDTO;
import life.zhiyuan.community.community.mapper.CommentMapper;
import life.zhiyuan.community.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhuzhiwen by 2020/10/16 22:42
 */
@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(420l);
        comment.setLikeCount(0l);
        commentMapper.insert(comment);
        return null;
    }
}
