/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import taskmgt.AdminGUI;
import taskmgt.Data;
import taskmgt.Models.Administrator;
import taskmgt.Models.ModelType;
import taskmgt.Models.Project;
import taskmgt.Models.Task;
import taskmgt.Models.TeamLeader;
import taskmgt.Models.TeamMember;
import taskmgt.Models.User;

/**
 *
 * @author AAperauch
 */
public class Porter {
    
    public static String readTextFile(String file)
    {
        try {
            //Read all of the data written to the file
            Path path = Paths.get(file);
            List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
            
            //Convert list to string builder
            StringBuilder sb = new StringBuilder();
            for (String s : list) {
                sb.append(s).append("\n");
            }
            
            return sb.toString();
        } catch (IOException ex) {
            Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static void writeTextFile(String file, String data)
    {
        
    }
    
    public static byte[] readBinaryFile(String file)
    {
        //Read bytes from file and return bytes        
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] encText = new byte[fis.available()];
            fis.read(encText);
            fis.close();
            return encText;
        } catch (IOException ex) {
            Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public static void writeBinaryFile(String file, byte[] data)
    {
        try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException ex) {
            Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean exportCSV(String file, ModelType type) {
        CSVWriter writer = null;
        
        try {
            List<String[]> dataList = new ArrayList<>();
            
            //Load the data into the LinkedList lists.
            Data.Initialize();
            
            writer = new CSVWriter(new FileWriter(file));
            
            if (type == ModelType.Project)
            {
            for (Project project:Data.projectList)
                dataList.add(project.toStringArray());
            }
            else if (type == ModelType.Task)
            {
                for (Task task:Data.taskList)
                    dataList.add(task.toStringArray());
            }
            else
            {
                for (User user:Data.userList)
                    dataList.add(user.toStringArray());
            }
            
            writer.writeAll(dataList);
            writer.flush();
            writer.close();
            
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (writer != null)
            {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return false;
    }
    
    public static boolean importCSV(String file, ModelType type, boolean merge) {        
        
        CSVReader reader = null;
        
        try {
            File f = new File(file);
            
            //If file does not exist
            if (!f.exists())
                f.createNewFile();
            
            reader = new CSVReader(new FileReader(file));
            List<String[]> dataList = reader.readAll();
            
            if (type == ModelType.Project)
            {
                for (String[] strArr:dataList)
                {
                    Project p = new Project(strArr);
                    Data.projectList.add(p);
                }
            }
            else if (type == ModelType.Task)
            {
                for (String[] strArr:dataList)
                {
                    Task t = new Task(strArr);
                    Data.taskList.add(t);
                }
            }
            else
            {
                for (String[] strArr:dataList)
                {
                    if (strArr[strArr.length - 1].equalsIgnoreCase(ModelType.TeamLeader.name()))
                    {
                        TeamLeader leader = new TeamLeader(strArr);
                        Data.userList.add(leader);
                    }
                    else if (strArr[strArr.length - 1].equalsIgnoreCase(ModelType.TeamMember.name()))
                    {
                        TeamMember member = new TeamMember(strArr);
                        Data.userList.add(member);
                    }
                    else if (strArr[strArr.length - 1].equalsIgnoreCase(ModelType.Admin.name()))
                    {
                        Administrator admin = new Administrator(strArr);
                        Data.userList.add(admin);
                    }
                }
            }
            
            reader.close();
            
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null)
            {
                try {
                    reader.close();
                } catch (IOException ex) {
                Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return false;
    }
    
}
