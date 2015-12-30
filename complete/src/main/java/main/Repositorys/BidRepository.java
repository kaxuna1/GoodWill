package main.Repositorys;

import main.models.Bid;
import main.models.Filial;
import main.models.Product;
import main.models.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vakhtanggelashvili on 12/24/15.
 */
@Transactional
public interface BidRepository  extends JpaRepository<Bid, Long> {
    List<Bid> findByTender(@Param("tender")Tender tender);
    List<Bid> findByTenderAndProductOrderByBidAsc(@Param("tender")Tender tender,@Param("product")Product product);
}
