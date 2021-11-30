package com.accountbook.service;

import com.accountbook.domain.enums.AssetType;
import com.accountbook.domain.enums.EventType;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventRequest;
import com.accountbook.dto.category.CategoryDto;
import com.accountbook.dto.category.CategoryRequest;
import com.accountbook.dto.user.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
class EcoEventServiceTest {

    @Autowired
    private EcoEventService ecoEventService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    //금융 이벤트 등록
    @Test
    public void enrollEcoEvnet(){
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
    public void updateEcoEvent(){
        //given
        EcoEventRequest ecoEventRequest = getEcoEventRequest();
        ecoEventService.enrollEcoEvents(ecoEventRequest);
        List<EcoEventDto> ecoEventDtos = ecoEventService.getAllEcoEvent();
        EcoEventDto ecoEventDto = ecoEventDtos.get(ecoEventDtos.size()-1);

        //when
        EcoEventRequest ecoEventUpdateRequest = new EcoEventRequest(null,null,40000L, AssetType.CASH,null);
        ecoEventService.updateEcoEvents(ecoEventUpdateRequest, ecoEventDto.getSeq());

        //then
        EcoEventDto ecoEventUpdateDto = ecoEventService.getOneEcoEvent(ecoEventDto.getSeq());
        assertEquals(ecoEventUpdateDto.getAmount(), 40000L);
        assertEquals(ecoEventUpdateDto.getAssetType(), AssetType.CASH);
    }

    //금융 이벤트 삭제
    //@Test(expected = Not)
    @Test
    @Rollback(false)
    public void deleteEcoEvnet(){
        //given
        EcoEventRequest ecoEventRequest = getEcoEventRequest();
        ecoEventService.enrollEcoEvents(ecoEventRequest);
        List<EcoEventDto> ecoEventDtos = ecoEventService.getAllEcoEvent();
        EcoEventDto ecoEventDto = ecoEventDtos.get(ecoEventDtos.size()-1);

        //when
        ecoEventService.deleteEcoEvents(ecoEventDto.getSeq());

        //then
        ecoEventService.getOneEcoEvent(ecoEventDto.getSeq());
    }

    public EcoEventRequest getEcoEventRequest(){
        String userId = getUser();
        Long categorySeq = getCategory(userId);

        EcoEventRequest ecoEventRequest = new EcoEventRequest(EventType.EXPENDITURE, LocalDateTime.now(), 30000L, AssetType.CASH, categorySeq);
        return ecoEventRequest;
    }

    //테스트용 User 생성
    private String getUser() {
        UserRequest request = new UserRequest();
        String userId = "rlfehd1";

        request.setId(userId);
        request.setPassword("ghdrlfehed123!");
        request.setName("홍길동");
        request.setEmail("ghdrlfehd@gmail.com");
        request.setBirthDate(Year.of(2000).atMonth(12).atDay(31).atTime(14,03));

        userService.addUser(request);

        return userId;
    }

    //테스트용 Category 생성
    private Long getCategory(String userId) {

        CategoryRequest categoryRequest = new CategoryRequest();

        categoryRequest.setUserId("rlfehd1");
        categoryRequest.setName("chicken");
        categoryRequest.setEventType(EventType.EXPENDITURE);
        categoryRequest.setUseYn(true);

        categoryService.addCategory(categoryRequest);
        List<CategoryDto> categoryDtoList = categoryService.getCategoryListByUser(userId);

        return categoryDtoList.get(categoryDtoList.size()-1).getSeq();
    }
}