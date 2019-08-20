package top.andnux.chain.service.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import top.andnux.chain.entity.WalletEntity;
import top.andnux.eos.EosService;
import top.andnux.eos.common.WalletKeyType;
import top.andnux.eos.common.transaction.PackedTransaction;
import top.andnux.eos.common.transaction.SignedPackedTransaction;
import top.andnux.eos.exception.EosApiError;
import top.andnux.eos.exception.EosApiException;
import top.andnux.eos.request.chain.AbiJsonToBinRequest;
import top.andnux.eos.request.chain.RequiredKeysRequest;
import top.andnux.eos.request.chain.transaction.PushTransactionRequest;
import top.andnux.eos.request.wallet.transaction.SignTransactionRequest;
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

public class EosWalletServiceImpl extends AbstractWalletService implements top.andnux.chain.service.EosWalletService {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(JacksonConverterFactory.create());

    private static Retrofit retrofit;

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        builder.baseUrl(baseUrl);
        builder.client(httpClient.build());
        builder.addConverterFactory(JacksonConverterFactory.create());
        retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    /**
     * Execute a REST call and block until the response is received.
     */
    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                EosApiError apiError = getEosApiError(response);
                throw new EosApiException(apiError);
            }
        } catch (IOException e) {
            throw new EosApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    private static EosApiError getEosApiError(Response<?> response) throws IOException, EosApiException {
        return (EosApiError) retrofit.responseBodyConverter(EosApiError.class, new Annotation[0])
                .convert(response.errorBody());
    }

    private EosService eosService; 

    @Override
    public void setBaseUrl(String baseUrl) {
        super.setBaseUrl(baseUrl);
        eosService = createService(EosService.class, baseUrl); 
    }

    @Override
    public WalletEntity generateWallet(Long uid, String name, String pwd) throws Exception {
        return null;
    }

    @Override
    public void importByPrivateKey(Long uid, String pwd, String privateKey) throws Exception {

    }

    @Override
    public ChainInfo getChainInfo() {
        return executeSync(eosService.getChainInfo());
    }

    @Override
    public Block getBlock(String blockNumberOrId) {
        return executeSync(eosService.getBlock(Collections.singletonMap("block_num_or_id", blockNumberOrId)));
    }

    @Override
    public Account getAccount(String accountName) {
        return executeSync(eosService.getAccount(Collections.singletonMap("account_name", accountName)));
    }

    @Override
    public Abi getAbi(String accountName) {
        return executeSync(eosService.getAbi(Collections.singletonMap("account_name", accountName)));
    }

    @Override
    public Code getCode(String accountName) {
        return executeSync(eosService.getCode(Collections.singletonMap("account_name", accountName)));
    }

    @Override
    public TableRow getTableRows(String scope, String code, String table) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(7);

        requestParameters.put("scope", scope);
        requestParameters.put("code", code);
        requestParameters.put("table", table);
        requestParameters.put("json", "true");

        return executeSync(eosService.getTableRows(requestParameters));
    }

    @Override
    public List<String> getCurrencyBalance(String code, String accountName, String symbol) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(3);

        requestParameters.put("code", code);
        requestParameters.put("account", accountName);
        requestParameters.put("symbol", symbol);

        return executeSync(eosService.getCurrencyBalance(requestParameters));
    }

    @Override
    public AbiBinToJson abiBinToJson(String code, String action, String binargs) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(3);

        requestParameters.put("code", code);
        requestParameters.put("action", action);
        requestParameters.put("binargs", binargs);

        return executeSync(eosService.abiBinToJson(requestParameters));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> AbiJsonToBin abiJsonToBin(String code, String action, T args) {
        return executeSync(eosService.abiJsonToBin(new AbiJsonToBinRequest(code, action, args)));
    }

    @Override
    public PushedTransaction pushTransaction(String compression, SignedPackedTransaction packedTransaction) {
        return executeSync(eosService.pushTransaction(new PushTransactionRequest(compression, packedTransaction, packedTransaction.getSignatures())));
    }

    @Override
    public List<PushedTransaction> pushTransactions(List<PushTransactionRequest> pushTransactionRequests) {
        return executeSync(eosService.pushTransactions(pushTransactionRequests));
    }

    @Override
    public RequiredKeys getRequiredKeys(PackedTransaction transaction, List<String> keys) {
        return executeSync(eosService.getRequiredKeys(new RequiredKeysRequest(transaction, keys)));
    }

    @Override
    public Map<String, CurrencyStats> getCurrencyStats(String code, String symbol) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(2);

        requestParameters.put("code", code);
        requestParameters.put("symbol", symbol);

        return executeSync(eosService.getCurrencyStats(requestParameters));
    }

    @Override
    public String createWallet(String walletName) {
        return executeSync(eosService.createWallet(walletName));
    }

    @Override
    public void openWallet(String walletName) {
        executeSync(eosService.openWallet(walletName));
    }

    @Override
    public void lockWallet(String walletName) {
        executeSync(eosService.lockWallet(walletName));
    }

    @Override
    public void lockAllWallets() {
        executeSync(eosService.lockAll());
    }

    @Override
    public void unlockWallet(String walletName, String walletPassword) {
        List<String> requestFields = new ArrayList<>(2);

        requestFields.add(walletName);
        requestFields.add(walletPassword);
        executeSync(eosService.unlockWallet(requestFields));
    }

    @Override
    public void importKeyIntoWallet(String walletName, String key) {
        List<String> requestFields = new ArrayList<>(2);

        requestFields.add(walletName);
        requestFields.add(key);
        executeSync(eosService.importKey(requestFields));
    }

    @Override
    public List<String> listWallets() {
        return executeSync(eosService.listWallets());
    }

    @Override
    public List<List<String>> listKeys() {
        return executeSync(eosService.listKeys());
    }

    @Override
    public List<String> getPublicKeys() {
        return executeSync(eosService.getPublicKeys());
    }

    @Override
    public SignedPackedTransaction signTransaction(PackedTransaction packedTransaction, List<String> publicKeys, String chainId) {
        return executeSync(eosService.signTransaction(new SignTransactionRequest(packedTransaction, publicKeys, chainId)));
    }

    @Override
    public void setWalletTimeout(Integer timeout) {
        executeSync(eosService.setTimeout(timeout));
    }

    @Override
    public String signDigest(String digest, String publicKey) {
        return executeSync(eosService.signDigest(Arrays.asList(digest, publicKey)));
    }

    @Override
    public String createKey(String walletName, WalletKeyType keyType) {
        return executeSync(eosService.createKey(Arrays.asList(walletName, keyType.name())));
    }

    @Override
    public Actions getActions(String accountName, Integer pos, Integer offset) {
        LinkedHashMap<String, Object> requestParameters = new LinkedHashMap<>(3);

        requestParameters.put("account_name", accountName);
        requestParameters.put("pos", pos);
        requestParameters.put("offset", offset);

        return executeSync(eosService.getActions(requestParameters));
    }

    @Override
    public Transaction getTransaction(String id) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(1);

        requestParameters.put("id", id);

        return executeSync(eosService.getTransaction(requestParameters));
    }

    @Override
    public KeyAccounts getKeyAccounts(String publicKey) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(1);

        requestParameters.put("public_key", publicKey);

        return executeSync(eosService.getKeyAccounts(requestParameters));
    }

    @Override
    public ControlledAccounts getControlledAccounts(String controllingAccountName) {
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(1);

        requestParameters.put("controlling_account", controllingAccountName);

        return executeSync(eosService.getControlledAccounts(requestParameters));
    }
}
