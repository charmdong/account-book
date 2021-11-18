package com.accountbook;

import com.accountbook.domain.entity.ComCategory;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.EventType;
import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.domain.repository.category.ComCategoryRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.category.CategoryDto;
import com.accountbook.dto.category.CategoryRequest;
import com.accountbook.dto.user.UserDto;
import com.accountbook.dto.user.UserRequest;
import com.accountbook.service.CategoryService;
import com.accountbook.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AccountBookApplicationTests {

	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ComCategoryRepository comCategoryRepository;

	@BeforeEach
	public void beforeTest() {
		System.out.println("######################");
		System.out.println("##### START TEST #####");
		System.out.println("######################");
	}

	@AfterEach
	public void afterTest() {
		System.out.println("######################");
		System.out.println("##### END   TEST #####");
		System.out.println("######################");
	}

	@Test
	@Rollback(value = false)
	@Ignore
	public void addUserTest () throws Exception {
		// given
		UserRequest request = new UserRequest();

		request.setId("test1");
		request.setPassword("password1");
		request.setName("Donggun");
		request.setEmail("chungdk1993@naver.com");
		request.setBirthDate(Year.of(1993).atMonth(11).atDay(17).atTime(14,59));

		// when
		userService.addUser(request);
		User findUser = userRepository.findById("test1").get();
		System.out.println("findUser = " + findUser);

		// then
		assertThat(findUser.getId()).isEqualTo("test1");
	}

	@Test
	@Ignore
	public void findUserTest () throws Exception {
	    // given
	    String userId = "test1";
		User user = userRepository.findById(userId).get();
		System.out.println("user = " + user);

		UserDto findUser = userService.getUser(userId);
		System.out.println("findUser = " + findUser);

		// when

	    // then
	}

	@Test
	@Rollback(value = false)
	@Ignore
	public void updateUserTest() throws Exception {
	    // given
	    UserRequest request = new UserRequest();
		System.out.println("request = " + request);
		request.setName("chungdk");

		userService.updateUser("test1", request);
	    // when
		User user = userRepository.findById("test1").get();

		// then
		assertThat(user.getName()).isEqualTo("chungdk");
	}

	@Test
	@Rollback(value = false)
	public void addUserCategoryTest() throws Exception {
		UserRequest request = new UserRequest();

		request.setId("test1");
		request.setPassword("password1");
		request.setName("Donggun");
		request.setEmail("chungdk1993@naver.com");
		request.setBirthDate(Year.of(1993).atMonth(11).atDay(17).atTime(14,59));

		// when
		userService.addUser(request);

	    // given
	    CategoryRequest categoryRequest = new CategoryRequest();

		categoryRequest.setUserId("test1");
		categoryRequest.setName("chicken");
		categoryRequest.setEventType(EventType.EXPENDITURE);
		categoryRequest.setUseYn(true);

	    // when
		categoryService.addUserCategory(categoryRequest);

		// then
	}

	@Test
	public void findUserCategoryTest () throws Exception {
	    // given
		List<CategoryDto> result = categoryService.getUserCategoryList("test1");
		for (CategoryDto categoryDto : result) {
			System.out.println("categoryDto = " + categoryDto);
		}
		// when

	    // then
	}
}
