package com.grad.TeenTalkServer_withAI.repository;

import com.grad.TeenTalkServer_withAI.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
