package com.ow.service.impl;

import com.ow.dto.ChatMessageQueryDto;
import com.ow.entity.ChatMessage;
import com.ow.exception.BusinessException;
import com.ow.mapper.ChatMessageMapper;
import com.ow.service.ChatService;
import com.ow.utils.GsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author lavnote
 */
@Service
public class ChatServiceImpl implements ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);


    @Resource
    private ChatMessageMapper chatMessageMapper;

    @Override
    public boolean saveMessage(ChatMessage message) throws BusinessException {
        logger.debug("saveMessage request: {}", GsonUtil.gson().toJson(message));
        if (Objects.isNull(message) || StringUtils.isBlank(message.getSessionId())) {
            throw new BusinessException("会话已失效，请重新发起聊天！");
        }
        ChatMessageQueryDto dto = ChatMessageQueryDto.getInstance().setSessionId(message.getSessionId());
        if (CollectionUtils.isEmpty(chatMessageMapper.selectByCondition(dto))) {
            return chatMessageMapper.insert(message) > 0;
        }
        return chatMessageMapper.updateBySessionId(message) > 0;
    }

    @Override
    public List<ChatMessage> queryMessage(ChatMessageQueryDto message) {
        logger.debug("queryMessage request: {}", GsonUtil.gson().toJson(message));
        return chatMessageMapper.selectByCondition(message);
    }
}
