package com.prototype.demo.repository;

import com.prototype.demo.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula,Long> {

    boolean existsByTitulo(String titulo);

    @Query(value = "SELECT * FROM pelicula u WHERE  u.titulo = :titulo",nativeQuery = true)
    Pelicula findByTitulo(@Param("titulo") String titulo);

    @Query(value ="SELECT * FROM pelicula u WHERE  u.genero_id = ?1", nativeQuery = true)
    List<Pelicula> findByGeneroId(Long generoId);

    @Modifying
    @Query(value ="UPDATE pelicula u set calificacion=?1, fecha_de_creacion=?2, imagen=?3, titulo=?4, genero_id=?5  where u.id = ?6 ", nativeQuery = true)
    void updatePelicula(float calificacion, Date fecha_de_creacion, String imagen, String titulo, Long genero_id, Long id);

    @Query(value = "SELECT * FROM pelicula u order by fecha_de_creacion ASC",nativeQuery = true)
    List<Pelicula> findAllOrderByFechaDeCreacionAsc();

    @Query(value = "SELECT * FROM pelicula u order by fecha_de_creacion DESC",nativeQuery = true)
    List<Pelicula> findAllOrderByFechaDeCreacionDesc();


}
