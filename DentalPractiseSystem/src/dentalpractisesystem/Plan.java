/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dentalpractisesystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;


/**
 *
 * @author jordan
 */
public class Plan extends SQLConnector {
    
    // Instance variables
    private String name;
    private double cost;
    private int repairs;
    private int checkups;
    private int visits;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getRepairs() {
        return repairs;
    }

    public void setRepairs(int repairs) {
        this.repairs = repairs;
    }

    public int getCheckups() {
        return checkups;
    }

    public void setCheckups(int checkups) {
        this.checkups = checkups;
    }

    public Plan(String name, double cost, int repairs, int checkups, int visits) {
        this.name = name;
        this.cost = cost;
        this.repairs = repairs;
        this.checkups = checkups;
        this.visits = visits;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }
    
    public static void createTable() {
        update("CREATE TABLE IF NOT EXISTS Plan(" +
            "    name VARCHAR(50) NOT NULL PRIMARY KEY," +
            "    price REAL(4,2)," +
            "    repairs INTEGER," +
            "    checkups INTEGER," +
            "    visits INTEGER);");
    }
    
    
    
}
