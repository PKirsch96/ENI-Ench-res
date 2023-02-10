package fr.eni.eniD2WM147.dal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionProvider {
    private static DataSource dataSource;

    static {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
            Connection cnx = dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    méthode permettant d'obtenir une connection issue du pool de connection
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
