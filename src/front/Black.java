package front;

import main.Main;
import structs.Seq;
import utils.files.loadsave.BaseSaveLoadStream;

import javax.swing.*;
import java.awt.*;

public class Black {
    public Seq<String> attaches = new Seq<String>();
    public String message = "";

    public JPanel container;
    public Button settings;

    public Black() {

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

        settings = new Button();
        settings.setBounds(Settings.ACW.get() - Settings.ACU.get(), 0, Settings.ACU.get(), Settings.ACH.get());

        container.add(settings);

        container.setVisible(true);
        container.setBounds(Settings.ACM.get(), heightOffset, Settings.ACW.get(), Settings.ACH.get());
        panel.add(container);
    }
}
