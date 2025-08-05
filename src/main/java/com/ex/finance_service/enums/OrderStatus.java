package com.ex.finance_service.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum OrderStatus {
    /**
     * Заказ создан клиентом.
     */
    CREATED("Created"),

    /**
     * Ожидает подтверждения рестораном.
     */
    PENDING("Pending"),

    /**
     * Подтвержден рестораном.
     */
    CONFIRMED("Confirmed"),

    /**
     * Заказ готовится в ресторане.
     */
    PREPARING("Preparing"),

    /**
     * Заказ готов и ждёт курьера.
     */
    READY_FOR_PICKUP("Ready for Pickup"),

    /**
     * Курьер забрал заказ, доставляет.
     */
    DELIVERING("Delivering"),

    /**
     * Заказ доставлен.
     */
    DELIVERED("Delivered"),

    /**
     * Заказ отменён рестораном.
     */
    CANCELLED_BY_RESTAURANT("Cancelled by Restaurant"),

    /**
     * Заказ отменён системой.
     */
    CANCELLED_BY_SYSTEM("Cancelled by System"),

    /**
     * Заказ отменён клиентом.
     */
    CANCELLED_BY_CUSTOMER("Cancelled by Customer");

    private final String value;

    /**
     * Конструктор для статуса заказа с его строковым значением.
     *
     * @param value строковое представление статуса
     */
    OrderStatus(String value) {
        this.value = value;
    }

    /**
     * Возвращает Optional с OrderStatus, соответствующим переданной строке,
     * игнорируя регистр.
     *
     * @param status строковое представление статуса
     * @return Optional с найденным OrderStatus или пустой, если нет совпадений
     */
    public static Optional<OrderStatus> from(String status) {
        return Arrays.stream(values())
                .filter(s -> s.value.equalsIgnoreCase(status))
                .findFirst();
    }
}