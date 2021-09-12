package com.ow.service;

import com.ow.dto.LeaveMessageQueryDto;
import com.ow.entity.LeaveMessage;
import com.ow.exception.BusinessException;

import java.util.List;

/**
 * @author lavnote
 */
public interface LeaveService {

    /**
     * 留言
     *
     * @param message LeaveMessage
     * @return boolean
     */
    boolean saveMessage(LeaveMessage message) throws BusinessException;

    /**
     * 留言
     *
     * @param message LeaveMessageQueryDto
     * @return boolean
     */
    List<LeaveMessage> queryMessage(LeaveMessageQueryDto message);
}
