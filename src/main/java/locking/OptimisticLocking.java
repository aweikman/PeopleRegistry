package locking;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Random;

public class OptimisticLocking {
    private static final int QUERY_TIMEOUT = 70;//query timeout threshold in seconds

    private static final Random roller = new Random();

    private static Connection conn = null;

    public OptimisticLocking() {
    }

    public static void main(String [] args) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DataSource ds = getDataSource();
            conn = ds.getConnection();

            System.out.println("grabbing timestamp...");
            stmt = conn.prepareStatement("select last_modified from people ");
            rs = stmt.executeQuery();
            rs.next();
            Timestamp ts1 = rs.getTimestamp("last_modified");

            System.out.println("pausing for 60 seconds...");

            //pause for 60 seconds
            try {
                Thread.sleep(roller.nextInt(60 * 1000));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("checking timestamps...");
            // can reuse the statement since its query has not changed
            rs = stmt.executeQuery();
            rs.next();
            Timestamp ts2 = rs.getTimestamp("last_modified");

            if(!ts2.equals(ts1)) {
                System.out.println("Record HAS CHANGED!!!");
            } else {
                System.out.println("Record has NOT changed");
            }

            System.out.println("done.");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                conn.close();
            } catch(Exception e) {

            }
        }
    }

    public static DataSource getDataSource() {
        try {
            MysqlDataSource mysqlDS = new MysqlDataSource();
            mysqlDS.setURL("jdbc:mysql://cs3743.fulgentcorp.com:3306/cs4743_vrn320?serverTimezone=UTC#");
            mysqlDS.setUser("vrn320");
            mysqlDS.setPassword("KLkdnbefN2ATdpqyMtVo");
            return mysqlDS;
        } catch(RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
