package com.memoryvault.util;

import java.sql.Connection;
import java.util.Properties;

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

            host = host.replace("jdbc:mysql://", "").trim();
            port = port.trim();
            database = database.trim();
            username = username.trim();
            password = password.trim();

            String url = "jdbc:mysql://" + host + ":" + port + "/" + database
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);

            return new com.mysql.cj.jdbc.Driver().connect(url, props);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}