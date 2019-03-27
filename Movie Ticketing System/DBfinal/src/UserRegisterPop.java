import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;


public class UserRegisterPop extends JDialog {
	
	String userName_in;
	String password_in;
	String rePassword_in;
	String eMail_in;
	private JTextField textField;
	private JPasswordField textField_1;
	private JPasswordField textField_2;
	private JTextField textField_3;
	public UserRegisterPop() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(-1);
			}
		});
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(new Dimension(385, 350));
		
		JLabel lblCreateId = new JLabel("Create ID");
		lblCreateId.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblId = new JLabel("ID");
		
		JLabel lblPassword = new JLabel("Password");
		
		JLabel lblConfirmPassword = new JLabel("Confirm");
		
		JLabel lblEmail = new JLabel("Email");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JPasswordField();
		textField_1.setColumns(10);
		
		textField_2 = new JPasswordField();
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				
				try {
					userName_in = textField.getText();
					password_in = new String(textField_1.getPassword());
					rePassword_in =new String( textField_2.getPassword());
					eMail_in = textField_3.getText();

					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_db", "root",
							"1234");
					Statement sql = DB.createStatement();
					ResultSet cursor = sql
							.executeQuery("select * from userlist where name='" + userName_in + "'");
					//빈칸이 있는지 체크
					if (userName_in.equals(null) || password_in.equals("") || eMail_in.equals("")) {
						JOptionPane.showMessageDialog(null, "빈칸이 있으면 안됩니다.");
					} else {
						// 아이디가 있는지 체크
						if (cursor.next()) {
							JOptionPane.showMessageDialog(null, "이미 있는 아이디입니다.");
							textField.setText("");
							textField_1.setText("");
							textField_2.setText("");
							textField_3.setText("");
						} else {
							// 비밀번호와 재입력이 같은지 체크
							if (password_in.equals(rePassword_in)) {

								
								int rowsAffected = sql.executeUpdate("insert into userlist (name,password,email,rootkey,movie,time,seat,price) values('" + userName_in
										+ "', '" + password_in + "', '" + eMail_in + "', '0','','','',NULL)");

								JOptionPane.showMessageDialog(null, "user 등록이 완료 되었습니다!!!");
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.");
								textField_1.setText("");
								textField_2.setText("");
							}

						}
					}
					

					DB.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(117)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnSignUp)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblId)
								.addComponent(lblPassword)
								.addComponent(lblConfirmPassword)
								.addComponent(lblEmail))
							.addGap(21)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_3, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
								.addComponent(textField_1)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
								.addComponent(textField_2))))
					.addGap(76))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(154, Short.MAX_VALUE)
					.addComponent(lblCreateId)
					.addGap(144))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addComponent(lblCreateId)
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblId)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblConfirmPassword)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(36)
					.addComponent(btnSignUp)
					.addContainerGap(62, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}


}
