package top.andnux.chain.service.impl;

import java.util.List;

import top.andnux.chain.entity.UserEntity;
import top.andnux.chain.service.UserService;
import top.andnux.chain.sqlite.DaoSession;
import top.andnux.chain.sqlite.SQLiteManager;
import top.andnux.chain.sqlite.UserEntityDao;

public class UserServiceImpl implements UserService {

    @Override
    public void insert(UserEntity entity) {
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        UserEntityDao userEntityDao = daoSession.getUserEntityDao();
        userEntityDao.insert(entity);
    }

    @Override
    public void update(UserEntity entity) {
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        UserEntityDao userEntityDao = daoSession.getUserEntityDao();
        userEntityDao.update(entity);
    }

    @Override
    public void delete(UserEntity entity) {
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        UserEntityDao userEntityDao = daoSession.getUserEntityDao();
        userEntityDao.delete(entity);
    }

    @Override
    public UserEntity getCurrentUser() {
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        UserEntityDao userEntityDao = daoSession.getUserEntityDao();
        return userEntityDao.queryBuilder()
                .where(UserEntityDao.Properties.Is_current.eq(true))
                .unique();
    }

    @Override
    public void setCurrentUser(UserEntity entity) {
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        UserEntityDao userEntityDao = daoSession.getUserEntityDao();
        List<UserEntity> entities = userEntityDao.loadAll();
        for (UserEntity userEntity : entities) {
            userEntity.setIs_current(false);
        }
        entity.setIs_current(true);
        userEntityDao.update(entity);
        userEntityDao.updateInTx(entities);
    }
}
