package org.drk.pelicula;

import org.drk.common.DAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del DAO para la entidad Pelicula.
 * Proporciona métodos para guardar, actualizar, eliminar y consultar películas en la base de datos.
 */

public class PeliculaDAO implements DAO<Pelicula> {

    private DataSource dataSource;


    /**
     * Mapea un ResultSet a un objeto Pelicula.
     */
    private Pelicula mapper(ResultSet rs) throws SQLException {
        return new Pelicula(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("genero"),
                rs.getInt("año")
        );
    }

    public PeliculaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Guarda una nueva película en la base de datos.
     */
    @Override
    public Optional<Pelicula> save(Pelicula pelicula) {
        String sql = "INSERT INTO `pelicula` (`titulo`, `genero`, `año`) VALUES (?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            pst.setString(1, pelicula.getTitulo());
            pst.setString(2, pelicula.getGenero());
            pst.setInt(3, pelicula.getAño());

            int res = pst.executeUpdate();
            if (res > 0) {
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) {
                        pelicula.setId(keys.getInt(1));
                    }
                }
                return Optional.of(pelicula);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /**
     * Actualiza una película existente en la base de datos.
     */
    @Override
    public Optional<Pelicula> update(Pelicula pelicula) {
        String sql = "UPDATE `pelicula` SET `titulo` = ?, `genero` = ?, `año` = ? WHERE `id` = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)
        ) {
            pst.setString(1, pelicula.getTitulo());
            pst.setString(2, pelicula.getGenero());
            pst.setInt(3, pelicula.getAño());
            pst.setInt(4, pelicula.getId());

            int res = pst.executeUpdate();
            if (res > 0) {
                return Optional.of(pelicula);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Pelicula> delete(Pelicula pelicula) {
        return Optional.empty();
    }

    /**
     * Obtiene todas las películas de la base de datos.
     */
    @Override
    public List<Pelicula> findAll() {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT * FROM `pelicula`";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                peliculas.add(mapper(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return peliculas;
    }

    @Override
    public Optional<Pelicula> findById(Integer id) {
        return Optional.empty();
    }
}
