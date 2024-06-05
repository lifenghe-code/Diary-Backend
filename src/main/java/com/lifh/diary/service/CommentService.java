package com.lifh.diary.service;

import com.lifh.diary.model.domain.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author li_fe
* @description 针对表【comment(评论)】的数据库操作Service
* @createDate 2024-05-26 08:22:51
*/
public interface CommentService extends IService<Comment> {
    Integer comment(Comment comment,HttpServletRequest request);
    List<Comment> getAllComments(Integer dairyId);
}
