package com.accountbook.service;

import com.accountbook.domain.enums.EventType;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventRequest;
import com.accountbook.dto.user.UserCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class EcoEventServiceTest {

    @Autowired
    private EcoEventService ecoEventService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    //금융 이벤트 조회 By User
    @Test
    public void 금융이벤트_조회_by_User() throws Exception{
        //given
        String userId = getUser();

        //when
        List<EcoEventDto> ecoEventList = ecoEventService.getEcoEventByUser(userId);

        //then
        Optional<EcoEventDto> anyDto = ecoEventList.stream()
                                                   .filter(dto -> !userId.equals(dto.getUserId()))
                                                   .findAny();

        assertEquals(Optional.empty(), anyDto);
    }

    //금융 이벤트 등록
    @Test
    public void 금융이벤트_등록() throws Exception{
        //given
        int prevSize = ecoEventService.getAllEcoEvent().size();
        EcoEventRequest ecoEventRequest = getEcoEventRequest();
        //when
        ecoEventService.enrollEcoEvents(ecoEventRequest);
        //then
        int currentSize = ecoEventService.getAllEcoEvent().size();
        assertEquals(prevSize+1, currentSize);
    }

    //금융 이벤트 수정
    @Test
    public void 금융이벤트_수정() throws Exception{
        //given
        EcoEventRequest ecoEventRequest = getEcoEventRequest();
        Long ecoEventSeq =  ecoEventService.enrollEcoEvents(ecoEventRequest);
        String userId = getUser();


        //when
        EcoEventRequest ecoEventUpdateRequest = new EcoEventRequest(userId,EventType.INCOME, now(),40000L,ecoEventRequest.getCategorySeq());
        ecoEventService.updateEcoEvents(ecoEventUpdateRequest, ecoEventSeq);

        //then
        EcoEventDto ecoEventUpdateDto = ecoEventService.getOneEcoEvent(ecoEventSeq);
        assertEquals(ecoEventUpdateDto.getAmount(), 40000L);
    }

    //금융 이벤트 삭제
    @Test
    public void deleteEcoEvnet() throws Exception{
        //given
        Long ecoEventSeq = ecoEventService.enrollEcoEvents(getEcoEventRequest());

        //when
        boolean result = ecoEventService.deleteEcoEvents(ecoEventSeq);

        //then
        assertTrue(result);
    }
    //테스트용 EcoEvent 생성
    public EcoEventRequest getEcoEventRequest() throws Exception{
        String userId = getUser();
        Long categorySeq = getCategory(userId);

        EcoEventRequest ecoEventRequest = new EcoEventRequest(userId,EventType.EXPENDITURE, now(),30000L,categorySeq);
        return ecoEventRequest;
    }

    //테스트용 User 생성
    private String getUser() throws Exception{

        UserCreateRequest request = new UserCreateRequest();
        String userId = "gildong1";

        if(userService.getUser(userId) == null) {
            request.setId(userId);
            request.setPassword("ghdrlfehed123!");
            request.setName("홍길동");
            request.setEmail("ghdrlfehd@gmail.com");
            request.setBirthDate(Year.of(2000).atMonth(12).atDay(31).atTime(14, 03));

            userService.addUser(request);
        }

        return userId;
    }

    //테스트용 Category 생성
    private Long getCategory(String userId) throws Exception{
        return categoryService.getCategoryList().get(0).getSeq();
    }
}