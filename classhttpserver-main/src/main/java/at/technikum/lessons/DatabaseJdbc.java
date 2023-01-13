package at.technikum.lessons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseJdbc {
    public static void main(String[] args) throws SQLException {
        try (final Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/swen1db",
                "swen1user",
                "swen1pw")) {

            final PreparedStatement createTable = connection.prepareStatement("""
                        CREATE TABLE IF NOT EXISTS messages(
                            id int primary key,
                            content varchar(500)
                        );
                    """
            );
            createTable.execute();
            createTable.close();

            final var insert = connection.prepareStatement("""
                INSERT INTO messages VALUES (?, ?)
            """);
            insert.setInt(1, 1);
            insert.setString(2, "Hallo ihre Nachricht lautet: Schöne Feiertage");
            insert.execute();
            insert.close();
        }
    }
}
