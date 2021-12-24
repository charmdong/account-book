package com.accountbook.service;

import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.user.UserDto;
import com.accountbook.dto.user.UserRequest;
import com.accountbook.exception.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @return UserDto
     * @throws Exception
     */
    public UserDto addUser(UserRequest request) throws Exception {

        User user = User.createUser(request);
        userRepository.addUser(user);

        if(userRepository.findById(user.getId()).isEmpty()) {
            throw new InsertUserException(UserExceptionCode.INSERT_FAIL);
        }

        return getUser(user.getId());
    }

    /**
     * 사용자 정보 조회
     * @param userId
     * @return UserDto
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public UserDto getUser(String userId) throws Exception {

        return new UserDto(
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND))
        );
    }

    /**
     * 사용자 정보 수정
     * @param request
     * @return UserDto
     * @throws Exception
     */
    public UserDto updateUser(String userId, UserRequest request) throws Exception {

        User user = userRepository.findById(userId).get();

        if (user == null || user.checkInfoUpdate(request)) {
            throw new UpdateUserException(UserExceptionCode.UPDATE_FAIL);
        }

        user.changeUser(request);

        userRepository.flush();

        return getUser(userId);
    }

    /**
     * 사용자 비밀번호 변경
     * @param userId
     * @param password
     * @throws Exception
     */
    public void changePassword(String userId, String password) throws Exception {

        User user = userRepository.findById(userId).get();

        if (!user.checkPwdUpdate(password)) {
            throw new UpdateUserException(UserExceptionCode.PWD_UPDATE_FAIL);
        }

        user.changePassword(password);
    }

    /**
     * 사용자 탈퇴
     * @param userId
     * @return 삭제 여부
     * @throws Exception
     */
    public Boolean deleteUser(String userId) throws Exception {

        userRepository.deleteById(userId);

        if(userRepository.findById(userId).isPresent()) {
            throw new DeleteUserException(UserExceptionCode.DELETE_FAIL);
        }

        return true;
    }

    /**
     * 사용자 아이디 찾기
     * @param request
     * @return userId
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public String findUserId(UserRequest request) throws Exception {

        User user = userRepository
                .findByNameAndEmail(request.getName(), request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND));

        return user.getId();
    }

    /**
     * 사용자 패스워드 찾기
     * @param request
     * @return password
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public String findPassword(UserRequest request) throws Exception {

        User user = userRepository
                .findByIdAndEmail(request.getId(), request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND));

        return user.getId();
    }
}
