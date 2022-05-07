package com.accountbook.service;
import com.accountbook.domain.enums.EventType;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventReadRequest;
import com.accountbook.dto.EcoEvent.EcoEventRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class EcoEventServiceTest {

    @Autowired
    private EcoEventService ecoEventService;

    @Autowired
    private EntityManager em;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Test
    @Rollback(value = false)
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

    @Test
    public void 금융이벤트_수정() throws Exception{
        //given
        EcoEventRequest ecoEventRequest = getEcoEventRequest();
        List<EcoEventDto> ecoEventDtos = ecoEventService.getEcoEventByUser(ecoEventRequest.getUserId());

        if(ecoEventDtos.isEmpty()){
        } else{
            ecoEventRequest.setAmount(90000L);
            ecoEventRequest.setEventType(EventType.INCOME);

            //when
            ecoEventService.updateEcoEvents(ecoEventRequest,ecoEventDtos.get(0).getSeq());

            //then
            em.flush();
            List<EcoEventDto> ecoEventByUserDtos = ecoEventService.getEcoEventByUser(ecoEventRequest.getUserId());
            assertEquals(90000L, ecoEventByUserDtos.get(0).getAmount());
        }
    }

    @Test
    public void 금융이벤트_삭제() throws Exception{
        //given
        EcoEventRequest ecoEventRequest = getEcoEventRequest();
        List<EcoEventDto> ecoEventDtos = ecoEventService.getEcoEventByUser(ecoEventRequest.getUserId());

        if(ecoEventDtos.isEmpty()){
        } else{
            //when
            boolean check = ecoEventService.deleteEcoEvents(ecoEventDtos.get(0).getSeq());

            //then
            assertEquals(true,check);
        }
    }

    @Test
    public void 금융이벤트_통계() throws Exception{
        //given
        EcoEventReadRequest ecoEventReadRequest = getEcoEventReadRequest();
        List<EcoEventDto> ecoEventDtos = ecoEventService.getEcoEventByUser(ecoEventReadRequest.getUserId());

        if(ecoEventDtos.isEmpty()){
        } else{
            //when
            ecoEventService.summarizeEcoEvents(ecoEventReadRequest);
        }
    }

    //테스트용 EcoEvent 생성
    public EcoEventRequest getEcoEventRequest() throws Exception{

        // UserServiceTest.addUserTest() 실행 후 실행
        String userId = "user1";

        //insert into CATEGORY values (3, now(), now(), 0, 'EXPENDITURE', '애완동물');
        Long categorySeq = 3L;
        EcoEventRequest ecoEventRequest = new EcoEventRequest(userId,EventType.EXPENDITURE, now(),50000L,categorySeq);

        return ecoEventRequest;
    }

    //테스트용 Read EcoEvent 생성
    public EcoEventReadRequest getEcoEventReadRequest() throws Exception{

        // UserServiceTest.addUserTest() 실행 후 실행
        String userId = "user1";

        //insert into CATEGORY values (2, now(), now(), 0, 'EXPENDITURE', '식비');
        Long categorySeq = 2L;
        EcoEventReadRequest ecoEventRequest  = new EcoEventReadRequest(userId, EventType.EXPENDITURE, now(), now().plusDays(1));

       return ecoEventRequest;
    }
}