package com.accountbook.common.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class schedulerSample {
    
    // ms(밀리세컨드) 기준 1000 = 1 sec
    @Scheduled(fixedDelay = 10000)
    // 왼쪽부터 "초 분 시 일 월 년"
    // @Scheduled(cron = "0 0 0 * * *")
    private void scheduleTest() throws InterruptedException {
      log.info(" >> 스케쥴러 테스트");  
    }
}
