import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
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

public class Login extends JDialog {
	private JTextField textField;
	private JTextField textField_1;

	String userName;
	String password;
	String eMail;
	int rootKey;

	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(-1);
			}
		});
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(new Dimension(335, 300));

		JLabel lblNewLabel = new JLabel("도서관리시스템 로그인");
		lblNewLabel.setFont(new Font("나눔고딕", Font.BOLD, 20));

		JLabel lblNewLabel_1 = new JLabel("아이디");

		textField = new JTextField();
		textField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("패스 워드");

		textField_1 = new JPasswordField();
		textField_1.setColumns(10);

		JCheckBox chckbxNewCheckBox = new JCheckBox("관리자");

		JButton btnNewButton = new JButton("로그인");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					// 로그인창에 적은 사용자정보를 UserInfo에 저장
					UserInfo.userName = textField.getText();
					UserInfo.password = textField_1.getText();

					if (chckbxNewCheckBox.isSelected()) {
						UserInfo.rootKey = 1;
					} else {
						UserInfo.rootKey = 0;
					}

					// DB연결후 아이디 체크
					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_wb", "root",
							"1234");
					Statement sql = DB.createStatement();
					ResultSet cursor = sql.executeQuery(
							"select * from userlist where name = '"+UserInfo.userName+"'");

					while (cursor.next()) {
						userName = cursor.getString(1);
						password = cursor.getString(2);
						eMail = cursor.getString(3);
						rootKey = Integer.parseInt(cursor.getString(4));
					}

					// 로그인 아이디와 비밀번호 비교후 로그인
					if (userName.equals(UserInfo.userName) && password.equals(UserInfo.password)) {
						if (rootKey == UserInfo.rootKey) {
							dispose();
						} else {
							if (UserInfo.rootKey == 1) {
								JOptionPane.showMessageDialog(null, "관리자가 아닙니다. 체크를 풀어주세요.");
							} else if (UserInfo.rootKey == 0) {
								JOptionPane.showMessageDialog(null, "관리자 입니다. 체크를 해주세요.");
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호 오류입니다.");
					}
					// DB를 써서 아이디 패스워드가 맞는지 아닌지 맞다면 관리자인지 아닌지 체크

					// 로그인 유저의 정보를 UserInfo에 저장
					UserInfo.userName = userName;
					UserInfo.password = password;
					UserInfo.eMail = eMail;
					UserInfo.rootKey = rootKey;
					
					DB.close();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호 오류입니다.");
				}
				if (UserInfo.rootKey == 0) {
					WindowMain.mnNewMenu2.setEnabled(false);
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(72)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(btnNewButton)
						.addComponent(chckbxNewCheckBox)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(groupLayout.createSequentialGroup().addComponent(lblNewLabel_1)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(textField, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(Alignment.LEADING,
												groupLayout.createSequentialGroup().addComponent(lblNewLabel_2)
														.addGap(18).addComponent(textField_1,
																GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)))))
				.addContainerGap(31, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(50).addComponent(lblNewLabel).addGap(34)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1).addComponent(
						textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_2).addComponent(
						textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(chckbxNewCheckBox).addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(btnNewButton).addContainerGap(26, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
	}
}
