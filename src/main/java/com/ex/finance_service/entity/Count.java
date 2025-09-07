package com.ex.finance_service.entity;


import com.ex.finance_service.enums.CountStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "count")
public class Count {
    @Id
    @GeneratedValue
    private UUID id;

    private int count;

    @Column(name = "route_event_id")
    private UUID routeEventId;

    @Enumerated(EnumType.STRING)
    @Column(name = "count_status")
    private CountStatus countStatus;

    @CreationTimestamp
    private LocalDateTime createDt;

}
