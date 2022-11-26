package funcs;

public interface LoadFunc<T> {
    T get(byte[] data, int point);
}
