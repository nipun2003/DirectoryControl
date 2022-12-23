package org.example.data.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresDatabase {

    private final String databaseName;
    private final String userName;
    private final String userPassword;

    public PostgresDatabase(String databaseName, String userName, String userPassword) {
        this.databaseName = databaseName;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public Connection connectToDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/" + databaseName,
                            userName, userPassword);
        } catch (Exception e) {
            return null;
        }
    }

    private static PostgresDatabase INSTANCE = null;

    private static PostgresDatabase getDatabase(
            String userName, String userPassword, String databaseName
    ) {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        INSTANCE = new PostgresDatabase(
                databaseName, userName, userPassword
        );
        return INSTANCE;
    }
}
