package front;

import main.Main;
import utils.Log;
import utils.files.loadsave.BaseSaveLoadStream;

import javax.swing.*;
import java.awt.*;

public class Account {
    public String name = "new", group = "";
    public int messenger = 0;

    public String dsToken, dsBotName, dsGroupId;
    public String vkLogin, vkPassword, vkGroupId;

    public JPanel container;
    public Checkbox checkbox;
    public JLabel label;
    public Button settings;

    public Account(){
        Main.w.accountList.accounts.add(this);
    }

    public void load(BaseSaveLoadStream stream){
        name = stream.readS();
        group = stream.readS();
        messenger = stream.readI();
    }

    public void save(BaseSaveLoadStream stream){
        stream.writeS(name);
        stream.writeS(group);
        stream.writeI(messenger);
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
        settings.addActionListener((e) -> Main.w.accountList.constructSettings(this));

        container.add(label);
        container.add(checkbox);
        container.add(settings);

        container.setVisible(true);
        container.setBounds(Settings.ACM.get(), heightOffset, Settings.ACW.get(), Settings.ACH.get());
        panel.add(container);
    }
}
