package front;

import funcs.Out1In0;
import main.Main;
import utils.Log;

public class Settings {
    public static Out1In0<Integer>
            ACW = () -> 800-15,
            ACU = () -> 30,
            ACH = () -> ACU.get(),
            ACM = () -> ACU.get()
            ;
}
