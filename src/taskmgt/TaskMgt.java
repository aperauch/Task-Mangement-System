package taskmgt;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Ray
 */
public class TaskMgt {
    /**
     * @param args the command line arguments
     */
  public static void main(String[] args) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        }
        //Data.InitializeAdmin("admin", "admin@mgt.com", "123456");
        loadView();
    }
    
    public static void loadView(){
        Data.Initialize();
        Data.checkforEmptyLists();
        //Data.InitializeAdmin("admin", "admin@mgt.com", "123456");//DEBUG
        LoginGUI loginForm=new LoginGUI();
        loginForm.show();
        loginForm.setLocationRelativeTo(null);
    }
}
