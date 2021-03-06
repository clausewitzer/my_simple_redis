package com.bear.pojo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bear on 16-12-22.
 */
public class SimpleRedisCommand {

    private String name;
    private List<byte[]> listCommand;
    private boolean isWrong = false;
    private int commandSize = 0;

    public SimpleRedisCommand(String name, List<byte[]> listCommand) {
        this.name = name;
        this.listCommand = listCommand;

        for (int i = 1; i < listCommand.size(); i++) {
            commandSize++;
            //TODO low 需要重构
            if (i == 1) {
                this.arg1 = listCommand.get(i);
            } else if (i == 2) {
                this.arg2 = listCommand.get(i);
            }

        }

    }

    private byte[] arg1;
    private byte[] arg2;


    public byte[] getArg1() {
        return arg1;
    }

    public byte[] getArg2() {
        return arg2;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SimpleRedisCommand{" +
                "name='" + name + '\'' +
                ", listCommand=" + listCommand +
                ", isWrong=" + isWrong +
                ", commandSize=" + commandSize +
                ", arg1=" + Arrays.toString(arg1) +
                ", arg2=" + Arrays.toString(arg2) +
                '}';
    }

    /**
     * 将解析后的协议封装 成供反射执行的对象
     *
     * @param types
     * @param objects
     */
    public void setInvokeParams(Class<?>[] types, Object[] objects) {

        int position = 0;

        for (Class<?> type : types) {
            //目前只支持简单的操作 byte[]数组为参数的操作
            if (type == byte[].class) {
                if (position >= objects.length) {
                    throw new IllegalArgumentException("wrong number of agruments for " + name + " command");//非法的参数命令
                }
                objects[position] = listCommand.get(position + 1);
            } else {

                //TODO 支持复杂的操作参数

            }
            position++;
        }


    }
}
