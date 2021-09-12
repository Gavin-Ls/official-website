package com.ow.controller;

import com.ow.entity.LeaveMessage;
import com.ow.exception.BusinessException;
import com.ow.service.LeaveService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lavnote
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private LeaveService leaveService;

    /**
     * 留言
     *
     * @param message LeaveMessage
     * @return boolean
     */
    @PostMapping("leave/message")
    public boolean saveMessage(@RequestBody LeaveMessage message) throws BusinessException {
        return leaveService.saveMessage(message);
    }
}
