package com.ow.service.impl;

import com.ow.dto.LeaveMessageQueryDto;
import com.ow.entity.LeaveMessage;
import com.ow.exception.BusinessException;
import com.ow.mapper.LeaveMessageMapper;
import com.ow.service.LeaveService;
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
public class LeaveServiceImpl implements LeaveService {
    private static final Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);


    @Resource
    private LeaveMessageMapper leaveMessageMapper;

    @Override
    public boolean saveMessage(LeaveMessage message) throws BusinessException {
        logger.debug("saveMessage request: {}", GsonUtil.gson().toJson(message));
        if (Objects.isNull(message) || StringUtils.isBlank(message.getSessionId())) {
            throw new BusinessException("会话已失效，请重新发起聊天！");
        }
        LeaveMessageQueryDto dto = LeaveMessageQueryDto.getInstance().setSessionId(message.getSessionId());
        if (CollectionUtils.isEmpty(leaveMessageMapper.selectByCondition(dto))) {
            return leaveMessageMapper.insert(message) > 0;
        }
        return leaveMessageMapper.updateBySessionId(message) > 0;
    }

    @Override
    public List<LeaveMessage> queryMessage(LeaveMessageQueryDto message) {
        logger.debug("queryMessage request: {}", GsonUtil.gson().toJson(message));
        return leaveMessageMapper.selectByCondition(message);
    }
}
