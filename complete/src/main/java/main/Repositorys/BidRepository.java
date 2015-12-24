package main.Repositorys;

import main.models.Bid;
import main.models.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vakhtanggelashvili on 12/24/15.
 */
@Transactional
public interface BidRepository  extends JpaRepository<Bid, Long> {
}
