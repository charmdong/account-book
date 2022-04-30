package com.accountbook.dto.user;

import com.accountbook.domain.enums.EventType;
import com.accountbook.domain.enums.RequestType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePrevInfoRequest {

    private EventType eventType;    // 수입, 지출 구분
    private RequestType requestType;// Insert, Update, Delete 구분
    private Long beforeAmount;      // Update 시 이전 금액
    private Long afterAmount;       // 금액

}
