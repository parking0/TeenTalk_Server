package com.grad.TeenTalkServer_withAI.repository;

import com.grad.TeenTalkServer_withAI.domain.Friend;
import com.grad.TeenTalkServer_withAI.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByFromUserAndToUser(Member fromUser, Member toUser);
    List<Friend> findByFromUser(Member fromUser);
}
