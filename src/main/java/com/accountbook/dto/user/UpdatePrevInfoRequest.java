package com.accountbook.dto.user;

import com.accountbook.domain.enums.EventType;
import lombok.Data;

@Data
public class UpdatePrevInfoRequest {

    private EventType eventType;    // 수입, 지출 구분
    private int requestType;        // Insert, Update, Delete 구분
    private Long beforeAmount;      // Update 시 이전 금액
    private Long afterAmount;       // 금액

}
