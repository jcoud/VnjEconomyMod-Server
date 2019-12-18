package vnj.vnjems.sql;

import vnj.vnjems.User;
import vnj.vnjems.Vnjeconomymod;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class SqlExecutor {
    public SqlExecutor() {
//        if (!checkTableExists())
        makeTable();
    }

    private boolean checkTableExists() {
        String sql = "SELECT " + SqlKeyWords.USER_ID + " FROM " + SqlKeyWords.USER_LIST;
        return getQueryResult(sql);
    }

    private void makeTable() {
        String table_users =
                "create table " + SqlKeyWords.USER_LIST + "(\n" +
                SqlKeyWords.USER_ID         + " integer auto_increment primary key,\n" +
                SqlKeyWords.USER_NAME       + " varchar(45) not null unique,\n" +
                SqlKeyWords.USER_UUID       + " varchar(45) not null,\n" +
                SqlKeyWords.USER_BALANCE    + " long not null\n" +
                ");\n" +
                "create index " + SqlKeyWords.USER_ID + " on " + SqlKeyWords.USER_LIST + " (" + SqlKeyWords.USER_ID + ");";

        Vnjeconomymod.logger.info("[h2] Table 'USER_LIST' creating... ");
        Vnjeconomymod.logger.info("[h2] Result: " + (getQueryResult(table_users)? "successful" : "skipped - already exists"));
    }
    static boolean execute(String sqlQuery) {
        try (Connection connection = DataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                final int updatedRows = statement.executeUpdate(sqlQuery);
                connection.commit();
                Vnjeconomymod.logger.debug("Sql query commit");
                if (updatedRows > 0)
                    return true;
            } catch(SQLException e) {
                connection.rollback();
                Vnjeconomymod.logger.warn("SQL request execution error. Cause: " + e.getMessage());
            }
        } catch(SQLException e) {
            Vnjeconomymod.logger.warn("SQL connection error. Cause: " + e.getMessage());
        }
        return false;
    }

    private static boolean getQueryResult(String sqlQuery) {
        try (Connection connection = DataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.execute(sqlQuery);
            } catch (SQLException e){
                Vnjeconomymod.logger.debug("SQL request execution error. Cause: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            Vnjeconomymod.logger.debug("SQL connection error. Cause: " + e.getMessage());
            return false;
        }
        return true;
    }

    static User findUser(String sqlQuery) {
        try (Connection connection = DataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                User user = null;
                while (resultSet.next()) {
                    user = new User(
                            resultSet.getString(SqlKeyWords.USER_NAME.name()),
                            UUID.fromString(resultSet.getString(SqlKeyWords.USER_UUID.name())),
                            resultSet.getLong(SqlKeyWords.USER_BALANCE.name()),
                            resultSet.getInt(SqlKeyWords.USER_ID.name())
                    );
                }
                connection.commit();
                return user;
            } catch (SQLException e){
                connection.rollback();
                Vnjeconomymod.logger.warn("SQL request execution error. Cause: " + e.getMessage());
            }
        } catch (SQLException e) {
            Vnjeconomymod.logger.warn("SQL request execution error. Cause: " + e.getMessage());
        }
        return null;
    }
}
