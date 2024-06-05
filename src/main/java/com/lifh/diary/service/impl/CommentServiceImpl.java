package com.lifh.diary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lifh.diary.common.ErrorCode;
import com.lifh.diary.exception.BusinessException;
import com.lifh.diary.mapper.DiaryMapper;
import com.lifh.diary.model.domain.Comment;
import com.lifh.diary.model.domain.Diary;
import com.lifh.diary.model.vo.UserVo;
import com.lifh.diary.service.CommentService;
import com.lifh.diary.mapper.CommentMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.lifh.diary.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author li_fe
* @description 针对表【comment(评论)】的数据库操作Service实现
* @createDate 2024-05-26 08:22:51
*/

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{
    @Resource
    CommentMapper commentMapper;
    @Resource
    DiaryMapper diaryMapper;
    @Override
    public Integer comment(Comment comment,HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserVo currentUser = (UserVo) userObj;
        if (currentUser != null){
            QueryWrapper<Diary> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("diary_id", comment.getDiaryId());
            if(!diaryMapper.exists(queryWrapper)){
                throw new BusinessException(ErrorCode.NULL_ERROR,"该日记不存在，或已被删除");
            }
            if(comment.getContent().isBlank()){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"评论内容不能为空");
            }
            commentMapper.insert(comment);
            return 1;
        }
        else{
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
    }

    @Override
    public List<Comment> getAllComments(Integer dairyId) {
        QueryWrapper<Diary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("diary_id",dairyId);
        boolean exists = diaryMapper.exists(queryWrapper);
        if(exists){
            QueryWrapper<Comment> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("diary_id",dairyId);
            queryWrapper1.orderByDesc("create_time");
            return commentMapper.selectList(queryWrapper1);
        }
        else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该日记不存在，或已被删除");
        }
    }
}




