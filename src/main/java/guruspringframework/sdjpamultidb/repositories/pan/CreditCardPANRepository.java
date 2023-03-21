package guruspringframework.sdjpamultidb.repositories.pan;

import guruspringframework.sdjpamultidb.domain.pan.CreditCardPAN;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CreditCardPANRepository extends JpaRepository<CreditCardPAN, Long> {
    Optional<CreditCardPAN> findByCreditCardId(Long id);
}
