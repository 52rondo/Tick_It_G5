package tw.com.tibame.util.common;

public class Common {
	// MySQL 8之後連線URL需加上SSL與時區設定
	public final static String driver = "com.mysql.cj.jdbc.Driver";
	public final static String URL = "jdbc:mysql://localhost:3306/TICK_IT?useUnicode=yes&characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Taipei";
	public final static String USER = "root";
	public final static String PASSWORD = "password";

}
