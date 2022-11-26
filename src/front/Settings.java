package front;

import funcs.Out1In0;
import main.Main;

public class Settings {
    public static Out1In0<Integer>
            ACW = () -> Main.w.accounts.getWidth(),
            ACU = () -> 30,
            ACH = () -> ACU.get()
            ;
}
