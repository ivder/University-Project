import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class UserPanel extends JTabbedPane {
	public UserPanel(){
		JPanel panel = new TicketingForm();
		addTab("사용자관리", null, panel, null);
		
		JPanel panel_1 = new MovieRegister();
		addTab("영화등록", null, panel_1, null);
	}
}
