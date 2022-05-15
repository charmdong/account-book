package com.accountbook.common.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import com.accountbook.domain.enums.EventType;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import com.accountbook.dto.EcoEvent.EcoEventReadRequest;
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
@RequiredArgsConstructor
public class UserStatistics {

    private final UserService userService;

    // 왼쪽부터 "초 분 시 일 월 년"
    @Scheduled(cron = "0 0 0 * * *")
    private void scheduleTest() throws InterruptedException {
        userService.getUserStatistics();
    }
}
