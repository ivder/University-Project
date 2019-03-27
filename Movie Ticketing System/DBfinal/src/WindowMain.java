import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Font;


public class WindowMain extends JFrame {
	
	static JMenu  mnNewMenu2;
	public WindowMain() {
		setSize(new Dimension(770, 480));
		setTitle("Movie Ticketing System");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("일반 예매");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Ticketing (예매)");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(new ReservationPanel());
				revalidate();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		mnNewMenu2 = new JMenu("관리자");
		menuBar.add(mnNewMenu2);
		
		JMenuItem mntmNewMenuItem3 = new JMenuItem("Reservation");
		mntmNewMenuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(new UserPanel());
				revalidate();
			}
		});
		mnNewMenu2.add(mntmNewMenuItem3);
		getContentPane().setLayout(null);
		
		JLabel lblPopularMovies = new JLabel("Popular Movies");
		lblPopularMovies.setFont(new Font("Narkisim", Font.BOLD | Font.ITALIC, 18));
		lblPopularMovies.setBounds(32, 63, 150, 37);
		getContentPane().add(lblPopularMovies);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Javadoc\\DBfinal\\images\\dr.jpg"));
		lblNewLabel.setBounds(32, 98, 259, 323);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Javadoc\\DBfinal\\images\\Fantastic-Beasts.png"));
		lblNewLabel_1.setBounds(270, 98, 259, 323);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Javadoc\\DBfinal\\images\\busan.jpg"));
		lblNewLabel_2.setBounds(513, 98, 259, 323);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Javadoc\\DBfinal\\images\\Cinema_XXI_2015732253591.png"));
		lblNewLabel_3.setBounds(257, 28, 251, 59);
		getContentPane().add(lblNewLabel_3);
		
		
		
	}


	public static void main(String[] args) {
		new WindowMain().setVisible(true);
		new Login().setVisible(true);
	}

}
