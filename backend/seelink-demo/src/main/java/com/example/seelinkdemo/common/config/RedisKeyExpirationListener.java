package com.example.seelinkdemo.common.config;

import cn.hutool.extra.spring.SpringUtil;
import com.example.seelinkdemo.seelinkapi.service.SeeLinkApiService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 坚挺到过期消息
     *
     * @param message key
     * @param pattern 消息事件
     * @return void
     * @author lei
     * @date 2022-09-27 10:17:54
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        log.info("过期消息key:{}.......pattern:{}", expiredKey, new String(pattern, StandardCharsets.UTF_8));
        // 如果token过期，刷新token
        if (StringUtils.equals(expiredKey,"token")) {
            SeeLinkApiService seeLinkApiService = SpringUtil.getBean("seeLinkApiService", SeeLinkApiService.class);
            seeLinkApiService.refreshToken();
        }
    }
}