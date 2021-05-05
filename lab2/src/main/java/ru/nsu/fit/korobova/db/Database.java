package ru.nsu.fit.korobova.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final Connection _connection;

    public Database() throws SQLException, ClassNotFoundException, IOException {
        Class.forName("org.postgresql.Driver");
        _connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testbase","postgres", "password");
        _connection.setAutoCommit(false);

        Statement initDdlStatement = _connection.createStatement();
        String sqlInitStatement = Files.readString(Path.of("src/main/resources/posgresqlBase/initDatabase.sql"));
        initDdlStatement.execute(sqlInitStatement);
    }

    public Connection getConnection() {
        return _connection;
    }

    public void Dispose() throws SQLException {
        if (!_connection.isClosed()) {
            _connection.close();
        }
    }
}
