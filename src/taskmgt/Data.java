package taskmgt;
/**
 *
 * @author Ray
 */
import java.util.Date;
import taskmgt.Models.*;
import java.util.LinkedList;

//Good Job Alex!

public class Data {
    //Properties
    private static User CurrentUser;
    
    //Lists
    public static LinkedList<User> userList = new LinkedList();
    public static LinkedList<Project> projectList = new LinkedList();

    //Serializer
    private final static Serializer<User> userSerializer=new Serializer(".//Data","User.ser");
    private final static Serializer<Project> projectSerializer=new Serializer(".//Data","Project.ser");
    
    //Data Manipulation
    //Pull List from File
    public static void pullUser(){ userList=userSerializer.readObject();}
    public static void pullProject(){ projectList=projectSerializer.readObject();}
    
    //Push List to File
    public static void pushUser(){ userSerializer.writeObject(userList);}
    public static void pushProject(){ projectSerializer.writeObject(projectList);}
    
    //Initialize and Finalize
    public static void Initialize(){
        pullUser();
        pullProject();             
    }
    
    public static void checkforEmptyLists(){
        if(userList == null) {
            userList = new LinkedList<>();
        }
        
        if(projectList == null) {
            projectList = new LinkedList<>();
        }
    }
    
    public static void Finalize(){
        pushUser();
        pushProject();
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
    
    
     public static void InitializeMember(String name,String email,boolean leader) {
        User member;
         if(leader){
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
    public static LinkedList<Project> getProjectList() { return projectList;}
    public static LinkedList<User> getUserList() { return userList;}
    
    public static LinkedList<TeamLeader> getLeaders(){
        LinkedList<TeamLeader> leaders=new LinkedList();
        for(User user:userList){
            if(user instanceof TeamLeader) leaders.add((TeamLeader)user);
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

    public static User getUserByEmail(String email){
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
    
//    public static LinkedList<Task> getProjectTasks(int projectID){
//        LinkedList<Task> projectTasks=new LinkedList();
//        for(Task task:taskList){
//            if(task.getProjectID()==projectID){
//                projectTasks.add(task);
//            }
//        }
//        return projectTasks;
//    }
    
    public static Project getProject(int projectID){
        for(Project project:projectList){
            if(project.getID()==projectID){
                return project;
            }
        }
        return null;
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
        userList = new LinkedList<>();
    }
  
    public static void printMemberRpt(String[] e)
    {
        
        //Create Member Array from Email Array
        TeamMember[] memArr = new TeamMember[e.length];
        for(int i=0;i<e.length;i++)
        { memArr[i] = (TeamMember) getUserByEmail(e[i]);}
        
        // Create Lists - Project Master 
        LinkedList<Project> projectMaster = getProjectList();
        
        //Wow!  A linked list of linked lists of Task Type!
        LinkedList<LinkedList<Task>> mainList = new LinkedList<LinkedList<Task>>();
        
        // Loop - 
        Task t1 = new Task();
        mainList.add(new LinkedList<Task>());
        mainList.get(0).add(t1);
        mainList.size();
        
        // Loop - For each
        
        
        
    } 
    
 
}
