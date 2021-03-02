package top.xgoding.caching;

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
public interface UserService {
    User saveOrUpdate(User user);

    User get(String userId);

    boolean delete(String userId);
}
