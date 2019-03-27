import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;


public class WindowMain extends JFrame {
	
	static JMenu  mnNewMenu2;
	public WindowMain() {
		setSize(new Dimension(500, 600));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("도서");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("도서관리");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(new BookPanel());
				revalidate();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		mnNewMenu2 = new JMenu("관리자");
		menuBar.add(mnNewMenu2);
		
		JMenuItem mntmNewMenuItem3 = new JMenuItem("사용자 관리");
		mntmNewMenuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(new UserPanel());
				revalidate();
			}
		});
		mnNewMenu2.add(mntmNewMenuItem3);


		
		
	}

	public static void main(String[] args) {
		new WindowMain().setVisible(true);
		new Login().setVisible(true);
	}

}
