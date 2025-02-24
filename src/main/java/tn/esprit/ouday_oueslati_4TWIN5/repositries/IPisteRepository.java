package tn.esprit.ouday_oueslati_4TWIN5.repositries;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Color;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Piste;

import java.awt.print.Pageable;
import java.util.List;


public interface IPisteRepository extends JpaRepository<Piste, Long> {

    @Query("SELECT p FROM Piste p WHERE " +
            "(:name IS NULL OR p.namePiste LIKE %:name%) AND " +
            "(:color IS NULL OR p.color = :color) AND " +
            "(:minLength IS NULL OR p.length >= :minLength) AND " +
            "(:maxSlope IS NULL OR p.slope <= :maxSlope)")
    List<Piste> filterPistes(
            @Param("name") String name,
            @Param("color") Color color,
            @Param("minLength") Integer minLength,
            @Param("maxSlope") Integer maxSlope
    );

}
