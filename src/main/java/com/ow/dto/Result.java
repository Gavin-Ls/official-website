package com.ow.dto;

import com.ow.enums.ResultStatusEnum;

import java.util.Optional;

/**
 * @param <T>
 * @author lavnote
 */
public class Result<T> {

    private ResultStatusEnum status;

    private String message;

    private T data;

    public String getStatus() {
        return Optional.ofNullable(status).orElse(ResultStatusEnum.ok).name();
    }

    public void setStatus(ResultStatusEnum status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResultBuilder<T> result() {
        return ResultBuilder.resultBuilder();
    }

    public static final class ResultBuilder<T> {
        private ResultStatusEnum status;
        private String message;
        private T data;

        private ResultBuilder() {
        }

        private static <T> ResultBuilder<T> resultBuilder() {
            return new ResultBuilder<>();
        }

        public ResultBuilder<T> withStatus(ResultStatusEnum status) {
            this.status = status;
            return this;
        }

        public ResultBuilder<T> withMessage(String message) {
            this.message = message;
            return this;
        }

        public ResultBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        public Result<T> build() {
            Result<T> result = new Result<>();
            result.setStatus(status);
            result.setMessage(message);
            result.setData(data);
            return result;
        }
    }
}
