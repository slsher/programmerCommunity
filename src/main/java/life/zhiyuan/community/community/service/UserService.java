package life.zhiyuan.community.community.service;

import life.zhiyuan.community.community.dto.*;
import life.zhiyuan.community.community.mapper.UserExtMapper;
import life.zhiyuan.community.community.mapper.UserMapper;
import life.zhiyuan.community.community.model.Comment;
import life.zhiyuan.community.community.model.User;
import life.zhiyuan.community.community.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhuzhiwen by 2020/10/15 20:25
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtMapper userExtMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
       if (users.size()==0){
           //插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else {
           User dbUser=users.get(0);
           User updateUser = new User();
           updateUser.setGmtModified(System.currentTimeMillis());
           updateUser.setAvatarUrl(user.getAvatarUrl());
           updateUser.setName(user.getName());
           updateUser.setToken(user.getToken());

           UserExample example = new UserExample();
           example.createCriteria()
                   .andIdEqualTo(dbUser.getId());
           userMapper.updateByExampleSelective(updateUser, example);
           //更新
       }
    }

    public PaginationDTO list(String search, Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search= Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        PaginationDTO paginationDTO = new PaginationDTO();

        UserQueryDTO userQueryDTO = new UserQueryDTO();
        userQueryDTO.setSearch(search);
        Integer totalCount =  userExtMapper.countBySearch(userQueryDTO);

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

        userQueryDTO.setSize(size);
        userQueryDTO.setPage(offset);
        List<User> users = userExtMapper.selectBySearch(userQueryDTO);
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user1 : users) {
            User user = userMapper.selectByPrimaryKey(user1.getId());
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user1, userDTO);
            userDTO.setUser(user);
            userDTOList.add(userDTO);
        }

        paginationDTO.setData(userDTOList);
        return paginationDTO;
    }
}
