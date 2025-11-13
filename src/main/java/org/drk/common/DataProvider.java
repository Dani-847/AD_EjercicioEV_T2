package org.drk.common;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Proveedor de la fuente de datos (DataSource) para la conexión a la base de datos MySQL.
 * Utiliza el patrón singleton para asegurar que solo exista una instancia de DataSource.
 */

public class DataProvider {

    private static DataSource dataSource;

    private DataProvider() {}

    public static DataSource getDataSource() {
        if(dataSource == null) {
            var ds = new MysqlDataSource();
            ds.setURL("jdbc:mysql://localhost:3306/ad");
            ds.setUser("root");
            ds.setPassword("root");

            dataSource = ds;
        }
        return dataSource;
    }

}
