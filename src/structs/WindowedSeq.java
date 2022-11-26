package structs;

import utils.Log;

public class WindowedSeq<T> {
    T[] seq;
    int counter = 0;
    int maxSize;

    public WindowedSeq(int size) {
        seq = (T[]) (new Object[size]);
        maxSize = size;
    }

    public void add(T obj){
        seq[counter++] = obj;
        if (counter == maxSize) {
            counter = 0;
        }
    }

    public Seq<T> seq(){
        int repeatCounter = counter-1;
        Seq<T> out = new Seq<T>();
        for (int i = 0; i < maxSize; i++) {
            if (repeatCounter == -1) return out;
            T v = seq[repeatCounter--];
            if (repeatCounter < 0) {
                repeatCounter = maxSize-1;
            } else if (repeatCounter == counter) {
                return out;
            }
            if (v == null) {
                return out;
            }
            out.add(v);
        }
        return out;
    }
}
