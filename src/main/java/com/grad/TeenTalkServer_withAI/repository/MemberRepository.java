package com.grad.TeenTalkServer_withAI.repository;

import com.grad.TeenTalkServer_withAI.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository      // 첫번째 인자 : 어떤 Entity인지, 두번째 인자 : 기본키 타입
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserName(String userName);
}
