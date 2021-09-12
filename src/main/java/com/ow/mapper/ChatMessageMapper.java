package com.ow.mapper;

import com.ow.dto.ChatMessageQueryDto;
import com.ow.entity.ChatMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lavnote
 */
@Repository
public interface ChatMessageMapper {

    /**
     * query chat list!
     *
     * @param message ChatMessageQueryDto
     * @return List<ChatMessage>
     */
    List<ChatMessage> selectByCondition(ChatMessageQueryDto message);

    /**
     * save chat record.
     *
     * @param message ChatMessage
     * @return long
     */
    long insert(ChatMessage message);

    /**
     * modify chat record
     *
     * @param message ChatMessage
     * @return long
     */
    long updateBySessionId(ChatMessage message);
}
