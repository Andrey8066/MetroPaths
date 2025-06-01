package com.metropaths;

import java.sql.SQLException;
import java.util.ArrayList;


public class Connections {
    protected DatabaseHandler databaseHandler;
    public Connections(DatabaseHandler databaseHandler) throws SQLException{
        this.databaseHandler = databaseHandler;
    }

    public ArrayList<Connection> GetAll() throws SQLException {
        ArrayList<Connection> res = new ArrayList<>();
        for (String[] data : this.databaseHandler.getAll("connections")){
            res.add(new Connection(data, databaseHandler));
        }
        return res;
    }

    public String getColorById(Integer id) throws SQLException{
        return (new Line(this.databaseHandler.getAllByParam("lines", "id", Integer.toString(id)).get(0))).getColor();
    }
}
