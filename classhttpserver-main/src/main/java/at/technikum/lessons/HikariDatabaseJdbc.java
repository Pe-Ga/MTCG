package at.technikum.lessons;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HikariDatabaseJdbc {
    public static void main(String[] args) throws SQLException {
        HikariConfig config = new HikariConfig("src/main/resources/hikari.properties");
        HikariDataSource ds = new HikariDataSource(config);

        try (final Connection connection = ds.getConnection()) {

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
            insert.setInt(1, 2);
            insert.setString(2, "Hallo ihre Nachricht lautet: Schöne Feiertage");
            insert.execute();
            insert.close();
        }
    }
}
