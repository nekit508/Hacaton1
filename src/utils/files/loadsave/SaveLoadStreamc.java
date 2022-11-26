package utils.files.loadsave;

public interface SaveLoadStreamc {
    StreamTypes getType();

    int readI();
    void writeI(int i);
    byte readB();
    void writeB(byte b);
    float readF();
    void writeF(float f);
    long readL();
    void writeL(long l);
    String readS();
    void writeS(String s);

    void open();
    void close();
}
