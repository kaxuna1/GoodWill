package main.Repositorys;

import main.models.Filial;
import main.models.ProductRequest;
import main.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kakha on 12/15/2015.
 */
@Transactional
public interface ProductRequestRepository extends JpaRepository<ProductRequest, Long> {

    Page<ProductRequest> findByActive(@Param("active")boolean active, Pageable pageable);
    Page<ProductRequest> findByAcceptedAndSentToTender(@Param("accepted")boolean accepted,
                                                       @Param("sentToTender")boolean sentToTender, Pageable pageable);
    Page<ProductRequest> findByActiveAndAccepted(@Param("active")boolean active,@Param("accepted")boolean accepted, Pageable pageable);
    Page<ProductRequest> findByFilial(@Param("filial")Filial filial, Pageable pageable);
}
