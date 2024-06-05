package com.lifh.diary.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lifh.diary.common.BaseResponse;
import com.lifh.diary.common.ErrorCode;
import com.lifh.diary.common.ResultUtils;
import com.lifh.diary.exception.BusinessException;
import com.lifh.diary.model.domain.Comment;
import com.lifh.diary.model.domain.Diary;
import com.lifh.diary.model.domain.User;
import com.lifh.diary.model.dto.UserDto;
import com.lifh.diary.model.dto.UserLoginDto;
import com.lifh.diary.model.dto.UserRegisterDto;
import com.lifh.diary.model.vo.UserVo;
import com.lifh.diary.service.CommentService;
import com.lifh.diary.service.DiaryService;
import com.lifh.diary.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.lifh.diary.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("api/user")
@Slf4j
public class ApiController {
    @Resource
    UserService userService;
    @Resource
    DiaryService diaryService;

    @Resource
    CommentService commentService;

    @PostMapping("/register")
    public BaseResponse<Integer> userRegister(@RequestBody UserRegisterDto userRegisterDto){
        Integer result = userService.register(userRegisterDto);
        if(result == 1){
            return ResultUtils.success(result);
        }
        else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
    @PostMapping("/login")
    public BaseResponse<UserVo> userLogin(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request){
        UserVo userLoginVo = userService.login(userLoginDto,request);
        if(userLoginVo != null){
            return ResultUtils.success(userLoginVo);
        }
        else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
    @GetMapping("/current")
    public BaseResponse<UserVo> userCurrent(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserVo currentUser = (UserVo) userObj;
        if (currentUser != null){
            String account = currentUser.getAccount();
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", account);
            User user = userService.getOne(queryWrapper);
            UserVo loginUser = new UserVo();
            BeanUtils.copyProperties(user,loginUser);
            return ResultUtils.success(loginUser);
        }
        else{
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
    }
    @GetMapping("/allDiary")
    public BaseResponse<?> allDiary(){
        List<Diary> diaries = diaryService.allDiary();
        return ResultUtils.success(diaries);
    }
    @GetMapping("/queryUser")
    public BaseResponse<?> queryUser(UserDto userDto){
        UserVo userVo = userService.queryUser(userDto);
        if(userVo == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"不存在该用户");
        }
        else return ResultUtils.success(userVo);
    }
    @GetMapping("/diary")
    public BaseResponse<List<Diary>> getUserDiary(Integer userId){
        List<Diary> diary = userService.getDiary(userId);
        return ResultUtils.success(diary);
    }
    @PostMapping("/saveDiary")
    public BaseResponse<Integer> saveDiary(Diary diary,HttpServletRequest request){
        Integer result = userService.saveDiary(diary, request);
        if(result == 1){
            return ResultUtils.success(1);
        }
        else{
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
    @GetMapping("/findSavedDiary")
    public BaseResponse<Diary> findSavedDiary(HttpServletRequest request){
        if(request == null) {
            throw  new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Diary diary = userService.findSavedDiary(request);
        return ResultUtils.success(diary);
    }
    @PostMapping("/publishDiary")
    public BaseResponse<Integer> publishDiary(Diary diary,HttpServletRequest request){
        Integer result = userService.publishDiary(diary, request);
        if(result == 1){
            return ResultUtils.success(1);
        }
        else{
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
    @PostMapping("/comment")
    public BaseResponse<Integer> comment(Comment comment, HttpServletRequest request) {
        Integer result = commentService.comment(comment, request);
        if(result == 1){
            return ResultUtils.success(result);
        }
        else throw  new BusinessException(ErrorCode.SYSTEM_ERROR);
    }

    @GetMapping("getAllComments")
    public BaseResponse<List<Comment>> getAllComments(Integer diaryId) {
        List<Comment> allComments = commentService.getAllComments(diaryId);
        return ResultUtils.success(allComments);
    }
}
