package com.grad.TeenTalkServer_withAI.controller;

import com.grad.TeenTalkServer_withAI.dto.statistic.StatisticRequestDTO;
import com.grad.TeenTalkServer_withAI.dto.statistic.StatisticResponseDTO;
import com.grad.TeenTalkServer_withAI.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic")
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/messageType")
    public ResponseEntity<StatisticResponseDTO> messageTypeStatistic (@ModelAttribute StatisticRequestDTO statisticRequestDTO) {
        return statisticService.getStatistic(statisticRequestDTO);
    }

}
