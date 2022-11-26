package front;

import main.Main;
import utils.Log;
import utils.files.loadsave.BaseSaveLoadStream;

import javax.swing.*;
import java.awt.*;

//b6d9ac00d8b131881ff119806cf0500000000000000008b490f0b0399476be78500c758d13e28cee09d1c

public class Account {
    public String token = "null", name = "new", group = "";

    JPanel container;

    public Account(){
    }

    public void load(BaseSaveLoadStream stream){
        token = stream.readS();
        name = stream.readS();
        group = stream.readS();
    }

    public void save(BaseSaveLoadStream stream){
        stream.writeS(token);
        stream.writeS(name);
        stream.writeS(group);
    }

    static int getFarCompX(Component... c){
        int out = c[0].getX();
        for (Component component : c) {
            out += component.getWidth();
        }
        return out;
    }

    static int getFarCompY(Component... c){
        int out = c[0].getY();
        for (Component component : c) {
            out += component.getHeight();
        }
        return out;
    }


    public void constructItems(JPanel panel, int heightOffset){
        container = new JPanel();
        container.setLayout(null);

        Checkbox checkBox = new Checkbox();
        checkBox.setBounds(0, 0, Settings.ACU.get(), Settings.ACH.get());

        JLabel label = new JLabel();
        label.setText(name + "     группа: " + group);
        label.setBounds(getFarCompX(checkBox), 0, Settings.ACW.get() - 2 * Settings.ACU.get(), Settings.ACH.get());

        Button settings = new Button();
        settings.setBounds(getFarCompX(checkBox, label), 0, Settings.ACU.get(), Settings.ACH.get());

        container.add(label);
        container.add(checkBox);
        container.add(settings);

        container.setVisible(true);
        container.setBounds(0, Settings.ACH.get() + heightOffset, Settings.ACW.get(), Settings.ACH.get());
        panel.add(container);
    }
}
