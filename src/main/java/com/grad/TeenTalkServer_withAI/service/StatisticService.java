package com.grad.TeenTalkServer_withAI.service;

import com.grad.TeenTalkServer_withAI.domain.Member;
import com.grad.TeenTalkServer_withAI.dto.statistic.StatisticRequestDTO;
import com.grad.TeenTalkServer_withAI.dto.statistic.StatisticResponseDTO;
import com.grad.TeenTalkServer_withAI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticService {

    private final MemberRepository memberRepository;

    public ResponseEntity<StatisticResponseDTO> getStatistic(StatisticRequestDTO statisticRequestDTO){
        Optional<Member> member = memberRepository.findById(statisticRequestDTO.getMemberId());
        if(member.isEmpty()){  // 존재하지 않는 회원의 통계를 원할 때
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else{
            Long nClean = member.get().getClean();
            Long nHate = member.get().getHate();
            int clean_sta = calculateSta(nClean,nHate);
            int hate_sta = 100-clean_sta;

            StatisticResponseDTO statisticResponseDTO = new StatisticResponseDTO(member.get().getId(),nClean,nHate,clean_sta,hate_sta);

            log.info("[Service][getStatistic]   memberId: {}, nClean : {}, nHate: {}, clean_sta: {}, hate_sta: {}"
                    , statisticResponseDTO.getMemberId(),statisticResponseDTO.getNClean(),statisticResponseDTO.getNHate()
                    ,statisticResponseDTO.getSta_clean(),statisticResponseDTO.getSta_hate());

            return ResponseEntity.ok(statisticResponseDTO);
        }
    }

    private int calculateSta(Long clean, Long hate){
        double fraction =  (double) clean /(clean+hate);
        return (int) (fraction*100);
    }
}
