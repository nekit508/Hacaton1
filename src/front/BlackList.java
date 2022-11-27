package front;

import main.Main;
import structs.Seq;
import utils.Log;
import utils.files.loadsave.BaseSaveLoadStream;
import utils.files.loadsave.StreamTypes;

import javax.swing.*;
import java.awt.*;

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
    public TextArea area;
    public void constructSettings(Black b){
        settingBlack = b;
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(null);
        settingsPanel.setBounds(Main.w.accountsScrollPane.getBounds());
        settingsPanel.setVisible(true);
        Main.w.accountsScrollPane.setVisible(false);

        area = new TextArea();
        area.setText(settingBlack.message);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(0, Settings.SCH.get(), settingsPanel.getWidth(), settingsPanel.getHeight() - Settings.SCH.get());
        Button apply = new Button("Сохранить");
        apply.addActionListener(e -> {
            apply();
        });
        apply.setBounds(0, 0, settingsPanel.getWidth(), Settings.SCH.get());

        settingsPanel.add(scroll);
        settingsPanel.add(apply);

        Main.w.frame.add(settingsPanel);
    }

    public void apply(){

    }
}
