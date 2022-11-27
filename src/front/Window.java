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
        loadBlacks();

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
        accountsScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        accountsScrollPane.setBounds(0, 50, 800, 500);
        accountsScrollPane.setVisible(true);
        accountsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        accountsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
            accountsScrollPane.getViewport().setViewSize(accounts.getSize());
            accountsScrollPane.getViewport().reshape(0, 0, 8000, 8000);
        } else if (selectedPanel == 1) {
            blacks = new JPanel();
            blacks.setVisible(true);
            blacks.setLayout(null);
            blackList.construct();
            accountsScrollPane.setViewportView(blacks);
        }
    }

    public void loadAccounts(){
        accountList = new AccountList();

        BaseSaveLoadStream stream = new BaseSaveLoadStream(StreamTypes.READ, "data\\" + Settings.accountsSaveFile.get());
        accountList.load(stream);
        stream.close();
    }

    public void loadBlacks(){
        blackList = new BlackList();

        BaseSaveLoadStream stream = new BaseSaveLoadStream(StreamTypes.READ, "data\\" + Settings.blacksSaveFile.get());
        blackList.load(stream);
        stream.close();
    }
}
