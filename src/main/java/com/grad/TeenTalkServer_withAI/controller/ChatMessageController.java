package com.grad.TeenTalkServer_withAI.controller;

import com.grad.TeenTalkServer_withAI.domain.ChatMessage;
import com.grad.TeenTalkServer_withAI.service.ChatMessageService;
import com.grad.TeenTalkServer_withAI.service.PredictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final PredictService predictService;


    /**
     *  메시지 전송 - /pub/chat/message
     */
    @MessageMapping("/chat/message")
    public ResponseEntity<ChatMessage> handleMessage (ChatMessage message) {

        log.info("[Controller][handleMessage] before AI  -  Message : {}, MsgType : {}", message.getContent() ,message.getMsgType());

        // 먼저 AI 서버로 메시지 보내기
        ChatMessage AIResult = predictService.predictLabel(message);
        ChatMessage finalMsg = chatMessageService.sendMessage(AIResult);
        
        log.info("[Controller][handleMessage] after AI  -  Message : {}, MsgType : {}", message.getContent() ,message.getMsgType());

        return ResponseEntity.ok(finalMsg);
    }

    /**
     *  모든 메시지 내역 전송 - /message/findAll
     */
    @GetMapping("/findAll")
    @ResponseBody
    public ResponseEntity<List<ChatMessage>> findAllMessages() {
        log.info("[Controller][findAllMessages]");
        return ResponseEntity.ok(chatMessageService.findAllMessages());
    }

    /**
     *  각 방의 메시지 히스토리 - /message/find/{roomId}
     */
    @GetMapping("/find/{roomId}")
    @ResponseBody
    public ResponseEntity<List<ChatMessage>> loadRoomMessages(@PathVariable Long roomId) {
        List<ChatMessage> result = chatMessageService.findByRoomId(roomId);
        log.info("[Controller][loadRoomMessages]   RoomId : {}", roomId);
        return ResponseEntity.ok(result);
    }

}
