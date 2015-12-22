package main.Repositorys;

import main.models.Filial;
import main.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kakha on 11/24/2015.
 */
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select u from Product u WHERE u.name LIKE CONCAT('%',:name,'%')")
    Page<Product> findByNameAndPage(@Param("name")String name,Pageable pageable);

    Page<Product> findByFilials(@Param("filial")Filial filial, Pageable pageable);
}
