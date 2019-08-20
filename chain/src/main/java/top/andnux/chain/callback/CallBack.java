package top.andnux.chain.callback;

public interface CallBack<T> {

    void onSuccess(T data);

    void onError(Exception e);
}
