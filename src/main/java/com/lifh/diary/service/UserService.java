package com.lifh.diary.service;
import com.lifh.diary.model.domain.Diary;
import com.lifh.diary.model.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import com.lifh.diary.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lifh.diary.model.dto.UserLoginDto;
import com.lifh.diary.model.dto.UserRegisterDto;
import com.lifh.diary.model.vo.UserVo;

import javax.naming.InsufficientResourcesException;
import java.util.List;


/**
* @author li_fe
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-05-26 08:20:20
*/
public interface UserService extends IService<User> {
    Integer register(UserRegisterDto registerDto);
    UserVo login(UserLoginDto loginDto, HttpServletRequest request);
    UserVo queryUser(UserDto userDto);
    List<Diary> getDiary(Integer userId);

    Integer saveDiary(Diary diary,HttpServletRequest request);

    Diary findSavedDiary(HttpServletRequest request);

    Integer publishDiary(Diary diary,HttpServletRequest request);

}
