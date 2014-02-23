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
import javax.swing.JOptionPane;
import taskmgt.AdminGUI;
import taskmgt.TaskSystem;
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
            //Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
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
            //Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
    
    
    public static void writeBinaryFile(String file, byte[] data)
    {
        try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException ex) {
            //Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            //Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static boolean exportCSV(String file, ModelType type) {
        CSVWriter writer = null;
       
      
        try {
            List<String[]> dataList = new ArrayList<>();
            
            //Load the data into the LinkedList lists.
            TaskSystem.Initialize();
            
            writer = new CSVWriter(new FileWriter(file));
            
            if (type == ModelType.Project)
            {
                for (Project project:TaskSystem.getProjectList())
                    dataList.add(project.toStringArray());
                }
            else if (type == ModelType.Task)
            {
                for (Project project:TaskSystem.getProjectList()) {
                    for (Task task :project.getTasks())
                        dataList.add(task.toStringArray());
                    
                    
                    
                }
            }
            else
            {
                for (User user:TaskSystem.getUserList())
                    dataList.add(user.toStringArray());
            }
            
            writer.writeAll(dataList);
            writer.flush();
            writer.close();
            
            return true;
        } catch (IOException ex) {
            //Logger.getLogger(AdminGUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (writer != null)
            {
                try {
                    writer.close();
                } catch (IOException ex) {
                    //Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        return false;
    }
    
    public static boolean importCSV(String file, ModelType type) {        
        
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
                    Project p = null;
                    Task t = null;
                    String[] impProject = new String[6];
                    String[] impTask = new String[7];
                    
                    //Get project elements
                    for (int i = 0; i < 6; i++) {
                        impProject[i] = strArr[i];                       
                    }
                    
                    //Create the new project and add to list
                    p = new Project(impProject);
                    TaskSystem.getProjectList().add(p);
                    
                    //If there are project members
                    if (strArr.length >= 6)
                    {
                        //int numMembers = (strArr.length - 5);
                        for (int i = 6; i < strArr.length; i++)
                        {
                            if (TaskSystem.getUserList() != null)
                            {
                                User u = TaskSystem.getUserByEmail(strArr[i]);
                                if (u != null)
                                    p.addMember(u);
                            }
                        }
                    }
                }

            }
            else if (type == ModelType.Task)
            {
                for (String[] strArr:dataList)
                {
                    //Create a new task
                    Task t = new Task(strArr);
                    //Get the corresponding project and add the newly created task
                    TaskSystem.getProjectByID(t.getProjectID()).addTask(t);
                }
            }
            else
            {
                for (String[] strArr:dataList)
                {
                    if (strArr[strArr.length - 1].equalsIgnoreCase(ModelType.TeamLeader.name()))
                    {
                        TeamLeader leader = new TeamLeader(strArr);
                        TaskSystem.setUserList(leader);
                    }
                    else if (strArr[strArr.length - 1].equalsIgnoreCase(ModelType.TeamMember.name()))
                    {
                        TeamMember member = new TeamMember(strArr);
                        TaskSystem.setUserList(member);
                    }
                    else if (strArr[strArr.length - 1].equalsIgnoreCase(ModelType.Admin.name()))
                    {
                        Administrator admin = new Administrator(strArr);
                        TaskSystem.setUserList(admin);
                    }
                }
            }
            
            reader.close();
            
            return true;
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            //Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (reader != null)
            {
                try {
                    reader.close();
                } catch (IOException ex) {
                    //Logger.getLogger(Porter.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        return false;
    }
    
}
