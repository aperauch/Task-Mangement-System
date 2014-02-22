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
  
    public static void printMemberRpt(TeamMember[] memberList) throws IOException
    {
            PrintWriter output ;

            try {

            File file = new File ("reports/memberReport.txt");
            file.getParentFile().mkdirs();

            output = new PrintWriter("reports/memberReport.txt"); 
           
          output.println("\nMEMBER REPORT\n-------------"); 
          String taskHeader = String.format("%-30s%-15s%-15s%-15s%-4s", "TASK", "OWNER", "START DATE","END DATE", "DONE");
          String line = String.format("----------------------------------------------------------------------------------------");
            
           int[] totalProjectCount = new int[memberList.length];
           int[] totalTaskCount = new int[memberList.length];
           int[] totalTaskCompletedCount = new int[memberList.length]; 
           
           LinkedList<Project> uniqueProjectList = new LinkedList<Project>();
           
           //PRINTS .TOSTRING OF EVERY MEMBER SELECTED
           for (TeamMember member: memberList){output.println(member.toString());}
           output.println();
           
           //CONSOLIDATES UNIQUE PROJECTS OF EACH MEMBER INTO CONSOLIDATED LIST
            LinkedList<Project> projectList = null;
            for (TeamMember member: memberList){

                projectList = member.getProjects();
                
                int i = 0;
                for (Project p : projectList){
                    totalProjectCount[i]++;
                    if(!uniqueProjectList.contains(p)){uniqueProjectList.add(p);}
                }
                i++;
            }
      
            //SORT PROJECT LIST (BY START DATE)
            Collections.sort(uniqueProjectList);
            
            //FOR EACH PROJECT...
            for( Project p : uniqueProjectList){
              
                int[] taskCount = new int[memberList.length];
                int[] taskCompletedCount = new int[memberList.length]; 
                
                // PRINT THE PROJECT .TOSTRING
                output.println(p.toString());
               
                // PRINT THE HEADER
                output.println(line);
                output.println(taskHeader);
                output.println(line);
                
                // GET THE PROJECT'S TASKS (ALL)
                LinkedList<Task> taskList = p.getTasks();
                LinkedList<Task> projectTaskList = new LinkedList<Task>();

                            for( Task t : taskList)
                            {     
                                for(int j=0; j < memberList.length; j++){
                                    if(Data.getUserByEmail(t.getOwner()) == memberList[j])
                                      {  
                                          projectTaskList.add(t);
                                          
                                          if(t.getStatus() == State.Completed){ taskCompletedCount[j]++;}
                                          
                                          taskCount[j]++;
                                      }
                                }

                            }
                 
                 Collections.sort(projectTaskList);
                 // Collections.sort(projectTaskList, Comparator<Task> c);

                 for (Task pt : projectTaskList)
                 {
                     output.println(pt.toString());
                 }
                 
                 output.println();
                 
                 int k=0;
                 for (User m : memberList){
                 float percent = 0;  
                 if(taskCount[k]!=0){
                     percent = (float)taskCompletedCount[k]/(float)taskCount[k]*100;
                     String mem = String.format("%s completed %d out of %d tasks (%.1f%%)", m.getName(), taskCompletedCount[k],taskCount[k],percent);
                     output.println(mem);
                     
                     totalTaskCount[k] += taskCount[k];
                     totalTaskCompletedCount[k] += taskCompletedCount[k];
                                    }
                 k++;
                 }
                 output.println();
            }
                
                
                
            output.println("\nTOTALS"); 
            output.println(line);
            output.printf("%-30s%-15s%-15s%-15s%-10s", "MEMBER", "PROJECTS", "TASKS","COMPLETED", "(%)");
            output.println(line);
            
            //PRINT MEMBER TOTALS
            for(int i=0;i<memberList.length;i++){
                float percent = (float)totalTaskCompletedCount[i]/(float)totalTaskCount[i]*100;
                String mem = String.format("%-30s%-15d%-15d%-15d%-10.1f", memberList[i].getName(), totalProjectCount[i], totalTaskCount[i], totalTaskCompletedCount[i], percent);
                output.println(mem);
            }
            
            //PRINT GRAND TOTAL (IF MULTIPLE)
            if(memberList.length > 1){
                    int grandTotalProjects = 0;
                    int grandTotalTasks = 0;
                    int grandTotalCompleted = 0;
                
                for(int i=0;i<memberList.length;i++){
                    grandTotalProjects += totalProjectCount[i];
                    grandTotalTasks += totalTaskCount[i];
                    grandTotalCompleted += totalTaskCompletedCount[i]; 
                }
                
                float percent = (float)grandTotalCompleted/(float)grandTotalTasks*100;
                    output.println(line);
                    String grand = String.format("%-30s%-15d%-15d%-15d%-10.1f", "GRAND TOTAL",grandTotalProjects, grandTotalTasks, grandTotalCompleted, percent);
                    output.println(grand);
            }
            
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
           
        
        
    } 
    
 
}
