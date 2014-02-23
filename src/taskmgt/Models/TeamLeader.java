package taskmgt.Models;

import java.io.Serializable;
import java.util.ArrayList;
import taskmgt.TaskSystem;

/**
 *
 * @author Ray
 */
public class TeamLeader extends TeamMember implements Serializable {
    //Attributes
    private final ModelType type = ModelType.TeamLeader;

    //Constructors
    public TeamLeader(String name,String email){
        super(name,email, "");
    }
    
    public TeamLeader(String name,String email, String password){
        super(name,email, password);
    }
    
    public TeamLeader(TeamMember member){
        this.active=member.active;
        this.email=member.email;
        this.name=member.name;
        this.password=member.password;
    }

    public TeamLeader(String[] strArr) {
        super(strArr[0], strArr[1], strArr[2]);        
        this.active= Boolean.valueOf(strArr[3]);
    }
    //Methods
    public void createProject(){
        
    }
    public void assignProject(User user){
        
    }
    
    public void updateTaskOwnerAndUpdateUserList(String oldEmail, String newEmail, int taskID, Project project){
        User newUser = TaskSystem.getUserByEmail(oldEmail);
        
        Task changedTask = project.getTaskByID(taskID);
        for(Task task:project.getTasks()){
            if(oldEmail.equalsIgnoreCase(task.getOwner()) & task.getID() == taskID){
                task.setOwner(newEmail);
                break;
            }
        }
        
        TaskSystem.getUserList().remove(newUser);
        newUser.setEmail(newEmail);
        TaskSystem.setUserList(newUser);
    }
    
//    public void updateTaskOwnerAndUpdateUserList(String oldEmail, String newEmail, int taskID){
//        //User newUser=TaskSystem.getUserByEmail(oldEmail);
//        Task changedTask = TaskSystem.getTaskByID(taskID);
//        for(Task task:TaskSystem.taskList){
//            if(task.getOwner().equalsIgnoreCase(oldEmail) & task.getID() == taskID){
//                task.setOwner(newEmail);
//                break;
//            }
//        }
//        
//        TaskSystem.taskList.remove(changedTask);
//        //Data.userList.remove(newUser);
//        changedTask.setOwner(newEmail);
//        //newUser.setEmail(newEmail);
//        TaskSystem.taskList.add(changedTask);
//        //Data.userList.add(newUser);
//    }
    
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
    
     @Override
    public String toString() {
        
        return(super.toString() + "/Leader");
 }
    
}
