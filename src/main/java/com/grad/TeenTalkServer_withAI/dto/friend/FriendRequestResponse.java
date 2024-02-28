package com.grad.TeenTalkServer_withAI.dto.friend;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FriendRequestResponse {

    String requestMember;       // 요청한 멤버
    String otherPerson;         // 팔로우하고 싶은 대상
}
