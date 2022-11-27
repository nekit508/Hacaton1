package front;

import main.Main;
import structs.Seq;
import utils.Log;
import utils.files.loadsave.BaseSaveLoadStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Black {
    public Seq<String> attaches = new Seq<String>();
    public String message = "";

    public JPanel container;
    public Button settings;
    public JLabel label;

    public Black() {
        Main.w.blackList.blacks.add(this);
        attaches.add("assets\\coal.png");
    }

    public void load(BaseSaveLoadStream stream){
        int l = stream.readI();
        for (int i = 0; i < l; i++) {
            attaches.add(stream.readS());
        }
        message = stream.readS();
    }

    public void save(BaseSaveLoadStream stream){
        stream.writeI(attaches.getSize());
        attaches.each(stream::writeS);
        stream.writeS(message);
    }

    public void constructItems(JPanel panel, int heightOffset){
        container = new JPanel();
        container.setLayout(null);

        label = new JLabel();
        attaches.eachIndexed((a, i) -> {
            try {
                BufferedImage bufferedImage = ImageIO.read(new File(a));
                ImageIcon icon = new ImageIcon(bufferedImage.getScaledInstance(Settings.ACU.get(), Settings.ACU.get(), BufferedImage.TYPE_INT_RGB));
                label.setIcon(icon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        label.setBounds(0, 0, Settings.ACU.get(), Settings.ACU.get());

        settings = new Button();
        settings.setBounds(Settings.ACW.get() - Settings.ACU.get(), 0, Settings.ACU.get(), Settings.ACH.get());
        settings.addActionListener((e) -> Main.w.blackList.deleteBlack(this));

        container.add(settings);
        container.add(label);

        container.setVisible(true);
        container.setBounds(0, heightOffset, Settings.ACW.get(), Settings.ACH.get());
        panel.add(container);
    }
}
