//package com.example.jpaproject;
//
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MemberRepositoryTest {
//    @Autowired MemberRepository memberRepository;
//
//    @Test
//    @Transactional
//    @Rollback
//    public void testMember() throws Exception{
//        // ddl - create를 설정했기때문에 자동으로 테이블 생성해줌
//        // Test에 트랜잭션 어노테이션이 있으면 롤백함 @Rollback(false)라는 옵션으로 끌 수 있음
//
//        // given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        // when
//        Long savedID = memberRepository.save(member);
//        Member findMember = memberRepository.find(savedID);
//
//        // then
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member);
//        // findMember == member 같은 트랜잭션 안에서 저장하고 조회하면 영속성 컨텍스트가 똑같음 그래서 같은 엔티티
//        // 1차 캐시? 라는 곳에서 그냥 가져옴 select 문을 안함
//    }
//}