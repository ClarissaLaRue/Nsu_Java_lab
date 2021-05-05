package ru.nsu.fit.korobova.services.dao;

import ru.nsu.fit.korobova.db.Database;
import ru.nsu.fit.korobova.models.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DaoNodeServices {
    private final Database _database;

    public DaoNodeServices(Database database) {
        _database = database;
    }

    public void addNodeBySqlRequest(Node node) throws SQLException {
        String insertStatement = String.format("INSERT INTO node (id, user_name) VALUES (%d, %s);", node.getId(), node.getUser());

        Connection connection = _database.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(insertStatement);
    }

    public void addNodeListBySqlRequest(List<Node> nodeList) throws SQLException {
        Connection connection = _database.getConnection();

        for (Node node : nodeList) {
            String insertStatement = String.format("INSERT INTO node (id, user_name) VALUES (%d, %s);", node.getId(), node.getUser());
            Statement statement = connection.createStatement();
            statement.execute(insertStatement);
        }
    }

    public void addNodeByPreparedStatement(Node node) throws SQLException {
        Connection connection = _database.getConnection();
        PreparedStatement statement = getPreparedStatementForNode(connection, node);
        statement.executeUpdate();
    }


    public void addNodeListByPreparedStatement(List<Node> nodeList) throws SQLException {
        Connection connection = _database.getConnection();

        PreparedStatement statement = null;

        for (Node node : nodeList) {
            statement = getPreparedStatementForNode(connection, node);
        }

        if (statement != null) {
            statement.executeUpdate();
        }
    }


    public void addNodeByBatch(Node node) throws SQLException {
        Connection connection = _database.getConnection();
        PreparedStatement statement = getPreparedStatementForNode(connection, node);
        statement.executeBatch();
    }

    public void addNodeListByBatch(List<Node> nodeList) throws SQLException {
        Connection connection = _database.getConnection();

        PreparedStatement statement = null;
        for (Node node : nodeList) {
            statement = getPreparedStatementForNode(connection, node);
        }

        if (statement != null) {
            statement.addBatch();
        }
    }

    private PreparedStatement getPreparedStatementForNode(Connection connection, Node node) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO node (id, latitude, longitude, user_name) VALUES (?, ?, ?, ?);");
        statement.setLong(1, node.getId().longValue());
        statement.setString(2, node.getUser());
        return statement;
    }
}
