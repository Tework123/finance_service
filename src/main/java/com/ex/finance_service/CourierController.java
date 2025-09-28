package com.ex.finance_service;


import com.ex.finance_service.service.CountService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для управления курьерами.
 */
@RestController
@RequestMapping("/courier")
@AllArgsConstructor
public class CourierController {
    private final CountService countService;


    /**
     * Создает нового курьера.
     *
     * @return пустой ответ с HTTP 200 OK
     */
    @PostMapping("/create")
    public ResponseEntity<Void> createCourier() {
        countService.create();
        return ResponseEntity.ok().build();
    }

}
