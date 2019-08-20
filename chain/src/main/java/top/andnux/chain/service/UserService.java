package top.andnux.chain.service;

import top.andnux.chain.entity.UserEntity;

public interface UserService {

    /**
     * 新增一个用户
     *
     * @param entity
     */
    void insert(UserEntity entity);

    /**
     * 更新一个用户
     *
     * @param entity
     */
    void update(UserEntity entity);

    /**
     * 删除一个用户
     *
     * @param entity
     */
    void delete(UserEntity entity);

    /**
     * 获得当前用户
     *
     * @return
     */
    UserEntity getCurrentUser();

    /**
     * 设置为当前用户
     *
     * @param entity
     */
    void setCurrentUser(UserEntity entity);
}
