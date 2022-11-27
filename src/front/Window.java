package front;

import utils.files.loadsave.BaseSaveLoadStream;
import utils.files.loadsave.StreamTypes;

import javax.swing.*;
import java.awt.*;

public class Window {
    public JFrame frame;
    public JPanel top, accounts, blacks;
    public JScrollPane accountsScrollPane;

    public AccountList accountList;
    public BlackList blackList;

    public byte selectedPanel = 0;

    public Window(){
    }

    public void construct(){
        loadAccounts();

        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 815, 600);
        frame.setLayout(null);
        frame.setFocusable(false);

        top = new JPanel();
        top.setVisible(true);
        top.setBounds(0, 0, 800, 50);
        top.setLayout(null);
        frame.add(top);

        Button aB = new Button("Аккаунты");
        aB.addActionListener((e) -> {
            selectedPanel = 0;
            reconstructPanels();
        });
        aB.setBounds(0, 0, 400, 50);
        top.add(aB);

        Button bB = new Button("Черновики");
        bB.addActionListener((e) -> {
            selectedPanel = 1;
            reconstructPanels();
        });
        bB.setBounds(400, 0, 400, 50);
        top.add(bB);

        accountsScrollPane = new JScrollPane();
        accountsScrollPane.setBounds(0, 50, 800, 500);
        accountsScrollPane.setVisible(true);
        accountsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(accountsScrollPane);

        reconstructPanels();

        frame.setVisible(true);
    }

    public void reconstructPanels(){
        if (accounts != null) {
            accountsScrollPane.setViewportView(null);
        }

        if (selectedPanel == 0) {
            accounts = new JPanel();
            accounts.setVisible(true);
            accounts.setLayout(null);
            accountList.construct();
            accountsScrollPane.setViewportView(accounts);
        } else if (selectedPanel == 1) {
            blacks = new JPanel();
            blacks.setVisible(true);
            blacks.setLayout(null);
            accountsScrollPane.setViewportView(blacks);
        }
    }

    public void loadAccounts(){
        accountList = new AccountList();

        BaseSaveLoadStream stream = new BaseSaveLoadStream(StreamTypes.READ, "data\\accounts.data");
        accountList.load(stream);
        stream.close();
    }

    public void loadBlacks(){
        BaseSaveLoadStream stream = new BaseSaveLoadStream(StreamTypes.READ, "data\\blacks.data");
    }
}
