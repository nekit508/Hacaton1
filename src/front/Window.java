package front;

import structs.Seq;
import utils.Log;
import utils.files.loadsave.BaseSaveLoadStream;
import utils.files.loadsave.StreamTypes;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Window {
    public JFrame frame;
    public JPanel top, accounts;

    public AccountList accountList;

    public Window(){
    }

    public void construct(){
        loadAccounts();

        frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 600);
        frame.setLayout(null);

        top = new JPanel();
        top.setVisible(true);
        top.setBounds(0, 0, 800, 50);
        top.setLayout(null);
        frame.add(top);

        Button aB = new Button("Аккаунты");
        aB.addActionListener((e) -> System.exit(9));
        aB.setBounds(0, 0, 400, 50);
        top.add(aB);

        Button bB = new Button("Черновики");
        bB.addActionListener((e) -> System.exit(9));
        bB.setBounds(400, 0, 400, 50);
        top.add(bB);

        accounts = new JPanel();
        accounts.setVisible(true);
        accounts.setBounds(0, 50, 800, 500);
        accounts.setLayout(null);
        frame.add(accounts);

        accountList.construct();
    }

    public void loadAccounts(){
        accountList = new AccountList();

        BaseSaveLoadStream stream = new BaseSaveLoadStream(StreamTypes.READ, "data\\accounts.data");
        accountList.load(stream);
        stream.close();
    }
}
