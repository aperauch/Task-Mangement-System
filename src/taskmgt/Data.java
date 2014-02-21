package taskmgt;
/**
 *
 * @author Ray
 */
import java.util.Date;
import taskmgt.Models.*;
import java.util.LinkedList;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
import java.util.Collections;
import java.util.Comparator;

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
  
    public static void printMemberRpt(String e) throws IOException
    {
            PrintWriter output ;

            try {

            File file = new File ("reports/memberReport.txt");
            file.getParentFile().mkdirs();

            output = new PrintWriter("reports/memberReport.txt"); 
        
           TeamMember member = (TeamMember) getUserByEmail(e);
           
           
           LinkedList<Project> projectList = member.getProjects();
          
           int totalTaskCount = 0;
           int totalTaskCompletedCount = 0;
           
           output.println("\nMEMBER REPORT\n-------------");
           String taskHeader = String.format("\n%-30s%-15s%-15s%-15s%-4s", "TASK", "OWNER", "START DATE","END DATE", "DONE");
           String line = String.format("----------------------------------------------------------------------------------------");
           
           output.println(member.toString());
           output.println();
           
           if (projectList.size() == 0) {output.println("This member is not assigned to any projects");}
           for( Project p : projectList){
               int taskCount = 0;
               int taskCompletedCount = 0;
               
               output.println(p.toString());

               output.println(taskHeader);
               output.println(line);
               LinkedList<Task> taskList = p.getTasks();
               LinkedList<Task> projectTaskList = new LinkedList<Task>();
               
                  for( Task t : taskList)
                  {     
                   
                      if(Data.getUserByEmail(t.getOwner()) == member)
                            {  
                                projectTaskList.add(t);
  
                                // output.println(t.toString());
                                if(t.getStatus() == State.Completed){ taskCompletedCount++;}
                                taskCount++;
                            }
                 
                  }
                float percent = 0;  
                if(taskCount!=0){percent = (float)taskCompletedCount/(float)taskCount*100;}  
                Collections.sort(projectTaskList);
                // Collections.sort(projectTaskList, Comparator<Task> c);
                
                for (Task t : projectTaskList)
                {
                    output.println(t.toString());
                }
                
                output.printf("\nCompleted %d out of %d tasks (%.1f%%)\n\n", taskCompletedCount,taskCount,percent);
                
                totalTaskCount += taskCount;
                totalTaskCompletedCount += taskCompletedCount;
           }
           
           float percentTotal = 0;
                if(totalTaskCount!=0){percentTotal = (float) totalTaskCompletedCount / (float) totalTaskCount*100;}
           
           output.println(line);
           output.printf("\nMEMBER TOTALS:\nProjects: %d \tTasks: %d \t Completed: %d (%.1f%%)", projectList.size(), totalTaskCount,totalTaskCompletedCount,percentTotal);
           
           output.flush();

 }

 catch(IOException ioe) {

 System.out.println(ioe.toString());
    }
    
 Desktop desktop = Desktop.getDesktop();
        File dirToOpen = null;
        try {
            dirToOpen = new File("reports/memberReport.txt");
            desktop.open(dirToOpen);
        } catch (IllegalArgumentException iae) {
            System.out.println("File Not Found");
        }
           
//        //Create Member Array from Email Array
//        TeamMember[] memArr = new TeamMember[e.length];
//        for(int i=0;i<e.length;i++)
//        { memArr[i] = (TeamMember) getUserByEmail(e[i]);}
//        
//        // Create Lists - Project Master 
//        LinkedList<Project> projectMaster = getProjectList();
//        
//        //Wow!  A linked list of linked lists of Task Type!
//        LinkedList<LinkedList<Task>> mainList = new LinkedList<LinkedList<Task>>();
//        
//        // Loop - 
//        Task t1 = new Task();
//        mainList.add(new LinkedList<Task>());
//        mainList.get(0).add(t1);
//        mainList.size();
//        
//        // Loop - For each
        
        
        
    } 
    
 
}
