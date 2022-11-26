package utils;

import structs.HashSeq;

public class Time {
    private static HashSeq<String, Long> marks = new HashSeq<String, Long>();

    public static void mark(String m){
        marks.put(m, System.currentTimeMillis());
    }

    public static long getTime(String m){
        long out = System.currentTimeMillis() - marks.get(m);
        marks.remove(m);
        return out;
    }
}
