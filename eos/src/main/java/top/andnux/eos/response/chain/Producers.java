package top.andnux.eos.response.chain;

/**
 * @auther Yangming
 * @date 2018/8/16 12:50 AM
 */
public class Producers {

    private String producer_name;

    private String block_signing_key;

    public String getProducer_name() {
        return producer_name;
    }

    public void setProducer_name(String producer_name) {
        this.producer_name = producer_name;
    }

    public String getBlock_signing_key() {
        return block_signing_key;
    }

    public void setBlock_signing_key(String block_signing_key) {
        this.block_signing_key = block_signing_key;
    }
}
