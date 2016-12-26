package com.bear.storage;

import com.bear.pojo.replay.BulkReply;
import com.bear.pojo.replay.IntegerReply;
import com.bear.pojo.replay.MultiBulkReply;

/**
 *  基于levelDB 实现的的redis存储
 *
 * Created by bear on 16-12-23.
 */
public class RedisCoreImplByLevelDB implements RedisCoreInterface {

    @Override
    public MultiBulkReply keys(byte[] pattern0) throws SimpleRedisException {
        return null;
    }

    @Override
    public IntegerReply del(byte[] keys) throws SimpleRedisException {
        return null;
    }

    @Override
    public BulkReply get(byte[] key) throws SimpleRedisException {
        return null;
    }

    @Override
    public IntegerReply set(byte[] name, byte[] value) throws SimpleRedisException {
        return null;
    }

    @Override
    public IntegerReply quit() throws SimpleRedisException {
        System.out.println("the quit method is method!");
        return null;
    }
}
