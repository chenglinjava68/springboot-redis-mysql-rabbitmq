package com.liuahang.repo.service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPoolConfig;

import com.liuahang.repo.util.JedisHelper;
import com.liuahang.repo.util.JedisSentinelPoolConfig;

@Service
public class RedisService {
	@Autowired
    private Environment env;
	
	
	 
	public JedisHelper getRedisHelper(){
		  JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
          jedisPoolConfig.setMaxTotal(Integer.parseInt(env.getProperty("spring.redis.pool.max-total")));
          jedisPoolConfig.setMaxIdle(Integer.parseInt(env.getProperty("spring.redis.pool.min-idle")));
          jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(env.getProperty("spring.redis.pool.max-wait")));
          JedisSentinelPoolConfig JedisSentinelPoolConfig  = new JedisSentinelPoolConfig(env.getProperty("redis.master.name"),env.getProperty("redis.sentinels"),jedisPoolConfig);
          JedisHelper helper = new JedisHelper(JedisSentinelPoolConfig);
		  helper.setJedisSentinelPoolConfig(JedisSentinelPoolConfig);
		return helper;
	}    
}
