package com.grad.TeenTalkServer_withAI.controller;

import com.grad.TeenTalkServer_withAI.domain.ChatRoom;
import com.grad.TeenTalkServer_withAI.dto.ChatRoomListResponse;
import com.grad.TeenTalkServer_withAI.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chatRoom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     *  채팅방 생성
     *  @return -  채팅방 id 생성해서 ChatRoom 객체 반환
     *              중복되는 방일 경우 409 CONFLICT
     */
    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createRoom(@ModelAttribute ChatRoom chatRoom){

        ChatRoom duplicateResult = chatRoomService.checkDuplicateRoom(chatRoom.getUser1(), chatRoom.getUser2());

        if(duplicateResult != null){   // 중복된 방 있음
            log.info("[Controller][createRoom]  FAIL  - duplicate RoomId:{}  User1:{}, User2:{}",duplicateResult.getId(),duplicateResult.getUser1(),duplicateResult.getUser2());
            return new ResponseEntity<>(duplicateResult, HttpStatus.CONFLICT);
        }
        else{
            ChatRoom newRoom = chatRoomService.createRoom(chatRoom);                        // 정상적으로 방 생성
            log.info("[Controller][createRoom]  SUCCESS  - new RoomId:{} , User1:{}, User2:{}",newRoom.getId(),newRoom.getUser1(),newRoom.getUser2());
            return ResponseEntity.ok(newRoom);
        }
    }


    /**
     *  특정 유저가 속한 채팅방 목록
     *  /chatRoom/find/user/{userId}
     *  @return - List<ChatRoomListResponse>   - (roomId, 채팅상대 이름) 객체의 리스트
     **/
    @GetMapping("/find/user/{userId}")
    @ResponseBody
    public ResponseEntity<List<ChatRoomListResponse>> userRoomInfo(@PathVariable Long userId) {
        List<ChatRoomListResponse> result = chatRoomService.findUserRooms(userId);
        if(result != null){
            log.info("[Controller][userRoomInfo] SUCCESS    userId : {}", userId);
        }
        else{
            log.info("[Controller][userRoomInfo] SUCCESS BUT no room created yet");
        }
        return ResponseEntity.ok(result);
    }


    /**
     *  모든 채팅방 불러오기 - DB에 있는 모든 채팅방
     *  @return -  ChatRoom list 반환
     */
    @GetMapping("/findAll")
    @ResponseBody
    public ResponseEntity<List<ChatRoom>> findAllRooms() {
        log.info("[Controller][findAllRooms]");
        return ResponseEntity.ok(chatRoomService.findAllRooms());
    }


    /**
     *  채팅방 찾기  with RoomId
     *  @return -  ChatRoom 객체 반환
     *             해당하는 방이 없으면 UNAUTHORIZED 상태코드 반환
     */
    @GetMapping("/find/room/{roomId}")
    @ResponseBody
    public ResponseEntity<ChatRoom> userInfo(@PathVariable Long roomId) {
        ChatRoom result = chatRoomService.findById(roomId);
        if(result != null){
            log.info("[Controller][roomInfo] SUCCESS    RoomId:{}", roomId);
            return ResponseEntity.ok(result);
        }
        else{
            log.info("[Controller][roomInfo] FAIL   RoomId:{}", roomId);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
