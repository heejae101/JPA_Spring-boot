package com.example.jpaproject.service;

import com.example.jpaproject.domain.Address;
import com.example.jpaproject.domain.Member;
import com.example.jpaproject.domain.Order;
import com.example.jpaproject.domain.OrderStatus;
import com.example.jpaproject.domain.item.Book;
import com.example.jpaproject.exception.NotEnoughStockException;
import com.example.jpaproject.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    // 순수하게 단위테스트하는 것이 제일 좋은 방법이지만 통합으로 테스트진행한다.

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember();
        Book book = createBook("ㅇㅇ", 10000, 10);

        int orderCount = 2;
        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.",1,getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.",10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 줄어야한다.",8,book.getStockQuatity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Book item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        // when
        orderService.order(member.getId(),item.getId(),orderCount);

        // then
        fail("재고 수향 부족 예외가 발행해야 한다.");
    }
    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Book item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.",10,item.getStockQuatity());
    }


    private Book createBook(String name, int orderPrice, int stockQuatity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(orderPrice);
        book.setStockQuatity(stockQuatity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가", "123-123"));
        em.persist(member);
        return member;
    }

}