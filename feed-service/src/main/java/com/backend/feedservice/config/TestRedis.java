package com.backend.feedservice.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Connection;

public class TestRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://default:"+ System.getenv("REDIS_PASSWORD") +"@redis-18837.c14.us-east-1-3.ec2.redns.redis-cloud.com:18837");
        jedis.connect();
    }
}