package com.grad.TeenTalkServer_withAI.controller;

import com.grad.TeenTalkServer_withAI.dto.friend.FriendListResponse;
import com.grad.TeenTalkServer_withAI.dto.friend.FriendRequestResponse;
import com.grad.TeenTalkServer_withAI.dto.member.MemberDTO;
import com.grad.TeenTalkServer_withAI.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor        // MemberService에 대한 멤버를 사용 가능
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;

    /**
     ** 친구추가 : /friend/follow/{userName}
     * @return -  성공하면 (요청한 멤버 이름, 요청받은 멤버 이름) 객체 반환   - FriendRequestResponse
     *            이미 팔로우한 경우 - 409 CONFLICT
     */
    @PostMapping("/follow/{userName}")
    public ResponseEntity<FriendRequestResponse> followFriend(@ModelAttribute MemberDTO memberDTO, @PathVariable String userName) {
        if(userName.equals(memberDTO.getUserName())){
            log.info("[Controller][followFriend]  can't follow myself  -  userName : {}", userName);
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        ResponseEntity<FriendRequestResponse> result = friendService.follow(memberDTO,userName);

        if(HttpStatus.FORBIDDEN.isSameCodeAs(result.getStatusCode())){
            log.info("[Controller][followFriend]  there is no member with the name  -  userName : {}", userName);
        }
        else if(HttpStatus.CONFLICT.isSameCodeAs(result.getStatusCode())){         // 이미 팔로우 한 상태
            log.info("[Controller][followFriend]  already followed  -  from:{} , to:{}", memberDTO.getUserName(), userName);
        }
        else if(HttpStatus.OK.isSameCodeAs(result.getStatusCode())) {
            log.info("[Controller][followFriend]  SUCCESS  - from:{} , to:{}", memberDTO.getUserName(), userName);
        }

        return result;
    }

    /**
     ** 친구 목록 : /friend/follow/list/{userName}
     * @return -  FriendListResponse - 친구 이름 리스트
     */
    @GetMapping("/follow/list/{userName}")
    public ResponseEntity<List<FriendListResponse>> followerList(@PathVariable String userName){
        List<FriendListResponse> result = friendService.loadFollowerList(userName);

        log.info("[Controller][followerList] userName : {} , friends_Num : {}", userName, result.size());

        return ResponseEntity.ok(result);
    }

}
