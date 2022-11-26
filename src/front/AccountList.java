package front;

import main.Main;
import structs.Seq;
import utils.Log;
import utils.files.loadsave.BaseSaveLoadStream;
import utils.files.loadsave.StreamTypes;

import javax.swing.*;
import java.awt.*;

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
            accounts.add(acc);
        }
    }

    public void save(BaseSaveLoadStream stream){
        stream.writeI(accounts.getSize());
        accounts.each((a) -> {
            a.save(stream);
        });
    }

    public void construct(){
        JPanel panel = Main.w.accounts;
        panel.removeAll();

        accounts.eachIndexed((a, i) -> {
            a.constructItems(panel, i);
        });

        Button create = new Button();
        create.setLabel("Добавить аккаунт");
        create.setBounds(0, Settings.ACH.get() * accounts.getSize(), Settings.ACW.get(), Settings.ACH.get());
        create.addActionListener((e) -> {
            createNewAccount();
        });
        panel.add(create);

        Main.w.frame.repaint();
    }

    public Account createNewAccount(){
        Account acc = new Account();
        accounts.add(acc);
        BaseSaveLoadStream writes = new BaseSaveLoadStream(StreamTypes.WRITE, "data\\accounts.data");
        save(writes);
        writes.close();
        construct();
        return acc;
    }


}
