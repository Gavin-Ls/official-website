package com.ow.exception;

import com.ow.dto.Result;
import com.ow.enums.ResultStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lavnote
 */
@RestControllerAdvice
public class ExceptionConfiguration {
    private static Logger logger = LoggerFactory.getLogger(ExceptionConfiguration.class);

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public Result<String> handlerBusinessException(BusinessException e) {
        logger.error(e.getLocalizedMessage(), e);
        return Result.<String>result().withStatus(ResultStatusEnum.fail).withMessage(e.getLocalizedMessage()).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Result<String> handlerException(Exception e) {
        logger.error(e.getLocalizedMessage(), e);
        return Result.<String>result().withStatus(ResultStatusEnum.fail).withMessage(e.getLocalizedMessage()).build();
    }


}
