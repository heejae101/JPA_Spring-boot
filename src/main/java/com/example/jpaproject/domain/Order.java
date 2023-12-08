package com.example.jpaproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // ManyToOne은 N+1 문제가 있고 기본으로 이거로 되어 있어서 LAZY로 한다.
    @ManyToOne(fetch = LAZY)
    // 연관관계 주인
    // Foreign key 설정
    @JoinColumn(name = "member_id")
    private  Member member;

    // order 영속성 설정할 때 퍼시스트를 각각해줘야하는데 자동으로 해줌
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문의 상태 [ORDER ,CANCEL]

    // == 연관관계 메서드 양방향일때 한 코드로 줄임
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}
