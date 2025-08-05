package com.ex.finance_service.enums;

import lombok.Getter;

/**
 * Перечисление типов транспорта, используемых для маршрутизации.
 * Каждый тип сопоставлен с названием, используемым в OpenRouteService API.
 */
@Getter
public enum TransportType {
    /**
     * Пешеход (ходьба).
     */
    FOOT("foot-walking"),

    /**
     * Велосипед.
     */
    BIKE("cycling-regular"),

    /**
     * Автомобиль.
     */
    CAR("driving-car");

    /**
     * Название профиля транспорта для OpenRouteService.
     */
    private final String openRouteName;

    /**
     * Конструктор с именем профиля OpenRouteService.
     *
     * @param openRouteName название профиля транспорта в OpenRouteService
     */
    TransportType(String openRouteName) {
        this.openRouteName = openRouteName;
    }
}