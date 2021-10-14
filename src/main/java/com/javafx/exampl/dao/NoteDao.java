package com.javafx.exampl.dao;

import com.javafx.exampl.entity.Note;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NoteDao {

    private static final String URL = "jdbc:mysql://localhost:3306/note_store";
    private static final String USER = "root";
    private static final String PASSWORD = "7454378";

    public static final String INSERT_DATA = "INSERT INTO note(description, created_time) VALUES (?, ?)";
    public static final String SELECT_ALL_DATA = "SELECT * FROM note";
    public static final String DELETE_DATA = "DELETE FROM note WHERE id = ?";

    @SneakyThrows
    public Note create(Note note) throws DaoException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(INSERT_DATA, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, note.getDescription());
            Timestamp timestamp = Timestamp.valueOf(note.getCreatedTime());
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            note.setId(id);
            return note;
        } catch (SQLException e) {
            throw new DaoException("Failed to connect");
        }
    }

    public List<Note> findAll() throws DaoException {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_DATA);
            List<Note> noteList = new ArrayList<>();
            while (resultSet.next()){
                resultSet.getInt("id");
                resultSet.getString("description");
                resultSet.getTimestamp("created_time");
                Note note = new Note(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("created_time").toLocalDateTime()
                );
                noteList.add(note);
            }
            return noteList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DaoException("Failed to connect");
        }
    }

    public void delete(Note note) throws DaoException {
        try {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement(DELETE_DATA);
        preparedStatement.setInt(1, note.getId());
        preparedStatement.executeUpdate();
    } catch (SQLException | ClassNotFoundException e) {
        throw new DaoException("Failed to connect");
    }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
