package com.grad.TeenTalkServer_withAI.repository;

import com.grad.TeenTalkServer_withAI.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByUser1AndUser2(Long User1, Long User2);
}
