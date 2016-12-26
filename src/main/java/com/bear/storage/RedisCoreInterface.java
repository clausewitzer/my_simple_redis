package com.bear.storage;

import com.bear.pojo.replay.BulkReply;
import com.bear.pojo.replay.IntegerReply;
import com.bear.pojo.replay.MultiBulkReply;

/**
 * Created by bear on 16-12-23.
 *
 *  redis实现的接口
 */
public interface RedisCoreInterface {


    /**
     * keys 命令
     * @param pattern0
     * @return
     * @throws SimpleRedisException
     */
    public MultiBulkReply keys(byte[] pattern0) throws SimpleRedisException;


    /**
     *
     * @param keys
     * @return
     * @throws SimpleRedisException
     */
    public IntegerReply  del(byte[] keys) throws SimpleRedisException;


    /**
     * 获取某个key的值
     * @param key
     * @return
     * @throws SimpleRedisException
     */
    public BulkReply get(byte[] key) throws SimpleRedisException;


    /**
     * 设置某个key的值
     * @param name
     * @param value
     * @return
     * @throws SimpleRedisException
     */
    public IntegerReply set(byte[] name,byte[] value) throws SimpleRedisException;


    /**
     * 退出redis客户端返回对应的数据
     * @return
     * @throws SimpleRedisException
     */
    public IntegerReply quit() throws SimpleRedisException;




}
