package stark.dataworks.basic.data.redis;

import org.springframework.data.redis.core.RedisTemplate;
import stark.dataworks.basic.data.json.JsonSerializer;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    public void set(String key, String value, long timeoutInMilliseconds)
    {
        redisTemplate.opsForValue().set(key, value, timeoutInMilliseconds, TimeUnit.MILLISECONDS);
    }

    public void set(String key, String value, long timeout, TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public void set(String key, Object value, long timeoutInMilliseconds)
    {
        String valueJson = JsonSerializer.serialize(value);
        set(key, valueJson, timeoutInMilliseconds);
    }

    public void set(String key, Object value, long timeout, TimeUnit timeUnit)
    {
        String valueJson = JsonSerializer.serialize(value);
        set(key, valueJson, timeout, timeUnit);
    }

    public boolean containsKey(String key)
    {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setAdd(String key, String... values)
    {
        redisTemplate.opsForSet().add(key, values);
    }

    public boolean setContains(String key, String value)
    {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    public boolean expire(String key, long timeout, TimeUnit timeUnit)
    {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, timeUnit));
    }

    public Long setCount(String key)
    {
        return redisTemplate.opsForSet().size(key);
    }

    public Set<String> setGetAll(String key)
    {
        return redisTemplate.opsForSet().members(key);
    }
}
