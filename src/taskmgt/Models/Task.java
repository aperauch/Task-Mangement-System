/**
 * Jan 25, 2014
 * Aron Aperauch
 * ISM 6257, Section 0305 - Fall 2013
 * Dr. Seema Bandyopadhyay
 *
 * Task.java
 */

package taskmgt.Models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import taskmgt.Data;

public class Task implements Serializable{
   //Attributes
    private int id;
    private String title;
    private String owner;
    private int projectID;
    private Date startDate;
    private Date endDate;
    private State status;
    private ModelType type = ModelType.Task;
    private SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
    
    //Constructor
    public Task(String title,String owner,int projectID, Date startDate, Date endDate){
        this.title=title;
        this.owner=owner;
        this.projectID=projectID;
        this.startDate=startDate;
        this.endDate=endDate;
        this.status=State.New;
        this.setID();
    }

    public Task(String[] strArr) {
        this.id = Integer.valueOf(strArr[0]);
        this.title=strArr[1];
        this.owner=strArr[2];
        this.projectID=Integer.valueOf(strArr[3]);
        
        try {
            this.startDate = simpleDate.parse(strArr[4]);
            this.endDate = simpleDate.parse(strArr[5]);
        } catch (ParseException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.status = State.valueOf(strArr[6]);
    }
    
    //Get
    public int getID() { return this.id; }
    public String getTitle() { return this.title; }
    public String getOwner() { return this.owner; }
    public int getProjectID(){return this.projectID;}
    public Date getStartDate() { return this.startDate; }
    public Date getEndDate() { return this.endDate; }
    public State getStatus() { return this.status; }
    public ModelType getType() { return this.type; }
    //Set
    public void setTitle(String title){ this.title=title;}
    public void setOwner(String owner){this.owner=owner;}
    public void setProjectID(int projectID){this.projectID=projectID;}
    public void setStartDate(Date startDate){this.startDate=startDate;}
    public void setEndDate(Date endDate){this.endDate=endDate;}
    public void setStatus(State status){this.status=status;}
    private void setID(){
        int max=0;
        for(Task task:Data.taskList){
            if(task!=null)
                max=task.getID()>=max?task.getID():max;
        }
        this.id=max+1;
    }
    public String[] toStringArray() {
        ArrayList<String> attrs = new ArrayList<>();

        attrs.add(Integer.toString(id));
        attrs.add(owner);
        attrs.add(title);
        attrs.add(Integer.toString(projectID));
        attrs.add(simpleDate.format(startDate));
        attrs.add(simpleDate.format(endDate));
        attrs.add(status.name());

       return attrs.toArray(new String[attrs.size()]);
    }
    
    public String[] toTableRow() {
        ArrayList<String> attrs = new ArrayList<>();

        attrs.add(title);        
        attrs.add(simpleDate.format(startDate));
        attrs.add(simpleDate.format(endDate));    
        attrs.add(owner);
        attrs.add(status.name());

       return attrs.toArray(new String[attrs.size()]);
    }
}
