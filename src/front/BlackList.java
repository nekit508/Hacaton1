package front;

import main.Main;
import structs.Seq;
import utils.files.loadsave.BaseSaveLoadStream;
import utils.files.loadsave.StreamTypes;

public class BlackList {
    public Seq<Black> blacks = new Seq<Black>();

    public BlackList(){
        blacks.clear();
    }

    public void load(BaseSaveLoadStream stream){
        int l = stream.readI();
        for (int i = 0; i < l; i++) {
            Account acc = new Account();
            acc.load(stream);
        }
    }

    public void save(BaseSaveLoadStream stream) {
        stream.writeI(blacks.getSize());
        blacks.each((b) -> {
            b.save(stream);
        });
    }

    public void construct(){

    }

    public Account createNewBlack(){
        Account acc = new Account();
        rebuild();
        return acc;
    }

    public void deleteBlack(Black black){
        blacks.remove(black);
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
