package com.accountbook.service;

import com.accountbook.common.utils.CookieUtils;
import com.accountbook.common.utils.SessionUtils;
import com.accountbook.domain.entity.CustomSetting;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.ecoEvent.EcoEventRepository;
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
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private final EcoEventRepository ecoEventRepository;

    /**
     * 아이디, 패스워드 기반 세션 성립
     *
     * @param userId
     * @param password
     * @param request
     * @param response
     * @return LoginInfo
     */
    public LoginInfo login (String userId, String password, HttpServletRequest request, HttpServletResponse response) {

        // 1. id로 사용자 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND));

        // 2. password 비교
        String encodedPassword = Base64.encodeBase64String(password.getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equals(encodedPassword)) {
            throw new UserException(UserExceptionCode.INCORRECT_PWD);
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
     */
    public UserDto addUser (UserCreateRequest request) {

        // 1. 아이디 중복 확인
        String id = request.getId();
        if (userRepository.existById(id)) {
            throw new InsertUserException(UserExceptionCode.PRESENT_USER_ID);
        }

        // 2. 이메일 중복 확인
        String email = request.getEmail();
        if (StringUtils.hasText(email)) {
            if (userRepository.existByEmail(email)) {
                throw new InsertUserException(UserExceptionCode.PRESENT_USER_EMAIL);
            }
        }

        // 3. 패스워드 유효성 검사
        String password = request.getPassword();
        if (!isValidatePassword(password)) {
            throw new InsertUserException(UserExceptionCode.INVALID_PWD);
        }

        // 패스워드 암호화
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
     * 사용자 전체 정보 조회
     *
     * @return 사용자 전체 목록
     */
    @Transactional(readOnly = true)
    public List<UserDto> findAllUser() {

        return userRepository.findAllUser().stream().map(UserDto::new).collect(Collectors.toList());
    }

    /**
     * 사용자 정보 조회
     *
     * @param userId
     * @return UserDto
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
     */
    public void changePassword (String userId, PasswordRequest request) {

        User user = userRepository.findById(userId).get();
        String originalPassword = Base64.encodeBase64String(request.getOriginPassword().getBytes(StandardCharsets.UTF_8));

        if (!user.checkPwdUpdate(originalPassword)) {
            throw new UpdateUserException(UserExceptionCode.INCORRECT_PWD);
        }

        String newPassword = Base64.encodeBase64String(request.getNewPassword().getBytes(StandardCharsets.UTF_8));
        user.changePassword(newPassword);
    }

    /**
     * 사용자 탈퇴
     *
     * @param userId
     * @return 삭제 여부
     */
    public Boolean deleteUser (String userId) {

        try {
            userRepository.deleteById(userId);
            ecoEventRepository.deleteByUserId(userId);
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
     */
    @Transactional(readOnly = true)
    public String findPassword (String userId, UserInfoRequest request) {

        User user = userRepository.findByIdAndEmail(userId, request.getEmail()).orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND));
        String pwd = new String(java.util.Base64.getDecoder().decode(user.getPassword()));
        StringBuilder maskPwd = new StringBuilder();

        for (int index = 0; index < pwd.length(); index++) {
            if (2 <= index && index < pwd.length() - 2) {
                maskPwd.append("*");
            }
            else maskPwd.append(pwd.charAt(index));
        }

        return maskPwd.toString();
    }

    /**
     * 사용자 설정 수정하기
     *
     * @param userId
     * @param request
     * @return
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
     */
    public CustomSettingDto getCustomSetting (String userId) {

        // userId에 해당하는 setting 찾기
        CustomSetting setting = settingRepository.findById(userId)
                .orElseThrow(() -> new SettingNotFoundException(UserExceptionCode.SETTING_NOT_FOUND));

        return new CustomSettingDto(setting);
    }

    /**
     * 패스워드 유효성 검사
     *
     * @param password
     * @return
     */
    private Boolean isValidatePassword (String password) {

        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";
        return Pattern.matches(pattern, password);
    }
}
