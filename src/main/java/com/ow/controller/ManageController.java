package com.ow.controller;

import com.google.common.collect.Maps;
import com.ow.dto.ChatMessageQueryDto;
import com.ow.dto.LeaveMessageQueryDto;
import com.ow.dto.LoginDto;
import com.ow.dto.Result;
import com.ow.entity.ChatMessage;
import com.ow.entity.LeaveMessage;
import com.ow.exception.BusinessException;
import com.ow.service.ChatService;
import com.ow.service.LeaveService;
import com.ow.utils.EncryptUtil;
import com.ow.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 管理员登陆/登出
 *
 * @author lavnote
 */
@RestController
@RequestMapping("/manage/")
public class ManageController {

    @Resource
    private LeaveService leaveService;

    @Resource
    private ChatService chatService;


    @Value("${login.manage.name}")
    private String name;

    @Value("${login.manage.password}")
    private String password;

    /**
     * 管理员登陆
     *
     * @param loginDto LoginDto
     * @return Result<Map.Entry               <               String               ,                               String>>
     * @throws Exception Exception
     */
    @PostMapping("login")
    public Result<Map.Entry<String, String>> manageLogin(@RequestBody LoginDto loginDto) throws Exception {
        if (Objects.isNull(loginDto) ||
                StringUtils.isBlank(loginDto.getName()) ||
                StringUtils.isBlank(loginDto.getPassword())) {
            throw new BusinessException("用户名和密码不能为空!");
        }

        if (!StringUtils.equals(loginDto.getName(), name) ||
                !StringUtils.equals(EncryptUtil.cipherText(loginDto.getPassword()), password)) {
            throw new BusinessException("用户名或密码不正确!");
        }

        String token = TokenUtil.generateToken();
        TokenUtil.setKey(token, loginDto.getName());

        Map.Entry<String, String> entry = Maps.immutableEntry(TokenUtil.TOKEN_NAME, token);
        return Result.<Map.Entry<String, String>>result().withData(entry).build();
    }

    /**
     * 管理员登出
     *
     * @param token token
     * @return Result<String>
     */
    @PostMapping("logout")
    public Result<String> manageLogout(@RequestHeader(TokenUtil.TOKEN_NAME) String token) {
        TokenUtil.removeKey(token);
        return Result.<String>result().build();
    }

    /**
     * 留言查询
     *
     * @param message LeaveMessageQueryDto
     * @param token   token
     * @return boolean
     */
    @PostMapping("leave/message/query")
    public List<LeaveMessage> queryMessage(@RequestHeader(TokenUtil.TOKEN_NAME) String token,
                                           @RequestBody LeaveMessageQueryDto message) throws BusinessException {
        if (StringUtils.isBlank(TokenUtil.getValue(token))) {
            throw new BusinessException("未登录!");
        }
        return leaveService.queryMessage(message);
    }

    /**
     * 聊天查询
     *
     * @param message ChatMessageQueryDto
     * @param token   token
     * @return boolean
     */
    @PostMapping("chat/message/query")
    public List<ChatMessage> queryMessage(@RequestHeader(TokenUtil.TOKEN_NAME) String token,
                                          @RequestBody ChatMessageQueryDto message) throws BusinessException {
        if (StringUtils.isBlank(TokenUtil.getValue(token))) {
            throw new BusinessException("未登录!");
        }
        return chatService.queryMessage(message);
    }
}
