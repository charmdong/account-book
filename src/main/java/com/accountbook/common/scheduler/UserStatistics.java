package com.accountbook.common.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import com.accountbook.domain.enums.EventType;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.service.EcoEventService;
import com.accountbook.service.UserService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원의 지난달 수입, 지출 계산 스케쥴러
 */
@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class UserStatistics {

    private final UserService userService;
    private final EcoEventService ecoEventService;

    // ms(밀리세컨드) 기준 1000 = 1 sec
    @Scheduled(fixedDelay = 30000)
    // 왼쪽부터 "초 분 시 일 월 년"
    // @Scheduled(cron = "0 0 0 * * *")
    private void scheduleTest() throws InterruptedException {
        
        var nowDate = LocalDateTime.now();
        var nowYear = nowDate.getYear();
        var nowMonth = nowDate.getMonth();
        var nowDay = nowDate.getDayOfMonth();

        try {
            // Get user list
            var users = userService.getUsers();
            users.forEach(user -> {

                // Get user init day
                int initDay = user.getSettingDto().getInitDay();
                
                // 초기화 날짜 check
                if (initDay == nowDay) {

                    // make start date
                    LocalDateTime startDate = LocalDateTime.of(nowYear, nowMonth.minus(1), initDay, 0, 0, 0);

                    // make end date
                    LocalDateTime endDate = startDate.plusMonths(1);

                    // Get ecoEvent about user
                    List<EcoEventDto> ecoEvnet = ecoEventService.getEcoEventByDate(user.getId(), startDate, endDate);

                    // 지난 달 수입, 지출 금액
                    Long prevIncome = 0L;
                    Long prevExpenditure = 0L;

                    for(var event : ecoEvnet) {
                        // 수입
                        if (event.getEventType().equals(EventType.INCOME)) {
                            prevIncome += event.getAmount();    
                        } 
                        // 지출
                        else {
                            prevExpenditure += event.getAmount();
                        }
                    }

                    user.setPrevIncome(prevIncome);
                    user.setPrevExpenditure(prevExpenditure);
                    log.info(" >> Succes {} update statistics scheduler.", user.getId());
                }
            });

        } catch (Exception e) {
            log.info(" >> Failed to user statistics scheduler: {}", e);
        }
    }
}
