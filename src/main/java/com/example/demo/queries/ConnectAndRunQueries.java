package com.example.demo.queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.demo.user.User;


public class ConnectAndRunQueries {    

    String url = "jdbc:postgresql://192.168.3.64:5432/test_db";
    String username;
    String password;
    Connection con;

    public ConnectAndRunQueries(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Connection connect() throws SQLException{
        System.out.println("Trying connect to DB...");
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.print("An Exception: ");
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public User select (String login) throws SQLException{
        String query = "select T1.login, T1.password, T1.date, T2.email "
                        + "from registration_data T1 "
                        + "inner join email_data T2 "
                        + "on T1.login = T2.login "
                        + "where T1.login = '"
                        + login + "'";
        
        try {
            con = connect();
            System.out.println("Connection is established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            try {
                rs.next();
                User user = new User(rs.getString(1), rs.getString(2), 
                        rs.getDate(3), rs.getString(4));
                return user;
            } catch (SQLException e) {
                System.out.println("Nothing was found");
                throw e;
            }
            finally {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public int insert(User user){
        String query = "insert into registration_data values (?, ?, ?);\n" + 
                        "insert into email_data values (?, ?);";
        
        try {
            con = connect();
            System.out.println("Connection is established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        try (PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, user.login);
            st.setString(2, user.password);           
            st.setDate(3, user.date);           
            st.setString(4, user.login);           
            st.setString(5, user.email);     
            return st.executeUpdate();      
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }
    
}
