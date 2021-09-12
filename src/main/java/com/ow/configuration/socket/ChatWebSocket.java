package com.ow.configuration.socket;

import com.google.common.base.Joiner;
import com.ow.dto.ChatMessageDto;
import com.ow.entity.ChatMessage;
import com.ow.exception.BusinessException;
import com.ow.service.ChatService;
import com.ow.utils.DateUtil;
import com.ow.utils.GsonUtil;
import com.ow.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * push pull message
 *
 * @author lavnote
 */
@Component
@ServerEndpoint("/chat/message/push/{token}/{sendSessionId}")
public class ChatWebSocket {
    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocket.class);

    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    private static CopyOnWriteArraySet<ChatMessageDto> chatSet = new CopyOnWriteArraySet<>();

    @Resource
    private ChatService chatService;

    private static ChatService staticChatService;
    /**
     * 管理员客户端
     */
    private static String sessionId = StringUtils.EMPTY;

    @PostConstruct
    public void init() {
        staticChatService = chatService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        String clientId = UUID.randomUUID().toString();
        if (StringUtils.isNotBlank(token) && !NumberUtils.isDigits(token)) {
            if (StringUtils.isBlank(TokenUtil.getValue(token))) {
                sendMessage(session, StringUtils.EMPTY, "未登录!", StringUtils.EMPTY);
                return;
            }
            sendMessage(session, StringUtils.EMPTY, String.format("连接成功！当前在线总人数=%d", ONLINE_COUNT.get()), StringUtils.EMPTY);
            sessionId = session.getId();
            broadMessage("管理员上线了!");
        } else {
            sendMessage(session, sessionId, String.format("连接成功！当前排队人数=%d", ONLINE_COUNT.get()), clientId);
            logger.info("客户上线了!");
        }
        ChatMessageDto dto = new ChatMessageDto();
        dto.setSession(session);
        dto.setClientId(clientId);
        chatSet.add(dto);
        logger.info("连接成功！当前在线总人数={}", ONLINE_COUNT.incrementAndGet());
    }

    @OnClose
    public void onClose(Session session, @PathParam("token") String token) {
        for (ChatMessageDto dto : chatSet) {
            if (dto.getSession() == session) {
                chatSet.remove(dto);
            }
        }
        if (StringUtils.isNotBlank(token) && !NumberUtils.isDigits(token)) {
            if (StringUtils.isBlank(TokenUtil.getValue(token))) {
                sendMessage(session, StringUtils.EMPTY, "未登录!", StringUtils.EMPTY);
                return;
            }
            broadMessage("管理员下线了!");
            sessionId = StringUtils.EMPTY;
        }
        logger.info("退出成功！当前在线总人数={}", ONLINE_COUNT.decrementAndGet());
    }

    @OnMessage
    public void onMessage(Session session, String message,
                          @PathParam("token") String token,
                          @PathParam("sendSessionId") String sendSessionId) {
        logger.info("接收消息={}", message);
        if (StringUtils.isNotBlank(token) && !NumberUtils.isDigits(token)) {
            if (StringUtils.isBlank(TokenUtil.getValue(token))) {
                sendMessage(session, StringUtils.EMPTY, "未登录!", StringUtils.EMPTY);
                return;
            }
            appointMessage(sendSessionId, session.getId(), message);
        } else {
            String userId = session.getId();
            //客户给管理员发消息
            appointMessage(sessionId, userId, message);
        }
    }

    /**
     * 错误时调用
     *
     * @param session   客户端
     * @param throwable throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("发生错误,session id = {},error = {}", session.getId(), throwable.getMessage());
    }

    /**
     * 发送消息
     *
     * @param session       客户端
     * @param message       message
     * @param sendSessionId 发送消息方session id
     * @param clientId      客户终端的唯一标记
     */
    private static void sendMessage(Session session, String sendSessionId, String message, String clientId) {
        ChatMessage chatMessage = null;
        try {
            logger.info("发送消息={}", message);
            String currentDate = DateUtil.date2Str(new Date());
            String textMessage = String.format("(我:%s-对方:%s) %s =>> %s", session.getId(), sendSessionId, currentDate, message);
            //给管理员发消息
            if (sessionId.equals(session.getId())) {
                for (ChatMessageDto dto : chatSet) {
                    Session s = dto.getSession();
                    if (s.getId().equals(sendSessionId)) {
                        clientId = dto.getClientId();
                    }
                }
            }
            if (StringUtils.isNotBlank(clientId)) {
                chatMessage = new ChatMessage();
                chatMessage.setContent(textMessage);
                chatMessage.setSessionId(clientId);
                staticChatService.saveMessage(chatMessage);
            }

            session.getBasicRemote().sendText(textMessage);
        } catch (IOException e) {
            logger.error(Joiner.on("|").join(session.getId(), sendSessionId, message), e);
        } catch (BusinessException e) {
            logger.error(GsonUtil.gson().toJson(chatMessage), e);
        }
    }

    /**
     * 群发消息
     *
     * @param message message
     */
    private static void broadMessage(String message) {
        for (ChatMessageDto dto : chatSet) {
            Session s = dto.getSession();
            if (s.isOpen() && !s.getId().equals(sessionId)) {
                sendMessage(s, sessionId, message, dto.getClientId());
            }
        }
    }

    /**
     * 指定客户端发送消息
     *
     * @param receiveSessionId 客户端
     * @param message          message
     * @param sendSessionId    发送消息方session id
     */
    private static void appointMessage(String receiveSessionId, String sendSessionId, String message) {
        Session session = null;
        String clientId = null;
        for (ChatMessageDto dto : chatSet) {
            Session s = dto.getSession();
            if (s.isOpen() && s.getId().equals(receiveSessionId)) {
                session = s;
                clientId = dto.getClientId();
                break;
            }
        }
        if (Objects.isNull(session)) {
            logger.warn("会话不存在!{}", receiveSessionId);
            return;
        }
        sendMessage(session, sendSessionId, message, clientId);
    }
}
