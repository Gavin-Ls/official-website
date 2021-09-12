package com.ow.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登陆token管理
 *
 * @author lavnote
 */
public class TokenUtil {

    public static final String TOKEN_NAME = "token";

    private static Logger LOGGER = LoggerFactory.getLogger(TokenUtil.class);
    /**
     * 初始容量
     */
    private static final int INITIAL_CAPACITY = 1;
    /**
     * 最大容量
     */
    private static final int MAXIMUM_SIZE = 1;
    /**
     * 登陆超时时间(秒)
     */
    private static final int EXPIRE_AFTER_ACCESS_TIME = 900;


    /**
     * 设置token缓存。
     * 初始缓存容量INITIAL_CAPACITY，最大MAXIMUM_SIZE(最大可以登陆MAXIMUM_SIZE个账号)，登陆超时时间(秒)
     */
    private static LoadingCache<String, String> localCache;

    static {
        localCache = CacheBuilder.
                newBuilder().
                initialCapacity(INITIAL_CAPACITY).
                maximumSize(MAXIMUM_SIZE).
                expireAfterAccess(EXPIRE_AFTER_ACCESS_TIME, TimeUnit.SECONDS).
                build(
                        new CacheLoader<String, String>() {
                            @Override
                            public String load(String s) {
                                return "";
                            }
                        }
                );
    }

    /**
     * 添加缓存
     *
     * @param key String
     * @param value String
     */
    public static void setKey(String key, String value) {
        localCache.invalidateAll();
        localCache.put(key, value);
    }

    /**
     * 删除缓存
     *
     * @param key String
     */
    public static void removeKey(String key) {
        localCache.invalidate(key);
    }

    /**
     * 获取并刷新缓存
     *
     * @param key String
     * @return String
     */
    public static String getValue(String key) {
        String value;
        try {
            value = localCache.get(key);
        } catch (Exception e) {
            LOGGER.error("", e);
            value = StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * 生成登陆token
     *
     * @return String
     */
    public static String generateToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) throws InterruptedException {
        String token = generateToken();
        setKey(token, "admin");
        System.out.println("before: " + getValue(token));
        Thread.sleep(2000);
        System.out.println("before: " + getValue(token));
        Thread.sleep(1000);
        System.out.println("before: " + getValue(token));
        Thread.sleep(2000);
        System.out.println("before: " + getValue(token));
        Thread.sleep(3000);
        System.out.println("before: " + getValue(token));
        Thread.sleep(1000);
        System.out.println("after:" + getValue(token));
    }
}
