package com.example.jpaproject.service;

import com.example.jpaproject.domain.Delivery;
import com.example.jpaproject.domain.Member;
import com.example.jpaproject.domain.Order;
import com.example.jpaproject.domain.OrderItem;
import com.example.jpaproject.domain.item.Item;
import com.example.jpaproject.repository.ItemRepository;
import com.example.jpaproject.repository.MemberRepository;
import com.example.jpaproject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 라이프 사이클 생각해야함
        // 다른것은 참조하는 곳이 없는데 여기선 많이 쓰기 때문에 cascade ALL 설정했음 자주 참조하는 것 은 별도 레파지토리 만들고 퍼시스트 하는게 좋음
        // order랑 delivery 두개를 cascade 설정 했음
        // 그런데 협업해서 하는 거기 때문에 기존 설계대로 넣는게 아닌 new연산자로 생성해서 넣는 경우도 있는데 그걸 방지하기 위해선
        // protected 생성자를 생성해 준다.
        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장 cascade ALL
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소 로직을 축소할 수 있음 JPA 강점 ㅋㅋㅋ 개꿀 더티체킹 하면서 데이터베이스에 업데이트 쿼리가 날라감
        order.cancel();
    }

    // 검색

}
