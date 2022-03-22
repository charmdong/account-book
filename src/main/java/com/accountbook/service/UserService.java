package com.accountbook.service;

import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.user.PasswordRequest;
import com.accountbook.dto.user.UserDto;
import com.accountbook.dto.user.UserRequest;
import com.accountbook.exception.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * UserService
 *
 * @author donggun
 * @since 2021/11/23
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * 아이디, 패스워드 기반 세션 성립
     * @param userId
     * @param password
     * @param request
     * @param response
     * @return
     * @throws RuntimeException
     */
    public UserDto login (String userId, String password, HttpServletRequest request, HttpServletResponse response) throws RuntimeException {

        // 1. id로 사용자 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND));

        // 2. password 비교
        if(!user.getPassword().equals(password)) {
            throw new UserException(UserExceptionCode.INVALID_PWD);
        }

        // 3. UID 생성 및 만료 기한 설정
        String uid = UUID.randomUUID().toString();
        LocalDateTime expireDate = LocalDateTime.now().plusDays(14);

        // 4. UID, expireDate 저장하기
        user.changeSessionInfo(uid, expireDate, request.getRemoteAddr());

        UserDto loginInfo = new UserDto(user);

        // 5. Session 생성
        HttpSession session = request.getSession();
        session.setAttribute("loginInfo", loginInfo);
        session.setMaxInactiveInterval(60 * 30);

        // 6. Cookie 생성
        Cookie cookie = new Cookie("UID", uid);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 14); // 2 weeks
        response.addCookie(cookie);

        return loginInfo;
    }

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

        return new UserDto(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND)));
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
     * @param request
     * @throws Exception
     */
    public void changePassword(String userId, PasswordRequest request) throws Exception {

        User user = userRepository.findById(userId).get();

        if (!user.checkPwdUpdate(request.getOriginPassword())) {
            throw new UpdateUserException(UserExceptionCode.PWD_UPDATE_FAIL);
        }

        user.changePassword(request.getNewPassword());
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

        return user.getPassword();
    }
}
