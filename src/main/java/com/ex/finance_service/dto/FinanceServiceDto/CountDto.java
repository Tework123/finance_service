package com.ex.finance_service.dto.FinanceServiceDto;

import com.ex.finance_service.enums.CountStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CountDto {

    private UUID id;

    private int count;

    private UUID routeEventId;

    private CountStatus countStatus;

    private LocalDateTime createDt;

}
