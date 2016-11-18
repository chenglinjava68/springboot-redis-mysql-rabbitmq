package com.liuahang.repo.util;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class JedisSentinelPoolConfig {
    private String masterName;
    private String sentinels;
    private JedisPoolConfig jedisConfig;

    public JedisSentinelPoolConfig(String masterName, String sentinels, JedisPoolConfig jedisConfig) {
        //TODO: validation
        this.masterName = masterName;
        this.sentinels = sentinels;
        this.jedisConfig = jedisConfig;
    }

    public JedisSentinelPool getJedisSentinelPool() {
        String[] sentinelstrs = sentinels.split(",");
        Set<String> sentinelsSet = new HashSet<String>(sentinelstrs.length);
        for (String st : sentinelstrs) {
            sentinelsSet.add(st);
        }
        System.out.println("masterName==" + masterName);
        System.out.println("sentinelsSet==" + sentinelsSet);
        System.out.println("jedisConfig==" + jedisConfig);
        return new JedisSentinelPool(masterName, sentinelsSet, jedisConfig);
    }
}
