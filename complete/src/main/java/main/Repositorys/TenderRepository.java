package main.Repositorys;

import main.models.Filial;
import main.models.Tender;
import main.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by vakhtanggelashvili on 12/24/15.
 */
@Transactional
public interface TenderRepository extends JpaRepository<Tender, Long> {
    Page<Tender> findByActiveAndEnded(@Param("active")boolean active,@Param("ended")boolean ended, Pageable pageable);
    List<Tender> findByActiveAndStarted(@Param("active")boolean active,@Param("started")boolean started);

    @Query("select u from Tender u WHERE u.active=:active and u.started=:started and u.ended=:ended and u.startDate<:startDate")
    List<Tender> findForStart(@Param("active")boolean active, @Param("started")boolean started, @Param("ended")boolean ended, @Param("startDate")Date startDate);

    @Query("select u from Tender u WHERE u.active=:active and u.started=:started and u.ended=:ended and u.endDate<:endDate")
    List<Tender> findForEnd(@Param("active")boolean active, @Param("started")boolean started, @Param("ended")boolean ended, @Param("endDate")Date endDate);

    Page<Tender> findByStartedAndEnded(@Param("started")boolean started,@Param("ended")boolean ended, Pageable pageable);

    @Query("select u from Tender u")
    Page<Tender> finMyWond(@Param("user")User user,Pageable pageable);
}
