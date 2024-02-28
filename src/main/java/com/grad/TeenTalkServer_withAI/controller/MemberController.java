package com.grad.TeenTalkServer_withAI.controller;

import com.grad.TeenTalkServer_withAI.dto.member.MemberControlDTO;
import com.grad.TeenTalkServer_withAI.dto.member.MemberUpdateDTO;
import com.grad.TeenTalkServer_withAI.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor        // MemberService에 대한 멤버를 사용 가능
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    /**
     * * 로그인 : /member/login
     * 조회 결과, 로그인 성공 -> 멤버 객체
     * 로그인 실패 -> null
     */
    @GetMapping("/login")
    public ResponseEntity<MemberControlDTO> login(@ModelAttribute MemberControlDTO memberControlDTO) {

        MemberControlDTO loginResult = memberService.login(memberControlDTO);         // 반환 결과 : 멤버 객체 or NULL

        if (loginResult != null) {      // login 성공
            log.info("[Controller][new_login] SUCCESS   -  UserName:{}", memberControlDTO.getUserName());
            return ResponseEntity.ok(loginResult);
        }
        else {                       // login 실패     - 유저가 없거나, 비밀번호 틀림
            log.info("[Controller][new_login] FAIL");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    /**
     * 회원 가입 : /member/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberControlDTO> signup(@ModelAttribute MemberControlDTO memberControlDTO) {
        MemberControlDTO result = memberService.save(memberControlDTO);

        if(result == null){          // 중복된 회원 있음
            log.info("[Controller][new_signup] DUPlICATE   -  UserName:{}, UserId:{}", result.getUserName(),result.getId());
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);     // 409 Conflict는 리소스의 충돌을 의미하는 상태코드
        }
        else{
            log.info("[Controller][new_signup] SUCCESS   -  UserName:{}, UserId:{}", memberControlDTO.getUserName(), memberControlDTO.getId());
            return ResponseEntity.ok(memberControlDTO);
        }
    }

    /**
     * 회원 아이디 변경 : /member/change/name
     */
    @PostMapping("/change/name")
    public ResponseEntity<MemberUpdateDTO> changeName(@ModelAttribute MemberUpdateDTO memberUpdateDTO) {
        MemberUpdateDTO result = memberService.changeName(memberUpdateDTO);

        if(result == null){          // 중복된 회원 있음
            log.info("[Controller][changeName] DUPlICATE");
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);     // 409 Conflict는 리소스의 충돌을 의미하는 상태코드
        }
        else{
            log.info("[Controller][changeName] SUCCESS   -  UserName:{}, UserId:{}, NewName:{}", memberUpdateDTO.getUserName(), memberUpdateDTO.getId(),memberUpdateDTO.getNewName());
            return ResponseEntity.ok(memberUpdateDTO);
        }
    }

}
