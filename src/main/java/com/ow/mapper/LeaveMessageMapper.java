package com.ow.mapper;

import com.ow.dto.LeaveMessageQueryDto;
import com.ow.entity.LeaveMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lavnote
 */
@Repository
public interface LeaveMessageMapper {
    /**
     * LeaveMessageQueryDto to List<LeaveMessage>
     *
     * @param message LeaveMessageQueryDto
     * @return List<LeaveMessage>
     */
    List<LeaveMessage> selectByCondition(LeaveMessageQueryDto message);

    /**
     * save  LeaveMessage
     *
     * @param message LeaveMessage
     * @return Long
     */
    Long insert(LeaveMessage message);

    /**
     * modify LeaveMessage
     *
     * @param message LeaveMessage
     * @return Long
     */
    Long updateBySessionId(LeaveMessage message);
}
