package com.ow.dto;

import com.ow.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @author lavnote
 */
public class LeaveMessageQueryDto {

    private String sessionId;

    private String phone;

    private String content;

    private int state;

    private String createTimeStart;

    private String createTimeEnd;

    private String updateTimeStart;

    private String updateTimeEnd;

    public static LeaveMessageQueryDto getInstance() {
        return new LeaveMessageQueryDto();
    }

    public String getSessionId() {
        return Objects.isNull(sessionId) ? null : (StringUtils.isBlank(sessionId) ? StringUtils.EMPTY : sessionId.trim());
    }

    public LeaveMessageQueryDto setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getPhone() {
        return Objects.isNull(phone) ? null : (StringUtils.isBlank(phone) ? StringUtils.EMPTY : phone.trim());
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return Objects.isNull(content) ? null : (StringUtils.isBlank(content) ? StringUtils.EMPTY : content.trim());
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreateTimeStart() {
        return DateUtil.str2Date(createTimeStart);
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return DateUtil.str2Date(createTimeEnd);
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Date getUpdateTimeStart() {
        return DateUtil.str2Date(updateTimeStart);
    }

    public void setUpdateTimeStart(String updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    public Date getUpdateTimeEnd() {
        return DateUtil.str2Date(updateTimeEnd);
    }

    public void setUpdateTimeEnd(String updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }
}
