package stark.dataworks.basic.data.redis;

import org.springframework.data.redis.core.RedisTemplate;
import stark.dataworks.basic.data.json.JsonSerializer;

import java.util.List;

public class RedisQuickOperation
{
    private final RedisTemplate<String, String> redisTemplate;

    public RedisQuickOperation(RedisTemplate<String, String> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    public String get(String key)
    {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key)
    {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public void set(String key, String value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> void set(String key, List<T> list)
    {
        String listJson = JsonSerializer.serialize(list);
        set(key, listJson);
    }

    public <T> List<T> getList(String key, Class<T> clazz)
    {
        String listJson = redisTemplate.opsForValue().get(key);
        return JsonSerializer.deserializeList(listJson, clazz);
    }
}
