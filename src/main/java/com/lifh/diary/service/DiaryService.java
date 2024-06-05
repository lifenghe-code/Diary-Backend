package com.lifh.diary.service;

import com.lifh.diary.model.domain.Diary;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
* @author li_fe
* @description 针对表【dairy(日记)】的数据库操作Service
* @createDate 2024-05-26 08:22:12
*/
public interface DiaryService extends IService<Diary> {
    List<Diary> allDiary();
}
