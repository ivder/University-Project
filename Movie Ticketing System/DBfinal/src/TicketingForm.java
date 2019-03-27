import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")

public class TicketingForm extends JPanel{   

      Connection con,con1;
      Statement stmt,st;

      PreparedStatement preStatement,updatePreStmt;
      JLabel title, nameLabel, timeLabel, movieLabel, seatLabel, priceLabel, priceField;
      JTextField nameField, timeField, seatField;
      JButton registerButton, exitButton,updateButton,deleteButton,resetButton,refresh;
      JComboBox movieField;

      JRadioButton weekday, weekend;
      ButtonGroup bg;

      JPanel panel;
      JTable table;

      DefaultTableModel model;
      JScrollPane scrollpane;
      
      String time = "";
      String select;
      int price;
      ResultSet rst,rstLast,rs;
      Object[][] data;
      int serialNo; 
      String SHOW = "Show";
      private JLabel label;
      private JLabel lblNewLabel;
      private JLabel imagelabel;

      public TicketingForm() {
      	setBackground(SystemColor.control);
           // TODO Auto-generated constructor stub

            // Calling connect method, this will connect us to database
            connect();         

            // Defining Labels 

            title = new JLabel("Movie Ticketing");
            title.setFont(new Font("Tahoma", Font.BOLD, 13));

            title.setBounds(60, 7, 200, 30);

            nameLabel = new JLabel("이름"); 

            nameLabel.setBounds(30, 52, 60, 30);

            timeLabel = new JLabel("시간"); 

            timeLabel.setBounds(30, 200, 60, 30);

            movieLabel = new JLabel("영화이름"); 

            movieLabel.setBounds(30, 93, 60, 30); 

            seatLabel = new JLabel("자리"); 

            seatLabel.setBounds(30, 153, 60, 30);

            // Defining Name field
            nameField = new JTextField(); 

            nameField.setBounds(95, 52, 130, 30);         

            // Defining time Buttons
            weekday = new JRadioButton("평일");

            weekday.setBounds(95, 200, 60, 30);

            weekend = new JRadioButton("주말");

            weekend.setBounds(155, 200, 70, 30);            

            bg = new ButtonGroup(); 

            bg.add(weekday); 

            bg.add(weekend);

            seatField = new JTextField();

            seatField.setBounds(95, 153, 89, 30);
            setLayout(null);

 

            // fixing all Label,TextField,RadioButton

            add(title);

            add(nameLabel);

            add(timeLabel);

            add(movieLabel);

            add(seatLabel);
            
            priceLabel = new JLabel("Price : ");
            priceLabel.setBounds(30, 252, 46, 14);
            add(priceLabel);

            add(nameField);

            add(weekday);

            add(weekend);

            add(seatField);


            // Defining Exit Button

            exitButton = new JButton("Exit"); 

            exitButton.setBounds(25, 270, 80, 25);            

            // Defining Register Button

            registerButton = new JButton("등록");

            registerButton.setBounds(110, 270, 100, 25);


            // Defining Update Button

            updateButton = new JButton("수정");

            updateButton.setBounds(110, 305, 100, 25);

            updateButton.setEnabled(false);


            // Defining Delete Button

            deleteButton = new JButton("삭제");

            deleteButton.setBounds(25, 305, 80, 25);

            deleteButton.setEnabled(false);


            // Defining Reset Button

            resetButton = new JButton("Reset");

            resetButton.setBounds(60, 340, 100, 25);

            resetButton.setEnabled(false); 


            // fixing all Buttons

            add(exitButton);

            add(registerButton);

            add(updateButton);

            add(deleteButton);

            add(resetButton);    
            
            imagelabel = new JLabel("");
            imagelabel.setBounds(60, 398, 46, 14);
            add(imagelabel);


            // Defining Panel

            panel = new JPanel();

            panel.setLayout(new GridLayout());

            panel.setBounds(250, 20, 480, 330);

            panel.setBorder(BorderFactory.createDashedBorder(Color.blue));

            add(panel);

            // Defining Refresh Button

            refresh = new JButton("Refresh Table");

            refresh.setBounds(350, 350, 270, 15);

            add(refresh);


            //Defining Model for table

            model = new DefaultTableModel();

            //Adding object of DefaultTableModel into JTable

            table = new JTable(model);


            //Fixing Columns move

            table.getTableHeader().setReorderingAllowed(false);


            // Defining Column Names on model

            model.addColumn("Name");

            model.addColumn("Movie");

            model.addColumn("Time");

            model.addColumn("Seat");

            model.addColumn("Price");
 

            // Enable Scrolling on table

           scrollpane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,

                                           JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            panel.add(scrollpane);


            //Displaying Frame in Center of the Screen

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            this.setLocation(dim.width/2-this.getSize().width/2,

                             dim.height/2-this.getSize().height/2);
            
            priceField = new JLabel("");
            priceField.setFont(new Font("Tahoma", Font.BOLD, 11));
            priceField.setBounds(94, 252, 46, 14);
            add(priceField);
            
            movieField = new JComboBox();
            movieField.setBounds(95, 97, 130, 26);
            add(movieField);
            
            label = new JLabel("원");
            label.setBounds(179, 252, 46, 14);
            add(label);
            
            lblNewLabel = new JLabel("개");
            lblNewLabel.setBounds(194, 161, 46, 14);
            add(lblNewLabel);
            

            setVisible(true);
                       
            //breakline
            
          //Retreive value from Movielist database to ComboBox movieField
            try{
      	      con1 = DriverManager.getConnection("jdbc:mysql://localhost/movie_db","root","1234");
      	      st = con1.createStatement();
      	      String s = "select* from movielist";
      	      rs = st.executeQuery(s);
      	        while(rs.next())
      	        {
      	            movieField.addItem(rs.getString(2));
      	            //Blob blob = rs.getBlob(5);
      	            //imagelabel.setIcon(new ImageIcon(blob.getBytes(1, (int)blob.length())));
      	        }
      	    }catch(Exception e){
      	        JOptionPane.showMessageDialog(null, "ERROR");
      	    }finally{
      	        try{
      	            st.close();
      	            rs.close();
      	            con1.close();
      	        }catch(Exception e){
      	            JOptionPane.showMessageDialog(null, "ERROR CLOSE");
      	        }
      	    }
            
            nameField.addKeyListener(new KeyAdapter() {

                public void keyTyped(KeyEvent e) {

                if(nameField.getText().length()>=15)

                      e.consume();

                }

          });
            
            seatField.addKeyListener(new KeyAdapter() {

                public void keyTyped(KeyEvent e) {

                      char c = e.getKeyChar();

                      if (!((c >= '0') && (c <= '9') ||

                           (c == KeyEvent.VK_BACK_SPACE) ||

                           (c == KeyEvent.VK_DELETE))) {

                            // getToolkit().beep();

                            e.consume();

                      }

                      if(seatField.getText().length()>9) 

                            e.consume();

                }

          });

          weekday.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                      time = "평일";
                      price=7000*Integer.parseInt(seatField.getText());
                      priceField.setText(String.valueOf(price));

                }
          });

          weekend.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                      time = "주말";
                      price=9000*Integer.parseInt(seatField.getText());;
                      priceField.setText(String.valueOf(price));

                }

          });
          

          exitButton.addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent arg0) {

                      try{

                            con.close();

                            System.exit(0);

                      }catch(Exception ex){

                             System.out.println(ex.getMessage());

                      }

                }

          });

          registerButton.addActionListener(new AbstractAction(SHOW){

                 public void actionPerformed(ActionEvent ae){

                       try{

                          if (ae.getSource() == registerButton) {

                              if (nameField.getText().equals(""))

                                 JOptionPane.showMessageDialog(null, 

                                             "Please provide Name");


                              else if(seatField.getText().equals(""))
                                 JOptionPane.showMessageDialog(null, "Please select seat");

                              else if(time.equals(""))

                                 JOptionPane.showMessageDialog(null, "Please select Time");

                              else {

                               //Fetching column values from Database

                               preStatement.setString(1,nameField.getText());          
                               preStatement.setString(2,"");
                               preStatement.setString(3,"");
                               preStatement.setString(4,"");
                               preStatement.setString(5,"");
                               preStatement.setString(6,time);
                               preStatement.setString(5,(String)movieField.getSelectedItem());
                               preStatement.setString(7,seatField.getText());                           
                               preStatement.setInt(8,Integer.parseInt(priceField.getText()));
                               
                             //Executing MySQL Update Query
                               int i = preStatement.executeUpdate();

                               if(i==1){

                                JOptionPane.showMessageDialog(panel, 

                                             "Successfully Registered");

                               }

                               // showing last row 

                              rstLast = stmt.executeQuery("select *from userlist");

                              rstLast.last();

                              String string = String.valueOf(rstLast.getString(1))+","+rstLast.getString(5)+","+rstLast.getString(6)+","+rstLast.getString(7)+","+rstLast.getInt(8);

                               Object[] row = null;

                               row = string.split(",");

                               model.addRow(row);

                               panel.revalidate();
                               // fields are blank

                               blankFields();

                             }

                            }

                     }catch(Exception ex){

                            System.out.println(ex.getMessage()); 

                     }

                 }

          });



          updateButton.addActionListener(new AbstractAction(SHOW){

            public void actionPerformed(ActionEvent e){

                if (nameField.getText().equals(""))

                      JOptionPane.showMessageDialog(null,

                                  "Please provide Name_Field");

                else if(seatField.getText().equals(""))

                      JOptionPane.showMessageDialog(null,

                                  "Please provide seat_Field");              

                else if(time.equals(""))

                      JOptionPane.showMessageDialog(null,

                                  "Please select Time");                  

                else {

                            int r = table.getSelectedRow();

                            try{

                            if(r>=0){

                               if(weekday.isSelected())
                               {
                                  time = weekday.getText();
                                  price=7000*Integer.parseInt(seatField.getText());;
                                  priceField.setText(String.valueOf(price));
                               }
                               	
                               else{
                                  time = weekend.getText();
                                  price=9000*Integer.parseInt(seatField.getText());;
                                  priceField.setText(String.valueOf(price));
                               }

                                  String name =(String)table.getModel().getValueAt(r,0) ;

                                  updatePreStmt = con.prepareStatement("update userlist set name=?,password=?,email=?,rootkey=?,movie=?,time=?,seat=?,price=? where name='"+name+ "'");

                                updatePreStmt.setString(1,nameField.getText());                             
                                updatePreStmt.setString(2,"");
                                updatePreStmt.setString(3,"");
                                updatePreStmt.setString(4,"");
                                updatePreStmt.setString(5,"");                                
                                updatePreStmt.setString(5,(String)movieField.getSelectedItem());
                                updatePreStmt.setString(6,time);
                                updatePreStmt.setString(7,seatField.getText());                                
                                updatePreStmt.setInt(8,Integer.parseInt(priceField.getText()));

                                int i = updatePreStmt.executeUpdate();

                                if(i==1){

                                   table.setValueAt(nameField.getText(),r,0);
                                   table.setValueAt(movieField.getSelectedItem(),r,1);
                                   table.setValueAt(time, r, 2);
                                   table.setValueAt(seatField.getText(), r, 3);
                                   table.setValueAt(priceField.getText(), r, 4);

                                }

                                else JOptionPane.showMessageDialog(panel,

                                   "ID does't Exists in Database");

                            }

                            }catch(Exception ex){

                                    System.out.println("Update section: "+ 

                                                        ex.getMessage()); 

                            }

                      }

            }

        });



          //Registering Anonymous Listener Class

          deleteButton.addActionListener(new AbstractAction(SHOW){ 

            public void actionPerformed(ActionEvent e){

                try{ 

                //Getting Selected Row No

                int r = table.getSelectedRow(); 

                if(r>=0){ 

                      if (JOptionPane.showConfirmDialog(panel,

                          "Are you sure want to Delete this 'RECORD' ?","WARNING",

                          JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){

                            String name = (String)table.getModel().getValueAt(r,0);



                            // Executing MySQL Update Command

                            int i = stmt.executeUpdate("delete from userlist where name='"+name+ "'");

                            if(i==1){

                                  model.removeRow(r);



                                  // fields are blank

                                  blankFields();

                                  registerButton.setEnabled(true);

                                  deleteButton.setEnabled(false);

                                  updateButton.setEnabled(false);

                            }

                      }

                }

                }catch(Exception ex){

                       System.out.println(ex.getMessage());

                }

            }

        });



         //Registering Anonymous Listener Class

          resetButton.addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent arg0) {

                      // calling method resetFields()

                      resetFields();

                      registerButton.setEnabled(true);

                      updateButton.setEnabled(false);

                      deleteButton.setEnabled(false);

                      resetButton.setEnabled(false);

                }

          });


          // Registering Anonymous Listener Class

          refresh.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent arg0) {

                      //calling  refresh() method

                      refreshTable();

                }

          });


          //Registering Anonymous Listener Class

          table.addMouseListener(new MouseListener(){

                public void mouseClicked(MouseEvent arg0){ 

                      //Getting Selected Row No

                int r = table.getSelectedRow();

                if(r>=0){ 

                      deleteButton.setEnabled(true);

                      updateButton.setEnabled(true);

                      resetButton.setEnabled(true);

                      registerButton.setEnabled(false); 

                      //Fetching records from Table on Fields


                      nameField.setText(""+table.getModel().getValueAt(r,0));
                      movieField.setSelectedItem(""+table.getModel().getValueAt(r,1));

                      if(table.getModel().getValueAt(r,2).equals("평일"))

                            weekday.setSelected(true);

                      else

                            weekend.setSelected(true);

                      seatField.setText(""+table.getModel().getValueAt(r,3));
                      priceField.setText(""+table.getModel().getValueAt(r,4));

                }

                }

//              @Override

                public void mouseReleased(MouseEvent arg0) {}

//              @Override

                public void mousePressed(MouseEvent arg0) {}

//              @Override 

                public void mouseExited(MouseEvent arg0) {}

//              @Override 

                public void mouseEntered(MouseEvent arg0) {}

          });


          // Displaying rows into the Frame table

          addRows();

      }

	private void addRows(){

          try{

          Object[] row = null;

          //Generating Serial No

          serialNo=1;

          rst = stmt.executeQuery("select *from userlist");

          while(rst.next()){ 

                String string = String.valueOf(rst.getString(1))+","+rst.getString(5)+","+rst.getString(6)+","+rst.getString(7)+","+rst.getInt(8);

                row = string.split(",");


                // Adding records in table model 

                model.addRow(row);

          }

          panel.revalidate();

          }catch(Exception ex){ System.out.println(ex.getMessage()); }

    }


    private void resetFields(){ 
          //calling method blankfields() 

          blankFields();

    }

    // refresh method

    private void refreshTable(){



          // removing all the rows of the table

          DefaultTableModel dm = (DefaultTableModel)table.getModel();

          dm.getDataVector().removeAllElements();

          System.out.println("Refresh Table");


          //calling method addRows

          addRows();
    }

    private void blankFields(){

          // fields will be blank

          nameField.setText("");

          time = "";

          bg.clearSelection();

          movieField.setSelectedIndex(1);;

          seatField.setText("");
          
          priceField.setText("");
    }


      // Connection with Database

      public void connect(){

            try{

                  Class.forName("com.mysql.jdbc.Driver");

                  con =DriverManager.getConnection(

                                  "jdbc:mysql://localhost:3306/movie_db","root","1234");

                  stmt = con.createStatement();

                  preStatement = con.prepareStatement("insert into userlist (name,password,email,rootkey,movie,time,seat,price) values(?,?,?,?,?,?,?,?)");

            }catch(Exception e){

                  System.out.print(e.getMessage());

            }

      }
}