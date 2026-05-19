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

            dataSource.setServerName(host);
            dataSource.setPortNumber(Integer.parseInt(port));
            dataSource.setDatabaseName(database);
            dataSource.setUser(username);
            dataSource.setPassword(password);

            dataSource.setUseSSL(false);
            dataSource.setAllowPublicKeyRetrieval(true);
            dataSource.setServerTimezone("UTC");

            return dataSource.getConnection();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}