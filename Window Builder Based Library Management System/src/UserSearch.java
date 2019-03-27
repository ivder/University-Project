import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class UserSearch extends JPanel {
	private JTextField textField;
	private JTable jtable = new JTable(
			new DefaultTableModel(new Object[][] {}, new String[] { "아이디", "패스워드", "전자메일주소", "관리자키" }));
	private JPopupMenu popupMenu;
	private JMenuItem mntmNewMenuItem_right;
	private JMenuItem mntmNewMenuItem_delet;
	private DefaultTableModel tableModel;

	public UserSearch() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		add(panel);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "아이디", "전자메일주소", "관리자키" }));
		panel.add(comboBox);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		do_load();

		JButton btnNewButton = new JButton("검색");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Combo Box의 아이템이 뭔지 체크
				String select = (String) comboBox.getSelectedItem();

				if (select.equals("아이디")) {
					select = "name";
				} else if (select.equals("전자메일주소")) {
					select = "email";
				} else if (select.equals("관리자키")) {
					select = "rootkey";
				}

				// Combo Box의 아이템 column의 이름과 비교한뒤 알맞은 검색결과출력

				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_wb", "root",
							"1234");
					Statement sql = DB.createStatement();
					ResultSet cursor;
					if (textField.getText().equals("")) {
						cursor = sql.executeQuery("select *from userlist");
					} else {
						cursor = sql.executeQuery(
								"select * from userlist where " + select + "='" + textField.getText() + "'");
					}

					tableModel.setRowCount(0);

					while (cursor.next()) {
						tableModel.addRow(new String[] { cursor.getString(1), cursor.getString(2), cursor.getString(3),
								cursor.getString(4) });
					}

					DB.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		panel.add(btnNewButton);

		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		jtable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 1)
					return;
				if (SwingUtilities.isLeftMouseButton(e) == false)
					return;
				if (jtable.getValueAt(jtable.getSelectedRow(), 3).equals("0")) {
					mntmNewMenuItem_delet.setEnabled(false);
					mntmNewMenuItem_right.setEnabled(true);
				}
				if (jtable.getValueAt(jtable.getSelectedRow(), 3).equals("1")) {
					mntmNewMenuItem_delet.setEnabled(true);
					mntmNewMenuItem_right.setEnabled(false);
				}

				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		scrollPane.setViewportView(jtable);

		popupMenu = new JPopupMenu();
		
		
		mntmNewMenuItem_right = new JMenuItem("관리자 권한부여");
		mntmNewMenuItem_delet = new JMenuItem("관리자 권한 삭제");

		
		// 관리자 자격부여
		mntmNewMenuItem_right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {

					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_wb", "root",
							"1234");
					Statement sql = DB.createStatement();
					int rowsAffected = sql.executeUpdate("update userlist set rootkey ='1' where name = '"
							+ jtable.getValueAt(jtable.getSelectedRow(), 0) + "'");

					JOptionPane.showMessageDialog(null, "관리자 자격부여 완료.");
					DB.close();
				} catch (Exception e1) {

				}
				do_load();
			}
		});
		popupMenu.add(mntmNewMenuItem_right);

		// 관리자 자격 삭제
		mntmNewMenuItem_delet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {

					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_wb", "root",
							"1234");
					Statement sql = DB.createStatement();
					int rowsAffected = sql.executeUpdate("update userlist set rootkey ='0' where name = '"
							+ jtable.getValueAt(jtable.getSelectedRow(), 0) + "'");

					JOptionPane.showMessageDialog(null, "관리자 자격회수 완료.");
					DB.close();
				} catch (Exception e1) {

				}
				do_load();
			}
		});
		popupMenu.add(mntmNewMenuItem_delet);

		
		
		jtable.setComponentPopupMenu(popupMenu);

	}

	private void do_load() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_wb", "root", "1234");
			Statement sql = DB.createStatement();
			ResultSet cursor = sql.executeQuery("select name, password, email, rootkey from userlist");
			tableModel = (DefaultTableModel) jtable.getModel();
			tableModel.setRowCount(0);

			while (cursor.next()) {
				tableModel.addRow(new String[] { cursor.getString(1), cursor.getString(2), cursor.getString(3),
						cursor.getString(4) });
			}

			DB.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
