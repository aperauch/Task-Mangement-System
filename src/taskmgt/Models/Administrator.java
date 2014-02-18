/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package taskmgt.Models;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import taskmgt.Data;

public class Administrator extends User implements Serializable{
    //Attributes
    private final ModelType type = ModelType.Admin;
    
    //Constructors
    public Administrator(String name,String email){
        super(name,email, "");
    }
    
    public Administrator(String name,String email, String password){
        super(name,email,password);
    }
    
    public Administrator(String[] strArr)
    {
        super(strArr[0], strArr[1], strArr[2]);
        this.active = Boolean.valueOf(strArr[3]);
    }
    
    //Methods
    public boolean createLeader(String name, String email){
        TeamLeader leader=new TeamLeader(name, email);
        for (User u : Data.userList){
            if(u.equals(leader)&&u.checkActive()){
                return false;
            }
            else if(u.equals(leader)&&!u.checkActive()){
                u.setActive(true);
                JOptionPane.showMessageDialog(null,"Reactivating Leader: " + name + "!","Warning",JOptionPane.WARNING_MESSAGE);
                return true;   
            }
        }
        Data.userList.add(leader);
        return true;
    }
    
    public boolean createMember(String name, String email){
        TeamMember member=new TeamMember(name, email);
        for (User u : Data.userList){
            if(u.equals(member)&&u.checkActive()){
                return false;
            }
            else if(u.equals(member)&&!u.checkActive()){
                u.setActive(true);
                JOptionPane.showMessageDialog(null,"Reactivating Member: " + name + "!","Warning",JOptionPane.WARNING_MESSAGE);
                return true;   
            }
        }
        Data.userList.add(member);
        return true;
    }
    
    //update Member Info
    public void updateName(String email, String name){
        User newUser=Data.getUserByEmail(email);
        int index=Data.userList.indexOf(newUser);
        Data.userList.get(index).setName(name);
    }
    
    public void updateEmail(String oldEmail, String newEmail){
        User newUser = Data.getUserByEmail(oldEmail);
        
        for(Project project:Data.projectList){
            if(project.getOwner().equalsIgnoreCase(oldEmail)){
                project.setOwner(newEmail);
                
                for (Task task:project.getTasks())
                    if (task.getOwner().equalsIgnoreCase(oldEmail))
                        task.setOwner(newEmail);
            }
        }
        
        Data.userList.remove(newUser);
        newUser.setEmail(newEmail);
        Data.userList.add(newUser);
    }
    
    public boolean changeMemberType(String email,boolean leaderFlag){
        if(leaderFlag){
            TeamMember member=(TeamMember) Data.getUserByEmail(email);
            Data.userList.remove(member);
            TeamLeader leader=new TeamLeader(member);
            Data.userList.add(leader);
        }
        else{
            TeamLeader leader = (TeamLeader) Data.getUserByEmail(email);
            if(leader.getProjects().isEmpty()){
                Data.userList.remove(leader);
                TeamMember member=new TeamMember(leader);
                Data.userList.add(member);
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
 