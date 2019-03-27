import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class UserPanel extends JTabbedPane {
	public UserPanel(){
		JPanel panel = new UserSearch();
		addTab("사용자검색", null, panel, null);
		
		JPanel panel_1 = new UserRegister();
		addTab("사용자등록", null, panel_1, null);
	}
}
