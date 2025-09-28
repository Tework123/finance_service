package com.ex.finance_service.service;

import com.ex.finance_service.dto.FinanceServiceDto.CountDto;
import com.ex.finance_service.dto.FinanceServiceDto.SendRouteEventsRequestDto;
import com.ex.finance_service.entity.Count;
import com.ex.finance_service.enums.CountStatus;
import com.ex.finance_service.repository.CountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountService {
    private final CountRepository countRepository;

    //    а вот допустим я создал запись и статус new,
//    а далее с этим статусом началась работа, а потом другой консумер упал ...надо его откатить...
    public void save(CountDto dto) {
        Count newCount = Count.builder()
                .id(dto.getId())
                .count(dto.getCount())
                .routeEventId(dto.getRouteEventId())
                .countStatus(CountStatus.NEW)
                .build();

        countRepository.save(newCount);
        log.info("Finance_service добавил count в бд, id = {}, date = {}", dto.getId(), newCount.getCreateDt());
    }

    public void cancel(UUID id) {
        Optional<Count> countOpt = countRepository.findById(id);

        if (countOpt.isPresent()) {
            Count count = countOpt.get();
            count.setCountStatus(CountStatus.CANCEL);
            countRepository.save(count);
            log.info("Finance_service отменил count в бд, id = {}", id);
        } else {
            log.info("Finance_service не нашел count в бд чтобы его отменить  {}", id);
        }

    }

//    @Transactional
    public void create() {
        Count newCount = Count.builder()
                .count(1)
                .countStatus(CountStatus.NEW)
                .routeEventId(UUID.randomUUID())
                .build();

        Count count = countRepository.save(newCount);
        log.info("Route_service добавил count в бд. id = {}, date = {}", count.getId(), newCount.getCreateDt());
    }
}
