package vnj.vnjems.sql;

import vnj.vnjems.User;

class UserSqlRequests {

    static User findByName(String user_name) {
        //language=SQLite
        final String sqlQuery = String.format(
                "SELECT * FROM "+ SqlKeyWords.USER_LIST + " " +
                "WHERE "+ SqlKeyWords.USER_NAME + " = '%s'", user_name);
        return SqlExecutor.findUser(sqlQuery);
    }

    static User findByUUID(String user_uuid) {
        //language=SQLite
        final String sqlQuery = String.format(
                "SELECT * FROM "+ SqlKeyWords.USER_LIST + " " +
                        "WHERE "+ SqlKeyWords.USER_UUID + " = '%s'", user_uuid);
        return SqlExecutor.findUser(sqlQuery);
    }

    static User finById(int user_id) {
        //language=SQLite
        final String sqlQuery = String.format(
                "SELECT * FROM "+ SqlKeyWords.USER_LIST + " " +
                "WHERE "+ SqlKeyWords.USER_ID + " = '%d'", user_id);
        return SqlExecutor.findUser(sqlQuery);
    }

    static boolean save(User newUser) {
        //language=SQLite
        final String sqlQuery = String.format(
                "INSERT INTO " + SqlKeyWords.USER_LIST+" ("+
                    SqlKeyWords.USER_NAME   + ", " +
                    SqlKeyWords.USER_UUID   + ", " +
                    SqlKeyWords.USER_BALANCE + ") " +
                "VALUES ('%s', '%s', '%d')",
                    newUser.getName(),
                    newUser.getUuid().toString(),
                    newUser.getBalance());
        return SqlExecutor.execute(sqlQuery);
    }

    static boolean removeByName(String user_name) {
        //language=SQLite
        final String sqlQuery = String.format(
                "DELETE FROM "+ SqlKeyWords.USER_LIST +" "+
                "WHERE " + SqlKeyWords.USER_NAME + " = '%s'", user_name);
        return SqlExecutor.execute(sqlQuery);
    }

    static boolean updateUserByName(User newUser, String user_name) {
        //language=SQLite
        final String sqlQuery = String.format(
                "UPDATE " + SqlKeyWords.USER_LIST + " " +
                "SET " +
                    SqlKeyWords.USER_NAME       + "='%s', " +
                    SqlKeyWords.USER_UUID       + "='%s', " +
                    SqlKeyWords.USER_BALANCE    + "='%d' " +
                "WHERE " + SqlKeyWords.USER_NAME + "='%s'",
                    newUser.getName(),
                    newUser.getUuid().toString(),
                    newUser.getBalance(),
                    user_name);
        return SqlExecutor.execute(sqlQuery);
    }
}
