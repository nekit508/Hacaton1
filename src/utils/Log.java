package utils;

public class Log {
    public static boolean consoleMode = true;

    public static <T> void info(T... objects){
        if(!consoleMode) return;
        for(T o: objects){
            out(o);
        }
    }

    public static <T> void infoln(T... objects){
        if(!consoleMode) return;
        for(T o: objects){
            outln(o);
        }
    }

    protected static <T> void out(T val){
        System.out.print(val);
    }

    protected static <T> void outln(T val){
        System.out.println(val);
    }
}
