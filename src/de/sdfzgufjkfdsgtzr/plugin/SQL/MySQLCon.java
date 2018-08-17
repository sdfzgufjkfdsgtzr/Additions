package de.sdfzgufjkfdsgtzr.plugin.SQL;


import de.sdfzgufjkfdsgtzr.plugin.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLCon {

    private Connection connection;
    private String host, database, username, password;
    public String table;
    private int port;
    private Main plugin;

    public MySQLCon(String host, String db, String user, String pw, String table, int port, Main plugin){
        this.host = host;
        this.database = db;
        this.username = user;
        this.password = pw;
        this.table = table;
        this.port = port;

        establishCon();
        this.plugin = plugin;
    }


    private void setConnection(Connection con){
        this.connection = con;
    }

    public Connection getConnection(){
        return this.connection;
    }


    private void establishCon(){
        try{
            synchronized (this){
                if(getConnection() != null && !getConnection().isClosed()){
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password));
            }
        }catch(SQLException | ClassNotFoundException e){
            plugin.conActive = false;
        }
    }

}
