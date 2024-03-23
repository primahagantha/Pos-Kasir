package dotjava.admin.config;

import java.sql.DriverManager;
import java.sql.Connection;

public class db_config {
    public static Connection conn;

    static final String JDBC_URL = "jdbc:mysql://localhost/cashier_db";
    static final String username = "root";
    static final String password = "";

    public static void initDBConnection()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(JDBC_URL, username, password);

            if(conn != null)
            {
                System.out.println("Connection is established");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
}
