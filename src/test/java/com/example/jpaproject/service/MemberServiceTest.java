package com.example.jpaproject.service;

import com.example.jpaproject.domain.Member;
import com.example.jpaproject.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
// 메모리 모드로

@RunWith(SpringRunner.class)
@SpringBootTest // spring을 테스트하려면 꼭 필요함
@Transactional // 기본적으로 롤백함
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;
    @Test
//    @Rollback(false) // 롤백 허용안함
    public void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("kim");

        Long saveId = memberService.join(member);

        // 영속성 컨텍스트에 있는 값들을 넣어줌 위에 Transaction 에서는 롤백해줌 이게 있으면 insert문이 강제로 나가고 롤백댐
        em.flush();
        // 같은 트랜잭션 안에서 같은 엔티티 id 값이 똑같으면 같은 영속성 컨테이너에서 하나로 통일 되기때문에
        // Insert문이 없음 ㅋㅋ JPA가 join을 하게 된다면 기본적으론 인서트가 안됨 커밋이 되게 중요함
        assertEquals(member, memberRepository.findOne(saveId));
    }

    // 이렇게 작성하면 오류가 뜨는 것이 정상이다라는 코드
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        // 여기서 예외가 터져야함
        memberService.join(member1);
        memberService.join(member2);
        // 너무 지저분함
//        try{
//            memberService.join(member2);
//        }catch (IllegalStateException e){
//            return;
//        }

        // 여기까지 오면 안되는데 오면 실패로 하는거
        fail("에외가 발생해야 한다.");
    }


}