import javax.swing.JTabbedPane;
import javax.swing.JPanel;


public class BookPanel extends JTabbedPane {
	public BookPanel() {
		
		JPanel panel = new BookSearch();
		addTab("도서검색", null, panel, null);
		
		JPanel panel_1 = new BookRegister();
		addTab("도서등록", null, panel_1, null);
	}

}
