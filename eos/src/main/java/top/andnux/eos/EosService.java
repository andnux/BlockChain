package top.andnux.eos;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import top.andnux.eos.common.transaction.SignedPackedTransaction;
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

public interface EosService {

    @POST("/v1/wallet/create")
    Call<String> createWallet(@Body String walletName);

    @POST("/v1/wallet/open")
    Call<Void> openWallet(@Body String walletName);

    @POST("/v1/wallet/lock")
    Call<Void> lockWallet(@Body String walletName);

    @GET("/v1/wallet/lock_all")
    Call<Void> lockAll();

    @POST("/v1/wallet/unlock")
    Call<Void> unlockWallet(@Body List<String> requestFields);

    @POST("/v1/wallet/import_key")
    Call<Void> importKey(@Body List<String> requestFields);

    @GET("/v1/wallet/list_wallets")
    Call<List<String>> listWallets();

    @GET("/v1/wallet/list_keys")
    Call<List<List<String>>> listKeys();

    @GET("/v1/wallet/get_public_keys")
    Call<List<String>> getPublicKeys();

    @POST("/v1/wallet/set_timeout")
    Call<Void> setTimeout(@Body Integer timeOut);

    @POST("/v1/wallet/sign_digest")
    Call<String> signDigest(@Body List<String> parameters);

    @POST("/v1/wallet/create_key")
    Call<String> createKey(@Body List<String> parameters);

    @POST("/v1/wallet/sign_transaction")
    Call<SignedPackedTransaction> signTransaction(@Body SignTransactionRequest unsignedTransaction);

    @POST("/v1/history/get_actions")
    Call<Actions> getActions(@Body Map<String, Object> requestFields);

    @POST("/v1/history/get_transaction")
    Call<Transaction> getTransaction(@Body Map<String, String> requestFields);

    @POST("/v1/history/get_key_accounts")
    Call<KeyAccounts> getKeyAccounts(@Body Map<String, String> requestFields);

    @POST("/v1/history/get_controlled_accounts")
    Call<ControlledAccounts> getControlledAccounts(@Body Map<String, String> requestFields);

    @GET("/v1/chain/get_info")
    Call<ChainInfo> getChainInfo();

    @POST("/v1/chain/get_block")
    Call<Block> getBlock(@Body Map<String, String> requestFields);

    @POST("/v1/chain/get_account")
    Call<Account> getAccount(@Body Map<String, String> requestFields);

    @POST("/v1/chain/get_abi")
    Call<Abi> getAbi(@Body Map<String, String> requestFields);

    @POST("/v1/chain/get_code")
    Call<Code> getCode(@Body Map<String, String> requestFields);

    @POST("/v1/chain/get_table_rows")
    Call<TableRow> getTableRows(@Body Map<String, String> requestFields);

    @POST("/v1/chain/get_currency_balance")
    Call<List<String>> getCurrencyBalance(@Body Map<String, String> requestFields);

    @POST("/v1/chain/abi_json_to_bin")
    Call<AbiJsonToBin> abiJsonToBin(@Body AbiJsonToBinRequest abiJsonToBinRequest);

    @POST("/v1/chain/abi_bin_to_json")
    Call<AbiBinToJson> abiBinToJson(@Body Map<String, String> requestFields);

    @POST("/v1/chain/push_transaction")
    Call<PushedTransaction> pushTransaction(@Body PushTransactionRequest pushTransactionRequest);

    @POST("/v1/chain/push_transactions")
    Call<List<PushedTransaction>> pushTransactions(@Body List<PushTransactionRequest> pushTransactionRequests);

    @POST("/v1/chain/get_required_keys")
    Call<RequiredKeys> getRequiredKeys(@Body RequiredKeysRequest requiredKeysRequest);

    @POST("/v1/chain/get_currency_stats")
    Call<Map<String, CurrencyStats>> getCurrencyStats(@Body Map<String, String> requestFields);

}
