package com.bear.storage;

import com.bear.pojo.replay.BulkReply;
import com.bear.pojo.replay.IntegerReply;
import com.bear.pojo.replay.MultiBulkReply;
import com.bear.pojo.replay.RedisReply;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于localMemory 实现的 redis存储
 * Created by bear on 16-12-23.
 */
public class RedisCoreImplByLocalMemory implements RedisCoreInterface {

    private HashMap<String, byte[]> database = new HashMap<String, byte[]>();


    @Override
    public MultiBulkReply keys(byte[] pattern0) throws SimpleRedisException {
        System.out.println("keys--->" + new String(pattern0));

        //TODO 根据正则匹配返回对应的key
        return new MultiBulkReply(  database.keySet());
    }

    @Override
    public IntegerReply del(byte[] keys) throws SimpleRedisException {
        System.out.println("del--->" + new String(keys));
        byte[] relsult = database.remove(new String(keys));
        if (relsult == null) {
            return new IntegerReply(0);
        } else {
            return new IntegerReply(1);
        }
    }

    @Override
    public BulkReply get(byte[] key) throws SimpleRedisException {
        System.out.println("get method invoke" + new String(key));
        byte[] value = database.get(new String(key, Charset.forName("UTF-8")));
        if (value != null && value.length > 0) {
            return new BulkReply(value);
        } else {
            return BulkReply.NIL_REPLY;
        }
    }

    @Override
    public IntegerReply set(byte[] name, byte[] value) throws SimpleRedisException {
        System.out.println("set method invoke" + new String(name));
        if (database.put(new String(name, Charset.forName("UTF-8")), value) == null) {
            return new IntegerReply(1);
        } else {
            return new IntegerReply(0);
        }
    }


    @Override
    public IntegerReply quit() throws SimpleRedisException {
        System.out.println("this quit method is execute");
        return null;
    }
}
