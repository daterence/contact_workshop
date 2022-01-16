package com.ssf.workshop13.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.ssf.workshop13.util.Constants.BEAN_CONTACT_CACHE;

@Repository
public class ContactRepository {
    private static final Logger logger = LoggerFactory.getLogger(ContactRepository.class);
    private static final String CONTACT_ENTITY = "contactlist";
    @Autowired
    @Qualifier(BEAN_CONTACT_CACHE)
    private RedisTemplate<String, String> template;

    // search redisRepo for contact using id
    public Optional<String> get(String id) {
        String value = template.opsForValue().get(id.trim());
        return Optional.ofNullable(value);
    }

    public void save(String id, String value) {
        logger.info("Value as string >>>> " + value);
        template.opsForValue().set(id.trim(), value, 10L, TimeUnit.MINUTES);
    }


}
