package com.example.demo.queries;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import com.example.demo.user.User;

@Component("db")
public class ConnectAndRunQueries {    

    private final String url = "jdbc:postgresql://192.168.3.64:5432/test_db";
    private final String username = "test_user";
    private final String password = "test_password";

    public User select (String login) throws SQLException{
        String query = "select T1.login, T1.password, T1.date, T2.email "
                        + "from registration_data T1 "
                        + "inner join email_data T2 "
                        + "on T1.login = T2.login "
                        + "where T1.login = '"
                        + login + "'";
        
        Statement st = null;
        ResultSet rs = null;
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            User user = new User(rs.getString(1), rs.getString(2), 
                    rs.getDate(3), rs.getString(4));
            FileWriter fileWriter = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(user.toString());
            bufferedWriter.close();
            return user;
        } catch (SQLException e) {    
            System.out.println("Nothing was found");
            throw e;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }
    }

    public int insert(User user){
        String query = "insert into registration_data values (?, ?, ?);\n" + 
                        "insert into email_data values (?, ?);";
    
        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement st = con.prepareStatement(query)) {
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
