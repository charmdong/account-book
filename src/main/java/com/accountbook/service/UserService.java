package com.accountbook.service;

import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.user.UserDto;
import com.accountbook.dto.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 사용자 Service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     * @param request
     */
    public void addUser(UserRequest request) {

        User user = User.createUser(request);
        userRepository.addUser(user);
    }

    /**
     * 사용자 정보 조회
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public UserDto getUser(String userId) {

        Optional<User> user = userRepository.findById(userId);
        return new UserDto(user.get());
    }

    /**
     * 사용자 정보 수정
     * @param request
     */
    public void updateUser(String userId, UserRequest request) {

        User user = userRepository.findById(userId).get();
        user.changeUser(request);
    }

    /**
     * 사용자 탈퇴
     * @param userId
     */
    public void deleteUser(String userId) {

        userRepository.deleteById(userId);
    }

    /**
     * 사용자 아이디 찾기
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public String findUserId(UserRequest request) {

        User user = userRepository.findById(request.getId()).get();
        return user.getId();
    }

    /**
     * 사용자 패스워드 찾기
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public String findPassword(UserRequest request) {

        User user = userRepository.findById(request.getId()).get();
        return user.getPassword();
    }
}
