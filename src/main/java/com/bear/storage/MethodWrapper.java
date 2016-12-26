package com.bear.storage;

import com.bear.pojo.SimpleRedisCommand;
import com.bear.pojo.replay.RedisReply;
import com.sun.javafx.css.converters.PaintConverter;

/**
 * Created by bear on 16-12-24.
 *
 *  核心存储包装类
 *  redis
 *
 */
public interface MethodWrapper {

     RedisReply  execute(SimpleRedisCommand command) throws SimpleRedisException;

}
