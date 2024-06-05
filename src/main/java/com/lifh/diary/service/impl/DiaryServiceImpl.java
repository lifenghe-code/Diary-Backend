package com.lifh.diary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lifh.diary.model.domain.Diary;
import com.lifh.diary.service.DiaryService;
import com.lifh.diary.mapper.DiaryMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author li_fe
* @description 针对表【dairy(日记)】的数据库操作Service实现
* @createDate 2024-05-26 08:22:12
*/
@Service
public class DiaryServiceImpl extends ServiceImpl<DiaryMapper, Diary>
    implements DiaryService {
    @Resource
    DiaryMapper diaryMapper;

    @Override
    public List<Diary> allDiary() {
        QueryWrapper<Diary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",0).or().eq("type",1);
        queryWrapper.orderByDesc("publish_time");
        List<Diary> diaries = diaryMapper.selectList(queryWrapper);
        return diaries;
    }
}




