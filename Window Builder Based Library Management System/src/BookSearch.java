import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookSearch extends JPanel{
	private JTextField textField;
	private JTable table = new JTable(
			new DefaultTableModel(new Object[][] {}, new String[] { "고유번호", "도서명", "저자명", "출판년도", "출판사", "대출자" }));;
	private JPopupMenu popupMenu;
	private JMenuItem mntmNewMenuItem_checkout;
	private JMenuItem mntmNewMenuItem_return;
	private DefaultTableModel tableModel;
	int MAX_PAGENO;
	private static int PAGE_SIZE = 10;
	private int currentPageNo;
	private JSlider slider;
	private JComboBox comboBox_2;

	String title;
	String author;
	String year;
	String publiser;
	String name;
	String select_Subject;
	String select_PageSize;
	String select_Mybook;

	public BookSearch() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		add(panel);
		slider = new JSlider();
		JComboBox comboBox = new JComboBox();
		// id, title, author, year, publisher , name
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "고유번호", "도서명", "저자명", "출판년도", "출판사", "대출자" }));
		panel.add(comboBox);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "전체 도서", "나의 대출 도서" }));
		panel.add(comboBox_1);

		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "10", "15", "20", "25", "30" }));
		panel.add(comboBox_2);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("검색");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// 콤보박스 1선택
				select_Subject = (String) comboBox.getSelectedItem();
				if (select_Subject.equals("고유번호")) {
					select_Subject = "id";
				} else if (select_Subject.equals("도서명")) {
					select_Subject = "title";
				} else if (select_Subject.equals("저자명")) {
					select_Subject = "author";
				} else if (select_Subject.equals("출판년도")) {
					select_Subject = "year";
				} else if (select_Subject.equals("출판사")) {
					select_Subject = "publisher";
				} else if (select_Subject.equals("대출자")) {
					select_Subject = "name";
				}
				// 콤보박스 2선택 
				select_PageSize = (String) comboBox_2.getSelectedItem();
				PAGE_SIZE = Integer.parseInt(select_PageSize);

				// 콤보박스 3선택(전체, 대여)
				select_Mybook = (String) comboBox_1.getSelectedItem();
				if (select_Mybook.equals("전체 도서")) {
					select_Mybook = "";
				}
				if (select_Mybook.equals("나의 대출 도서")) {
					select_Mybook = UserInfo.userName;
				}

				// MaxPage를 바뀐 PageSize에 맞춰주기위해 한번더 호출
				
				calculate_MaxPageNo();

				slider.setMinimum(0);
				slider.setMaximum(MAX_PAGENO);
				slider.setValue(0);
				
				try {//DB에 연결하여 검색기능을 통한 테이블을 불러옴
					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_wb", "root",
							"1234");
					Statement sql = DB.createStatement();
					ResultSet cursor;
					if (textField.getText().equals("") && select_Mybook.equals("")) {
						cursor = sql
								.executeQuery("select id, title, author, year, publisher, name from booklist limit "
							+ currentPageNo * PAGE_SIZE + ", " + PAGE_SIZE);
					} else if(textField.getText().equals("") && select_Mybook.equals(UserInfo.userName)  ){
						cursor = sql.executeQuery("select * from booklist where name= '" + select_Mybook + "'");
					} else{
						cursor = sql.executeQuery("select * from booklist where " + select_Subject + "= '"
								+ textField.getText() + "' and name= '" + select_Mybook + "'");
					}
					tableModel.setRowCount(0);
					while (cursor.next()) {
						tableModel.addRow(new String[] { cursor.getString(1), cursor.getString(2), cursor.getString(3),
								cursor.getString(4), cursor.getString(5), cursor.getString(6) });
					}
					
					DB.close();

				} catch (Exception e1) {
					// TODO Auto-generated catch block
				}

			}
		});

		panel.add(btnNewButton);

		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		//테이블 클릭시 클릭된 row의 값을 읽어 대출,반납버튼을 클릭할수있게해줌
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 1)
					return;
				if (SwingUtilities.isLeftMouseButton(e) == false)
					return;
				
				if(table.getValueAt(table.getSelectedRow(), 5).equals("")){
					mntmNewMenuItem_return.setEnabled(false);
					mntmNewMenuItem_checkout.setEnabled(true);
				}else if(UserInfo.userName.equals(table.getValueAt(table.getSelectedRow(), 5))){
					mntmNewMenuItem_checkout.setEnabled(false);
					mntmNewMenuItem_return.setEnabled(true);
				}else{
					mntmNewMenuItem_checkout.setEnabled(false);
					mntmNewMenuItem_return.setEnabled(false);
				}
				
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
				
			}
		});
		scrollPane.setViewportView(table);

		JPanel panel_2 = new JPanel();
		add(panel_2);

		JButton btnNewButton_1 = new JButton("Pre Page");
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (currentPageNo == 0) {
					return;
				}
				currentPageNo--;
				slider.setValue(currentPageNo);
				do_load();
			}
		});
		panel_2.add(btnNewButton_1);

		// 페이지에따라 변하도록 해준다.
		
		calculate_MaxPageNo();
		
	
		
		
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				
				currentPageNo = slider.getValue();
				slider.setValue(currentPageNo);
				do_load();
				
			}
		});
		
				JButton btnNewButton_2 = new JButton("Next Page");
				btnNewButton_2.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if (currentPageNo >= MAX_PAGENO) {
							return;
						}
						currentPageNo++;
						slider.setValue(currentPageNo);
						do_load();
					}
				});
				panel_2.add(btnNewButton_2);
		panel_2.add(slider);

		popupMenu = new JPopupMenu();

		// 대출 클릭시 대출일자에 현제 년도 월 일 을적어주고 JOptionPane.showMessageDialog 띄워준다.
		mntmNewMenuItem_checkout = new JMenuItem("대출");
		mntmNewMenuItem_return = new JMenuItem("반납");

		
		//대출 클릭시 
		mntmNewMenuItem_checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				try {
					
					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_wb", "root",
							"1234");
					Statement sql = DB.createStatement();
					int rowsAffected = sql.executeUpdate("update booklist set name ='" + UserInfo.userName
							+ "' where id = '" + table.getValueAt(table.getSelectedRow(), 0) + "'");
					
					JOptionPane.showMessageDialog(null, "대출완료 되었습니다.");
					DB.close();
				} catch (Exception e) {
					
				}
				
				do_load();

			}
		});
		popupMenu.add(mntmNewMenuItem_checkout);

		//반납 팝업버튼을 눌렀을경우 대출자를 초기화
		mntmNewMenuItem_return.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(table.getValueAt(table.getSelectedRow(), 5).equals(UserInfo.userName)){
					try {
						
						Class.forName("com.mysql.jdbc.Driver");
						Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_wb", "root",
								"1234");
						Statement sql = DB.createStatement();
						int rowsAffected = sql.executeUpdate("update booklist set name ='' where id = '" + table.getValueAt(table.getSelectedRow(), 0) + "'");

						JOptionPane.showMessageDialog(null, "반납왑료 되었습니다.");
						DB.close();
					} catch (Exception e1) {
						
					}
					do_load();
				}
			}
		});
		popupMenu.add(mntmNewMenuItem_return);

		table.setComponentPopupMenu(popupMenu);

		calculate_MaxPageNo();
		do_load();
	}

	private void calculate_MaxPageNo() {
		// TODO Auto-generated method stub
		try {
			
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_wb", "root", "1234");
			Statement sql = DB.createStatement();
			ResultSet cursor = sql.executeQuery("select count(*) from booklist");
			cursor.first();
			MAX_PAGENO = cursor.getInt(1) / PAGE_SIZE;
			cursor.close();
			DB.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void do_load() {
		// TODO Auto-generated method stub
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_wb", "root", "1234");
			Statement sql = DB.createStatement();

			ResultSet cursor = sql
					.executeQuery("select id, title, author, year, publisher, name from booklist limit "
							+ currentPageNo * PAGE_SIZE + ", " + PAGE_SIZE);
			tableModel = (DefaultTableModel) table.getModel();
			tableModel.setRowCount(0);
			while (cursor.next()) {
				tableModel.addRow(new String[] { cursor.getString(1), cursor.getString(2), cursor.getString(3),
						cursor.getString(4), cursor.getString(5), cursor.getString(6) });
			}
			
			DB.close();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
