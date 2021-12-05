package com.accountbook.service;

import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.user.UserDto;
import com.accountbook.dto.user.UserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void addUserTest () throws Exception {
        // given
        UserRequest request = addUserSample();

    }

    private UserRequest addUserSample () {
        UserRequest request = new UserRequest();

        request.setId("test1");
        request.setPassword("password1");
        request.setName("Donggun");
        request.setEmail("chungdk1993@naver.com");
        request.setBirthDate(Year.of(1993).atMonth(11).atDay(17).atTime(14,59));

        UserDto userDto = userService.addUser(request);
        System.out.println("userDto = " + userDto);

        return request;
    }

    @Test
    public void deleteUserTest () throws Exception {

        UserRequest request = addUserSample();

        User findUser = userRepository.findById(request.getId()).get();

        userRepository.deleteById(request.getId());

        System.out.println(userRepository.findById(findUser.getId()).isEmpty());
    }
}