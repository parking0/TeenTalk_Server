package com.grad.TeenTalkServer_withAI.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDTO {
    private Long id;
    private String userName;
    private String newName;
}
