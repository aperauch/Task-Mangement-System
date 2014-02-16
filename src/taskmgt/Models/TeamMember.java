package taskmgt.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import taskmgt.Data;

/**
 *
 * @author Ray
 */
public class TeamMember extends User implements Serializable{
    //Attribute
    private final ModelType type = ModelType.TeamMember;
    
    //Constuctors
    public TeamMember() { }
    
    public TeamMember(String name,String email){
        super(name,email,""); 
    }
    
    public TeamMember(String name, String email, String password) {
        super(name,email,password); 
    }
    
    public TeamMember(TeamLeader leader){
        this.email=leader.email;
        this.name=leader.name;
        this.password=leader.password;
        this.active=leader.active;
    }
    
    public TeamMember(String[] strArr){
        super(strArr[0], strArr[1], strArr[2]);        
        this.active= Boolean.valueOf(strArr[3]);
    }
    
    //Methods 
    public void submitTaskForApproval(Task task){
        
    }
    
    public HashSet<Project> getProjects(){
        HashSet<Project> projectList=new HashSet();
        for(Project project:Data.projectList){
            if(project.getOwner().toLowerCase().equals(this.email.toLowerCase()))
                projectList.add(project);
        }
        return projectList;
    }
    
    public HashSet<Task> getTasks(){
        HashSet<Task> taskList=new HashSet();
        for(Task task:Data.taskList){
            if(task.getOwner().toLowerCase().equals(this.email.toLowerCase()))
                taskList.add(task);
        }
        return taskList;
    }
    
    public HashSet<Task> getAssignedTasks(int projectID){
        HashSet<Task> taskList=new HashSet();
        for(Task task:Data.taskList){
            if(task.getOwner().toLowerCase().equals(this.email.toLowerCase())&&task.getProjectID()==projectID)
                taskList.add(task);
        }        
        return taskList;
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
