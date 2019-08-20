package top.andnux.chain.service.impl;

import android.app.Application;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
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
import org.web3j.protocol.core.methods.response.EthFilter;
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
import org.web3j.protocol.core.methods.response.ShhPost;
import org.web3j.protocol.core.methods.response.ShhUninstallFilter;
import org.web3j.protocol.core.methods.response.ShhVersion;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import io.github.novacrypto.bip39.SeedCalculator;
import io.github.novacrypto.bip39.wordlists.English;
import io.github.novacrypto.hashing.Sha256;
import top.andnux.chain.Utils;
import top.andnux.chain.entity.WalletEntity;
import top.andnux.chain.service.EthWalletService;
import top.andnux.chain.sqlite.DaoSession;
import top.andnux.chain.sqlite.SQLiteManager;
import top.andnux.chain.sqlite.WalletEntityDao;

public class EthWalletServiceImpl extends AbstractWalletService implements EthWalletService {

    @Override
    @SuppressWarnings("all")
    public void importByMnemonic(Long uid, String name, String pwd, String mnemonic) throws Exception {
        WalletEntity walletEntity = new WalletEntity();
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        WalletEntityDao walletEntityDao = daoSession.getWalletEntityDao();
        boolean b = MnemonicUtils.validateMnemonic(mnemonic);
        if (!b) {
            throw new RuntimeException("助记词格式错误");
        }
        Application app = Utils.getApp();
        File dir = app.getFilesDir();
        File keystoreDir = new File(dir, "keystore");
        if (!keystoreDir.exists()) {
            keystoreDir.mkdirs();
        }
        List<String> mnemonicList = Arrays.asList(mnemonic.split(" "));
        byte[] seed = new SeedCalculator()
                .withWordsFromWordList(English.INSTANCE)
                .calculateSeed(mnemonicList, pwd);
        ECKeyPair ecKeyPair = ECKeyPair.create(Sha256.sha256(seed));
        String newWalletFile = WalletUtils.generateWalletFile(pwd, ecKeyPair, keystoreDir, false);
        File file = new File(keystoreDir, newWalletFile);
        String keystoreJson = Utils.readFileString(file);
        walletEntity.setChain("ETH");
        walletEntity.setUser_id(uid);
        walletEntity.setState(WalletEntity.State.CREATE);
        walletEntity.setWallet_name(name);
        walletEntity.setKeystore(keystoreJson);
        Credentials credentials = WalletUtils.loadCredentials(pwd, file);
        walletEntity.setAddress(credentials.getAddress());
        walletEntity.setPrivate_key(credentials.getEcKeyPair().getPrivateKey().toString(16));
        walletEntity.setPublic_key(credentials.getEcKeyPair().getPublicKey().toString(16));
        String newMnemonic = MnemonicUtils.generateMnemonic(credentials.getEcKeyPair().getPrivateKey().toByteArray());
        walletEntity.setMnemonic(newMnemonic);
        walletEntityDao.insert(walletEntity);
    }

    @Override
    @SuppressWarnings("all")
    public void importByKeyStore(Long uid, String name, String pwd, String keyStore) throws Exception {
        WalletEntity walletEntity = new WalletEntity();
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        WalletEntityDao walletEntityDao = daoSession.getWalletEntityDao();
        Application app = Utils.getApp();
        File dir = app.getFilesDir();
        File keystoreDir = new File(dir, "keystore");
        if (!keystoreDir.exists()) {
            keystoreDir.mkdirs();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        WalletFile walletFile = null;
        String password = pwd.toString();
        walletFile = objectMapper.readValue(keyStore, WalletFile.class);
        ECKeyPair keyPair = Wallet.decrypt(password, walletFile);
        String privateKey = keyPair.getPrivateKey().toString(16);
        String publicKey = keyPair.getPublicKey().toString(16);
        Credentials credentials = Credentials.create(privateKey, publicKey);
        walletEntity.setChain("ETH");
        walletEntity.setUser_id(uid);
        walletEntity.setState(WalletEntity.State.CREATE);
        walletEntity.setWallet_name(name);
        walletEntity.setKeystore(keyStore);
        walletEntity.setAddress(credentials.getAddress());
        walletEntity.setPrivate_key(credentials.getEcKeyPair().getPrivateKey().toString(16));
        walletEntity.setPublic_key(credentials.getEcKeyPair().getPublicKey().toString(16));
        String newMnemonic = MnemonicUtils.generateMnemonic(credentials.getEcKeyPair().getPrivateKey().toByteArray());
        walletEntity.setMnemonic(newMnemonic);
        walletEntityDao.insert(walletEntity);
    }


    @Override
    @SuppressWarnings("all")
    public WalletEntity generateWallet(Long uid, String name, String pwd) throws Exception {
        WalletEntity walletEntity = new WalletEntity();
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        WalletEntityDao walletEntityDao = daoSession.getWalletEntityDao();
        Application app = Utils.getApp();
        if (app == null) return null;
        File dir = app.getFilesDir();
        File keystoreDir = new File(dir, "keystore");
        if (!keystoreDir.exists()) {
            keystoreDir.mkdirs();
        }
        String newWalletFile = WalletUtils.generateLightNewWalletFile(pwd, keystoreDir);
        File file = new File(keystoreDir, newWalletFile);
        String keystoreJson = Utils.readFileString(file);
        walletEntity.setChain("ETH");
        walletEntity.setUser_id(uid);
        walletEntity.setState(WalletEntity.State.CREATE);
        walletEntity.setWallet_name(name);
        walletEntity.setKeystore(keystoreJson);
        Credentials credentials = WalletUtils.loadCredentials(pwd, file);
        walletEntity.setAddress(credentials.getAddress());
        walletEntity.setPrivate_key(credentials.getEcKeyPair().getPrivateKey().toString(16));
        walletEntity.setPublic_key(credentials.getEcKeyPair().getPublicKey().toString(16));
        //助记词无法在其他钱包导入
        String mnemonic = MnemonicUtils.generateMnemonic(credentials.getEcKeyPair().getPrivateKey().toByteArray());
        walletEntity.setMnemonic(mnemonic);
        walletEntityDao.insert(walletEntity);
        return walletEntity;
    }

    @Override
    @SuppressWarnings("all")
    public void importByPrivateKey(Long uid, String name, String pwd, String privateKey) throws Exception {
        WalletEntity walletEntity = new WalletEntity();
        DaoSession daoSession = SQLiteManager.getInstance().getDaoSession();
        WalletEntityDao walletEntityDao = daoSession.getWalletEntityDao();
        Application app = Utils.getApp();
        if (app == null) return;
        File dir = app.getFilesDir();
        File keystoreDir = new File(dir, "keystore");
        if (!keystoreDir.exists()) {
            keystoreDir.mkdirs();
        }
        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey.getBytes());
        String newWalletFile = WalletUtils.generateWalletFile(pwd, ecKeyPair, keystoreDir, false);
        File file = new File(keystoreDir, newWalletFile);
        String keystoreJson = Utils.readFileString(file);
        walletEntity.setChain("ETH");
        walletEntity.setUser_id(uid);
        walletEntity.setState(WalletEntity.State.CREATE);
        walletEntity.setWallet_name(name);
        walletEntity.setKeystore(keystoreJson);
        Credentials credentials = WalletUtils.loadCredentials(pwd, file);
        List<WalletEntity> entities = queryAll(uid);
        for (WalletEntity entity : entities) {
            if (entity.getAddress().equalsIgnoreCase(credentials.getAddress())) {
                throw new RuntimeException("该账号已经存在，无需导入");
            }
        }
        walletEntity.setAddress(credentials.getAddress());
        walletEntity.setPrivate_key(credentials.getEcKeyPair().getPrivateKey().toString(16));
        walletEntity.setPublic_key(credentials.getEcKeyPair().getPublicKey().toString(16));
        //助记词无法在其他钱包导入
        String mnemonic = MnemonicUtils.generateMnemonic(credentials.getEcKeyPair().getPrivateKey().toByteArray());
        walletEntity.setMnemonic(mnemonic);
        walletEntityDao.insert(walletEntity);
    }

    private Web3j getWeb3j() {
        return Web3j.build(new HttpService(mBaseUrl));
    }

    @Override
    public BigInteger gasPrice() throws Exception {
        return getWeb3j().ethGasPrice().send().getGasPrice();
    }

    @Override
    public BigInteger getBalance(String address) throws Exception {
        return getWeb3j().ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
    }

    @Override
    public Web3ClientVersion web3ClientVersion() throws Exception {
        return getWeb3j().web3ClientVersion().send();
    }

    @Override
    public Web3Sha3 web3Sha3(String data) throws Exception {
        return getWeb3j().web3Sha3(data).send();
    }

    @Override
    public NetVersion netVersion() throws Exception {
        return getWeb3j().netVersion().send();
    }

    @Override
    public NetListening netListening() throws Exception {
        return getWeb3j().netListening().send();
    }

    @Override
    public NetPeerCount netPeerCount() throws Exception {
        return getWeb3j().netPeerCount().send();
    }

    @Override
    public EthProtocolVersion ethProtocolVersion() throws Exception {
        return getWeb3j().ethProtocolVersion().send();
    }

    @Override
    public EthCoinbase ethCoinbase() throws Exception {
        return getWeb3j().ethCoinbase().send();
    }

    @Override
    public EthSyncing ethSyncing() throws Exception {
        return getWeb3j().ethSyncing().send();
    }

    @Override
    public EthMining ethMining() throws Exception {
        return getWeb3j().ethMining().send();
    }

    @Override
    public EthHashrate ethHashrate() throws Exception {
        return getWeb3j().ethHashrate().send();
    }

    @Override
    public EthGasPrice ethGasPrice() throws Exception {
        return getWeb3j().ethGasPrice().send();
    }

    @Override
    public EthAccounts ethAccounts() throws Exception {
        return getWeb3j().ethAccounts().send();
    }

    @Override
    public EthBlockNumber ethBlockNumber() throws Exception {
        return getWeb3j().ethBlockNumber().send();
    }

    @Override
    public EthGetBalance ethGetBalance(String address, DefaultBlockParameter defaultBlockParameter) throws Exception {
        return getWeb3j().ethGetBalance(address, defaultBlockParameter).send();
    }

    @Override
    public EthGetStorageAt ethGetStorageAt(String address, BigInteger position, DefaultBlockParameter defaultBlockParameter) throws Exception {
        return getWeb3j().ethGetStorageAt(address, position, defaultBlockParameter).send();
    }

    @Override
    public EthGetTransactionCount ethGetTransactionCount(String address, DefaultBlockParameter defaultBlockParameter) throws Exception {
        return getWeb3j().ethGetTransactionCount(address, defaultBlockParameter).send();
    }

    @Override
    public EthGetBlockTransactionCountByHash ethGetBlockTransactionCountByHash(String blockHash) throws Exception {
        return getWeb3j().ethGetBlockTransactionCountByHash(blockHash).send();
    }

    @Override
    public EthGetBlockTransactionCountByNumber ethGetBlockTransactionCountByNumber(DefaultBlockParameter defaultBlockParameter) throws Exception {
        return getWeb3j().ethGetBlockTransactionCountByNumber(defaultBlockParameter).send();
    }

    @Override
    public EthGetUncleCountByBlockHash ethGetUncleCountByBlockHash(String blockHash) throws Exception {
        return getWeb3j().ethGetUncleCountByBlockHash(blockHash).send();
    }

    @Override
    public EthGetUncleCountByBlockNumber ethGetUncleCountByBlockNumber(DefaultBlockParameter defaultBlockParameter) throws Exception {
        return getWeb3j().ethGetUncleCountByBlockNumber(defaultBlockParameter).send();
    }

    @Override
    public EthGetCode ethGetCode(String address, DefaultBlockParameter defaultBlockParameter) throws Exception {
        return getWeb3j().ethGetCode(address, defaultBlockParameter).send();
    }

    @Override
    public EthSign ethSign(String address, String sha3HashOfDataToSign) throws Exception {
        return getWeb3j().ethSign(address, sha3HashOfDataToSign).send();
    }

    @Override
    public EthSendTransaction ethSendTransaction(org.web3j.protocol.core.methods.request.Transaction transaction) throws Exception {
        return getWeb3j().ethSendTransaction(transaction).send();
    }

    @Override
    public EthSendTransaction ethSendRawTransaction(String signedTransactionData) throws Exception {
        return getWeb3j().ethSendRawTransaction(signedTransactionData).send();
    }

    @Override
    public EthCall ethCall(org.web3j.protocol.core.methods.request.Transaction transaction, DefaultBlockParameter defaultBlockParameter) throws Exception {
        return getWeb3j().ethCall(transaction, defaultBlockParameter).send();
    }

    @Override
    public EthEstimateGas ethEstimateGas(org.web3j.protocol.core.methods.request.Transaction transaction) throws Exception {
        return getWeb3j().ethEstimateGas(transaction).send();
    }

    @Override
    public EthBlock ethGetBlockByHash(String blockHash, boolean returnFullTransactionObjects) throws Exception {
        return getWeb3j().ethGetBlockByHash(blockHash, returnFullTransactionObjects).send();
    }

    @Override
    public EthBlock ethGetBlockByNumber(DefaultBlockParameter defaultBlockParameter, boolean returnFullTransactionObjects) throws Exception {
        return getWeb3j().ethGetBlockByNumber(defaultBlockParameter, returnFullTransactionObjects).send();
    }

    @Override
    public EthTransaction ethGetTransactionByHash(String transactionHash) throws Exception {
        return getWeb3j().ethGetTransactionByHash(transactionHash).send();
    }

    @Override
    public EthTransaction ethGetTransactionByBlockHashAndIndex(String blockHash, BigInteger transactionIndex) throws Exception {
        return getWeb3j().ethGetTransactionByBlockHashAndIndex(blockHash, transactionIndex).send();
    }

    @Override
    public EthTransaction ethGetTransactionByBlockNumberAndIndex(DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) throws Exception {
        return getWeb3j().ethGetTransactionByBlockNumberAndIndex(defaultBlockParameter, transactionIndex).send();
    }

    @Override
    public EthGetTransactionReceipt ethGetTransactionReceipt(String transactionHash) throws Exception {
        return getWeb3j().ethGetTransactionReceipt(transactionHash).send();
    }

    @Override
    public EthBlock ethGetUncleByBlockHashAndIndex(String blockHash, BigInteger transactionIndex) throws Exception {
        return getWeb3j().ethGetUncleByBlockHashAndIndex(blockHash, transactionIndex).send();
    }

    @Override
    public EthBlock ethGetUncleByBlockNumberAndIndex(DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) throws Exception {
        return getWeb3j().ethGetUncleByBlockNumberAndIndex(defaultBlockParameter, transactionIndex).send();
    }

    @Override
    public EthGetCompilers ethGetCompilers() throws Exception {
        return getWeb3j().ethGetCompilers().send();
    }

    @Override
    public EthCompileLLL ethCompileLLL(String sourceCode) throws Exception {
        return getWeb3j().ethCompileLLL(sourceCode).send();
    }

    @Override
    public EthCompileSolidity ethCompileSolidity(String sourceCode) throws Exception {
        return getWeb3j().ethCompileSolidity(sourceCode).send();
    }

    @Override
    public EthCompileSerpent ethCompileSerpent(String sourceCode) throws Exception {
        return getWeb3j().ethCompileSerpent(sourceCode).send();
    }

    @Override
    public EthFilter ethNewFilter(org.web3j.protocol.core.methods.request.EthFilter ethFilter) throws Exception {
        return getWeb3j().ethNewFilter(ethFilter).send();
    }

    @Override
    public EthFilter ethNewBlockFilter() throws Exception {
        return getWeb3j().ethNewBlockFilter().send();
    }

    @Override
    public EthFilter ethNewPendingTransactionFilter() throws Exception {
        return getWeb3j().ethNewPendingTransactionFilter().send();
    }

    @Override
    public EthUninstallFilter ethUninstallFilter(BigInteger filterId) throws Exception {
        return getWeb3j().ethUninstallFilter(filterId).send();
    }

    @Override
    public EthLog ethGetFilterChanges(BigInteger filterId) throws Exception {
        return getWeb3j().ethGetFilterChanges(filterId).send();
    }

    @Override
    public EthLog ethGetFilterLogs(BigInteger filterId) throws Exception {
        return getWeb3j().ethGetFilterLogs(filterId).send();
    }

    @Override
    public EthLog ethGetLogs(org.web3j.protocol.core.methods.request.EthFilter ethFilter) throws Exception {
        return getWeb3j().ethGetLogs(ethFilter).send();
    }

    @Override
    public EthGetWork ethGetWork() throws Exception {
        return getWeb3j().ethGetWork().send();
    }

    @Override
    public EthSubmitWork ethSubmitWork(String nonce, String headerPowHash, String mixDigest) throws Exception {
        return getWeb3j().ethSubmitWork(nonce, headerPowHash, mixDigest).send();
    }

    @Override
    public EthSubmitHashrate ethSubmitHashrate(String hashrate, String clientId) throws Exception {
        return getWeb3j().ethSubmitHashrate(hashrate, clientId).send();
    }

    @Override
    public DbPutString dbPutString(String databaseName, String keyName, String stringToStore) throws Exception {
        return getWeb3j().dbPutString(databaseName, keyName, stringToStore).send();
    }

    @Override
    public DbGetString dbGetString(String databaseName, String keyName) throws Exception {
        return getWeb3j().dbGetString(databaseName, keyName).send();
    }

    @Override
    public DbPutHex dbPutHex(String databaseName, String keyName, String dataToStore) throws Exception {
        return getWeb3j().dbPutHex(databaseName, keyName, dataToStore).send();
    }

    @Override
    public DbGetHex dbGetHex(String databaseName, String keyName) throws Exception {
        return getWeb3j().dbGetHex(databaseName, keyName).send();
    }

    @Override
    public ShhPost shhPost(org.web3j.protocol.core.methods.request.ShhPost shhPost) throws Exception {
        return getWeb3j().shhPost(shhPost).send();
    }

    @Override
    public ShhVersion shhVersion() throws Exception {
        return getWeb3j().shhVersion().send();
    }

    @Override
    public ShhNewIdentity shhNewIdentity() throws Exception {
        return getWeb3j().shhNewIdentity().send();
    }

    @Override
    public ShhHasIdentity shhHasIdentity(String identityAddress) throws Exception {
        return getWeb3j().shhHasIdentity(identityAddress).send();
    }

    @Override
    public ShhNewGroup shhNewGroup() throws Exception {
        return getWeb3j().shhNewGroup().send();
    }

    @Override
    public ShhAddToGroup shhAddToGroup(String identityAddress) throws Exception {
        return getWeb3j().shhAddToGroup(identityAddress).send();
    }

    @Override
    public ShhNewFilter shhNewFilter(ShhFilter shhFilter) throws Exception {
        return getWeb3j().shhNewFilter(shhFilter).send();
    }

    @Override
    public ShhUninstallFilter shhUninstallFilter(BigInteger filterId) throws Exception {
        return getWeb3j().shhUninstallFilter(filterId).send();
    }

    @Override
    public ShhMessages shhGetFilterChanges(BigInteger filterId) throws Exception {
        return getWeb3j().shhGetFilterChanges(filterId).send();
    }

    @Override
    public ShhMessages shhGetMessages(BigInteger filterId) throws Exception {
        return getWeb3j().shhGetMessages(filterId).send();
    }

}
