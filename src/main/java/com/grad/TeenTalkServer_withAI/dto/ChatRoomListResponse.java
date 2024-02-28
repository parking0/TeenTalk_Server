package com.grad.TeenTalkServer_withAI.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatRoomListResponse {
    Long roomId;
    String otherPersonName;     // 대화 상대 이름
    Long otherPersonId;         // 대화 상대 아이디
}
