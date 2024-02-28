package com.grad.TeenTalkServer_withAI.dto.predictAI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PredictResponse {

    private String scores;
    private String labels;
    private String id;
}