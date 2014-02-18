package taskmgt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import taskmgt.Models.*;
import java.util.*;

/**
 *
 * @author Ray
 */
public final class MemberGUI extends javax.swing.JDialog {

    //User Define Methods
    //jTable Functions
    public void fillTableRow(User member){
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        String[] arr=new String[3];
        arr[0]=member.getEmail();
        arr[1]=member.getName();
        if(member instanceof TeamLeader){
            arr[2]="Leader";
        }
        else{
            arr[2]="Member";
        }
        tableModel.addRow(arr);
    }
    //Fill Whole Table
    public void fillTable(){
        DefaultTableModel tableModel=(DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        for(User member:Data.userList){
            if(!(member instanceof Administrator) && member.checkActive()){
                fillTableRow(member);
            }
        }
        
        jTable1.setModel(tableModel);
    }
    //Initial Table
    public void initialTable(){
        //Inital table
        jTable1.setShowGrid(false);
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jTable1.setGridColor(Color.BLUE);
        jTable1.getTableHeader().setReorderingAllowed(false);
        //Add comboBox
        TableColumn thirdColumn = jTable1.getColumnModel().getColumn(2);
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Leader");
        comboBox.addItem("Member");
        thirdColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }
    //
    public void cellListener(){
        Action action = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                TableCellListener tcl = (TableCellListener)e.getSource();
                if(tcl.getNewValue().toString().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Please fill in information!","Warning",JOptionPane.WARNING_MESSAGE);
                    fillTable();
                }
                else{
                    int col=tcl.getColumn();
                    int row=tcl.getRow();
                    String email=jTable1.getValueAt(row, 0).toString();
                    switch(col){
                        case 0: 
                            if(Data.getUserByEmail(email)!=null)
                                JOptionPane.showMessageDialog(null,"This member exists!","Warning",JOptionPane.WARNING_MESSAGE);
                            else
                                user.updateEmail(tcl.getOldValue().toString(), tcl.getNewValue().toString());
                            break;
                        case 1:
                            user.updateName(email, tcl.getNewValue().toString());
                            break;
                        case 2:
                            if(tcl.getOldValue().toString().equals("Member")&&tcl.getNewValue().toString().equals("Leader")){
                                user.changeMemberType(email, true);
                            }
                            else if(tcl.getOldValue().toString().equals("Leader")&&tcl.getNewValue().toString().equals("Member")){
                                if(!user.changeMemberType(email, false))
                                    JOptionPane.showMessageDialog(null,"This leader is currently in charge of a project!","Warning",JOptionPane.WARNING_MESSAGE);
                            }
                            break;
                    }
                }
                fillTable();
            }
        };
        TableCellListener tcl = new TableCellListener(jTable1, action);
    }
    
    //
    /**
     * Creates new form
     * @param parent
     * @param modal
     */
    public MemberGUI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.user=null;
    }
    
     public MemberGUI(java.awt.Frame parent, boolean modal, final User user) {
        super(parent, modal);
        initComponents();
        this.user=(Administrator)user;
        initialTable();
        //Pull data
        fillTable();
        cellListener();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonDeleteMember = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButtonAddMember = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButtonSearchMember = new javax.swing.JButton();
        jButtonMemberReport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add/Edit Member");
        setResizable(false);

        jButtonDeleteMember.setText("Delete");
        jButtonDeleteMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteMemberActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setToolTipText("");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Name:");

        jLabel2.setText("E-mail:");

        jLabel3.setText("Create Member");

        jButtonAddMember.setText("Add");
        jButtonAddMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddMemberActionPerformed(evt);
            }
        });

        jCheckBox1.setText("TeamLeader");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 191, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                                    .addComponent(jTextField2)))
                            .addComponent(jCheckBox1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jButtonAddMember, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jButtonAddMember)
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Email", "Name", "TeamLeader"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable1);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setToolTipText("");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel4.setText("Name:");

        jLabel5.setText("E-mail:");

        jLabel6.setText("Search Member");

        jButtonSearchMember.setText("Search");
        jButtonSearchMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchMemberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 191, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                            .addComponent(jTextField3)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jButtonSearchMember)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jButtonSearchMember)
                .addContainerGap())
        );

        jButtonMemberReport.setText("Member Report");
        jButtonMemberReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMemberReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonDeleteMember, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonMemberReport))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonDeleteMember)
                            .addComponent(jButtonMemberReport))))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButtonAddMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddMemberActionPerformed
        String name=jTextField1.getText().toString();
        String email=jTextField2.getText().toString();
        if(!Utilities.checkEmail(email)||!Utilities.isName(name)||name.isEmpty()||email.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please verify member information!","Warning",JOptionPane.WARNING_MESSAGE);
        }
        else if(jCheckBox1.isSelected()){
            if(!user.createLeader(name, email)){
                JOptionPane.showMessageDialog(null,"Member exists!","Warning",JOptionPane.WARNING_MESSAGE);
            }
        }
        else{
            if(!user.createMember(name, email)){
                JOptionPane.showMessageDialog(null,"Member exists!","Warning",JOptionPane.WARNING_MESSAGE);
            }
        }
        fillTable();
    }//GEN-LAST:event_jButtonAddMemberActionPerformed

    private void jButtonDeleteMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteMemberActionPerformed
        if(jTable1.getRowCount()!=0){
            int row = jTable1.getSelectedRow();
            if (row >= 0)
            {                 
                String email=jTable1.getValueAt(row, 0).toString();
                boolean flag=jTable1.getValueAt(row, 2).toString().equals("Leader");
                if(flag){
                    TeamLeader member = (TeamLeader)Data.getUserByEmail(email);
                    member.setActive(false);
                }
                else{
                    TeamMember member=(TeamMember)Data.getUserByEmail(email);
                    member.setActive(false);                   
                }
            } 
        }

        fillTable();
    }//GEN-LAST:event_jButtonDeleteMemberActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButtonSearchMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchMemberActionPerformed
        String name=jTextField4.getText().toString();
        String email=jTextField3.getText().toString();
        if(!Utilities.checkEmail(email)||!Utilities.isName(name)||name.isEmpty()||email.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please verify member information!","Warning",JOptionPane.WARNING_MESSAGE);
        }
        else{
            int row=jTable1.getRowCount();
            for(int i=0;i<row;i++){
                if(jTable1.getValueAt(i, 0).toString().toLowerCase().equals(email.toLowerCase())&&jTable1.getValueAt(i, 1).toString().toLowerCase().equals(name.toLowerCase()))
                    jTable1.setRowSelectionInterval(i, i);
            }
        }
    }//GEN-LAST:event_jButtonSearchMemberActionPerformed

    private void jButtonMemberReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMemberReportActionPerformed
    if(jTable1.getRowCount()!=0){
            int rows[]=jTable1.getSelectedRows();
            if(rows.length>0){
                
                for(int i=0;i<rows.length;i++){
                    String email=jTable1.getValueAt(rows[i], 0).toString();
                    boolean flag=jTable1.getValueAt(rows[i], 2).toString().equals("Leader");
                    
                    TeamMember member =(TeamMember)Data.getUserByEmail(email);
                    System.out.println("This is a report for " + member.getName());
                           
                   //LinkedList<Project> pl = new LinkedList<Project>();
                   //LinkedList<Task> tl = new LinkedList<Task>();
                   
                   LinkedList<Project> pl = member.getProjects();
                   LinkedList<Task> tl = member.getTasks();
                 
                   if( pl.size() == 0){JOptionPane.showMessageDialog(null,"This user is not assigned to any projects","Warning",JOptionPane.WARNING_MESSAGE);}
                   else if(tl.size() == 0){JOptionPane.showMessageDialog(null,"This user is not assigned to any tasks","Warning",JOptionPane.WARNING_MESSAGE);}
                   else{
                   for(Project p:pl)
                   
                       {
                           System.out.println(p.getTitle());
                     
                   
                   for(Task t:tl)
                       {
                           if (t.getProjectID()== p.getID()){
                           
                               System.out.println(t.getTitle());
                           }
                       }
                   
                       }            
                   }
                } 
                
            }
        }
    }//GEN-LAST:event_jButtonMemberReportActionPerformed

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
            java.util.logging.Logger.getLogger(MemberGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MemberGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MemberGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MemberGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MemberGUI dialog = new MemberGUI(new javax.swing.JFrame(), true);
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
    //User define
    private final Administrator user;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddMember;
    private javax.swing.JButton jButtonDeleteMember;
    private javax.swing.JButton jButtonMemberReport;
    private javax.swing.JButton jButtonSearchMember;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

}
