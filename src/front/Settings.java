package front;

import funcs.Out1In0;

public class Settings {
    public static Out1In0<Integer>
            ACW = () -> 800-15,
            ACU = () -> 30,
            ACH = () -> ACU.get(),
            ACM = () -> ACU.get(),
            SCW = () -> 800,
            SCU = () -> 20,
            SCH = () -> SCU.get(),
            SCM = () -> SCU.get()
            ;

    public static Out1In0<String>
            accountsSaveFile = () -> "accounts.data",
            blacksSaveFile = () -> "blacks.data"
            ;
}
