package com.metropaths;

import java.sql.SQLException;
import java.util.ArrayList;


public class Stations {
    protected DatabaseHandler databaseHandler;
    public Stations(DatabaseHandler databaseHandler) throws SQLException{
        this.databaseHandler = databaseHandler;
    }

    public ArrayList<Station> GetAll() throws SQLException {
        ArrayList<Station> res = new ArrayList<>();
        for (String[] data : this.databaseHandler.getAll("stations")){
            res.add( new Station(data));
        }
        return res;
    }

    public Station GetById(String id) throws SQLException {
        String[] res = new String[7];
        for (String[] data : this.databaseHandler.getDataByParam("stations", "*", "id", id)){
            res = data;
        }
        return new Station(res);
    }

    public Integer getLineIdById(Integer id) throws SQLException{
        return (new Station(this.databaseHandler.getAllByParam("stations", "id", Integer.toString(id)).get(0))).getLineId();
    }

    public  ArrayList<Station> geStationsByCoords(Integer x, Integer y) throws SQLException{
        ArrayList<String[]> data = databaseHandler.getAllBy2Param("stations", "map_x", Integer.toString(x), "map_y", Integer.toString(y));
        ArrayList<Station> res = new ArrayList<>();
        for (int i = 0; i < data.size(); i++){
            res.add(new Station(data.get(i)));
        }
        return res;
    }

    public Integer getNumberofColumns() throws SQLException{
        return databaseHandler.getNumberofColumns("stations");
    }
}
