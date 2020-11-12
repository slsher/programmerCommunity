package life.zhiyuan.community.community.service;

import life.zhiyuan.community.community.dto.*;
import life.zhiyuan.community.community.enums.CommentTypeEnum;
import life.zhiyuan.community.community.enums.NotificationStatusEnum;
import life.zhiyuan.community.community.enums.NotificationTypeEnum;
import life.zhiyuan.community.community.exception.CustomizeErrorCode;
import life.zhiyuan.community.community.exception.CustomizeException;
import life.zhiyuan.community.community.mapper.*;
import life.zhiyuan.community.community.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhuzhiwen by 2020/10/17 19:33
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private CommentMapper commentService;
    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_NOT_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                //不存在
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            //回复问题
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);

            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);

            //创建评论通知
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLAY_COMMENT,question.getId());
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
            //创建问题通知
            createNotify(comment,question.getCreator(),commentator.getName() ,question.getTitle(),NotificationTypeEnum.REPLAY_QUESTION,question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver,  String notifierName,String outerTitle, NotificationTypeEnum notificationType,Long outerId) {
//        if (receiver.equals(comment.getCommentator())){
//            return;
//        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);

        //获取评论人并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);

        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换 comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
    public PaginationDTO AdminList(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andCommentatorEqualTo(userId);
        Integer totalCount = (int) commentService.countByExample(commentExample);
        Integer totalPage;
        if (totalCount % size == 0) {
            // 如果等于0
            totalPage = totalCount / size;
        } else {
            // 如果不等于0
            totalPage = totalCount / size + 1;
        }


        //没有页数的处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);
        // 计算页面公式 size*(page-1)
        Integer offset = size * (page - 1);
        //QuestionService里面查询Question 同时循环查询user 赋值
        CommentExample example = new CommentExample();
        example.createCriteria()
                .andCommentatorEqualTo(userId);
        List<Comment> questions = commentMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<CommentDTO> commentDTOList = new ArrayList<>();

        for (Comment comment : questions) {
            User user = userMapper.selectByPrimaryKey(comment.getCommentator());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(user);
            commentDTOList.add(commentDTO);
        }
        paginationDTO.setData(commentDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(String search,Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search= Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        PaginationDTO paginationDTO = new PaginationDTO();

        CommentQueryDTO questionQueryDTO = new CommentQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount =  commentExtMapper.countBySearch(questionQueryDTO);

        Integer totalPage;
        if (totalCount % size == 0) {
            // 如果等于0
            totalPage = totalCount / size;
        } else {
            // 如果不等于0
            totalPage = totalCount / size + 1;
        }


        //没有页数的处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);
        // 计算页面公式 size*(page-1)
        Integer offset = size * (page - 1);

        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Comment> comments = commentExtMapper.selectBySearch(questionQueryDTO);
        List<CommentDTO> commentDTOList = new ArrayList<>();

        for (Comment comment : comments) {
            User user = userMapper.selectByPrimaryKey(comment.getCommentator());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(user);
            commentDTOList.add(commentDTO);
        }

        paginationDTO.setData(commentDTOList);
        return paginationDTO;
    }

    public void deleteByCommentId(Long id){
       Comment comment=new Comment();
       comment.setId(id);
       commentExtMapper.deleteByCommentId(comment);
    }
    public void deleteByCommentator(Long id){
       Comment comment=new Comment();
       comment.setCommentator(id);
       commentExtMapper.deleteByCommentator(comment);
    }

    public void deleteByParentId(Long id) {
        Comment comment=new Comment();
        comment.setParentId(id);
        commentExtMapper.deleteByParentId(comment);
    }
}
