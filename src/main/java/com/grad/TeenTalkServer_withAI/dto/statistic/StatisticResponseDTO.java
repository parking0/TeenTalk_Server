package com.grad.TeenTalkServer_withAI.dto.statistic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticResponseDTO {

    private Long memberId;
    private Long nClean;
    private Long nHate;
    private int sta_clean;
    private int sta_hate;

    public StatisticResponseDTO(Long memberId, Long nClean, Long nHate, int sta_clean, int sta_hate) {
        this.memberId = memberId;
        this.nClean = nClean;
        this.nHate = nHate;
        this.sta_clean = sta_clean;
        this.sta_hate = sta_hate;
    }
}
