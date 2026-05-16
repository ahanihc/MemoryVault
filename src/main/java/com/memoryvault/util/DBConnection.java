package com.memoryvault.util;

import java.sql.Connection;
import com.mysql.cj.jdbc.MysqlDataSource;

public class DBConnection {

    public static Connection getConnection() {

        try {
            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String database = System.getenv("MYSQLDATABASE");
            String username = System.getenv("MYSQLUSER");
            String password = System.getenv("MYSQLPASSWORD");

            if (host == null || port == null || database == null || username == null || password == null) {
                host = "localhost";
                port = "3306";
                database = "memoryvault_db";
                username = "root";
                password = "Ganavi2006#";
            }

            MysqlDataSource dataSource = new MysqlDataSource();

            dataSource.setURL(
                "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
            );

            dataSource.setUser(username);
            dataSource.setPassword(password);

            return dataSource.getConnection();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}