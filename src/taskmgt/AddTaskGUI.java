/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package taskmgt;

import java.awt.Component;
import java.text.*;
import java.util.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import taskmgt.Models.*;

/**
 *
 * @author Ray
 */
public class AddTaskGUI extends javax.swing.JDialog {
    
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    ProjectTaskGUI parentFrame;
    Task taskToEdit;
    
    private void setFormAdd(){
       this.setTitle("Add Task");
       addTaskBtn.setText("Add");

       //Populate Start Date and End Date with a date
       Date date = new Date();
       startDateField.setText(dateFormat.format(date).toString());
       endDateField.setText(dateFormat.format(date).toString());
    }
    
     private void setFormEdit(){
       this.setTitle("Edit Task");
       addTaskBtn.setText("Edit");
     }
     
    private void setFormEdit(Task task){
       this.setTitle("Edit Task");
       addTaskBtn.setText("Edit");
       
       //Populate fields for editing
       taskTitleField.setText(task.getTitle());
       startDateField.setText(dateFormat.format(task.getStartDate()));
       endDateField.setText(dateFormat.format(task.getEndDate()));
    }
    
    /**
     * Creates new form Task
     */
    //Default Constructor
    public AddTaskGUI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        taskToEdit = null;
        
        //The parent frame is the window the user was interacting with prior to this window.
        //This variable allows for updates to be called back to parent.
        parentFrame = (ProjectTaskGUI) parent;
      
        //Populate Members list
        for(TeamMember member:Data.getMembers()){
            if(member.checkActive()){
                ownerComboBox.addItem(member.getEmail());
            }
        }
    }
    
    //Called when adding a task
    public AddTaskGUI(java.awt.Frame parent, boolean modal, String flag) {
        super(parent, modal);
        initComponents();
        taskToEdit = null;
        
        //The parent frame is the window the user was interacting with prior to this window.
        //This variable allows for updates to be called back to parent.
        parentFrame = (ProjectTaskGUI) parent;
        
        //Populate Members list
        for(TeamMember member:Data.getMembers()){
            if(member.checkActive()){
                ownerComboBox.addItem(member.getEmail());
            }
        }
        
        if(flag.equals("add"))
            setFormAdd();
        else
            setFormEdit();
    }
    
    //Not Called!?!?!?
    public AddTaskGUI(java.awt.Frame parent, boolean modal, String flag, Task task) {
        super(parent, modal);
        initComponents();
        taskToEdit = task;
        
        //The parent frame is the window the user was interacting with prior to this window.
        //This variable allows for updates to be called back to parent.
        parentFrame = (ProjectTaskGUI) parent;
        
        //Populate Members list
        for(TeamMember member:Data.getMembers()){
            if(member.checkActive()){
                ownerComboBox.addItem(member.getEmail());
            }
        }
        
        if(flag.equals("add"))
            setFormAdd();
        else
            setFormEdit(task);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        taskTitleField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        addTaskBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ownerComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        startDateField = new javax.swing.JTextField();
        endDateField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Task");

        taskTitleField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taskTitleFieldActionPerformed(evt);
            }
        });

        jButton2.setText("Request");

        addTaskBtn.setText("Add");
        addTaskBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTaskBtnActionPerformed(evt);
            }
        });

        jLabel3.setText("End Date:");

        jLabel2.setText("Start Date:");

        jLabel4.setText("Team Member List:");

        jLabel1.setText("Task Title:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(47, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(taskTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ownerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton2)
                                .addComponent(endDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(addTaskBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(88, 88, 88))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(taskTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(startDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(endDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ownerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addTaskBtn)
                    .addComponent(jButton2))
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void taskTitleFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taskTitleFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_taskTitleFieldActionPerformed

    private void addTaskBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTaskBtnActionPerformed
        
        //Get attributes that are not given from textfields
        Project project = parentFrame.getSelectProject();
        int projectID = project.getID();
        String ownerEmail = (String) ownerComboBox.getSelectedItem();
               
        //Get attributes from the textfields
        String title = taskTitleField.getText();
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();
        
        //Convert date Strings to Date objects
        Date start = new Date();
        Date end = new Date();
        try {        
            start = dateFormat.parse(startDate);
            end = dateFormat.parse(endDate);
        } catch (ParseException ex) {
            Logger.getLogger(AddTaskGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //If adding a task
        if (addTaskBtn.getText().equalsIgnoreCase("add"))
        {
            //Create Task object with attributes
            Task task = new Task(title, ownerEmail, projectID, start, end);

            //Push task onto task list for the project
            Data.taskList.add(task);

            //Update parent frame with new tasks
            parentFrame.addTaskTableRow(task);
        }
        //Else, editing a task
        else
        {
            if (taskToEdit != null)
            {
                //Edit task details
                taskToEdit.setTitle(title);
                taskToEdit.setStartDate(start);
                taskToEdit.setEndDate(end);
                
                 //Show message
                JOptionPane.showMessageDialog(null, "Modified task " + title + " for project " + project.getTitle() + ".");

                //Update parent frame with new tasks
                parentFrame.addTaskTableRow(taskToEdit);
            }
        }
        
    }//GEN-LAST:event_addTaskBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddTaskGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddTaskGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddTaskGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddTaskGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddTaskGUI dialog = new AddTaskGUI(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTaskBtn;
    private javax.swing.JTextField endDateField;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox ownerComboBox;
    private javax.swing.JTextField startDateField;
    private javax.swing.JTextField taskTitleField;
    // End of variables declaration//GEN-END:variables
}