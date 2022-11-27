package front;

import main.Main;
import utils.Log;
import utils.files.loadsave.BaseSaveLoadStream;

import javax.swing.*;
import java.awt.*;

//b6d9ac00d8b131881ff119806cf0500000000000000008b490f0b0399476be78500c758d13e28cee09d1c

public class Account {
    public String token = "null", name = "new", group = "";

    public JPanel container;
    public Checkbox checkbox;
    public JLabel label;
    public Button settings;

    public Account(){
        Main.w.accountList.accounts.add(this);
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

    public void constructItems(JPanel panel, int heightOffset){
        container = new JPanel();
        container.setLayout(null);

        checkbox = new Checkbox();
        checkbox.setBounds(0, 0, Settings.ACU.get(), Settings.ACH.get());

        label = new JLabel();
        label.setText(name + "     группа: " + group);
        label.setBounds(Settings.ACU.get(), 0, Settings.ACW.get() - Settings.ACU.get()*2 - Settings.ACM.get(), Settings.ACH.get());

        settings = new Button();
        settings.setBounds(label.getX()+label.getWidth(), 0, Settings.ACU.get(), Settings.ACH.get());
        settings.addActionListener((e) -> Main.w.accountList.deleteAccount(this));

        container.add(label);
        container.add(checkbox);
        container.add(settings);

        container.setVisible(true);
        container.setBounds(Settings.ACM.get(), heightOffset, Settings.ACW.get(), Settings.ACH.get());
        panel.add(container);
    }
}
