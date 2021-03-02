package top.xgoding.caching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
@RequestMapping("/cache")
@RestController
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("/save")
    public User save(String name) {
        final User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUserName(name);
        User user1 = userService.saveOrUpdate(user);
        return user1;
    }

    @GetMapping("/get")
    public User get(String userId) {
        final User user = userService.get(userId);
        return user;
    }

    @GetMapping("/delete")
    public boolean delete(String userId) {
        final boolean delete = userService.delete(userId);
        return delete;
    }
}
