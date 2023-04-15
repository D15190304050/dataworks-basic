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

    public <T> T get(String key, Class<T> clazz)
    {
        String value = get(key);
        return JsonSerializer.deserialize(value, clazz);
    }

    public boolean delete(String key)
    {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public void set(String key, String value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value)
    {
        String valueJson = JsonSerializer.serialize(value);
        set(key, valueJson);
    }

    public <T> List<T> getList(String key, Class<T> clazz)
    {
        String listJson = redisTemplate.opsForValue().get(key);
        return JsonSerializer.deserializeList(listJson, clazz);
    }
}
