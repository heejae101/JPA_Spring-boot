package com.example.jpaproject.repository;

import com.example.jpaproject.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    // @PersistenceContext spring boot에선 그냥 Autowired로 쓸 수 있는데 그럼 final로 해서 RequireAugment로 처리 가능
    // 인젝션 <-- 알아보기
    private final EntityManager em;

    public void save(Member member){
        // 영속성 퍼시스트
        // 영속성 컨테스트 id값이 키가 됩니다.
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class , id);
    }

    public List<Member> findAll(){
        // JPQL 엔티티 객체에 대한 기본편
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
