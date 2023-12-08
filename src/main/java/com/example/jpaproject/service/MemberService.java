package com.example.jpaproject.service;

import com.example.jpaproject.domain.Member;
import com.example.jpaproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// 더티 체킹 안해서 뭐 읽기 전용 트랜잭션이면 리소스 안쓴다 뭐 이런식
// 읽기 작업이 많을 시에는 이렇게 하는게 좋음
@Transactional(readOnly = true)
@RequiredArgsConstructor // final로 되어있는 에들만 독립성을 추가함
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

//    @Autowired 생성자가 하나 있으면 자동으로 오토 해줌 필드를 final로 안하면 컨파일 시점에 넣기 때문에 생성자 인젝션
//    public MemberService(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    // 따로 설정을하면 이게 제일 우선권을 얻음
    @Transactional
    public Long join(Member member){
        // 동시에 가입을 한다면 중복될 수 있으므로 유니크 제약조건을 거는것을 추천
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void  validateDuplicateMember(Member member){
        List<Member> findmembers = memberRepository.findByName(member.getName());
        if(!findmembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
