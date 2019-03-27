import javax.swing.JTabbedPane;
import javax.swing.JPanel;


public class ReservationPanel extends JTabbedPane {
	public ReservationPanel() {
		
		JPanel panel = new TicketingFormCommon();
		addTab("ticket 예매", null, panel, null);
		
	}

}
