package taskmgt;

/**
 *
 * @author Ray
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
 
public class Serializer<Type> {
    private final String fileName;
    private final String dirPath;
    
    public Serializer(String dirPath, String filename) {
        this.fileName = filename;
        this.dirPath = dirPath;
        
        File directory = new File(dirPath);
        if (!directory.exists())
            createDir();
        
        String filePath;
        if (dirPath.endsWith("\\"))
            filePath = dirPath + filename;
        else if (dirPath.endsWith("//"))
            filePath = dirPath + filename;
        else
            filePath = dirPath + "\\" + filename;
        
        File file = new File(filePath);
        if (!file.exists())
        {
            createFile();
        }
        if (createDir()) { createFile();}
    }
    
    private boolean createDir() {
        if (dirPath!=null) {
            File path = new File(dirPath);
            if (path.exists()) { return true;}
            if (path.mkdirs()) return true;
        }
        return false;
    }
    
    private void createFile() {
        if (fileName!=null) {
            File file = new File(dirPath, fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void writeObject(HashSet<Type> message) {
        File file = new File(dirPath,fileName);
        if (file.isFile() == false) { return;}
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file,false));
            oos.writeObject(message);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public HashSet<Type> readObject() {
        File file = new File(dirPath,fileName);
        ObjectInputStream ois = null;
        HashSet<Type> message = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            try {
                message = (HashSet<Type>)ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }
    
}
