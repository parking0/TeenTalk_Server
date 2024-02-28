package com.grad.TeenTalkServer_withAI.service;

import com.grad.TeenTalkServer_withAI.domain.ChatRoom;
import com.grad.TeenTalkServer_withAI.domain.Member;
import com.grad.TeenTalkServer_withAI.dto.ChatRoomListResponse;
import com.grad.TeenTalkServer_withAI.repository.ChatRoomRepository;
import com.grad.TeenTalkServer_withAI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    /**
     *  채팅방 생성
     *  @return -  채팅방 id 생성해서 ChatRoom 객체 반환
     */
    public ChatRoom createRoom(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    /**
     * 특정 유저가 속한 채팅방 목록
     *  @return -List<ChatRoomListResponse>
     */
    public List<ChatRoomListResponse> findUserRooms(Long userId){
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        List<ChatRoomListResponse> resultList = new ArrayList<ChatRoomListResponse>();
        Optional<Member> requestMember = memberRepository.findById(userId);

        if(requestMember.isEmpty()){        //존재하지 않은 userId로 접근 시도
            log.info("[Service][findUserRooms] ERROR     there is no member with the user id ( {} )", userId );
            return null;
        }
        else{
            log.info("[Service][findUserRooms] Request User Name : {}",requestMember.get().getUserName());

            if(chatRoomList.isEmpty()){         // 아직 생성된 방이 없을 땐 null
                return null;
            }
            else {                               //채팅방 리스트
                chatRoomList
                        .stream()
                        .filter(item -> (item.getUser1().equals(userId) || item.getUser2().equals(userId)))
                        .toList()
                        .forEach(x -> {
                            if (x.getUser1().equals(userId)) {
                                Member user2 = memberRepository.findById(x.getUser2()).get();
                                ChatRoomListResponse chatRoomListResponse = new ChatRoomListResponse();
                                chatRoomListResponse.setRoomId(x.getId());
                                chatRoomListResponse.setOtherPersonName(user2.getUserName());
                                chatRoomListResponse.setOtherPersonId(user2.getId());
                                resultList.add(chatRoomListResponse);
                            } else {
                                Member user1 = memberRepository.findById(x.getUser1()).get();
                                ChatRoomListResponse chatRoomListResponse = new ChatRoomListResponse();
                                chatRoomListResponse.setRoomId(x.getId());
                                chatRoomListResponse.setOtherPersonName(user1.getUserName());
                                chatRoomListResponse.setOtherPersonId(user1.getId());
                                resultList.add(chatRoomListResponse);

                            }
                        });
            }

            return resultList;
        }

    }

    /**
     *  모든 채팅방 불러오기
     *  @return -  ChatRoom list 반환
     */
    public List<ChatRoom> findAllRooms() {
        List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
        Collections.reverse(chatRoomList);
        return chatRoomList;
    }

    /**
     *  채팅방 찾기  with RoomId
     *  @return -  ChatRoom 객체 반환
     */
    public ChatRoom findById(Long roomId) {
        Optional<ChatRoom> optionalChatRoom= chatRoomRepository.findById(roomId);

        return optionalChatRoom.orElse(null);
    }


    /**
     * 중복된 채팅방 있는지 확인하는 함수
     */
    public ChatRoom checkDuplicateRoom(Long id1, Long id2){
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByUser1AndUser2(id1, id2);
        if(optionalChatRoom.isPresent()){
            return optionalChatRoom.get();
        }
        else{
            optionalChatRoom = chatRoomRepository.findByUser1AndUser2(id2, id1);
            return optionalChatRoom.orElse(null);
        }
    }


}

