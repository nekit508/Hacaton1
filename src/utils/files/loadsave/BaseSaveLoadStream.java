package utils.files.loadsave;

import java.io.*;
import java.nio.file.Files;

public class BaseSaveLoadStream implements SaveLoadStreamc {
    public StreamTypes type;
    public File file;
    public DataInputStream fis = null;
    public DataOutputStream fos = null;

    public BaseSaveLoadStream(StreamTypes t, String fileName){
        type = t;
        file = new File(fileName);
        open();
    }

    @Override
    public StreamTypes getType() {
        return type;
    }

    @Override
    public int readI() {
        int out = 0;
        try {
            out = fis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    @Override
    public void writeI(int i) {
        try {
            fos.writeInt(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte readB() {
        byte out = 0;
        try {
            out = fis.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    @Override
    public void writeB(byte b) {
        try {
            fos.writeByte(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public float readF() {
        float out = 0;
        try {
            out = fis.readFloat();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    @Override
    public void writeF(float f) {
        try {
            fos.writeFloat(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long readL() {
        long out = 0;
        try {
            out = fis.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    @Override
    public void writeL(long l) {
        try {
            fos.writeLong(l);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readS() {
        String out = null;
        try {
            out = fis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    @Override
    public void writeS(String s) {
        try {
            fos.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void open() {
        try {
            if(type == StreamTypes.READ) fis = new DataInputStream(new FileInputStream(file));
            else {
                Files.deleteIfExists(file.toPath());
                Files.createFile(file.toPath());
                fos = new DataOutputStream(new FileOutputStream(file));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
