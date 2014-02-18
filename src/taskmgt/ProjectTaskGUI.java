package taskmgt;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.*;
import taskmgt.Models.ModelType;
import static taskmgt.Models.ModelType.TeamLeader;
import taskmgt.Models.Project;
import taskmgt.Models.Task;
import taskmgt.Models.State;
import taskmgt.Models.TeamLeader;
import taskmgt.Models.User;


/**
 *
 * @author Ray
 */
public class ProjectTaskGUI extends javax.swing.JFrame {//implements ListSelectionListener {
    
    private Project currentProject;
    private static boolean jListListenerFlag = false;
    private final SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
    
    
    /**
     * Creates new form Project
     */
    public ProjectTaskGUI() {
        initComponents();
        
//        //Add event listeners to jLists
//        addListenertoJList(jList1);
//        addListenertoJList(jList2);
        
        refreshProjectsList();
        cellListener();
    }
    
    public ProjectTaskGUI(final LoginGUI form){
        initComponents();
        
//        //Add event listeners to jLists
//        addListenertoJList(jList1);
//        addListenertoJList(jList2);
        
        refreshProjectsList();
        cellListener();
        this.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent we){
                    form.setVisible(true);
                   
                }
            }
        );
    }
    
    //Methods
    public Project getSelectProject() { 
        if (currentProject == null) {
            setSelectedProject();
        }
        
        return currentProject;
    }
    
    public void setSelectedProject (Project project) { 
        currentProject = project; 
    }
    
    public void setSelectedProject() { 
        String projectTitle = (String) jListProjects.getSelectedValue();
        
        if (projectTitle != null && !projectTitle.isEmpty()) {
            currentProject = Data.getProjectByTitle(projectTitle);
        }
    }
    
//    @Override
//    public void valueChanged(ListSelectionEvent e) {
//        if (e.getValueIsAdjusting()) {
//            refreshProjectsList();
//            addTaskTableRow();
//        }
//    }
    
//    private void addListenertoJList(JList jlist)
//    {
//        jlist.addListSelectionListener(this);
//    }
    //Initial Table
    public void initialTable(){
        //Inital table
        jTableTasks.setShowGrid(false);
        jTableTasks.setShowHorizontalLines(false);
        jTableTasks.setShowVerticalLines(false);
        jTableTasks.setGridColor(Color.BLUE);
        jTableTasks.getTableHeader().setReorderingAllowed(false);
        //Add comboBox
        TableColumn thirdColumn = jTableTasks.getColumnModel().getColumn(2);
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Pending");
        comboBox.addItem("Completed");
        thirdColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }
    
    public void refreshProjectsList()
    {
        DefaultListModel jListModel = new DefaultListModel();
        //If projects exists, then update Projects List
        if (Data.projectList.size() > 0) {           
            for(Project project:Data.projectList){
                if(project.getStatus() != State.Archived){
                    if (Data.getCurrentUser().getEmail().equalsIgnoreCase(project.getOwner()))
                        jListModel.addElement(project.getTitle());
                }

            }
            jListProjects.setModel(jListModel);
        } else {
            jListModel.addElement("<No Projects Yet>");
            jListProjects.setModel(jListModel);
        }
    }
    
    public void clearTaskTable() {
        DefaultTableModel model = (DefaultTableModel) jTableTasks.getModel();
        
        model.setRowCount(0);
        jTableTasks.setModel(model);
    }
    
    public void addTaskTableRow(Task t)
    {
        DefaultTableModel model = (DefaultTableModel) jTableTasks.getModel();
        
        model.addRow(t.toTableRow());
        jTableTasks.setModel(model);
        
//        //Get Tasks LinkedList for the selected t
//        LinkedList<Task> tasks = Data.getProjectTasks(t.getID());
//        
//        //Update Tasks List for given t
//        DefaultListModel jListModel = new DefaultListModel();
//        for(Task task:tasks){
//            if(task.getStatus() != State.Archived){
//               jListModel.addElement(task.getTitle());
//            }
//        }
//        //jListTasks.setModel(jListModel);
    }
    
//    public void addTaskTableRow()
//    {
//        //Get the selected element from the Projects jList
//        String projectTitle = (String) jListProjects.getSelectedValue();
//        
//        //If project title is not null or empty
//        if (projectTitle != null && !projectTitle.isEmpty())
//        {
//            //Get the Project object for the select elements
//            Project currentProject = Data.getProjectByTitle(projectTitle);
//
//            //If a project object was returned
//            if (currentProject != null){
//                //Get Tasks LinkedList for the selected project
//                LinkedList<Task> tasks = Data.getProjectTasks(currentProject.getID());
//
//                //Update Tasks List for selected Project
//                if (tasks != null)
//                {
//                    DefaultListModel jListModel = new DefaultListModel();
//                    for(Task task:tasks){
//                        if(task.getStatus() != State.Archived){
//                           jListModel.addElement(task.getTitle());
//                        }
//                    }
//                    //jListTasks.setModel(jListModel);
//                }
//                else
//                {
//                    String empty[] = { "Project " + currentProject.getTitle() + " has no tasks." };
//                    //jListTasks.setListData(empty);
//                }
//            }
//            else
//            {
//                String empty[] = { "No tasks yet!" };
//                //jListTasks.setListData(empty);
//            }
//        }
//    }
    
    
    private void cellListener(){
        Action action = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                TableCellListener tcl = (TableCellListener)e.getSource();
                if(tcl.getNewValue().toString().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Please fill in information!","Warning",JOptionPane.WARNING_MESSAGE);
                    //fillTable();
                }
                else{
                    
                    TeamLeader currentUser = null;
                    if ((Data.getCurrentUser()) instanceof TeamLeader)
                            currentUser = (TeamLeader) Data.getCurrentUser();
                    else
                        JOptionPane.showMessageDialog(null,"Only team leaders can edit tasks!","Warning",JOptionPane.WARNING_MESSAGE);
                    
                    int col=tcl.getColumn();
                    int row=tcl.getRow();
                    int taskId = Integer.parseInt(jTableTasks.getValueAt(row, 0).toString());
                    String origOwnerEmail=jTableTasks.getValueAt(row, 4).toString();
                    switch(col){
                        case 0://ID - Non-editable
                        case 1://Title
                            currentProject.getTaskByID(taskId).setTitle(tcl.getNewValue().toString());
                        case 2://Start Date
                            try {
                                currentProject.getTaskByID(taskId).setStartDate(simpleDate.parse(tcl.getNewValue().toString()));
                            } catch (ParseException ex) {
                                JOptionPane.showMessageDialog(null,"Inccrrect date format!\nUse MM/dd/yyyy","Warning",JOptionPane.WARNING_MESSAGE);
                            }
                        case 3://End Date
                            try {
                                currentProject.getTaskByID(taskId).setEndDate(simpleDate.parse(tcl.getNewValue().toString()));
                            } catch (ParseException ex) {
                                JOptionPane.showMessageDialog(null,"Inccrrect date format!\nUse MM/dd/yyyy","Warning",JOptionPane.WARNING_MESSAGE);
                            }
                        case 4://Owner Email
                                currentProject.getTaskByID(taskId).setOwner(tcl.getNewValue().toString());
                            break;
                        case 5://Status
                            String state = tcl.getNewValue().toString();
                            if (state.equalsIgnoreCase(State.Completed.name()))
                                currentProject.getTaskByID(taskId).setStatus(State.Completed);
                            else if (state.equalsIgnoreCase(State.New.name()))
                                currentProject.getTaskByID(taskId).setStatus(State.New);
                            else if (state.equalsIgnoreCase(State.Pending.name()))
                                currentProject.getTaskByID(taskId).setStatus(State.Pending);
                            else if (state.equalsIgnoreCase(State.Rejected.name()))
                                currentProject.getTaskByID(taskId).setStatus(State.Rejected);
                            else
                                JOptionPane.showMessageDialog(null,"Please pick a valid status!"
                                        + "New\nCompleted\nPending\nRejected","Warning",JOptionPane.WARNING_MESSAGE);
                            break;                        
                    }
                }
                    //fillTable();
            }
        };
        TableCellListener tcl = new TableCellListener(jTableTasks, action);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jListProjects = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ButtonCreateProject = new javax.swing.JButton();
        ButtonAddTask = new javax.swing.JButton();
        setStatusBtn = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableTasks = new javax.swing.JTable();

        setTitle("Project");
        setResizable(false);

        jListProjects.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListProjects.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListProjectsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListProjects);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("You have new notifications");

        jLabel2.setText("Notifications");

        jButton1.setText("Review");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 464, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 22, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setText("Projects:");

        jLabel4.setText("Tasks:");

        ButtonCreateProject.setText("Create Project");
        ButtonCreateProject.setActionCommand("Manage Projects");
        ButtonCreateProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCreateProjectActionPerformed(evt);
            }
        });

        ButtonAddTask.setText("Add Task");
        ButtonAddTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAddTaskActionPerformed(evt);
            }
        });

        setStatusBtn.setText("Set Status");
        setStatusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setStatusBtnActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Completed", "Archived" }));

        jLabel5.setText("Status:");

        jButton5.setText("Edit Project");
        jButton5.setActionCommand("Manage Projects");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTableTasks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Title", "Start Date", "End Date", "Owner", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableTasks);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ButtonCreateProject)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(ButtonAddTask, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(setStatusBtn)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonCreateProject)
                    .addComponent(ButtonAddTask)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(setStatusBtn)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        NotificationsGUI notifyForm=new NotificationsGUI(this,true);
        notifyForm.show();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ButtonCreateProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCreateProjectActionPerformed
        if(Data.getCurrentUser().getType() == ModelType.TeamLeader) {
            EditProjectGUI createProjectForm=new EditProjectGUI(this,true,"create");
            createProjectForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Only leaders can create a project. :/");
        }
    }//GEN-LAST:event_ButtonCreateProjectActionPerformed

    private void ButtonAddTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAddTaskActionPerformed
        if (jListProjects.getModel().getSize() <= 0)
        {
            JOptionPane.showMessageDialog(null, "Please create a project first.");
        }
        else if (jListProjects.isSelectionEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please select a project to add a task to.");
        }
        else
        {
            AddTaskGUI addTaskForm=new AddTaskGUI(this,true,"add");
            addTaskForm.setVisible(true);
        }
    }//GEN-LAST:event_ButtonAddTaskActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(Data.getCurrentUser().getType() != ModelType.TeamLeader) {
            JOptionPane.showMessageDialog(null, "Only leaders can create a project. :/");
        }
        if(jListProjects.isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "Please select a project to edit.");
        }
        else{ 
            EditProjectGUI editProjectForm=new EditProjectGUI(this,true,"edit");
            editProjectForm.setVisible(true);
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void setStatusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setStatusBtnActionPerformed
 
        //The selected state to change a project or task
        String changeState = (String) jComboBox1.getSelectedItem();
        
        //If there are no projects
        if (jListProjects.getModel().getSize() <= 0)
        {
            JOptionPane.showMessageDialog(null, "Please create a project before editing.");
        }
        //Else if there are projects but none are selected
        else if (jListProjects.isSelectionEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please select a project before editing.");
        }
        //Else, a single Project is selected
        else
        {
            //If no Tasks are selected
            //if (jListTasks.isSelectionEmpty())
            if (true)
            {
                //CODE TO CHANGE THE STATUS OF A PROJECT
                //The Project list is single-select.
                
                //Get the selected project title as a String
                String projectTitle = (String) jListProjects.getSelectedValue();
                //Get the Project object from the collection by its title
                Project project = Data.getProjectByTitle(projectTitle);
                
                //Set the state of the project
                //If state is set to completed
                if (changeState.equalsIgnoreCase(State.Completed.name()))
                {
                    project.setStatus(State.Completed);
                    JOptionPane.showMessageDialog(null, "Changed project " + project.getTitle() + " to " + changeState + ".");
                }
                //Else if, state is set to archived
                else if (changeState.equalsIgnoreCase(State.Archived.name()))
                {
                    project.setStatus(State.Archived);
                    JOptionPane.showMessageDialog(null, "Changed project " + project.getTitle() + " to " + changeState + ".");
                }
                //Else, show pop-up and do nothing
                else
                {
                    JOptionPane.showMessageDialog(null, "Changing project state to " + changeState + " is not implemented.");
                }
            }
            //Else, one or more Tasks are selected
            else
            {
                //CODE TO CHANGE STATUS OF A TASK
                //The Task list is multi-select, so get all the selected tasks.
              
                //For each selected object
//                for (Object obj:jListTasks.getSelectedValuesList())
//                {
//                    //Cast the object (i.e., the task title) as String
//                    String taskTitle = (String) obj;
//                    //Get the Task object by its title
//                    Task task = Data.getTaskByTitle(taskTitle);
//                    
//                    //If state is set to completed
//                    if (changeState.equalsIgnoreCase(State.Completed.name()))
//                    {
//                        task.setStatus(State.Completed);
//                        JOptionPane.showMessageDialog(null, "Changed task " + task.getTitle() + " to " + changeState + ".");
//                    }
//                    //Else if, state is set to archived
//                    else if (changeState.equalsIgnoreCase(State.Archived.name()))
//                    {
//                        task.setStatus(State.Archived);
//                        JOptionPane.showMessageDialog(null, "Changed task " + task.getTitle() + " to " + changeState + ".");
//                    }
//                    //Else, show pop-up and do nothing
//                    else
//                    {
//                        JOptionPane.showMessageDialog(null, "Changing task state to " + changeState + " is not implemented.");
//                    }
//                }
            }
        }        
        
    }//GEN-LAST:event_setStatusBtnActionPerformed
    
    private void jListProjectsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListProjectsValueChanged
        //First, clear the Task jTable
        clearTaskTable();
        
        //Get selected project title
        String projectTitle = (String) jListProjects.getSelectedValue();
        
        //Get project object from title
        currentProject = Data.getProjectByTitle(projectTitle);
        
        if (currentProject != null) {
            for (Task task : currentProject.getTasks()) {
                    addTaskTableRow(task);
            }
        }
        
    }//GEN-LAST:event_jListProjectsValueChanged

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
            java.util.logging.Logger.getLogger(ProjectTaskGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjectTaskGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjectTaskGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjectTaskGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProjectTaskGUI().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonAddTask;
    private javax.swing.JButton ButtonCreateProject;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jListProjects;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableTasks;
    private javax.swing.JButton setStatusBtn;
    // End of variables declaration//GEN-END:variables

   

}
