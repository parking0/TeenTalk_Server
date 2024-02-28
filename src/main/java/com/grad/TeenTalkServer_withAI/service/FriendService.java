package com.grad.TeenTalkServer_withAI.service;

import com.grad.TeenTalkServer_withAI.domain.Friend;
import com.grad.TeenTalkServer_withAI.domain.Member;
import com.grad.TeenTalkServer_withAI.dto.friend.FriendListResponse;
import com.grad.TeenTalkServer_withAI.dto.friend.FriendRequestResponse;
import com.grad.TeenTalkServer_withAI.dto.member.MemberDTO;
import com.grad.TeenTalkServer_withAI.repository.FriendRepository;
import com.grad.TeenTalkServer_withAI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {

    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public ResponseEntity<FriendRequestResponse> follow(MemberDTO requestMember, String memberName){

        if(loadMember(memberName) == null){     // 존재하지 않은 아이디에 요청
            log.info("[Service][follow] the friend doesn't exist  - friendName : {}", memberName);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        Member member1 = loadMember(requestMember.getUserName());
        Member member2 = loadMember(memberName);

        Optional<Friend> are_we_friend = friendRepository.findByFromUserAndToUser(member1, member2);


        if(are_we_friend.isEmpty()){        // 아직 팔로우를 안 했으면
            Friend friends = new Friend();
            friends.setFromUser(member1);
            friends.setToUser(member2);
            friendRepository.save(friends);         // DB에 저장

            member1.addFriend(member2.getUserName());

            FriendRequestResponse friendRequestResponse = new FriendRequestResponse();
            friendRequestResponse.setRequestMember(requestMember.getUserName());
            friendRequestResponse.setOtherPerson(memberName);

            return ResponseEntity.ok(friendRequestResponse);
        }

        return new ResponseEntity<>(null, HttpStatus.CONFLICT); // 이미 팔로우 했으면 null 반환
    }

    public List<FriendListResponse> loadFollowerList(String memberName){

        List<FriendListResponse> result = new ArrayList<>();
        List<Friend> friendList = friendRepository.findByFromUser(loadMember(memberName));

        for(Friend tmp : friendList){
            FriendListResponse friendListResponse = new FriendListResponse();
            friendListResponse.setFriendId(tmp.getToUser().getId());
            friendListResponse.setFriendName(tmp.getToUser().getUserName());

            result.add(friendListResponse);
        }

        result.sort(new FriendNameComparator());

        return result;
    }

    public Member loadMember(String userName){

        return memberRepository.findByUserName(userName);
    }

    public static class FriendNameComparator implements Comparator<FriendListResponse> {
        @Override
        public int compare(FriendListResponse f1, FriendListResponse f2) {
           return (f1.getFriendName().compareTo(f2.getFriendName()));
        };
    }

}
