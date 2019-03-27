import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;


public class Insert {
public static void main(String[] args) {
try {
Random random=new Random();
 Class.forName("com.mysql.jdbc.Driver");
 Connection DB=DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_WB", "root", "1234");
 Statement sql=DB.createStatement();
 sql.executeUpdate("insert into booklist (title,author,year,publisher,name) values ('자바','홍길동','2009','경성출판사','')");
 sql.executeUpdate("insert into booklist (title,author,year,publisher,name) values ('자바스크립트','김태양','2011','태양출판사','')");
 sql.executeUpdate("insert into booklist (title,author,year,publisher,name) values ('C의 개념','김철수','1999','부산출판사','')");
 sql.executeUpdate("insert into booklist (title,author,year,publisher,name) values ('자바의 원리','김자바','2008','부산출판사','')");
 sql.executeUpdate("insert into booklist (title,author,year,publisher,name) values ('자료구조','박철수','2011','경성출판사','')");
 sql.executeUpdate("insert into booklist (title,author,year,publisher,name) values ('원영체제','이철수','2009','경성출판사','')");
 sql.executeUpdate("insert into booklist (title,author,year,publisher,name) values ('데이터베이스','김철수','2015','경성출판사','')");
 sql.executeUpdate("insert into booklist (title,author,year,publisher,name) values ('MySQL','박영희','2014','부산출판사','')");

 for(int i=9; i<1000; i++){
	int length=random.nextInt(15)+5;
	int length2=random.nextInt(5)+5;
	int length3=random.nextInt(10)+5;
	String title = randomString (length);
	String author = randomString (length2);
	String publisher = randomString (length3);
	String year = randomYear ();
	
	sql.executeUpdate(insertSQL(title,author,year,publisher));
 }
 DB.commit();
 DB.close();
 } catch (Exception e) {
 } 
 }

	static String randomString (int length){
		Random random = new Random();
		char buf[]=new char[length];
		for (int j = 0; j < length; j++) {
		 buf[j]=(char) (random.nextInt('Z'-'A')+'A');
		 }
		return new String(buf);
	}
	
	static String randomYear ()
	{
		Random random = new Random();
		char buf[]=new char[4];
		for (int j = 0; j < 4; j++) {
		 buf[j]=(char) (random.nextInt('9'-'0')+'0');
		 }
		return new String(buf);
	}
	
	static String insertSQL (String title, String author, String year, String publisher)
	{
		return String.format("insert into booklist (title,author,year,publisher,name)"
				+ " values ('%s','%s','%s','%s','')",title, author, year, publisher);
	}
} 