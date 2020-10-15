package life.zhiyuan.community.community.service;

import life.zhiyuan.community.community.mapper.UserMapper;
import life.zhiyuan.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhuzhiwen by 2020/10/15 20:25
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
       User dbUser = userMapper.findByAccountId(user.getAccountId());
       if (dbUser==null){
           //插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else {
           dbUser.setGmtModified(System.currentTimeMillis());
           dbUser.setAvatarUrl(user.getAvatarUrl());
           dbUser.setName(user.getName());
           dbUser.setToken(user.getToken());
           userMapper.update(dbUser);
           //更新
       }
    }
}
