package top.andnux.chain.service;

import java.util.HashMap;
import java.util.Map;

import top.andnux.chain.service.impl.EosWalletServiceImpl;
import top.andnux.chain.service.impl.EthWalletServiceImpl;
import top.andnux.chain.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static Map<String, Object> sMap = new HashMap<>();

    static {
        sMap.put(EosWalletService.class.getCanonicalName(), new EosWalletServiceImpl());
        sMap.put(EthWalletService.class.getCanonicalName(), new EthWalletServiceImpl());
        sMap.put(UserService.class.getCanonicalName(), new UserServiceImpl());
    }

    public static <T> void put(Class<T> service, T object) {
        sMap.put(service.getCanonicalName(), object);
    }

    public static void clear() {
        sMap.clear();
    }

    @SuppressWarnings("all")
    public static <T> T get(Class<T> service) {
        return (T) sMap.get(service.getCanonicalName());
    }
}
