package front;

import main.Main;
import structs.HashSeq;
import structs.Seq;
import utils.Log;
import utils.files.loadsave.BaseSaveLoadStream;
import utils.files.loadsave.StreamTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class AccountList {
    public Seq<Account> accounts = new Seq<Account>();

    public AccountList(){
        accounts.clear();
    }

    public void load(BaseSaveLoadStream stream){
        int l = stream.readI();
        for (int i = 0; i < l; i++) {
            Account acc = new Account();
            acc.load(stream);
        }
    }

    public void save(BaseSaveLoadStream stream) {
        stream.writeI(accounts.getSize());
        accounts.each((a) -> {
            a.save(stream);
        });
    }

    int lastElement = 0;
    public void construct(){
        JPanel panel = Main.w.accounts;
        JScrollPane scrollPanel = Main.w.accountsScrollPane;

        int temp = scrollPanel.getVerticalScrollBar().getValue();
        panel.removeAll();

        HashSeq<String, Seq<Account>> hash = new HashSeq<String, Seq<Account>>();

        accounts.each(a -> {
            if (!hash.containsKey(a.group))
                hash.put(a.group, new Seq<Account>());
            hash.get(a.group).add(a);
        });

        lastElement = -Settings.ACH.get();
        hash.eachIndexed((g, seq, gInd) -> {
            Checkbox groupCheck = new Checkbox();
            groupCheck.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    seq.each(a -> a.checkbox.setState(true));
                } else {
                    seq.each(a -> a.checkbox.setState(false));
                }
            });
            groupCheck.setBounds(0, lastElement + Settings.ACH.get(), Settings.ACU.get(), Settings.ACH.get());
            panel.add(groupCheck);
            seq.eachIndexed((a, aInd) -> {
                a.constructItems(panel, lastElement + Settings.ACH.get());
                lastElement += Settings.ACH.get();
            });
        });

        Button create = new Button();
        create.setLabel("Добавить аккаунт");
        create.setBounds(0, Settings.ACH.get() * accounts.getSize(), Settings.ACW.get(), Settings.ACH.get());
        create.addActionListener((e) -> {
            createNewAccount();
        });
        panel.setBounds(0, 0, Settings.ACH.get() * (accounts.getSize() + 1) + 200, Settings.ACW.get());
        panel.setPreferredSize(panel.getSize());
        panel.add(create);
        scrollPanel.getVerticalScrollBar().setValue(temp);
    }

    public void createNewAccount(){
        new Account();
        rebuild();
    }

    public void deleteAccount(Account acc){
        accounts.remove(acc);
        rebuild();
    }

    public void rebuild(){
        BaseSaveLoadStream writes = new BaseSaveLoadStream(StreamTypes.WRITE, "data\\" + Settings.accountsSaveFile.get());
        save(writes);
        writes.close();
        construct();
        Main.w.frame.repaint();
    }

    public TextField createInputField(String fieldName, int yPos, JPanel panel, String defVal) {
        TextField f = new TextField(defVal);
        f.setBounds(0, yPos, Settings.SCW.get()/2, Settings.SCH.get());
        panel.add(f);
        JLabel l = new JLabel(fieldName);
        l.setBounds(Settings.SCW.get()/2, yPos, Settings.SCW.get()/2, Settings.SCH.get());
        panel.add(l);
        return f;
    }

    public Account settingsAccount;
    public JPanel settingsPanel;
    public int messenger = 0;
    public TextField dsToken, dsBotName, dsGroupId, vkLogin, vkPassword, vkGroupId, name, group;
    public void constructSettings(Account acc){
        settingsAccount = acc;
        settingsPanel = new JPanel();
        settingsPanel.setLayout(null);
        settingsPanel.setVisible(true);
        settingsPanel.setBounds(Main.w.accountsScrollPane.getBounds());
        Main.w.accountsScrollPane.setVisible(false);

        JPanel vkSettingsPanel = new JPanel();
        vkSettingsPanel.setVisible(true);
        vkSettingsPanel.setLayout(null);
        vkSettingsPanel.setBounds(0, Settings.SCH.get(), Settings.SCW.get(), settingsPanel.getHeight() - Settings.SCH.get());

        JPanel dsSettingsPanel = new JPanel();
        dsSettingsPanel.setVisible(false);
        dsSettingsPanel.setLayout(null);
        dsSettingsPanel.setBounds(0, Settings.SCH.get(), Settings.SCW.get(), settingsPanel.getHeight() - Settings.SCH.get());

        Checkbox vkC = new Checkbox();
        vkC.setLabel("vk");
        vkC.setBounds(0, 0, Settings.SCW.get() / 8, Settings.SCH.get());
        Checkbox dsC = new Checkbox();
        dsC.setLabel("discord");
        dsC.setBounds(Settings.SCW.get() / 8 , 0, Settings.SCW.get() / 8, Settings.SCH.get());
        Button apply = new Button();
        apply.setBounds(Settings.SCW.get() / 4 , 0, Settings.SCW.get() / 8, Settings.SCH.get());
        apply.addActionListener(e -> applySettings());
        apply.setLabel("Сохранить");
        Button delete = new Button();
        delete.setLabel("Удалить");
        delete.setBounds(Settings.SCW.get() / 8 * 3 , 0, Settings.SCW.get() / 8, Settings.SCH.get());
        delete.addActionListener(e -> {
            Main.w.frame.remove(settingsPanel);
            Main.w.accountsScrollPane.setVisible(true);
            deleteAccount(settingsAccount);
        });

        vkC.addItemListener(e -> {
            dsC.setState(!(e.getStateChange() == ItemEvent.SELECTED));
            if (e.getStateChange() == ItemEvent.SELECTED) {
                vkSettingsPanel.setVisible(true);
                dsSettingsPanel.setVisible(false);
                messenger = 1;
            }
        });
        dsC.addItemListener(e -> {
            vkC.setState(!(e.getStateChange() == ItemEvent.SELECTED));
            if (e.getStateChange() == ItemEvent.SELECTED) {
                dsSettingsPanel.setVisible(true);
                vkSettingsPanel.setVisible(false);
                messenger = 2;
            }
        });
        vkC.setState(true);

        discordSettings: {
            dsToken = createInputField("discord token", 0, dsSettingsPanel, settingsAccount.dsToken);
            dsBotName = createInputField("discord bot name", Settings.SCH.get(), dsSettingsPanel, settingsAccount.dsBotName);
            dsGroupId = createInputField("discord group id", Settings.SCH.get()*2, dsSettingsPanel, settingsAccount.dsGroupId);
            name = createInputField("name", Settings.SCH.get()*3, dsSettingsPanel, settingsAccount.name);
            group = createInputField("group", Settings.SCH.get()*4, dsSettingsPanel, settingsAccount.group);
        }
        vkSettings: {
            vkLogin = createInputField("vk login", 0, vkSettingsPanel, settingsAccount.vkLogin);
            vkPassword = createInputField("vk password", Settings.SCH.get(), vkSettingsPanel, settingsAccount.vkPassword);
            vkGroupId = createInputField("vk group id", Settings.SCH.get()*2, vkSettingsPanel, settingsAccount.vkGroupId);
            name = createInputField("name", Settings.SCH.get()*3, vkSettingsPanel, settingsAccount.name);
            group = createInputField("group", Settings.SCH.get()*4, vkSettingsPanel, settingsAccount.group);
        }

        settingsPanel.add(vkC);
        settingsPanel.add(dsC);
        settingsPanel.add(apply);
        settingsPanel.add(vkSettingsPanel);
        settingsPanel.add(dsSettingsPanel);
        settingsPanel.add(delete);

        Main.w.frame.add(settingsPanel);
    }

    public void applySettings(){
        Main.w.frame.remove(settingsPanel);
        Main.w.accountsScrollPane.setVisible(true);
        settingsAccount.name = name.getText();
        settingsAccount.group = group.getText();
        if (messenger == 1){
            settingsAccount.vkLogin = vkLogin.getText();
            settingsAccount.vkPassword = vkPassword.getText();
            settingsAccount.vkGroupId = vkGroupId.getText();
        } else if (messenger == 2) {
            settingsAccount.dsToken = dsToken.getText();
            settingsAccount.dsGroupId = dsGroupId.getText();
            settingsAccount.dsBotName = dsBotName.getText();
        }
        rebuild();
    }
}
