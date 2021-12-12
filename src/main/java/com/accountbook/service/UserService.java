package com.accountbook.service;

import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.user.UserDto;
import com.accountbook.dto.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * UserService
 *
 * @author donggun
 * @since 2021/11/23
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
    public UserDto addUser(UserRequest request) {

        User user = User.createUser(request);
        userRepository.addUser(user);

        return getUser(user.getId());
    }

    /**
     * 사용자 정보 조회
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public UserDto getUser(String userId) {

        return new UserDto(userRepository.findById(userId).orElseThrow(NoSuchElementException::new));
    }

    /**
     * 사용자 정보 수정
     * @param request
     */
    public UserDto updateUser(String userId, UserRequest request) {

        User user = userRepository.findById(userId).get();
        user.changeUser(request);

        userRepository.flush();

        return getUser(userId);
    }

    /**
     * 사용자 비밀번호 변경
     * @param userId
     * @param password
     */
    public void changePassword(String userId, String password) {

        User user = userRepository.findById(userId).get();
        user.changePassword(password);
    }

    /**
     * 사용자 탈퇴
     * @param userId
     * @return 삭제 여부
     */
    public Boolean deleteUser(String userId) {

        userRepository.deleteById(userId);

        return userRepository.findById(userId).isEmpty();
    }

    /**
     * 사용자 아이디 찾기
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public String findUserId(UserRequest request) {

        return userRepository.findByNameAndEmail(request.getName(), request.getEmail()).get().getId();
    }

    /**
     * 사용자 패스워드 찾기
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public String findPassword(UserRequest request) {

        return userRepository.findByIdAndEmail(request.getId(), request.getEmail()).get().getPassword();
    }
}
