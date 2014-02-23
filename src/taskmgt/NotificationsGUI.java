/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package taskmgt;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import taskmgt.Models.Project;
import taskmgt.Models.State;
import taskmgt.Models.Task;
import taskmgt.Models.TeamLeader;
import taskmgt.Models.User;


/**
 *
 * @author Ray
 */
public final class NotificationsGUI extends javax.swing.JDialog {

    private final User notifyUser;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private final ProjectTaskGUI ptGUI;
    //Jtable Function
    
    //Fill Whole Table
    private void fillTable(){
        DefaultTableModel tableModel=(DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        if(notifyUser instanceof TeamLeader){
            for(Project project:TaskSystem.getProjectList()){
                if(notifyUser.getEmail().equalsIgnoreCase(project.getOwner())){
                    for(Task task:project.getTasks()){
                        if(task.getStatus()==State.New){
                            LinkedList<String> row=new LinkedList();
                            row.add(project.getTitle());
                            row.add(Integer.toString(task.getID()));
                            row.add(task.getTitle());    
                            row.add(task.getOwner());
                            row.add(dateFormat.format(task.getStartDate()));
                            row.add(dateFormat.format(task.getEndDate()));   
                            tableModel.addRow(row.toArray());
                        } //end if
                } // end for
            } //end if  
            for(Task task:project.getTasks()){    
            if(task.getStatus()==State.ToDoNotify&&notifyUser.getEmail().equalsIgnoreCase(task.getOwner())){
                        LinkedList<String> row=new LinkedList();
                        row.add(project.getTitle());
                        row.add(Integer.toString(task.getID()));
                        row.add(task.getTitle());    
                        row.add(task.getOwner());
                        row.add(dateFormat.format(task.getStartDate()));
                        row.add(dateFormat.format(task.getEndDate()));   
                        tableModel.addRow(row.toArray());
                    } // end if
            }// end for
        } //end for
        } //end if
        else{
            for(Project project:TaskSystem.getProjectList()){  
            for(Task task:project.getTasks()){ 
         if(task.getStatus()==State.ToDoNotify&&notifyUser.getEmail().equalsIgnoreCase(task.getOwner())){
                        LinkedList<String> row=new LinkedList();
                        row.add(project.getTitle());
                        row.add(Integer.toString(task.getID()));
                        row.add(task.getTitle());    
                        row.add(task.getOwner());
                        row.add(dateFormat.format(task.getStartDate()));
                        row.add(dateFormat.format(task.getEndDate()));   
                        tableModel.addRow(row.toArray());
                    } // end if
        
        } //end for
            } //end for
                } //end else
        jTable1.setModel(tableModel);
    } // end fillTable()
    
    //Initial Table
    private void initialTable(){
        //Inital table
        jTable1.setShowGrid(false);
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jTable1.setGridColor(Color.BLUE);
        jTable1.getTableHeader().setReorderingAllowed(false);
    }
    
    /**
     * Creates new form Notes
     * @param parent
     * @param modal
     */
    public NotificationsGUI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        notifyUser=TaskSystem.getCurrentUser();
        if(notifyUser instanceof TeamLeader){
            jButtonOK.setVisible(false);
            jButtonApprove.setVisible(true);
            jButtonReject.setVisible(true);
        }
        else{
            jButtonOK.setVisible(true);
            jButtonApprove.setVisible(false);
            jButtonReject.setVisible(false);
        }
        initialTable();
        fillTable();
        ptGUI=(ProjectTaskGUI) parent;
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {

            clearNotifications();
            
            TaskSystem.Finalize();
            fillTable();
            ptGUI.refreshTasksList();
            ptGUI.refreshNotificationBox();
            }
        }
        );
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonApprove = new javax.swing.JButton();
        jButtonReject = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Notifications");
        setResizable(false);

        jButtonApprove.setText("Approve");
        jButtonApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApproveActionPerformed(evt);
            }
        });

        jButtonReject.setText("Reject");
        jButtonReject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRejectActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Project Title", "Task ID", "Task Title", "Owner", "Start Date", "End Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jButtonOK.setText("Ok");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jButtonApprove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonOK)
                        .addGap(24, 24, 24)
                        .addComponent(jButtonReject, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonApprove)
                    .addComponent(jButtonReject)
                    .addComponent(jButtonOK))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApproveActionPerformed

        if(jTable1.getRowCount()!=0){
            int row = jTable1.getSelectedRow();
            if (row >= 0){                 
                String pTitle=jTable1.getValueAt(row, 0).toString();
                int taskID=Integer.parseInt(jTable1.getValueAt(row, 1).toString());
                for(Project project:TaskSystem.getProjectList()){
                if(project.getTitle().equalsIgnoreCase(pTitle)){
                    for(Task task:project.getTasks()){
                        if(task.getID()==taskID){
                            task.setStatus(State.ToDoNotify);
                            break;
                        }
                    }
                    break;
                }
                }
            } 
        }
        
        //Update and refresh parent frame notifications
        ptGUI.refreshNotificationBox();
        
        TaskSystem.Finalize();
        fillTable();
    }//GEN-LAST:event_jButtonApproveActionPerformed

    private void jButtonRejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRejectActionPerformed

        if(jTable1.getRowCount()!=0){
        int row = jTable1.getSelectedRow();
        if (row >= 0){                 
            String pTitle=jTable1.getValueAt(row, 0).toString();
            int taskID=Integer.parseInt(jTable1.getValueAt(row, 1).toString());
            for(Project project:TaskSystem.getProjectList()){
                if(project.getTitle().equalsIgnoreCase(pTitle)){
                    for(Task task:project.getTasks()){
                        if(task.getID()==taskID&&task.getStatus()==State.New){
                            project.removeTask(task);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        }
        TaskSystem.Finalize();
        fillTable();
        ptGUI.refreshNotificationBox();
    }//GEN-LAST:event_jButtonRejectActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        clearNotifications();
        ptGUI.refreshNotificationBox();
        TaskSystem.Finalize();
        this.dispose();
    }//GEN-LAST:event_jButtonOKActionPerformed


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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            //java.util.logging.Logger.getLogger(NotificationsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                NotificationsGUI dialog = new NotificationsGUI(new javax.swing.JFrame(), true);
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

    private void clearNotifications(){
    // Change toDoNotify to toDo
                for(Project project:TaskSystem.getProjectList()){
                    for(Task task:project.getTasks()){
                        if(task.getStatus() == State.ToDoNotify && TaskSystem.getCurrentUser().getEmail().equalsIgnoreCase(task.getOwner())){
                            task.setStatus(State.ToDo);
                            System.out.println("works");
                        }
                    }
                }
    
    } 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonApprove;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JButton jButtonReject;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
