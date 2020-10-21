package life.zhiyuan.community.community.controller;

import life.zhiyuan.community.community.dto.CommentCreateDTO;
import life.zhiyuan.community.community.dto.CommentDTO;
import life.zhiyuan.community.community.dto.ResultDTO;
import life.zhiyuan.community.community.enums.CommentTypeEnum;
import life.zhiyuan.community.community.exception.CustomizeErrorCode;
import life.zhiyuan.community.community.model.Comment;
import life.zhiyuan.community.community.model.User;
import life.zhiyuan.community.community.service.CommentService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhuzhiwen by 2020/10/16 22:42
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {


        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment,user);
        return ResultDTO.okof();
    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id")Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okof(commentDTOS);
    }
}
