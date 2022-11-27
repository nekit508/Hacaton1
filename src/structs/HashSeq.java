package structs;

import funcs.Out0In2;
import funcs.Out0In3;

public class HashSeq<K, V> {
    public Seq<K> keys = new Seq<K>();
    public Seq<V> values = new Seq<V>();

    public boolean containsKey(K k){
        return keys.getIndex(k) != -1;
    }

    public boolean containsValue(V v){
        return values.getIndex(v) != -1;
    }

    public void put(K k, V v){
        keys.add(k);
        values.add(v);
    }

    public void remove(K k){
        int ind = keys.getIndex(k);
        keys.remove(ind);
        values.remove(ind);
    }

    public V get(K k){
        return values.get(keys.getIndex(k));
    }

    public void each(Out0In2<K, V> f){
        keys.eachIndexed((k, i) -> f.get(k, values.get(i)));
    }

    public void eachIndexed(Out0In3<K, V, Integer> f){
        keys.eachIndexed((k, i) -> f.get(k, values.get(i), i));
    }
}
