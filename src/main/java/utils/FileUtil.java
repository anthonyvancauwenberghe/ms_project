package utils;

import java.io.*;

public class FileUtil<T> {

    protected String path;

    public FileUtil(String path) {
        this.path = path;
    }

    public T readObjectFromFile(){
        try {
            FileInputStream in = new FileInputStream(path);
            ObjectInputStream r = new ObjectInputStream(in);
            return (T) r.readObject();

        } catch (IOException e) {

        }catch (ClassNotFoundException e) {
        }

        return null;
    }

    public void writeObjectToFile(T object){
        try {
            FileOutputStream f = new FileOutputStream(path);
            ObjectOutput w = new ObjectOutputStream(f);
            w.writeObject(object);

        } catch (IOException e) {

        }
    }
}
