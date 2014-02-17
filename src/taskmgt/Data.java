package taskmgt;
/**
 *
 * @author Ray
 */
import java.util.Date;
import taskmgt.Models.*;
import java.util.LinkedList;

public class Data {
    //Properties
    private static User CurrentUser;
    
    //Lists
    public static LinkedList<User> userList = new LinkedList();
    public static LinkedList<Project> projectList = new LinkedList();
    public static LinkedList<Task> taskList = new LinkedList();

    //Serializer
    private final static Serializer<User> userSerializer=new Serializer(".//Data","User.ser");
    private final static Serializer<Project> projectSerializer=new Serializer(".//Data","Project.ser");
    private final static Serializer<Task> taskSerializer=new Serializer(".//Data","Task.ser");
    
    //Data Manipulation
    //Pull List from File
    public static void pullUser(){ userList=userSerializer.readObject();}
    public static void pullProject(){ projectList=projectSerializer.readObject();}
    public static void pullTask(){ taskList=taskSerializer.readObject();}
    
    //Push List to File
    public static void pushUser(){ userSerializer.writeObject(userList);}
    public static void pushProject(){ projectSerializer.writeObject(projectList);}
    public static void pushTask(){ taskSerializer.writeObject(taskList);}
    
    //Initializ and Finalize
    public static void Initialize(){
        pullUser();
        pullProject();
        pullTask();              
    }
    
    public static void checkforEmptyLists(){
        if(userList == null) {
            userList = new LinkedList<>();
        }
        
        if(projectList == null) {
            projectList = new LinkedList<>();
        }
        
        if(taskList==null) {
            taskList = new LinkedList<>();
        }
    }
    
    public static void Finalize(){
        pushUser();
        pushProject();
        pushTask();
    }
    
    public static void InitializeAdmin(String name,String email,String password){
        Administrator admin=new Administrator(name,email,password);
        
        if (userList == null)
            userList = new LinkedList<>();
        
        userList.add(admin);
        Finalize();
    }
    
    public static void InitializeProject(String title, String owner, Date start, Date end) {
        Project project = new Project(title, owner, start, end);
        projectList.add(project);
        Finalize();
    }
    
    public static void InitializeTask(String title,String owner,int projectID, Date start, Date end) {
        Task task = new Task(title, owner, projectID,start, end);
        taskList.add(task);
        Finalize();
    }
    
     public static void InitializeMember(String name,String email,boolean leader) {
        User member;
         if(leader == true){
         member = new TeamLeader(name,email);
         }
         else{
         member = new TeamMember(name,email);
         }
        userList.add(member);
        Finalize();
    }
    
    //Methods
    public static void setCurrentUser(User user) { CurrentUser = user; }
    public static User getCurrentUser() { return CurrentUser; }
    
    //Get List
    public static LinkedList<TeamLeader> getLeaders(){
        LinkedList<TeamLeader> leaders=new LinkedList();
        for(User user:userList){
            if(user.getType()==ModelType.TeamLeader) leaders.add((TeamLeader)user);
        }
        return leaders;
    }
    
    public static LinkedList<TeamMember> getMembers(){
        LinkedList<TeamMember> members=new LinkedList();
        for(User user:userList){
            if(user instanceof TeamMember) members.add((TeamMember)user);
        }
        return members;
    }
    
    //Get Object
    public static User getUser(String email, String password){
        for(User user:userList){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                 return user;
            }
        }
        return null;
    }
    
    public static User getUser(String email){
        for(User user:userList){
            if(user.getEmail().toLowerCase().equals(email.toLowerCase())){
                 return user;
            }
        }
        return null;
    }
         
    public static TeamMember getMember(String email){
        for(User user:userList){
            if(user.getEmail().equals(email)&& user.getType() == ModelType.TeamMember){
                return (TeamMember) user;
            }
        }
        return null;
    }
    
    public static TeamLeader getLeader(String email){
        for(User user:userList){
            if(user.getEmail().equals(email)&& user.getType() == ModelType.TeamLeader){
                return (TeamLeader) user;
            }
        }
        return null;
    }
    
    public static LinkedList<Task> getProjectTasks(int projectID){
        LinkedList<Task> projectTasks=new LinkedList();
        for(Task task:taskList){
            if(task.getProjectID()==projectID){
                projectTasks.add(task);
            }
        }
        return projectTasks;
    }
    
    public static Project getProject(int projectID){
        for(Project project:projectList){
            if(project.getID()==projectID){
                return project;
            }
        }
        return null;
    }
    
    public static Task getTask(int taskID){
        for(Task task:taskList){
            if(task.getID()==taskID){
                return task;
            }
        }
        return null;
    }
    
    static Task getTaskByTitle(String title) {
        for(Task task:taskList){
            if(task.getTitle().equalsIgnoreCase(title)){
                return task;
            }
        }
        return null;
    }
    
    public static void transferMember(String fromEmail, String toEmail) {
       
        
    }

    public static Project getProjectByTitle(String projectTitle) {
        for(Project project:projectList){
            if(project.getTitle().equalsIgnoreCase(projectTitle)){
                return project;
            }
        }
        return null;
    }
    
    public static void clearAllLists()
    {
        projectList = new LinkedList<>();
        taskList = new LinkedList<>();
        userList = new LinkedList<>();
    }
 
}
