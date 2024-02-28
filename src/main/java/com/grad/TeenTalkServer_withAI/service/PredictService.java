package com.grad.TeenTalkServer_withAI.service;

import com.grad.TeenTalkServer_withAI.domain.ChatMessage;
import com.grad.TeenTalkServer_withAI.domain.Member;
import com.grad.TeenTalkServer_withAI.domain.MessageType;
import com.grad.TeenTalkServer_withAI.dto.predictAI.PredictRequest;
import com.grad.TeenTalkServer_withAI.dto.predictAI.PredictResponse;
import com.grad.TeenTalkServer_withAI.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PredictService {

    private WebClient webClient;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void initWebClient() {
        //webClient = WebClient.create("http://localhost:5000");
        //webClient = WebClient.create("http://0.0.0.0:8000");
        //webClient = WebClient.create("http://172.18.0.2:8000");
        webClient = WebClient.create("http://host.docker.internal:8000");
    }

    public ChatMessage predictLabel(ChatMessage chatMessage) {

        PredictRequest predictRequest = new PredictRequest(String.valueOf(chatMessage.getId()),chatMessage.getContent());

        log.info("[Service][predictLabel] Request to AI server  -  sentence : {}",predictRequest.getSentences());
        log.info("[Service][predictLabel] Request to AI server  -  sentence : {}",predictRequest.getSentences());

        PredictResponse predictResponse
                = webClient.post()
                .uri("/predict")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(predictRequest), PredictRequest.class)
                .retrieve()
                .bodyToMono(PredictResponse.class)
                .block();

        log.info("[Service][useWebClient] Response from AI server -  scores : {}, labels : {}, id : {}", predictResponse.getScores(), predictResponse.getLabels(), predictResponse.getId());

        Optional<Member> sender = memberRepository.findById(Long.parseLong(chatMessage.getSender()));

        if(predictResponse.getLabels().equals("HATE")){         // 혐오 발언이면
            chatMessage.setMsgType(MessageType.HATE);
            chatMessage.setContent("혐오발언이어서 삭제됐습니다.");
           sender.get().addMessageType(MessageType.HATE);
        }
        else{
            chatMessage.setMsgType(MessageType.CLEAN);
            sender.get().addMessageType(MessageType.CLEAN);
        }

        memberRepository.save(sender.get());

        // AI 결과 이후로 바꾸기
        return chatMessage;

    }
}


// docker run -p 8000:8000 --name rest_api gj98/gammini_server:latest
// http://0.0.0.0:8000/docs