package com.metropaths;

import java.sql.SQLException;
import java.util.ArrayList;


public class Lines {
    protected DatabaseHandler databaseHandler;
    public Lines(DatabaseHandler databaseHandler) throws SQLException{
        this.databaseHandler = databaseHandler;
    }

    public ArrayList<Line> GetAll() throws SQLException {
        ArrayList<Line> res = new ArrayList<>();
        for (String[] data : this.databaseHandler.getAll("lines")){
            res.add(new Line(data));
        }
        return res;
    }

    public String getColorById(Integer id) throws SQLException{
        return (new Line(this.databaseHandler.getAllByParam("lines", "id", Integer.toString(id)).get(0))).getColor();
    }
}
