package com.accountbook.service;

import com.accountbook.aop.LogTraceAspect;
import com.accountbook.domain.enums.DisplayOption;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.user.CustomSettingDto;
import com.accountbook.dto.user.UpdateSettingRequest;
import com.accountbook.dto.user.UserCreateRequest;
import com.accountbook.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@Import(LogTraceAspect.class)
@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("사용자 등록 테스트")
    public void addUserTest() throws Exception {
        // given
        UserDto createdUser = getUserDto();
        CustomSettingDto setting = createdUser.getSettingDto();

        // then
        assertThat(createdUser.getId()).isEqualTo("user1");
        assertThat(setting.getInitDay()).isEqualTo(1);
    }

    private UserDto getUserDto () throws Exception {
        UserCreateRequest request = new UserCreateRequest();

        request.setId("user1");
        request.setPassword("password1");
        request.setName("test1");
        request.setEmail("test@naver.com");
        request.setBirthDate(LocalDateTime.now());

        // when
        UserDto createdUser = userService.addUser(request);
        return createdUser;
    }

    @Test
    @DisplayName("사용자 조회 테스트")
    public void getUserTest() throws Exception {
        // given
        UserDto createdUser = getUserDto();

        // when
        UserDto user = userService.getUser(createdUser.getId());

        // then
        assertThat(user.getId()).isEqualTo("user1");
        assertThat(user.getSettingDto().getDisplayOption()).isEqualTo(DisplayOption.AMOUNT);
    }

    @Test
    @DisplayName("사용자 설정 정보 수정 테스트")
    public void updateCustomSettingTest() throws Exception {
        // given
        UserDto user = getUserDto();
        CustomSettingDto settingDto = user.getSettingDto();

        log.info("before={}", settingDto);

        // when
        UpdateSettingRequest request = new UpdateSettingRequest();
        request.setInitDay(20);

        CustomSettingDto updatedSettingDto = userService.updateCustomSetting(user.getId(), request);
        log.info("after={}", updatedSettingDto);
        // then
    }

    @Test
    public void checkDuplication () {

        Boolean resultById = userRepository.existById("member2");
        Boolean resultByEmail = userRepository.existByEmail("test@naver.com");

        assertThat(resultById).isTrue();
        assertThat(resultByEmail).isTrue();
    }

    @Test
    public void validatePasswordTest() {
        String password1 = "1!hell";
        String password2 = "hello123";
        String password3 = "hello!@#";
        String password4 = "hello12!@";

        assertThat(isValidatePassword(password1)).isFalse();
        assertThat(isValidatePassword(password2)).isFalse();
        assertThat(isValidatePassword(password3)).isFalse();
        assertThat(isValidatePassword(password4)).isTrue();
    }

    private Boolean isValidatePassword (String password) {

        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";
        return Pattern.matches(pattern, password);
    }
}