/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package taskmgt.Models;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import taskmgt.TaskSystem;

public class Administrator extends User implements Serializable{
    //Attributes
    private final ModelType type = ModelType.Admin;
    
    //Constructors
    public Administrator(String name,String email){
        super(name,email, "", true);
    }
    
    public Administrator(String name,String email, String password){
        super(name,email,password, true);
    }
    
    public Administrator(String[] strArr)
    {
        super(strArr[0], strArr[1], strArr[2], Boolean.valueOf(strArr[3]));
    }
    
    //Methods
    public boolean createLeader(String name, String email){
        TeamLeader leader=new TeamLeader(name, email);        
        for (User u : TaskSystem.getUserList()){
            if(u.equals(leader)&&u.checkActive()){
                return false;
            }
            else if(u.equals(leader)&&!u.checkActive()){
                u.setActive(true);
                JOptionPane.showMessageDialog(null,"Reactivating Leader: " + name + "!","Warning",JOptionPane.WARNING_MESSAGE);
                return true;   
            }
        }
        TaskSystem.getUserList().add(leader);
        return true;
    }
    
    public boolean createMember(String name, String email){
        TeamMember member=new TeamMember(name, email);
        for (User u : TaskSystem.getUserList()){
            if(u.equals(member)&&u.checkActive()){
                return false;
            }
            else if(u.equals(member)&&!u.checkActive()){
                u.setActive(true);
                JOptionPane.showMessageDialog(null,"Reactivating Member: " + name + "!","Warning",JOptionPane.WARNING_MESSAGE);
                return true;   
            }
        }
        TaskSystem.setUserList(member);
        return true;
    }
    
    //update Member Info
    public void updateName(String email, String name){
        User newUser=TaskSystem.getUserByEmail(email);
        int index=TaskSystem.getUserList().indexOf(newUser);
        TaskSystem.getUserList().get(index).setName(name);
    }
    
    public void updateEmail(String oldEmail, String newEmail){
        User newUser = TaskSystem.getUserByEmail(oldEmail);
        
        for(Project project:TaskSystem.getProjectList()){
            if(oldEmail.equalsIgnoreCase(project.getOwner())){
                project.setOwner(newEmail);
                
                for (Task task:project.getTasks())
                    if (oldEmail.equalsIgnoreCase(task.getOwner()))
                        task.setOwner(newEmail);
            }
        }
        
        TaskSystem.getUserList().remove(newUser);
        newUser.setEmail(newEmail);
        TaskSystem.setUserList(newUser);
    }
    
    public boolean changeMemberType(String email,boolean leaderFlag){
        if(leaderFlag){
            TeamMember member=(TeamMember) TaskSystem.getUserByEmail(email);
            TaskSystem.getUserList().remove(member);
            TeamLeader leader=new TeamLeader(member);
            TaskSystem.setUserList(leader);
        }
        else{
            TeamLeader leader = (TeamLeader) TaskSystem.getUserByEmail(email);
            boolean flag=true;
            for(Project project:TaskSystem.getProjectList()){
                if(email.equalsIgnoreCase(project.getOwner())){
                    flag=false;
                    break;
                }
            }
            if(flag){
                TaskSystem.getUserList().remove(leader);
                TeamMember member=new TeamMember(leader);
                TaskSystem.setUserList(member);
            }
            else{
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String[] toStringArray() {
        ArrayList<String> attrs = new ArrayList<>();
        
        attrs.add(name);
        attrs.add(email);
        attrs.add(password);
        attrs.add(String.valueOf(active));
        attrs.add(type.name());
        
        return attrs.toArray(new String[attrs.size()]);
    }
   
}
 