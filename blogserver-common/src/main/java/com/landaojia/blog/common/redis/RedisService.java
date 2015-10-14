package com.landaojia.blog.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {

    @Autowired(required = false)
    private ShardedJedisPool shardedJedisPool;

    private <T> T execute(Function<ShardedJedis, T> fun) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return fun.callback(shardedJedis);
        } finally {
            if (null != shardedJedis) {
                shardedJedis.close();
            }
        }
    }

    public Boolean exists(final String key) {
        return this.execute(new Function<ShardedJedis, Boolean>() {
            @Override
            public Boolean callback(ShardedJedis e) {
                return e.exists(key);
            }
        });
    }

    public Long sadd(final String key, final String... members) {
        return this.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long callback(ShardedJedis e) {
                return e.sadd(key, members);
            }
        });
    }

    public Long expire(final String key, final Integer seconds) {
        return this.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long callback(ShardedJedis e) {
                return e.expire(key, seconds);
            }
        });
    }

    public Boolean sismember(final String key, final String member) {
        return this.execute(new Function<ShardedJedis, Boolean>() {
            @Override
            public Boolean callback(ShardedJedis e) {
                return e.sismember(key, member);
            }
        });
    }

}