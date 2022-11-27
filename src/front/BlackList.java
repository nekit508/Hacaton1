package front;

import main.Main;
import structs.Seq;
import utils.Log;
import utils.files.loadsave.BaseSaveLoadStream;
import utils.files.loadsave.StreamTypes;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BlackList {
    public Seq<Black> blacks = new Seq<Black>();

    public BlackList(){
        blacks.clear();
    }

    public void load(BaseSaveLoadStream stream){
        int l = stream.readI();
        for (int i = 0; i < l; i++) {
            Black b = new Black();
            b.load(stream);
        }
    }

    public void save(BaseSaveLoadStream stream) {
        stream.writeI(blacks.getSize());
        blacks.each((b) -> {
            b.save(stream);
        });
    }

    public void construct(){
        JPanel panel = Main.w.blacks;
        panel.removeAll();

        blacks.eachIndexed((n, ind) -> {
            n.constructItems(panel, ind * Settings.ACH.get());
        });

        Button create = new Button();
        create.setBounds(0, blacks.getSize() * Settings.ACH.get(), Settings.ACW.get(), Settings.ACH.get());
        create.setLabel("Добавить черновик");
        create.addActionListener(e -> {
            createNewBlack();
        });
        panel.add(create);

        panel.setBounds(0, 0, Settings.ACH.get() * (blacks.getSize() + 1), Settings.ACW.get());
        panel.setPreferredSize(panel.getSize());
    }

    public void createNewBlack(){
        new Black();
        rebuild();
    }

    public void deleteBlack(Black black){
        blacks.remove(black);
        rebuild();
    }

    public void rebuild(){
        BaseSaveLoadStream writes = new BaseSaveLoadStream(StreamTypes.WRITE, "data\\" + Settings.blacksSaveFile.get());
        save(writes);
        writes.close();
        construct();
        Main.w.frame.repaint();
    }

    public Black settingBlack;
    public JTextArea area;
    public JPanel settingsPanel;
    public void constructSettings(Black b){
        settingBlack = b;
        settingsPanel = new JPanel();
        settingsPanel.setLayout(null);
        settingsPanel.setBounds(Main.w.accountsScrollPane.getBounds());
        settingsPanel.setVisible(true);
        Main.w.accountsScrollPane.setVisible(false);

        area = new JTextArea();
        area.setText(settingBlack.message);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(0, Settings.SCH.get(), settingsPanel.getWidth(), settingsPanel.getHeight() - Settings.SCH.get());
        scroll.setVisible(true);
        scroll.repaint();

        Button apply = new Button("Сохранить");
        apply.addActionListener(e -> {
            apply();
        });
        apply.setBounds(0, 0, settingsPanel.getWidth() / 3, Settings.SCH.get());
        Button delete = new Button("Удалить");
        delete.addActionListener(e -> {
            Main.w.frame.remove(settingsPanel);
            Main.w.accountsScrollPane.setVisible(true);
            deleteBlack(settingBlack);
            rebuild();
        });
        delete.setBounds(settingsPanel.getWidth() / 3, 0, settingsPanel.getWidth() / 3, Settings.SCH.get());
        Button post = new Button("Отправить");
        post.addActionListener(e -> {
            post();
        });
        post.setBounds(settingsPanel.getWidth() / 3 * 2, 0, settingsPanel.getWidth() / 3, Settings.SCH.get());

        scroll.add(area);
        scroll.setViewportView(area);

        settingsPanel.add(scroll);
        settingsPanel.add(apply);
        settingsPanel.add(delete);
        settingsPanel.add(post);

        Main.w.frame.add(settingsPanel);
    }

    public void apply(){
        Main.w.frame.remove(settingsPanel);
        Main.w.accountsScrollPane.setVisible(true);

        settingBlack.message = area.getText();

        rebuild();
    }

    public void post(){
        Main.w.frame.remove(settingsPanel);
        Main.w.accountsScrollPane.setVisible(true);

        Main.w.accountList.accounts.toLog();

        Main.w.accountList.accounts.each((a) -> {
            if (!a.enabled)
                return;
            if (a.messenger == 1) {
                ProcessBuilder v = new ProcessBuilder("python", new File("main.py").getAbsolutePath(), a.vkLogin,
                        a.vkPassword, a.vkGroupId, settingBlack.message);
                Log.infoln("python", new File("main.py").getAbsolutePath(), a.vkLogin,
                        a.vkPassword, a.vkGroupId, settingBlack.message);
                try {
                    v.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        rebuild();
    }
}
