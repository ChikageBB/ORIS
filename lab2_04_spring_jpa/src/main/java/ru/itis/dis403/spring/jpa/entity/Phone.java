package ru.itis.dis403.spring.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Phone {
    @Id
    private Long id;

    @Column(name = "phone_number")
    private String number;

}
