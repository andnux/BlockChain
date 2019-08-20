package top.andnux.chain.service;


import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.ShhFilter;
import org.web3j.protocol.core.methods.response.DbGetHex;
import org.web3j.protocol.core.methods.response.DbGetString;
import org.web3j.protocol.core.methods.response.DbPutHex;
import org.web3j.protocol.core.methods.response.DbPutString;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.EthCompileLLL;
import org.web3j.protocol.core.methods.response.EthCompileSerpent;
import org.web3j.protocol.core.methods.response.EthCompileSolidity;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByHash;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.EthGetCompilers;
import org.web3j.protocol.core.methods.response.EthGetStorageAt;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthGetUncleCountByBlockHash;
import org.web3j.protocol.core.methods.response.EthGetUncleCountByBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetWork;
import org.web3j.protocol.core.methods.response.EthHashrate;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.EthMining;
import org.web3j.protocol.core.methods.response.EthProtocolVersion;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthSign;
import org.web3j.protocol.core.methods.response.EthSubmitHashrate;
import org.web3j.protocol.core.methods.response.EthSubmitWork;
import org.web3j.protocol.core.methods.response.EthSyncing;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.EthUninstallFilter;
import org.web3j.protocol.core.methods.response.NetListening;
import org.web3j.protocol.core.methods.response.NetPeerCount;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.ShhAddToGroup;
import org.web3j.protocol.core.methods.response.ShhHasIdentity;
import org.web3j.protocol.core.methods.response.ShhMessages;
import org.web3j.protocol.core.methods.response.ShhNewFilter;
import org.web3j.protocol.core.methods.response.ShhNewGroup;
import org.web3j.protocol.core.methods.response.ShhNewIdentity;
import org.web3j.protocol.core.methods.response.ShhUninstallFilter;
import org.web3j.protocol.core.methods.response.ShhVersion;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;

import java.math.BigInteger;


public interface EthWalletService extends WalletService {

    /**
     * 私钥导入
     *
     * @param uid        //用户ID
     * @param pwd        //密码
     * @param privateKey //私钥
     */

    void importByPrivateKey(Long uid, String name, String pwd, String privateKey) throws Exception;

    /**
     * 助记词导入
     *
     * @param uid      //用户ID
     * @param pwd      //密码
     * @param mnemonic //助记词
     */
    void importByMnemonic(Long uid, String name, String pwd, String mnemonic) throws Exception;


    /**
     * KeyStore导入
     *
     * @param uid      //用户ID
     * @param pwd      //密码
     * @param keyStore //keyStore json
     */
    void importByKeyStore(Long uid, String name, String pwd, String keyStore) throws Exception;

    /**
     * 获得当前价格
     *
     * @return
     */
    BigInteger gasPrice() throws Exception;

    /**
     * 获得当前余额
     *
     * @param address
     * @return
     * @throws Exception
     */
    BigInteger getBalance(String address) throws Exception;

    /**
     * 返回当前的客户端版本
     *
     * @return
     * @throws Exception
     */
    Web3ClientVersion web3ClientVersion() throws Exception;

    /**
     * 返回指定数据的Keccak-256（不同于标准的SHA3-256算法）哈希值。
     *
     * @param data
     * @return
     * @throws Exception
     */
    Web3Sha3 web3Sha3(String data) throws Exception;

    /**
     * 返回当前连接网络的ID。
     *
     * @return
     * @throws Exception
     */
    NetVersion netVersion() throws Exception;

    /**
     * 如果客户端处于监听网络连接的状态，该调用返回true。
     *
     * @return
     * @throws Exception
     */
    NetListening netListening() throws Exception;

    /**
     * 返回当前客户端所连接的对端节点数量。
     *
     * @return
     * @throws Exception
     */
    NetPeerCount netPeerCount() throws Exception;

    /**
     * 返回当前以太坊协议的版本。
     *
     * @return
     * @throws Exception
     */
    EthProtocolVersion ethProtocolVersion() throws Exception;

    /**
     * 返回客户端的coinbase地址。
     *
     * @return
     * @throws Exception
     */
    EthCoinbase ethCoinbase() throws Exception;

    /**
     * 对于已经同步的客户端，该调用返回一个描述同步状态的对象；对于未同步客户端，返回false。
     *
     * @return
     * @throws Exception
     */
    EthSyncing ethSyncing() throws Exception;

    /**
     * 如果客户端在积极挖矿则返回true。
     *
     * @return
     * @throws Exception
     */
    EthMining ethMining() throws Exception;

    /**
     * 返回节点挖矿时每秒可算出的哈希数量。
     *
     * @return
     * @throws Exception
     */
    EthHashrate ethHashrate() throws Exception;

    /**
     * 返回当前的gas价格，单位：wei。
     *
     * @return
     * @throws Exception
     */
    EthGasPrice ethGasPrice() throws Exception;

    /**
     * 返回客户端持有的地址列表。
     *
     * @return
     * @throws Exception
     */
    EthAccounts ethAccounts() throws Exception;

    /**
     * 返回最新块的编号。
     *
     * @return
     * @throws Exception
     */
    EthBlockNumber ethBlockNumber() throws Exception;

    /**
     * 返回指定地址账户的余额。
     *
     * @param address
     * @param defaultBlockParameter
     * @return
     * @throws Exception
     */
    EthGetBalance ethGetBalance(String address,
                                DefaultBlockParameter defaultBlockParameter) throws Exception;

    /**
     * 返回指定地址存储位置的值。
     *
     * @param address
     * @param position
     * @param defaultBlockParameter
     * @return
     * @throws Exception
     */
    EthGetStorageAt ethGetStorageAt(String address,
                                    BigInteger position, DefaultBlockParameter defaultBlockParameter) throws Exception;

    /**
     * 返回指定地址发生的交易数量。
     *
     * @param address
     * @param defaultBlockParameter
     * @return
     * @throws Exception
     */
    EthGetTransactionCount ethGetTransactionCount(String address,
                                                  DefaultBlockParameter defaultBlockParameter) throws Exception;

    /**
     * 返回指定块内的交易数量，使用哈希来指定块。
     *
     * @param blockHash
     * @return
     * @throws Exception
     */
    EthGetBlockTransactionCountByHash ethGetBlockTransactionCountByHash(
            String blockHash) throws Exception;

    /**
     * 返回指定块内的交易数量，使用块编号指定块。
     *
     * @param defaultBlockParameter
     * @return
     * @throws Exception
     */
    EthGetBlockTransactionCountByNumber ethGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter) throws Exception;

    /**
     * 返回指定块的叔伯数量，使用哈希指定块。
     *
     * @param blockHash
     * @return
     * @throws Exception
     */
    EthGetUncleCountByBlockHash ethGetUncleCountByBlockHash(String blockHash) throws Exception;

    /**
     * 返回指定块的叔伯数量，使用块编号指定块。
     *
     * @param defaultBlockParameter
     * @return
     * @throws Exception
     */
    EthGetUncleCountByBlockNumber ethGetUncleCountByBlockNumber(
            DefaultBlockParameter defaultBlockParameter) throws Exception;

    /**
     * 返回指定地址的代码。
     *
     * @param address
     * @param defaultBlockParameter
     * @return
     * @throws Exception
     */
    EthGetCode ethGetCode(String address, DefaultBlockParameter defaultBlockParameter) throws Exception;

    /**
     * 使用如下公式计算以太坊签名：sign(keccak256("\x19Ethereum Signed Message:\n" + len(message) + message)))。
     * <p>
     * 通过给消息添加一个前缀，可以让结果签名被识别为以太坊签名。这可以组织恶意DApp签名任意数据（例如交易）并使用 签名冒充受害者。
     * <p>
     * 需要指出的是，进行签名的地址必须是解锁的。
     *
     * @param address
     * @param sha3HashOfDataToSign
     * @return
     * @throws Exception
     */
    EthSign ethSign(String address, String sha3HashOfDataToSign) throws Exception;

    /**
     * 创建一个新的消息调用交易，如果数据字段中包含代码，则创建一个合约。
     *
     * @param transaction
     * @return
     * @throws Exception
     */
    EthSendTransaction ethSendTransaction(org.web3j.protocol.core.methods.request.Transaction transaction) throws Exception;

    /**
     * 为签名交易创建一个新的消息调用交易或合约。
     *
     * @param signedTransactionData
     * @return
     * @throws Exception
     */
    EthSendTransaction ethSendRawTransaction(
            String signedTransactionData) throws Exception;

    /**
     * 立刻执行一个新的消息调用，无需在区块链上创建交易。
     *
     * @param transaction
     * @param defaultBlockParameter
     * @return
     * @throws Exception
     */
    EthCall ethCall(org.web3j.protocol.core.methods.request.Transaction transaction,
                    DefaultBlockParameter defaultBlockParameter) throws Exception;

    /**
     * 执行并估算一个交易需要的gas用量。该次交易不会写入区块链。注意，由于多种原因，例如EVM的机制 及节点旳性能，估算的数值可能比实际用量大的多。
     *
     * @param transaction
     * @return
     * @throws Exception
     */
    EthEstimateGas ethEstimateGas(org.web3j.protocol.core.methods.request.Transaction transaction) throws Exception;

    /**
     * 返回具有指定哈希的块。
     *
     * @param blockHash
     * @param returnFullTransactionObjects
     * @return
     * @throws Exception
     */
    EthBlock ethGetBlockByHash(String blockHash, boolean returnFullTransactionObjects) throws Exception;

    /**
     * 返回指定编号的块。
     *
     * @param defaultBlockParameter
     * @param returnFullTransactionObjects
     * @return
     * @throws Exception
     */
    EthBlock ethGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects) throws Exception;


    /**
     * 返回指定哈希对应的交易。
     *
     * @param transactionHash
     * @return
     * @throws Exception
     */
    EthTransaction ethGetTransactionByHash(String transactionHash) throws Exception;


    /**
     * 返回指定块内具有指定索引序号的交易。
     *
     * @param blockHash
     * @param transactionIndex
     * @return
     * @throws Exception
     */
    EthTransaction ethGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) throws Exception;

    /**
     * 返回指定编号的块内具有指定索引序号的交易。
     *
     * @param defaultBlockParameter
     * @param transactionIndex
     * @return
     * @throws Exception
     */
    EthTransaction ethGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) throws Exception;

    /**
     * 返回指定交易的收据，使用哈希指定交易。
     *
     * @param transactionHash
     * @return
     * @throws Exception
     */
    EthGetTransactionReceipt ethGetTransactionReceipt(String transactionHash) throws Exception;


    /**
     * 返回具有指定哈希的块具有指定索引位置的叔伯。
     *
     * @param blockHash
     * @param transactionIndex
     * @return
     * @throws Exception
     */
    EthBlock ethGetUncleByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) throws Exception;

    /**
     * 返回具有指定编号的块内具有指定索引序号的叔伯。
     *
     * @param defaultBlockParameter
     * @param transactionIndex
     * @return
     * @throws Exception
     */
    EthBlock ethGetUncleByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) throws Exception;


    /**
     * 返回客户端中有效的编译器列表。
     *
     * @return
     * @throws Exception
     */
    EthGetCompilers ethGetCompilers() throws Exception;

    /**
     * 返回编译后的LLL代码。
     *
     * @param sourceCode
     * @return
     * @throws Exception
     */
    EthCompileLLL ethCompileLLL(String sourceCode) throws Exception;


    /**
     * 返回编译后的solidity代码。
     *
     * @param sourceCode
     * @return
     * @throws Exception
     */
    EthCompileSolidity ethCompileSolidity(String sourceCode) throws Exception;


    /**
     * 返回编译后的Serpent代码。
     *
     * @param sourceCode
     * @return
     * @throws Exception
     */
    EthCompileSerpent ethCompileSerpent(String sourceCode) throws Exception;


    /**
     * 基于给定的选项创建一个过滤器对象，接收状态变化时的通知。要检查状态是否变化， 请调用eth_getFilterChanges。
     * <p>
     * 关于特定主题过滤器的说明：主题是顺序相关的。如果一个交易的日志有主题[A, B]，那么将被 以下的主题过滤器匹配：
     * <p>
     * [] 任何主题
     * [A] 先匹配A主题
     * [null, B] 先匹配其他主题，再匹配B主题
     * [A, B] 先匹配A主题，再匹配B主题，最后匹配其他主题
     * [[A, B], [A, B]] "先匹配A主题或B主题，再匹配A主题或B主题，最后匹配其他主题
     *
     * @param ethFilter
     * @return
     * @throws Exception
     */
    org.web3j.protocol.core.methods.response.EthFilter ethNewFilter(org.web3j.protocol.core.methods.request.EthFilter ethFilter) throws Exception;


    /**
     * 在节点中创建一个过滤器，以便当新块生成时进行通知。要检查状态是否变化， 请调用eth_getFilterChanges。
     *
     * @return
     * @throws Exception
     */
    org.web3j.protocol.core.methods.response.EthFilter ethNewBlockFilter() throws Exception;


    /**
     * 在节点中创建一个过滤器，以便当产生挂起交易时进行通知。 要检查状态是否发生变化，请调用eth_getFilterChanges。
     *
     * @return
     * @throws Exception
     */
    org.web3j.protocol.core.methods.response.EthFilter ethNewPendingTransactionFilter() throws Exception;


    /**
     * 写在具有指定编号的过滤器。当不在需要监听时，总是需要执行该调用。
     * 另外，过滤器 如果在一定时间内未接收到eth_getFilterChanges调用会自动超时。
     *
     * @param filterId
     * @return
     * @throws Exception
     */
    EthUninstallFilter ethUninstallFilter(BigInteger filterId) throws Exception;


    /**
     * 轮询指定的过滤器，并返回自上次轮询之后新生成的日志数组。
     *
     * @param filterId
     * @return
     * @throws Exception
     */
    EthLog ethGetFilterChanges(BigInteger filterId) throws Exception;


    /**
     * 返回指定编号过滤器中的全部日志。
     *
     * @param filterId
     * @return
     * @throws Exception
     */
    EthLog ethGetFilterLogs(BigInteger filterId) throws Exception;


    /**
     * 返回指定过滤器中的所有日志。
     *
     * @param ethFilter
     * @return
     * @throws Exception
     */
    EthLog ethGetLogs(org.web3j.protocol.core.methods.request.EthFilter ethFilter) throws Exception;


    /**
     * 返回当前块的哈希、种子哈希、以及要满足的边界条件，即目标。
     *
     * @return
     * @throws Exception
     */
    EthGetWork ethGetWork() throws Exception;


    /**
     * 用于提交POW解决方案。
     *
     * @param nonce
     * @param headerPowHash
     * @param mixDigest
     * @return
     * @throws Exception
     */
    EthSubmitWork ethSubmitWork(String nonce, String headerPowHash, String mixDigest) throws Exception;

    /**
     * 用于提交挖矿的哈希速率。
     *
     * @param hashrate
     * @param clientId
     * @return
     * @throws Exception
     */

    EthSubmitHashrate ethSubmitHashrate(String hashrate, String clientId) throws Exception;


    /**
     * 在本地数据库中存入字符串。
     * <p>
     * 注意：这个调用已经被启用，在将来版本中将被移除
     *
     * @param databaseName
     * @param keyName
     * @param stringToStore
     * @return
     * @throws Exception
     */
    @Deprecated
    DbPutString dbPutString(String databaseName, String keyName, String stringToStore) throws Exception;


    /**
     * 从本地数据库读取字符串。
     * 注意：这个调用已被废弃，在将来版本中将被移除。
     *
     * @param databaseName
     * @param keyName
     * @return
     * @throws Exception
     */
    @Deprecated
    DbGetString dbGetString(String databaseName, String keyName) throws Exception;


    /**
     * 将二进制数据写入本地数据库。
     * <p>
     * 注意：这个调用已被废弃，在将来 版本中将被移除。
     *
     * @param databaseName
     * @param keyName
     * @param dataToStore
     * @return
     * @throws Exception
     */
    @Deprecated
    DbPutHex dbPutHex(String databaseName, String keyName, String dataToStore) throws Exception;

    /**
     * 从本地数据库中读取二进制数据。
     * <p>
     * 注意：这个调用已被废弃，在将来版本中将被移除。
     *
     * @param databaseName
     * @param keyName
     * @return
     * @throws Exception
     */
    @Deprecated
    DbGetHex dbGetHex(String databaseName, String keyName) throws Exception;


    /**
     * 发送whisper消息。
     *
     * @param shhPost
     * @return
     * @throws Exception
     */
    org.web3j.protocol.core.methods.response.ShhPost shhPost(org.web3j.protocol.core.methods.request.ShhPost shhPost) throws Exception;


    /**
     * 返回当前的whisper协议版本。
     *
     * @return
     * @throws Exception
     */
    ShhVersion shhVersion() throws Exception;


    /**
     * 在客户端创建一个新的whisper身份标识。
     *
     * @return
     * @throws Exception
     */
    ShhNewIdentity shhNewIdentity() throws Exception;


    /**
     * 检查客户端是否持有指定身份标识的私钥。
     *
     * @param identityAddress
     * @return
     * @throws Exception
     */
    ShhHasIdentity shhHasIdentity(String identityAddress) throws Exception;


    /**
     * 创建分组。
     *
     * @return
     * @throws Exception
     */
    ShhNewGroup shhNewGroup() throws Exception;


    /**
     * 将指定身份标识加入分组。
     *
     * @param identityAddress
     * @return
     * @throws Exception
     */
    ShhAddToGroup shhAddToGroup(String identityAddress) throws Exception;


    /**
     * 创建一个过滤器，以便在客户端接收到匹配的whisper消息时进行通知。
     *
     * @param shhFilter
     * @return
     * @throws Exception
     */
    ShhNewFilter shhNewFilter(ShhFilter shhFilter) throws Exception;

    /**
     * 写在指定编号的过滤器，当不再需要某个过滤器时，总应当执行此调用进行清理。
     * 此外， 如果在一定时间内没有收到shh_getFilterChanges请求，过滤器将超时。
     *
     * @param filterId
     * @return
     * @throws Exception
     */
    ShhUninstallFilter shhUninstallFilter(BigInteger filterId) throws Exception;

    /**
     * 轮询whisper过滤器，返回自上次调用依赖的新消息。
     * <p>
     * 注意：调用shh_getMessages将复位本调用使用的缓冲区，因此不会收到重复的消息。
     *
     * @param filterId
     * @return
     * @throws Exception
     */
    ShhMessages shhGetFilterChanges(BigInteger filterId) throws Exception;

    /**
     * 读取匹配指定过滤器的所有消息。与shh_getFilterChanges不同，本调用返回所有消息。
     *
     * @param filterId
     * @return
     * @throws Exception
     */
    ShhMessages shhGetMessages(BigInteger filterId) throws Exception;

}
