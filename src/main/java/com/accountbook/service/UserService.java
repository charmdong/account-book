package com.accountbook.service;

import com.accountbook.common.utils.CookieUtils;
import com.accountbook.common.utils.SessionUtils;
import com.accountbook.domain.entity.CustomSetting;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.setting.CustomSettingRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.user.*;
import com.accountbook.exception.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
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
    private final CustomSettingRepository settingRepository;

    /**
     * 아이디, 패스워드 기반 세션 성립
     *
     * @param userId
     * @param password
     * @param request
     * @param response
     * @return LoginInfo
     * @throws RuntimeException
     */
    public LoginInfo login (String userId, String password, HttpServletRequest request, HttpServletResponse response) {

        // 1. id로 사용자 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND));

        // 2. password 비교
        String encodedPassword = Base64.encodeBase64String(password.getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equals(encodedPassword)) {
            throw new UserException(UserExceptionCode.INVALID_PWD);
        }

        // 3. UID 생성 및 만료 기한 설정
        String uid = UUID.randomUUID().toString();
        LocalDateTime expireDate = LocalDateTime.now().plusDays(CookieUtils.PLUS_DAY);

        // 4. UID, expireDate 저장하기
        user.changeSessionInfo(uid, expireDate, request.getRemoteAddr());

        LoginInfo loginInfo = new LoginInfo(user);

        // 5. Session 생성
        HttpSession session = request.getSession();
        session.setAttribute(SessionUtils.LOGIN_INFO, loginInfo);
        session.setMaxInactiveInterval(SessionUtils.SESSION_TIME);

        // 6. Cookie 생성
        Cookie cookie = new Cookie(CookieUtils.LOGIN_CHECK_COOKIE, uid);
        cookie.setPath("/");
        cookie.setMaxAge(CookieUtils.COOKIE_MAX_AGE); // 2 weeks
        response.addCookie(cookie);

        return loginInfo;
    }

    /**
     * 회원가입
     *
     * @param request
     * @return UserDto
     * @throws Exception
     */
    public UserDto addUser (UserCreateRequest request) {

        String password = request.getPassword();
        password = Base64.encodeBase64String(password.getBytes(StandardCharsets.UTF_8));
        request.setPassword(password);

        // 사용자 엔티티 생성
        User user = User.createUser(request);

        // 사용자 설정 엔티티 생성
        CustomSetting setting = CustomSetting.createCustomSetting();
        setting.setUser(user);
        user.setSetting(setting);

        userRepository.addUser(user);

        if (settingRepository.findById(user.getId()).isEmpty()) {
            throw new InsertUserException(UserExceptionCode.SETTING_FAIL);
        }

        return new UserDto(userRepository.findById(user.getId()).orElseThrow(() -> new InsertUserException(UserExceptionCode.INSERT_FAIL)));
    }

    /**
     * 사용자 정보 조회
     *
     * @param userId
     * @return UserDto
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public UserDto getUser (String userId) {

        return new UserDto(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND)));
    }

    /**
     * 사용자 정보 수정
     *
     * @param request
     * @return UserDto
     * @throws Exception
     */
    public UserDto updateUser (String userId, UserUpdateRequest request) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND));
        user.changeUser(request);

        return getUser(userId);
    }

    /**
     * 사용자 비밀번호 변경
     *
     * @param userId
     * @param request
     * @throws Exception
     */
    public void changePassword (String userId, PasswordRequest request) {

        User user = userRepository.findById(userId).get();
        String originalPassword = Base64.encodeBase64String(request.getOriginPassword().getBytes(StandardCharsets.UTF_8));

        if (!user.checkPwdUpdate(originalPassword)) {
            throw new UpdateUserException(UserExceptionCode.PWD_UPDATE_FAIL);
        }

        String newPassword = Base64.encodeBase64String(request.getNewPassword().getBytes(StandardCharsets.UTF_8));
        user.changePassword(newPassword);
    }

    /**
     * 사용자 탈퇴
     *
     * @param userId
     * @return 삭제 여부
     * @throws Exception
     */
    public Boolean deleteUser (String userId) {

        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new DeleteUserException(UserExceptionCode.NOT_FOUND);
        }

        return true;
    }

    /**
     * 사용자 아이디 찾기
     *
     * @param request
     * @return userId
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public String findUserId (UserInfoRequest request) {

        User user = userRepository.findByNameAndEmail(request.getName(), request.getEmail()).orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND));

        return user.getId();
    }

    /**
     * 사용자 패스워드 찾기
     *
     * @param request
     * @return password
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public String findPassword (String userId, UserInfoRequest request) {

        User user = userRepository.findByIdAndEmail(userId, request.getEmail()).orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND));

        return user.getPassword();
    }

    /**
     * 사용자 설정 수정하기
     *
     * @param userId
     * @param request
     * @return
     * @throws Exception
     */
    public CustomSettingDto updateCustomSetting (String userId, UpdateSettingRequest request) {

        // userId에 해당하는 setting 찾기
        CustomSetting setting = settingRepository.findById(userId)
                .orElseThrow(() -> new SettingNotFoundException(UserExceptionCode.SETTING_NOT_FOUND));

        // setting 수정
        setting.updateSetting(request);

        return getCustomSetting(userId);
    }

    /**
     * 사용자 설정 조회하기
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public CustomSettingDto getCustomSetting (String userId) {

        // userId에 해당하는 setting 찾기
        CustomSetting setting = settingRepository.findById(userId)
                .orElseThrow(() -> new SettingNotFoundException(UserExceptionCode.SETTING_NOT_FOUND));

        return new CustomSettingDto(setting);
    }
}
