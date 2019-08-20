package top.andnux.eos.response.chain.abi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Abi {

    private String accountName;

    private Abi abi;


    public String getAccountName() {
        return accountName;
    }

    @JsonProperty("account_name")
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Abi getAbi() {
        return abi;
    }

    public void setAbi(Abi abi) {
        this.abi = abi;
    }

}
