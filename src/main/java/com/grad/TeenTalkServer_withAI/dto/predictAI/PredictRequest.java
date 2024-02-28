package com.grad.TeenTalkServer_withAI.dto.predictAI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PredictRequest {
    private String id;
    private String sentences;     //메시지 내용

    public PredictRequest(String id, String sentences){
        this.id = id;
        this.sentences = sentences;
    }
}
