package com.oceandiary.api.diary.service;

import com.oceandiary.api.common.exception.PermissionException;
import com.oceandiary.api.diary.dto.DiaryRequest;
import com.oceandiary.api.diary.dto.DiaryResponse;
import com.oceandiary.api.diary.entity.Stamp;
import com.oceandiary.api.diary.repository.StampRepository;
import com.oceandiary.api.user.entity.User;
import com.oceandiary.api.user.exception.UserNotFoundException;
import com.oceandiary.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {
    // TODO: 방 퇴장과 연결 후 삭제 예정
    private final UserRepository userRepository;
    private final StampRepository stampRepository;

    @Transactional
    public DiaryResponse.StampOnlyId createStamp(DiaryRequest.StampCreate request, User user){
        Stamp stamp = Stamp.create(user, request.getCategory(), request.getEnterTime(), request.getExitTime());
        System.out.println(stamp.toString());
        Stamp savedStamp = stampRepository.save(stamp);
        return DiaryResponse.StampOnlyId.build(savedStamp);
    }

    public List<DiaryResponse.GetStamp> getList(Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Stamp> stamps = stampRepository.findAllByUserOrderByIdDesc(findUser);
        return stamps.stream().map(stamp -> DiaryResponse.GetStamp.build(stamp)).collect(Collectors.toList());
    }

    public DiaryResponse.GetDiaryContents getDiaryContents(Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Stamp> stamps = stampRepository.findAllByUserOrderByIdDesc(findUser);
        return DiaryResponse.GetDiaryContents.build(findUser, stamps);
    }

    @Transactional
    public DiaryResponse.UserInfo updateUserInfo(Long userId, User user, DiaryRequest.UserInfo request){
        if(!userId.equals(user.getId())) throw new PermissionException();
        user.updateUserInfo(request);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return DiaryResponse.UserInfo.build(user);
    }

    @Transactional
    public DiaryResponse.UserOnlyId deleteUserInfo(Long userId, User user){
        if(!userId.equals(user.getId())) throw new PermissionException();
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return DiaryResponse.UserOnlyId.build(user);
    }

}
