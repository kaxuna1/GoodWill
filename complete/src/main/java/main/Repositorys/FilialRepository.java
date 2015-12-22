package main.Repositorys;

import main.models.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kakha on 11/24/2015.
 */
@Transactional
public interface FilialRepository extends JpaRepository<Filial, Long> {
}
