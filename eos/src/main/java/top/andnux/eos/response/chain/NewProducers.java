package top.andnux.eos.response.chain;

/**
 * @auther Yangming
 * @date 2018/8/16 12:51 AM
 */
public class NewProducers {

    private long version;

    private Producers[] Producers;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Producers[] getProducers() {
        return Producers;
    }

    public void setProducers(Producers[] Producers) {
        this.Producers = Producers;
    }
}
