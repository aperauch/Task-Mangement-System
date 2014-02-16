/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package taskmgt.Models;

import java.io.Serializable;
import java.util.ArrayList;
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
        if(Data.userList.contains(leader)){
            return false;
        }
        else{
            Data.userList.add(leader);
            return true;
        }
    }
    
    public boolean createMember(String name, String email){  
        TeamMember member=new TeamMember(name, email);
        if(Data.userList.contains(member)){
            return false;
        }
        else{
            Data.userList.add(member);
            return true;
        }
    }
    
    //update Member Info
    public void updateName(String email, String name){
        User newUser=Data.getUser(email);
        newUser.setName(name);
        Data.userList.add(newUser);
    }
    
    public void updateEmail(String oldEmail, String newEmail){
        User newUser=Data.getUser(oldEmail);
        for(Project project:Data.projectList){
            if(project.getOwner().toLowerCase().equals(oldEmail)){
                project.setOwner(newEmail);
            }
        }
        for(Task task:Data.taskList){
            if(task.getOwner().toLowerCase().equals(oldEmail)){
                task.setOwner(newEmail);
            }
        }
        Data.userList.remove(newUser);
        newUser.setEmail(newEmail);
        Data.userList.add(newUser);
    }
    
    public boolean changeMemberType(String email,boolean leaderFlag){
        if(leaderFlag){
            TeamMember member=(TeamMember) Data.getUser(email);
            Data.userList.remove(member);
            TeamLeader leader=new TeamLeader(member);
            Data.userList.add(leader);
        }
        else{
            TeamLeader leader=(TeamLeader) Data.getUser(email);
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
 