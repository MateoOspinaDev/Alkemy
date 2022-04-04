package com.prototype.demo.repository;

import com.prototype.demo.model.Pelicula;
import com.prototype.demo.model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PersonajeRepository extends JpaRepository<Personaje,Long> {

    @Query(value ="SELECT * FROM personaje u WHERE  u.nombre = :nombre",nativeQuery = true)
    Personaje findByNombre(@Param("nombre") String nombre);

    @Query(value ="SELECT * FROM personaje u WHERE  u.edad = ?1", nativeQuery = true)
    List<Personaje> findByEdad(int edad);

    @Query(value ="SELECT * FROM personaje u WHERE  u.peso = ?1", nativeQuery = true)
    List<Personaje> findByPeso(float peso);


    @Modifying
    @Query(value ="UPDATE personaje u set imagen=?1,nombre=?2,edad=?1,peso=?3,historia=?4  where u.id = ?5 ", nativeQuery = true)
    void updatePersonaje(String imagen, String nombre, int edad, float peso, String historia, Long id);

}
