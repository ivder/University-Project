import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class UserRegister extends JPanel {
	private JTextField textField;
	private JPasswordField textField_1;
	private JPasswordField textField_2;
	private JTextField textField_3;

	String userName_in;
	String password_in;
	String rePassword_in;
	String eMail_in;

	public UserRegister() {

		// ID
		JLabel lblNewLabel = new JLabel("아이디");
		textField = new JTextField();
		textField.setColumns(10);

		// Password
		JLabel lblNewLabel_1 = new JLabel("패스워드");
		textField_1 = new JPasswordField();
		textField_1.setColumns(10);

		// Password recommend
		JLabel lblNewLabel_2 = new JLabel("패스워드 재입력");
		textField_2 = new JPasswordField();
		textField_2.setColumns(10);

		// E-mail
		JLabel lblNewLabel_3 = new JLabel("전자 메일");
		textField_3 = new JTextField();
		textField_3.setColumns(10);

		// Register Button
		JButton btnNewButton = new JButton("회원등록");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				
				try {
					userName_in = textField.getText();
					password_in = new String(textField_1.getPassword());
					rePassword_in =new String( textField_2.getPassword());
					eMail_in = textField_3.getText();

					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_wb", "root",
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

								
								int rowsAffected = sql.executeUpdate("insert into userlist values('" + userName_in
										+ "', '" + password_in + "', '" + eMail_in + "', '0')");

								JOptionPane.showMessageDialog(null, "회원 등록이 완료 되었습니다!!!");
								textField.setText("");
								textField_1.setText("");
								textField_2.setText("");
								textField_3.setText("");
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

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(
						99)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED, 163, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton))
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel_1)
										.addComponent(lblNewLabel).addComponent(lblNewLabel_2)
										.addComponent(lblNewLabel_3))
								.addGap(40)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))))
				.addContainerGap(107, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(61)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))
				.addGap(16)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3)).addGap(18).addComponent(btnNewButton)
				.addContainerGap(78, Short.MAX_VALUE)));
		setLayout(groupLayout);
	}
}
