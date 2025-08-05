package com.ex.finance_service.dto.FinanceServiceDto;

import com.ex.finance_service.enums.RouteEventStatus;
import com.ex.finance_service.enums.WeatherStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendRouteEventsRequestDto {
    private UUID courierId;
    private UUID orderId;
    List<RouteEventDto> routeEvents;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RouteEventDto {
        private UUID routeEventId;
        private LocationPointDto locationPoint;
        private RouteEventStatus routeEventStatus;
        private WeatherStatus weatherStatus;
        private LocalDateTime timestamp;
        private LocalDateTime timeCreate;
        private String message;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LocationPointDto {
        private UUID locationPointId;
        private double longitude;
        private double latitude;
        private LocalDateTime timestamp;
        private LocalDateTime timeCreate;
    }
}
