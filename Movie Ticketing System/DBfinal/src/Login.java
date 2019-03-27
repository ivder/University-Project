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
import javax.swing.ImageIcon;

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
		setSize(new Dimension(385, 350));

		JLabel lblNewLabel = new JLabel("Welcome to");
		lblNewLabel.setFont(new Font("Dialog", Font.ITALIC, 14));

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
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_db", "root",
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
		
		JLabel lblNewLabel_3 = new JLabel("Please Login to continue");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_4 = new JLabel("Don't have id?");
		
		JButton btnSignUp = new JButton("Sign Up");
		
		btnSignUp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new UserRegisterPop().setVisible(true);
			}
			
		});
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon("C:\\Javadoc\\DBfinal\\images\\Cinema_XXI_2015732253591.png"));
		
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(72)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSignUp)
							.addContainerGap())
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel_4)
								.addContainerGap())
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNewLabel_2)
									.addComponent(lblNewLabel_1))
								.addGap(18)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(textField_1)
									.addComponent(textField, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
								.addContainerGap(64, Short.MAX_VALUE)))))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(222, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxNewCheckBox)
						.addComponent(btnNewButton))
					.addGap(88))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(19)
					.addComponent(lblNewLabel)
					.addContainerGap(273, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(82)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_5)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
							.addGap(64))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(4)
					.addComponent(lblNewLabel_5)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(chckbxNewCheckBox)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton)
					.addGap(18)
					.addComponent(lblNewLabel_4)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSignUp)
					.addContainerGap(27, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
}
