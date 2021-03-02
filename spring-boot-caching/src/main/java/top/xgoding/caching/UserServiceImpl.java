package top.xgoding.caching;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * </p>
 *
 * @package: top.xgoding.caching
 * @description:
 * @author: yxguang
 * @date: 2021/3/1
 * @version: V1.0
 * @modified: yxguang
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {
    private static final Map<String, User> datas = new ConcurrentHashMap<String, User>();
    @Override
    @Cacheable(key = "#user.id",value = "user",sync = false)
    public User saveOrUpdate(User user) {
        datas.put(user.getId(), user);
        log.info("Save or update user : {}", user);
        return user;
    }

    @Override
    @Cacheable(key = "#userId", value = "user")
    public User get(String userId) {
        log.info("Get user by id : {}", userId);
        return datas.get(userId);
    }

    @Override
    @CacheEvict(key = "#userId",value = "user")
    public boolean delete(String userId) {
        log.info("Delete user by id : {}", userId);
        final User remove = datas.remove(userId);
        return true;
    }
}
