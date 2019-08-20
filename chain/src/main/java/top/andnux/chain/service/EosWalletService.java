package top.andnux.chain.service;

import java.util.List;
import java.util.Map;

import top.andnux.eos.common.WalletKeyType;
import top.andnux.eos.common.transaction.PackedTransaction;
import top.andnux.eos.common.transaction.SignedPackedTransaction;
import top.andnux.eos.request.chain.transaction.PushTransactionRequest;
import top.andnux.eos.response.chain.AbiBinToJson;
import top.andnux.eos.response.chain.AbiJsonToBin;
import top.andnux.eos.response.chain.Block;
import top.andnux.eos.response.chain.ChainInfo;
import top.andnux.eos.response.chain.RequiredKeys;
import top.andnux.eos.response.chain.TableRow;
import top.andnux.eos.response.chain.abi.Abi;
import top.andnux.eos.response.chain.account.Account;
import top.andnux.eos.response.chain.code.Code;
import top.andnux.eos.response.chain.currencystats.CurrencyStats;
import top.andnux.eos.response.chain.transaction.PushedTransaction;
import top.andnux.eos.response.history.action.Actions;
import top.andnux.eos.response.history.controlledaccounts.ControlledAccounts;
import top.andnux.eos.response.history.keyaccounts.KeyAccounts;
import top.andnux.eos.response.history.transaction.Transaction;

public interface EosWalletService extends WalletService {

    /**
     * 私钥导入
     *
     * @param uid        //用户ID
     * @param pwd        //密码
     * @param privateKey //私钥
     */

    void importByPrivateKey(Long uid, String pwd, String privateKey) throws Exception;

    /**
     * 获得链信息
     *
     * @return
     */
    ChainInfo getChainInfo();

    /**
     * 获得块信息
     *
     * @param blockNumberOrId
     * @return
     */
    Block getBlock(String blockNumberOrId);

    /**
     * 获得账户信息
     *
     * @param accountName
     * @return
     */
    Account getAccount(String accountName);

    /**
     * 调用返回指定账号所托管合约的abi描述信息。
     *
     * @param accountName
     * @return
     */
    Abi getAbi(String accountName);

    /**
     * 调用返回指定账号所托管合约的代码信息
     *
     * @param accountName
     * @return
     */
    Code getCode(String accountName);

    /**
     * 调用指定多索引数据表的数据行。
     *
     * @param scope
     * @param code
     * @param table
     * @return
     */
    TableRow getTableRows(String scope, String code, String table);

    /**
     * 调用返回指定账户的代币余额信息
     * @param code
     * @param accountName
     * @param symbol
     * @return
     */
    List<String> getCurrencyBalance(String code, String accountName, String symbol);

    /**
     * 调用将合约动作调用序列化为16进制字符串，以便应用于 push_action调用。
     * @param code
     * @param action
     * @param binargs
     * @return
     */
    AbiBinToJson abiBinToJson(String code, String action, String binargs);

    /**
     * 调用将16进制码流反序列化为合约的动作调用。
     * @param code
     * @param action
     * @param args
     * @param <T>
     * @return
     */
    <T> AbiJsonToBin abiJsonToBin(String code, String action, T args);

    /**
     * 调用将指定的交易提交到链上。
     * @param compression
     * @param packedTransaction
     * @return
     */
    PushedTransaction pushTransaction(String compression, SignedPackedTransaction packedTransaction);

    /**
     * 调用将一组交易提交到链上。
     * @param pushTransactionRequests
     * @return
     */
    List<PushedTransaction> pushTransactions(List<PushTransactionRequest> pushTransactionRequests);

    /**
     * 调用返回签名一个交易时需要的公钥清单。
     * @param transaction
     * @param keys
     * @return
     */
    RequiredKeys getRequiredKeys(PackedTransaction transaction, List<String> keys);

    /**
     * 调用返回指定代币的总体信息。
     * @param code
     * @param symbol
     * @return
     */
    Map<String, CurrencyStats> getCurrencyStats(String code, String symbol);

    /**
     * 创建钱包
     * @param walletName
     * @return
     */
    String createWallet(String walletName);

    /**
     * 打开钱包
     *
     * @param walletName
     */
    void openWallet(String walletName);

    /**
     * 锁钱包
     *
     * @param walletName
     */
    void lockWallet(String walletName);

    /**
     * 锁全部钱包
     */
    void lockAllWallets();

    /**
     * 解锁钱包
     *
     * @param walletName
     * @param walletPassword
     */
    void unlockWallet(String walletName, String walletPassword);

    /**
     * 导入私钥到钱包
     *
     * @param walletName
     * @param walletKey
     */
    void importKeyIntoWallet(String walletName, String walletKey);

    /**
     * 查询所有钱包
     *
     * @return
     */
    List<String> listWallets();

    /**
     * 查询所有Keys
     *
     * @return
     */
    List<List<String>> listKeys();

    /**
     * 查询所有公钥
     * @return
     */
    List<String> getPublicKeys();

    /**
     * 签名转账
     * @param unsignedTransaction
     * @param publicKeys
     * @param chainId
     * @return
     */
    SignedPackedTransaction signTransaction(PackedTransaction unsignedTransaction, List<String> publicKeys, String chainId);

    /**
     * 设置钱包超时时间
     * @param timeout
     */
    void setWalletTimeout(Integer timeout);

    /**
     * 签名加密
     * @param digest
     * @param publicKey
     * @return
     */
    String signDigest(String digest, String publicKey);

    /**
     * 创建密钥
     * @param walletName
     * @param walletKeyType
     * @return
     */
    String createKey(String walletName, WalletKeyType walletKeyType);

    /**
     * 调用返回指定合约上执行过的动作。
     * @param accountName
     * @param pos
     * @param offset
     * @return
     */
    Actions getActions(String accountName, Integer pos, Integer offset);

    /**
     * 调用返回指定的交易数据。
     * @param id
     * @return
     */
    Transaction getTransaction(String id);

    /**
     * 调用返回指定公钥所关联的账号。
     * @param publicKey
     * @return
     */
    KeyAccounts getKeyAccounts(String publicKey);

    /**
     * 调用返回指定账号的受控账号。
     * @param controllingAccountName
     * @return
     */
    ControlledAccounts getControlledAccounts(String controllingAccountName);

}
