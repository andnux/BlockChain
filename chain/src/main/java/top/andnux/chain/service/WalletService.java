package top.andnux.chain.service;

import java.util.List;

import top.andnux.chain.entity.WalletEntity;

public interface WalletService {

    /**
     * 设置基地址
     *
     * @param url
     */
    void setBaseUrl(String url);


    /**
     * 创建一个钱包
     *
     * @return
     */
    WalletEntity generateWallet(Long uid, String name, String pwd) throws Exception;


    /**
     * 查询用户钱包
     *
     * @param uid //用户ID
     * @return
     */
    List<WalletEntity> queryAll(Long uid);

    /**
     * 更新钱包
     *
     * @param entity
     * @return
     */
    void update(WalletEntity entity);

    /**
     * 删除钱包
     *
     * @param entity
     * @return
     */
    void delete(WalletEntity entity);
}
