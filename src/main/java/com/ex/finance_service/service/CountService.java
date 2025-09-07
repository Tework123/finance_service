package com.ex.finance_service.service;

import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.finance_service.entity.Count;
import com.ex.finance_service.enums.CountStatus;
import com.ex.finance_service.repository.CountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountService {
    private final CountRepository countRepository;

//    а вот допустим я создал запись и статус new,
//    а далее с этим статусом началась работа, а потом другой консумер упал ...надо его откатить...
    public void save(SendRouteEventsRequestDto.RouteEventDto dto) {
        Count newCount = Count.builder()
                .count(1)
                .routeEventId(dto.getRouteEventId())
                .countStatus(CountStatus.NEW)
                .build();

        countRepository.save(newCount);
        log.info("Finance_service добавил count в бд  {}", newCount.getCreateDt());
    }

    public void cancel(UUID routeEventId) {
        Count count = countRepository.findByRouteEventId(routeEventId);

        if (count != null) {
            count.setCountStatus(CountStatus.CANCEL);
            countRepository.save(count);
            log.info("Finance_service отменил count в бд  {}", routeEventId);
        }
        log.info("Finance_service не нашел count в бд чтобы его отменить  {}", routeEventId);

    }
}
