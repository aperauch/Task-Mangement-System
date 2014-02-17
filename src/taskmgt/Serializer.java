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
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
 
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
    
    public void writeObject(LinkedList<Type> message) {
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
    public LinkedList<Type> readObject() {
        try {
            File file = new File(dirPath,fileName);
            ObjectInputStream ois = null;
            LinkedList<Type> message = null;
            
            if(!file.exists())
                file.createNewFile();
            
            FileInputStream fis = new FileInputStream(file);
            
            if (fis.available() > 0)
            {
                ois = new ObjectInputStream(fis);                
                message = (LinkedList<Type>)ois.readObject();                
                
                return message;
            }  
            else {
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Serializer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Serializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
