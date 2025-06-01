package com.metropaths;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler { // Класс для работы с psql
    Connection connection;
    Statement statement;

    public DatabaseHandler(String jdbcUrl, String username, String password) throws SQLException { // Конструктор
        this.connection = DriverManager.getConnection(jdbcUrl, username, password);
        this.statement = this.connection.createStatement();

    }

    public ArrayList<String[]> getAll(String table) throws SQLException { // Метод для получения всех записей из таблицы
        ArrayList<String[]> lines = new ArrayList<>();
        ResultSet r = this.statement.executeQuery("SELECT * FROM " + table + ";");

        while (r.next()) {
            String[] row = new String[r.getMetaData().getColumnCount()];
            for (int i = 0; i < r.getMetaData().getColumnCount(); i++) {
                row[i] = r.getString(i + 1);
            }
            lines.add(row);

        }
        return lines;
    }

    public ArrayList<String[]> getAllByParam(String table, String param, String value) throws SQLException { // Метод
                                                                                                             // для
                                                                                                             // получения
                                                                                                             // всех
                                                                                                             // записей
                                                                                                             // по
                                                                                                             // условию
        ArrayList<String[]> lines = new ArrayList<>();
        ResultSet r = this.statement.executeQuery("SELECT * FROM " + table + " WHERE " + param + " = '" + value + "';");

        while (r.next()) {
            String[] row = new String[r.getMetaData().getColumnCount()];

            for (int i = 0; i < r.getMetaData().getColumnCount(); i++) {
                row[i] = r.getString(i + 1);
            }
            lines.add(row);

        }
        return lines;
    }

    public ArrayList<String[]> getAllBy2Param(String table, String param1, String value1, String param2, String value2)
            throws SQLException { // Метод
        // для
        // получения
        // всех
        // записей
        // по
        // условию
        ArrayList<String[]> lines = new ArrayList<>();
        ResultSet r = this.statement.executeQuery("SELECT * FROM " + table + " WHERE " + param1 + " = '" + value1
                + "'and " + param2 + " = '" + value2 + "'");

        while (r.next()) {
            String[] row = new String[r.getMetaData().getColumnCount()];

            for (int i = 0; i < r.getMetaData().getColumnCount(); i++) {
                row[i] = r.getString(i + 1);
            }
            lines.add(row);

        }
        return lines;
    }

    public ArrayList<String[]> getDataByParam(String table, String data, String param, String value)
            throws SQLException { // Метод для получения полей по условию
        ArrayList<String[]> lines = new ArrayList<>();

        ResultSet r = this.statement
                .executeQuery("SELECT " + data + " FROM " + table + " WHERE " + param + "=" + value);

        while (r.next()) {
            String[] row = new String[r.getMetaData().getColumnCount()];

            for (int i = 0; i < r.getMetaData().getColumnCount(); i++) {
                row[i] = r.getString(i + 1);
            }
            lines.add(row);
        }
        return lines;
    }

    public ArrayList<String> getDataByParamWithJoin(String table1, String table2, String join, String data,
            String param, String value) throws SQLException { // Метод для получения полей по условию с объединением
                                                              // нескольких таблиц

        ArrayList<String> lines = new ArrayList<>();
        ResultSet r = this.statement.executeQuery("SELECT " + table1 + "." + data + " FROM " + table1 + " JOIN "
                + table2 + " ON " + join + "  WHERE " + param + " = '" + value + "'");

        while (r.next()) {
            lines.add(r.getString(1));
        }
        return lines;
    }

    public void insertIntoDatabase(String table1, String values_names, String[] values) throws SQLException { // Метод
                                                                                                              // для
                                                                                                              // записи
                                                                                                              // в
                                                                                                              // БД
        System.out.println("INSERT INTO " + table1 + " (" + values_names + ") VALUES (" + values + ")");
        String sql = "INSERT INTO " + table1 + " (" + values_names + ") VALUES (?";
        for (int i = 0; i < values.length - 1; i++) {
            sql += ", ?";
        }
        sql += ")";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, values[0]);

        for (int i = 1; i < values.length; i++) {
            stmt.setString(i + 1, values[i]);
        }
        stmt.execute();
    }

    public void updateValueInDatabase(String table1, String value_name, String value, String param, String paramValue)
            throws SQLException { // Метод для обновления записи
        this.statement.execute(
                "UPDATE " + table1 + " SET " + value_name + " = " + value + " WHERE " + param + " = " + paramValue);
    }

    public void execCommand(String command) throws SQLException {
        this.statement.execute(command);
    }

    public void executeStatement(String values[]) throws SQLException {
        String sql = "INSERT INTO questions ( question, name, answer, topic ) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, values[0]);

        stmt.setString(1, values[0]);
        stmt.setString(2, values[1]);
        stmt.setString(3, values[2]);
        stmt.setInt(4, Integer.parseInt(values[3]));

        stmt.execute();
    }

    public Integer getNumberofColumns(String table) throws SQLException{
        ResultSet r = this.statement.executeQuery("SELECT COUNT(id) from " + table);
        return r.getInt(1);
    }

    protected void finalize() throws Throwable {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.finalize();
    }

}
