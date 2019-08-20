package top.andnux.chain.entity;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;

@Entity(nameInDb = "tb_wallet")
public class WalletEntity {

    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    private Long id;
    @Property(nameInDb = "user_id")
    private Long user_id;
    @Property(nameInDb = "state")
    @Convert(converter = StateConverter.class, columnType = Integer.class)
    private State state;    //状态
    @Property(nameInDb = "wallet_name")
    private String wallet_name; //钱包名字
    @Property(nameInDb = "chain")
    private String chain;//链
    @Property(nameInDb = "address")
    private String address;//地址
    @Property(nameInDb = "keystore")
    private String keystore;//keystore
    @Property(nameInDb = "private_key")
    private String private_key;//private_key
    @Property(nameInDb = "public_key")
    private String public_key;//public_key
    @Property(nameInDb = "mnemonic")
    private String mnemonic;//助记词

    @Generated(hash = 886518484)
    public WalletEntity(Long id, Long user_id, State state, String wallet_name, String chain,
            String address, String keystore, String private_key, String public_key,
            String mnemonic) {
        this.id = id;
        this.user_id = user_id;
        this.state = state;
        this.wallet_name = wallet_name;
        this.chain = chain;
        this.address = address;
        this.keystore = keystore;
        this.private_key = private_key;
        this.public_key = public_key;
        this.mnemonic = mnemonic;
    }

    @Generated(hash = 1363662176)
    public WalletEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getWallet_name() {
        return this.wallet_name;
    }

    public void setWallet_name(String wallet_name) {
        this.wallet_name = wallet_name;
    }

    public String getChain() {
        return this.chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKeystore() {
        return this.keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public String getPrivate_key() {
        return this.private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getPublic_key() {
        return this.public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getMnemonic() {
        return this.mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public static class StateConverter implements PropertyConverter<State, Integer> {
        @Override
        public State convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (State role : State.values()) {
                if (role.id == databaseValue) {
                    return role;
                }
            }
            return State.CREATE;
        }

        @Override
        public Integer convertToDatabaseValue(State entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }

    public enum State {
        CREATE(0), ACTIVE(1), DELETE(2);
        final int id;

        State(int id) {
            this.id = id;
        }
    }

}
