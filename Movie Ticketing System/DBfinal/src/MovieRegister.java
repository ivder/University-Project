import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MovieRegister extends JPanel {
	Connection connection=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	private JTextField textField;

	String title;
	String filePath=null;
	int duration;
	int period;
	
	private JTextField textField_1;
	private JTextField textField_2;

	public MovieRegister() {

		// ID
		JLabel lblNewLabel = new JLabel("Title");
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel label = new JLabel("분");
		
		JLabel lblNewLabel_3 = new JLabel("개월");
		
		JLabel lblNewLabel_4 = new JLabel("Movie Registration");
		lblNewLabel_4.setFont(new Font("Tempus Sans ITC", Font.BOLD | Font.ITALIC, 18));

		// Password
		JLabel lblNewLabel_1 = new JLabel("Duration");

		// Password recommend
		JLabel lblNewLabel_2 = new JLabel("Period");
		
		JLabel imagelabel = new JLabel("");
		JLabel lblNewLabel_5 = new JLabel("");

		JScrollPane scrollPane = new JScrollPane();
		
		// Register Button
		JButton btnNewButton = new JButton("영화등록");
		
		JButton btnBrowse = new JButton("Browse");
		
		btnBrowse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser=new JFileChooser(new File("C:\\"));
				 
				chooser.setMultiSelectionEnabled(false);
				chooser.setVisible(true);

				chooser.showOpenDialog(null);

				File file=chooser.getSelectedFile();
				if(file!=null){filePath=file.getPath();}
				if(filePath!=null){
				lblNewLabel_5.setText("File:"+" "+filePath);
				imagelabel.setIcon(new ImageIcon(filePath));
				scrollPane.setViewportView(imagelabel);
				}
				
			}
			
		});
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				
				try {
					title = textField.getText();
					duration = Integer.parseInt(textField_1.getText());
					period =Integer.parseInt(textField_2.getText());
					FileInputStream fileInputStream=new FileInputStream(filePath);
					byte b[]=new byte[fileInputStream.available()];

					Class.forName("com.mysql.jdbc.Driver");
					Connection DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_db", "root",
							"1234");
					Statement sql = DB.createStatement();
					ResultSet cursor = sql
							.executeQuery("select * from movielist where movie='" + title + "'");
					//빈칸이 있는지 체크
					if (title.equals(null)) {
						JOptionPane.showMessageDialog(null, "빈칸이 있으면 안됩니다.");
					} else {
						// 아이디가 있는지 체크
						if (cursor.next()) {
							JOptionPane.showMessageDialog(null, "이미 있는 영화입니다.");
							textField.setText("");
							textField_1.setText("");
							textField_2.setText("");
						} else {
							// 비밀번호와 재입력이 같은지 체크			
								int rowsAffected = sql.executeUpdate("insert into movielist(movie,duration,period,image) values('" + title
										+ "', '" + duration + "', '" + period + "','" + b + "')");

								JOptionPane.showMessageDialog(null, "영화 등록이 완료 되었습니다!!!");
								textField.setText("");
								textField_1.setText("");
								textField_2.setText("");
								imagelabel.setIcon(null);
								lblNewLabel_5.setText("");
								
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
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(45)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_4)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_1)
										.addComponent(lblNewLabel)
										.addComponent(lblNewLabel_2))
									.addGap(46))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_5)
										.addComponent(btnBrowse))
									.addGap(18))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnNewButton)
									.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textField)
										.addComponent(textField_1)
										.addComponent(textField_2))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(7)
											.addComponent(label))
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblNewLabel_3))))
								.addComponent(imagelabel)
								.addComponent(scrollPane))))
					.addContainerGap(205, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(42)
					.addComponent(lblNewLabel_4)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_3)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnBrowse)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_5))
								.addComponent(imagelabel))
							.addGap(119)
							.addComponent(btnNewButton))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
					.addGap(136))
		);
		setLayout(groupLayout);
	}
	
	
}


