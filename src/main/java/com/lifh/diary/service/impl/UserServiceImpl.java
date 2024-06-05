package com.lifh.diary.service.impl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lifh.diary.common.ResultUtils;
import com.lifh.diary.mapper.DiaryMapper;
import com.lifh.diary.model.domain.Diary;
import com.lifh.diary.model.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lifh.diary.common.ErrorCode;
import com.lifh.diary.exception.BusinessException;
import com.lifh.diary.mapper.UserMapper;
import com.lifh.diary.model.domain.User;
import com.lifh.diary.model.dto.UserLoginDto;
import com.lifh.diary.model.dto.UserRegisterDto;
import com.lifh.diary.model.vo.UserVo;
import com.lifh.diary.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.lifh.diary.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author li_fe
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-05-26 08:20:20
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    UserMapper userMapper;

    @Resource
    DiaryMapper diaryMapper;
    @Override
    public Integer register(UserRegisterDto registerDto) {
        // 进行校验
        // 长度、特殊字符等校验前端完成，这里只校验账户、用户名是否已经存在
        String account = registerDto.getAccount();
        String username = registerDto.getUsername();
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("account",account);
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("username",username);
        if(userMapper.exists(queryWrapper1))
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户已经存在！");
        }
        if(userMapper.exists(queryWrapper2)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名已经存在！");
        }
        User user = new User();
        BeanUtils.copyProperties(registerDto,user);
        userMapper.insert(user);
        Diary diary = new Diary();
        diary.setAuthorId(user.getUserId());
        diary.setTitle("");
        diary.setContent("");
        diary.setType(2);
        diaryMapper.insert(diary);
        return 1;
    }

    @Override
    public UserVo login(UserLoginDto loginDto, HttpServletRequest request) {
        String account = loginDto.getAccount();
        String password = loginDto.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",account);
        queryWrapper.eq("password",password);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号密码错误！");
        }
        if(user.getStatus() == 1){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该账号已冻结！");
        }
        if(user.getIsDelete() == 1){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号密码错误！");
        }
        UserVo userLoginVo = new UserVo();
        BeanUtils.copyProperties(user,userLoginVo);
        request.getSession().setAttribute(USER_LOGIN_STATE,userLoginVo);
        return userLoginVo;
    }

    @Override
    public UserVo queryUser(UserDto userDto) {
        QueryWrapper<User> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("user_id", userDto.getUserId());
        queryWrapper.or().eq("account", userDto.getAccount());
        //queryWrapper.eq("account",queryUserDto.getAccount());
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该用户不存在！");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return userVo;
    }

    @Override
    public List<Diary> getDiary(Integer userId) {
        QueryWrapper<Diary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id",userId);
        List<Diary> diaries = diaryMapper.selectList(queryWrapper);
        return diaries;
    }

    @Override
    public Integer saveDiary(Diary diary,HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserVo currentUser = (UserVo) userObj;
        if (currentUser != null){
            QueryWrapper<Diary> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("author_id",currentUser.getUserId());
            queryWrapper.eq("type",2);
            Diary diary1 = diaryMapper.selectOne(queryWrapper);
            if(diary1 != null) {
                diary1.setTitle(diary.getTitle());
                diary1.setContent(diary.getContent());
                diaryMapper.updateById(diary1);
            }
            else {
                diary.setAuthorId(currentUser.getUserId());
                diary.setType(2);
                diaryMapper.insert(diary);
            }
            return 1;
        }
        else{
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }

    }

    @Override
    public Diary findSavedDiary(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserVo currentUser = (UserVo) userObj;
        if (currentUser != null){
            QueryWrapper<Diary> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("author_id",currentUser.getUserId());
            queryWrapper.eq("type",2);
            Diary diary = diaryMapper.selectOne(queryWrapper);
            return diary;
        }
        else throw  new BusinessException(ErrorCode.NOT_LOGIN);
    }

    @Override
    public Integer publishDiary(Diary diary, HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserVo currentUser = (UserVo) userObj;
        if (currentUser != null){
            if(diary != null &&  !diary.getTitle().isBlank() && !diary.getContent().isBlank()) {
                diary.setAuthorId(currentUser.getUserId());
                diaryMapper.insert(diary);
                UpdateWrapper<Diary> updateWapper = new UpdateWrapper<>();
                updateWapper.eq("author_id",currentUser.getUserId());
                updateWapper.eq("type",2);
                Diary diary1 = new Diary();
                diary1.setTitle("");
                diary1.setContent("");
                diaryMapper.update(diary1,updateWapper);
                return 1;
            }
            else {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"标题和内容不能为空");
            }
        }
        else{
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
    }
}




