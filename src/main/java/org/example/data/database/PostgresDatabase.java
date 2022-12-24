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
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static PostgresDatabase INSTANCE = null;

    public static PostgresDatabase getDatabase() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        INSTANCE = new PostgresDatabase(
                "directory_demo", "lucifer", "2003"
        );
        return INSTANCE;
    }
}
