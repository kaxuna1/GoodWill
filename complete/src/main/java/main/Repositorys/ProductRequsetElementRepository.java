
package main.Repositorys;

import main.models.ProductRequest;
import main.models.ProductRequestElement;
import main.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kakha on 12/15/2015.
 */
@Transactional
public interface ProductRequsetElementRepository extends JpaRepository<ProductRequestElement, Long> {
}
