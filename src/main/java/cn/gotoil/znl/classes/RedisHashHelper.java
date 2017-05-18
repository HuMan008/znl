package cn.gotoil.znl.classes;


import cn.gotoil.bill.tools.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;


@Component
public class RedisHashHelper {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void set(String key, Object object, Set<String> exceptFields) {
        Map<String, Object> map = ObjectHelper.getObjectMapper().convertValue(object, Map.class);

        if (exceptFields != null && exceptFields.size() > 0) {
            map.entrySet().removeIf(e -> exceptFields.contains(e.getKey()));
        }
        map.entrySet().removeIf(e -> e.getValue() == null);
        map.entrySet().forEach(e -> e.setValue(e.getValue().toString()));
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, map);
    }

    public <T> T get(String key, Class<T> tClass) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Map entries = hashOperations.entries(key);
        if (entries == null || entries.size() < 1) {
            return null;
        }
        return ObjectHelper.getObjectMapper().convertValue(entries, tClass);
    }

}
