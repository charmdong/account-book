package com.accountbook.domain.repository.user;

import com.accountbook.domain.entity.User;
import com.accountbook.dto.user.UserRequest;
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

    @BeforeEach
    void before() throws Exception {
        UserRequest userRequest = new UserRequest();

        userRequest.setName("tester");
        userRequest.setBirthDate(LocalDateTime.now());
        userRequest.setEmail("test@gmail.com");
        userRequest.setId("user1");
        userRequest.setPassword("password");

        User user = User.createUser(userRequest);
        user.changeSessionInfo("uid1", LocalDateTime.now().plusDays(10));

        userRepository.addUser(user);
    }

    @Test
    @DisplayName("Update expireDate by UID")
    public void updateExpireDateTest() throws Exception {
        // given
        User findUser = userRepository.findById("user1").get();

        // when
        LocalDateTime now = LocalDateTime.now();
        userRepository.updateExpireDateByUid("uid1", now);
        User user = userRepository.findByUid("uid1");

        // then
        assertThat(user.getExpireDate().getMonth()).isEqualTo(now.getMonth());
    }

}