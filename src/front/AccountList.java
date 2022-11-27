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
import java.util.HashMap;

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

        hash.eachIndexed((g, seq, gInd) -> {
            Checkbox groupCheck = new Checkbox();
            groupCheck.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    seq.each(a -> a.checkbox.setState(true));
                } else {
                    seq.each(a -> a.checkbox.setState(false));
                }
            });
            groupCheck.setBounds(0, gInd*Settings.ACH.get(), Settings.ACU.get(), Settings.ACH.get());
            panel.add(groupCheck);
            seq.eachIndexed((a, aInd) -> {
                a.constructItems(panel, (aInd+gInd)*Settings.ACH.get());
            });
        });


        Button create = new Button();
        create.setLabel("Добавить аккаунт");
        create.setBounds(0, Settings.ACH.get() * accounts.getSize(), Settings.ACW.get(), Settings.ACH.get());
        create.addActionListener((e) -> {
            createNewAccount();
        });
        panel.setBounds(0, 0, Settings.ACH.get() * (accounts.getSize() + 1), Settings.ACW.get());
        panel.setPreferredSize(panel.getSize());
        panel.add(create);
        scrollPanel.getVerticalScrollBar().setValue(temp);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(10);
    }

    public Account createNewAccount(){
        Account acc = new Account();
        rebuild();
        return acc;
    }

    public void deleteAccount(Account acc){
        accounts.remove(acc);
        rebuild();
    }

    public void rebuild(){
        BaseSaveLoadStream writes = new BaseSaveLoadStream(StreamTypes.WRITE, "data\\accounts.data");
        save(writes);
        writes.close();
        construct();
        Main.w.frame.repaint();
    }
}
