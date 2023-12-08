package com.example.jpaproject.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    // 일대일 환경에서는 foreign key를 아무곳이나 두는데 주로 엑세스가 많이되는 곳에 한다.
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // EnumType.ORDINAL 숫자로 나옴
    // 꼭 String으로 사용해야함
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
