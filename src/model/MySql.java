package model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.util.HashMap;

public class MySql {

    private static Connection con;
    private static PreparedStatement stmt;

    private static void createConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mypharmacy", "root", "Hansara@2003");
    }

    public static Connection getConnection() throws Exception {
        if (con == null) {
            createConnection();
        }
        return con;
    }

    public static ResultSet select(String query) throws Exception {
        if (con == null) {
            createConnection();
        }
        ResultSet rs = con.createStatement().executeQuery(query);
//        con.close();
        return rs;
    }

    public static int iud(String query) throws Exception {
        if (con == null) {
            createConnection();
        }
        int result = con.createStatement().executeUpdate(query);
//        con.close();
        return result;
    }

    public static ResultSet select(String query, HashMap<Integer, Object> val) throws Exception {
        if (con == null) {
            createConnection();
        }

        PreparedStatement stmt = con.prepareStatement(query);

        for (HashMap.Entry<Integer, Object> entry : val.entrySet()) {
            if (entry.getValue() instanceof String) {
                stmt.setString(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {
                stmt.setInt(entry.getKey(), (Integer) entry.getValue());
            } else if (entry.getValue() instanceof Double) {
                stmt.setDouble(entry.getKey(), (Double) entry.getValue());
            }
        }

        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public static ResultSet select(String query, Object[] val) throws Exception {
        if (con == null) {
            createConnection();
        }

        PreparedStatement stmt = con.prepareStatement(query);

        for (int i = 0; i < val.length; i++) {
            if (val[i] instanceof String) {
                stmt.setString(i + 1, (String) val[i]);
            } else if (val[i] instanceof Integer) {
                stmt.setInt(i + 1, (Integer) val[i]);
            } else if (val[i] instanceof Double) {
                stmt.setDouble(i + 1, (Double) val[i]);
            } else if (val[i] instanceof Long) {
                stmt.setLong(i + 1, (Long) val[i]);
            } else if (val[i] instanceof Boolean) {
                stmt.setBoolean(i + 1, (Boolean) val[i]);
            } else if (val[i] instanceof Float) {
                stmt.setFloat(i + 1, (Float) val[i]);
            }
        }

        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public static long iud(String query, HashMap<Integer, Object> val) throws Exception {
        if (con == null) {
            createConnection();
        }

        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        for (HashMap.Entry<Integer, Object> entry : val.entrySet()) {
            if (entry.getValue() instanceof String) {
                stmt.setString(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {
                stmt.setInt(entry.getKey(), (Integer) entry.getValue());
            } else if (entry.getValue() instanceof Double) {
                stmt.setDouble(entry.getKey(), (Double) entry.getValue());
            } else if (entry.getValue() instanceof Long) {
                stmt.setDouble(entry.getKey(), (Long) entry.getValue());
            }
        }

        int result = stmt.executeUpdate();

        if (result > 0) {
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return -1;
    }

    public static long iud(String query, Object[] val) throws Exception {
        if (con == null) {
            createConnection();
        }

        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < val.length; i++) {
            if (val[i] instanceof String) {
                stmt.setString(i + 1, (String) val[i]);
            } else if (val[i] instanceof Integer) {
                stmt.setInt(i + 1, (Integer) val[i]);
            } else if (val[i] instanceof Double) {
                stmt.setDouble(i + 1, (Double) val[i]);
            } else if (val[i] instanceof Long) {
                stmt.setLong(i + 1, (Long) val[i]);
            } else if (val[i] instanceof Boolean) {
                stmt.setBoolean(i + 1, (Boolean) val[i]);
            } else if (val[i] instanceof Float) {
                stmt.setFloat(i + 1, (Float) val[i]);
            }
        }
        int result = stmt.executeUpdate();

        if (result > 0) {
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return -1;

    }

    public static void executeSearch() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void executeSearch(String select__from_gender_INNER_JOIN) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
