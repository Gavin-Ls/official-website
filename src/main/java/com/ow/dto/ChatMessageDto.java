package com.ow.dto;

import javax.websocket.Session;
import java.util.Objects;

/**
 * @author lavnote
 */
public class ChatMessageDto {

    private Session session;

    private String clientId;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatMessageDto that = (ChatMessageDto) o;
        return Objects.equals(session, that.session) &&
                Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, clientId);
    }
}
