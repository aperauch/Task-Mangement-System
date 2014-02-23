package taskmgt.Models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import taskmgt.TaskSystem;

public class Project implements Serializable, Comparable<Project>{
    //Attributes
    private int id;
    private String title;
    private TeamLeader owner;
    private Date startDate;
    private Date endDate;
    private State status;
    private LinkedList<User> members = new LinkedList();
    private LinkedList<Task> tasks = new LinkedList();
    private final ModelType type = ModelType.Project;
    private final SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
    
    //Constructor
    public Project(String title,String owner, Date startDate, Date endDate){
        this.title=title;
        this.owner=(TeamLeader)TaskSystem.getUserByEmail(owner);
        this.startDate=startDate;
        this.endDate=endDate;
        this.status=State.New;
        this.setID();
    }

    public Project(String[] strArr) {
        this.id = Integer.parseInt(strArr[0]);
        this.title = strArr[1];
        this.owner = (TeamLeader)TaskSystem.getUserByEmail(strArr[2]);
        
        try {
            this.startDate = simpleDate.parse(strArr[3]);
            this.endDate = simpleDate.parse(strArr[4]);
        } catch (ParseException ex) {
            //Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        this.status = State.valueOf(strArr[5]);
    }
    
    //Get
    public int getID() { return this.id; }
    public String getTitle() { return this.title; }
    public String getOwner() { 
        if(this.owner!=null)
            return this.owner.getEmail();
        else
            return null;
    }
    public String getOwnerName(){
        if(this.owner!=null)
            return this.owner.getName();
        else
            return null;
    }
    public Date getStartDate() { return this.startDate; }
    public Date getEndDate() { return this.endDate; }
    public State getStatus() { return this.status; }
//    public ModelType getType() { return this.type; }
    public LinkedList<User> getMembers() { return this.members; }
    public LinkedList<Task> getTasks() { return this.tasks; }
    
    //Set
    public void setTitle(String title){ this.title=title;}
    public void setOwner(String owner){this.owner=(TeamLeader)TaskSystem.getUserByEmail(owner);}
    public void setStartDate(Date startDate){this.startDate=startDate;}
    public void setEndDate(Date endDate){this.endDate=endDate;}
//    public void setStatus(State status){this.status=status;}
    public void setMembers(LinkedList<User> members) { this.members = members; }
//    public void setTasks(LinkedList<Task> tasks) { this.tasks = tasks; }
    
    //Other methods
    //Add to Collection
    public void addTask(Task task){ 
        if (task.getID() == 0)
            task.setID(getNextTaskID());
        
        tasks.add(task); 
    }
    
    public void addMember(User user) { members.add(user); }
    
    //Remove from Collection
    public void removeTask(Task task) { tasks.remove(task); }
//    public void removeMember(User user) { members.remove(user); }
    
   
    public int getNextTaskID()
    {
        int max = 0;
        for(Task task:tasks){
            if(task!=null)
                max=task.getID()>=max?task.getID():max;
        }
        return max+1;
    }
    
    public Task getTaskByID(int taskID){
        for(Task task:tasks){
            if(task.getID()==taskID){
                return task;
            }
        }
        return null;
    }
    
//    public LinkedList<Task> getTaskByMemberEmail(TeamMember member){
//        LinkedList<Task> taskList=new LinkedList();
//        for(Task task:tasks){
//            if(task.getOwner().equalsIgnoreCase(member.getEmail()))
//                taskList.add(task);
//        }
//        return taskList;
//    }
//     
    
//    public int compareTo(Project p)
//    {
//        return this.title.compareToIgnoreCase(p.title);
//    }
    

//    public int compareTo(String p){
    //    return this.title.compareToIgnoreCase(p);
   // }
//    public int compareTo(String p)
//    {
//        return this.title.compareToIgnoreCase(p);
//    }

    @Override
        public int compareTo(Project p)
    {
        int retVal = this.startDate.compareTo(p.startDate);
        if(retVal != 0) return retVal;
        else{return this.id - p.getID();}
    }
    
    private void setID(){
        int max=0;
        for(Project project:TaskSystem.getProjectList()){
            if(project!=null)
            max=project.getID()>=max?project.getID():max;
        }
        this.id=max+1;
    }
    
    public String[] toStringArray()
    {
        LinkedList<String> attrs = new LinkedList<>();

        attrs.add(Integer.toString(id));
        attrs.add(owner.getEmail());
        attrs.add(title);
        attrs.add(simpleDate.format(startDate));
        attrs.add(simpleDate.format(endDate));
        attrs.add(status.name());
       
        for (Task t : tasks) {
            attrs.add((Integer.toString(t.getID())));
            attrs.add(t.getOwner());
            attrs.add(t.getTitle());
            attrs.add(Integer.toString(t.getProjectID()));
            attrs.add(simpleDate.format(t.getStartDate()));
            attrs.add(simpleDate.format(t.getEndDate()));
            attrs.add(t.getStatus().name());
        }

        return attrs.toArray(new String[attrs.size()]);
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Project){
            Project project=(Project) obj;
            return project.title.compareToIgnoreCase(this.title)==0;
        }
        return false;
    }

//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 73 * hash + Objects.hashCode(this.title);
//        return hash;
//    }
//    
     @Override
    public String toString() {
        String p = String.format("%-30s%-30s%-22s%-25s",title,"START DATE:" + simpleDate.format(startDate),"END DATE:" + simpleDate.format(endDate),"LEADER:" + owner.getName());
        return(p);
 }
           
}
