package com.accountbook.service;

import com.accountbook.domain.enums.EventType;
import com.accountbook.domain.repository.setting.CustomSettingRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventReadRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StatisticsService {
    private final UserRepository userRepository;
    private final EcoEventService ecoEventService;
    private final CustomSettingRepository settingRepository;

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
