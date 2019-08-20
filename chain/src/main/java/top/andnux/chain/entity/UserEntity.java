package top.andnux.chain.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

@Entity(nameInDb = "tb_user")
public class UserEntity {
    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    private Long id;
    @Property(nameInDb = "phone")
    private String phone;//手机
    @Property(nameInDb = "password")
    private String password;//密码
    @Property(nameInDb = "state")
    private int state; //状态
    @Property(nameInDb = "env")
    private String env; //环境
    @Property(nameInDb = "is_current")
    private boolean is_current; //是不是当前用户
    @Property(nameInDb = "create_time")
    private Date create_time; //创建时间
    @Property(nameInDb = "update_time")
    private Date update_time; //更新时间

    @Generated(hash = 1880951463)
    public UserEntity(Long id, String phone, String password, int state, String env,
            boolean is_current, Date create_time, Date update_time) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.state = state;
        this.env = env;
        this.is_current = is_current;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    @Generated(hash = 1433178141)
    public UserEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean getIs_current() {
        return this.is_current;
    }

    public void setIs_current(boolean is_current) {
        this.is_current = is_current;
    }

    public Date getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getEnv() {
        return this.env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
