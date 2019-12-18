package vnj.vnjems.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariDataSource ds;
    private static String jdbc_url = "jdbc:h2:./vnjdb/database;mode=mysql";

    private DataSource() {}

    static {
        HikariConfig hc = new HikariConfig();
        hc.setDriverClassName("org.h2.Driver");
        hc.setJdbcUrl(jdbc_url);
        ds = new HikariDataSource(hc);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}