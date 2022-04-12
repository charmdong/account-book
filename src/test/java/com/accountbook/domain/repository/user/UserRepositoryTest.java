package com.accountbook.domain.repository.user;

import com.accountbook.domain.entity.CustomSetting;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.setting.CustomSettingRepository;
import com.accountbook.dto.user.UserCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomSettingRepository settingRepository;

    @BeforeEach
    void before() throws Exception {
        UserCreateRequest userRequest = new UserCreateRequest();

        userRequest.setName("tester");
        userRequest.setBirthDate(LocalDateTime.now());
        userRequest.setEmail("test@gmail.com");
        userRequest.setId("user1");
        userRequest.setPassword("password");

        User user = User.createUser(userRequest);
        user.changeSessionInfo("uid1", LocalDateTime.now().plusDays(10), "127.0.0.1");

        userRepository.addUser(user);
    }

    @Test
    @DisplayName("Update expireDate by UID")
    public void updateExpireDateTest() throws Exception {
        // given
        User findUser = userRepository.findById("user1").get();

        // when
        LocalDateTime now = LocalDateTime.now();
        userRepository.updateExpireDateByToken("uid1", now);
        User user = userRepository.findByToken("uid1");

        // then
        assertThat(user.getExpireDate().getMonth()).isEqualTo(now.getMonth());
    }

    @Test
    @DisplayName("사용자 설정 정보 조회 테스트")
    public void userSettingTest() throws Exception {
        User user = userRepository.findById("user1").get();

        CustomSetting setting = CustomSetting.createCustomSetting();

        setting.setUser(user);
        user.setSetting(setting);

        settingRepository.addSetting(setting);

        User result = userRepository.findById("user1").get();
        CustomSetting findSetting = settingRepository.findById("user1").get();

        assertThat(result.getSetting()).isEqualTo(findSetting);
    }

}