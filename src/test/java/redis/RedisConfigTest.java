package redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest
public class RedisConfigTest {

    //@Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    //@Test
    public void testReactiveRedisTemplateBean() {
        assertNotNull(reactiveRedisTemplate, "El bean ReactiveRedisTemplate no debería ser nulo");
    }
}