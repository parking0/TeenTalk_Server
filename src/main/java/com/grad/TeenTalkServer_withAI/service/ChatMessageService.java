package com.grad.TeenTalkServer_withAI.service;

import com.grad.TeenTalkServer_withAI.domain.ChatMessage;
import com.grad.TeenTalkServer_withAI.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessageSendingOperations sendingOperations;

    /**
     *  메시지 전송 - /sub/chat/room/{roomId}
     */
    public ChatMessage sendMessage(ChatMessage message){

        message = chatMessageRepository.save(message);
        sendingOperations.convertAndSend("/sub/chat/room/" + message.getRoomId(),message);
        log.info("[Service][sendMessage] Message : {}, messageId : {}, RoomId : {}, SenderId : {}, ReceiverId : {}, MsgType : {}",
                message.getContent(), message.getId(),message.getRoomId(),message.getSender(),message.getReceiver(),message.getMsgType());
        return message;
    }

    /**
     *  모든 메시지 불러오기
     *  @return -  ChatMessage list 반환
     */
    public List<ChatMessage> findAllMessages() {
        List<ChatMessage> chatMessageList = chatMessageRepository.findAll();
        Collections.reverse(chatMessageList);
        return chatMessageList;
    }

    /**
     *  특정 채팅방의 메시지 리스트  with RoomId
     *  @return -  ChatMessage list 반환
     */
    public List<ChatMessage> findByRoomId(Long roomId) {
        List<ChatMessage> messageList = chatMessageRepository.findAll();

        List<ChatMessage> resultList = new ArrayList<>();
        for(ChatMessage message : messageList){
            if(Long.parseLong(message.getRoomId()) == roomId){
                resultList.add(message);
            }
        }
        return resultList;
    }

}