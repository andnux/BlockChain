package top.andnux.chain.sqlite;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

public class SQLiteManager {

    private static SQLiteManager ourInstance = null;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Application mApplication;
    private Configuration mConfiguration;
    private List<Class<? extends AbstractDao<?, ?>>> mDao = new ArrayList<>();

    private SQLiteManager() {
        addDaoClass(UserEntityDao.class);
        addDaoClass(WalletEntityDao.class);
    }

    public static SQLiteManager getInstance() {
        if (ourInstance == null) {
            synchronized (SQLiteManager.class) {
                if (ourInstance == null) {
                    ourInstance = new SQLiteManager();
                }
            }
        }
        return ourInstance;
    }

    public void addDaoClass(Class<? extends AbstractDao<?, ?>> daoClazz) {
        mDao.add(daoClazz);
        init(mApplication, mConfiguration);
    }

    public void remove(Class<? extends AbstractDao<?, ?>> daoClazz) {
        mDao.remove(daoClazz);
        init(mApplication, mConfiguration);
    }


    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    @SuppressWarnings("all")
    public static void init(Application application,
                            Configuration configuration) {
        SQLiteManager instance = getInstance();
        instance.mConfiguration = configuration;
        instance.mApplication = application;
        String[] strings = application.getPackageName().split(".");
        String dbName = strings[strings.length - 1];
        if (configuration == null) {
            configuration = new Configuration(dbName, "");
        }
        if (TextUtils.isEmpty(configuration.dbName)) {
            configuration.dbName = dbName;
        }
        Class[] daoClasses = new Class[instance.mDao.size()];
        for (int i = 0; i < instance.mDao.size(); i++) {
            daoClasses[i] = instance.mDao.get(i);
        }
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(application, configuration.dbName,
                null, daoClasses);
        if (TextUtils.isEmpty(configuration.sbPassword)) {
            SQLiteDatabase database = helper.getWritableDatabase();
            instance.mDaoMaster = new DaoMaster(database);
            instance.mDaoSession = instance.mDaoMaster.newSession();
        } else {
            Database database = helper.getEncryptedWritableDb(configuration.sbPassword);
            instance.mDaoMaster = new DaoMaster(database);
            instance.mDaoSession = instance.mDaoMaster.newSession();
        }
    }


    public static class Configuration {

        private String dbName;
        private String sbPassword;

        public Configuration() {
        }

        public Configuration(String dbName, String sbPassword) {
            this.dbName = dbName;
            this.sbPassword = sbPassword;
        }

        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public String getSbPassword() {
            return sbPassword;
        }

        public void setSbPassword(String sbPassword) {
            this.sbPassword = sbPassword;
        }
    }
}
