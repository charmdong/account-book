package com.accountbook.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.accountbook.common.utils.CookieUtils;
import com.accountbook.common.utils.SessionUtils;
import com.accountbook.domain.entity.CustomSetting;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.EventType;
import com.accountbook.domain.repository.setting.CustomSettingRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventReadRequest;
import com.accountbook.dto.user.CustomSettingDto;
import com.accountbook.dto.user.LoginInfo;
import com.accountbook.dto.user.PasswordRequest;
import com.accountbook.dto.user.UpdateSettingRequest;
import com.accountbook.dto.user.UserCreateRequest;
import com.accountbook.dto.user.UserDto;
import com.accountbook.dto.user.UserInfoRequest;
import com.accountbook.dto.user.UserUpdateRequest;
import com.accountbook.exception.user.DeleteUserException;
import com.accountbook.exception.user.InsertUserException;
import com.accountbook.exception.user.SettingNotFoundException;
import com.accountbook.exception.user.UpdateUserException;
import com.accountbook.exception.user.UserException;
import com.accountbook.exception.user.UserExceptionCode;
import com.accountbook.exception.user.UserNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private final EcoEventService ecoEventService;
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
    public LoginInfo login (String userId, String password, HttpServletRequest request, HttpServletResponse response) throws RuntimeException {

        // 1. id로 사용자 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND));

        // 2. password 비교
        if (!user.getPassword().equals(password)) {
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
    public UserDto addUser (UserCreateRequest request) throws Exception {

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

        Optional<User> createdUser = userRepository.findById(user.getId());

        if (createdUser.isEmpty()) {
            throw new InsertUserException(UserExceptionCode.INSERT_FAIL);
        }

        return new UserDto(createdUser.get());
    }

    /**
     * 사용자 정보 조회
     *
     * @param userId
     * @return UserDto
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public UserDto getUser (String userId) throws Exception {

        return new UserDto(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(UserExceptionCode.NOT_FOUND)));
    }

    /**
     * 사용자 정보 수정
     *
     * @param request
     * @return UserDto
     * @throws Exception
     */
    public UserDto updateUser (String userId, UserUpdateRequest request) throws Exception {

        User user = userRepository.findById(userId).get();

        if (user == null) {
            throw new UpdateUserException(UserExceptionCode.UPDATE_FAIL);
        }

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
    public void changePassword (String userId, PasswordRequest request) throws Exception {

        User user = userRepository.findById(userId).get();

        if (!user.checkPwdUpdate(request.getOriginPassword())) {
            throw new UpdateUserException(UserExceptionCode.PWD_UPDATE_FAIL);
        }

        user.changePassword(request.getNewPassword());
    }

    /**
     * 사용자 탈퇴
     *
     * @param userId
     * @return 삭제 여부
     * @throws Exception
     */
    public Boolean deleteUser (String userId) throws Exception {

        userRepository.deleteById(userId);

        if (userRepository.findById(userId).isPresent()) {
            throw new DeleteUserException(UserExceptionCode.DELETE_FAIL);
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
    public String findUserId (UserInfoRequest request) throws Exception {

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
    public String findPassword (String userId, UserInfoRequest request) throws Exception {

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
    public CustomSettingDto updateCustomSetting (String userId, UpdateSettingRequest request) throws Exception {

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
    public CustomSettingDto getCustomSetting (String userId) throws Exception {

        // userId에 해당하는 setting 찾기
        CustomSetting setting = settingRepository.findById(userId)
                .orElseThrow(() -> new SettingNotFoundException(UserExceptionCode.SETTING_NOT_FOUND));

        return new CustomSettingDto(setting);
    }

    /**
     * 사용자 통계 메서드
     */
    public void getUserStatistics () {
        var nowDate = LocalDateTime.now();
        var nowYear = nowDate.getYear();
        var nowMonth = nowDate.getMonth();
        var nowDay = nowDate.getDayOfMonth();

        try {
            // Get user list
            var users = userRepository.findAll();
            for (var user : users) {
                // Get user init day
                int initDay = user.getSetting().getInitDay();

                // 초기화 날짜 check
                if (initDay == nowDay) {

                    // make start date
                    LocalDateTime startDate = LocalDateTime.of(nowYear, nowMonth.minus(1), initDay, 0, 0, 0);

                    // make end date
                    LocalDateTime endDate = startDate.plusMonths(1);

                    // Get expenditure ecoEvent
                    EcoEventReadRequest expenditureEcoEventRequest = new EcoEventReadRequest(user.getId(), EventType.EXPENDITURE, startDate, endDate);
                    List<EcoEventDto> expenditureEcoEvent = ecoEventService.getAllEcoEvnetByEventTypeAndUseDate(expenditureEcoEventRequest);

                    // Get income ecoEvent
                    EcoEventReadRequest incomeEcoEventRequest = new EcoEventReadRequest(user.getId(), EventType.INCOME, startDate, endDate);
                    List<EcoEventDto> incomeEcoEvnet = ecoEventService.getAllEcoEvnetByEventTypeAndUseDate(incomeEcoEventRequest);

                    // 지난 달 수입, 지출 금액
                    Long prevIncome = 0L;
                    Long prevExpenditure = 0L;

                    for (var event : expenditureEcoEvent) {
                        prevExpenditure += event.getAmount();
                    }

                    for (var event : incomeEcoEvnet) {
                        prevIncome += event.getAmount();
                    }

                    user.updatePrevInfo(prevIncome, prevExpenditure);
                    userRepository.addUser(user);
                    log.info(" >> Succes {} update statistics scheduler.", user.toString());
                }
            }
        } catch (Exception e) {
            log.info(" >> Failed to user statistics scheduler: {}", e);
        }
    }
}
