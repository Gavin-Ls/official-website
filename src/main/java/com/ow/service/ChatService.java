package com.ow.service;

import com.ow.dto.ChatMessageQueryDto;
import com.ow.entity.ChatMessage;
import com.ow.exception.BusinessException;

import java.util.List;

/**
 * @author lavnote
 */
public interface ChatService {

    /**
     * 聊天留言
     *
     * @param message ChatMessage
     * @return boolean
     */
    boolean saveMessage(ChatMessage message) throws BusinessException;

    /**
     * 聊天留言
     *
     * @param message ChatMessageQueryDto
     * @return boolean
     */
    List<ChatMessage> queryMessage(ChatMessageQueryDto message);
}
