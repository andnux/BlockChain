package top.andnux.chain.service.impl;

import java.util.List;

import top.andnux.chain.entity.WalletEntity;
import top.andnux.chain.service.WalletService;
import top.andnux.chain.sqlite.DaoSession;
import top.andnux.chain.sqlite.SQLiteManager;
import top.andnux.chain.sqlite.WalletEntityDao;

public abstract class AbstractWalletService implements WalletService {

    String mBaseUrl;

    @Override
    public void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Override
    public List<WalletEntity> queryAll(Long uid) {
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        WalletEntityDao walletEntityDao = daoSession.getWalletEntityDao();
        return walletEntityDao.queryBuilder()
                .where(WalletEntityDao.Properties.User_id.eq(uid))
                .build().list();
    }

    @Override
    public void update(WalletEntity entity) {
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        WalletEntityDao walletEntityDao = daoSession.getWalletEntityDao();
        walletEntityDao.update(entity);
    }

    @Override
    public void delete(WalletEntity entity) {
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        WalletEntityDao walletEntityDao = daoSession.getWalletEntityDao();
        walletEntityDao.delete(entity);
    }
}
