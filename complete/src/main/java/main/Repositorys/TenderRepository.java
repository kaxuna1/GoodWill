package main.Repositorys;

import main.models.Filial;
import main.models.Tender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vakhtanggelashvili on 12/24/15.
 */
@Transactional
public interface TenderRepository extends JpaRepository<Tender, Long> {
    Page<Tender> findByActive(@Param("active")boolean active, Pageable pageable);
    List<Tender> findByActiveAndStarted(@Param("active")boolean active,@Param("started")boolean started);
}
