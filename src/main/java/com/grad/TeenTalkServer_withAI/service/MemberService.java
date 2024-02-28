package com.grad.TeenTalkServer_withAI.service;

import com.grad.TeenTalkServer_withAI.domain.Member;
import com.grad.TeenTalkServer_withAI.dto.member.MemberControlDTO;
import com.grad.TeenTalkServer_withAI.dto.member.MemberUpdateDTO;
import com.grad.TeenTalkServer_withAI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberControlDTO login(MemberControlDTO loginMember) {        //entity객체는 service에서만

        // MemberName으로 로그인 가능 여부 판별
        Member byUserName = memberRepository.findByUserName(loginMember.getUserName());

        if (byUserName != null) {       // 해당 유저 이름이 존재
            if (byUserName.getUserPw().equals(loginMember.getUserPw())) {
                //비밀번호 일치
                loginMember.setId(byUserName.getId());
                return loginMember;
            } else {
                // 비밀번호 불일치
                return null;
            }
        } else {
            // 조회 결과가 없다
            return null;
        }
    }

    public MemberControlDTO save(MemberControlDTO memberControlDTO) {
        // repsitory의 save 메서드 호출
        boolean validateResult = validateDuplicateMember(memberControlDTO.getUserName());

        if(!validateResult){       // 중복 회원 있어서 넘김.
            return null;
        }
        Member newMember = new Member(memberControlDTO.getUserName(), memberControlDTO.getUserPw());
        memberRepository.save(newMember);
        memberControlDTO.setId(newMember.getId());
        return memberControlDTO;
    }

    public MemberUpdateDTO changeName(MemberUpdateDTO memberUpdateDTO) {
        boolean validateResult = validateDuplicateMember(memberUpdateDTO.getNewName());

        if(!validateResult){       // 중복 회원 있어서 넘김.
            return null;
        }
        Member member = memberRepository.findByUserName(memberUpdateDTO.getUserName());
        member.updateName(memberUpdateDTO.getNewName());
        memberRepository.save(member);

        log.info("[Service][changeName]   Changed Name:{}, UserId:{}, beforeName:{}", member.getUserName(),member.getId(), memberUpdateDTO.getUserName());

        return memberUpdateDTO;
    }

    private boolean validateDuplicateMember(String name) {
        Member findMember = memberRepository.findByUserName(name);
        if(findMember != null){         // 중복된 회원 있음
            return false;
        }
        return true;
    }
}