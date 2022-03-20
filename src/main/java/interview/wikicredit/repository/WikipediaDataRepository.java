package interview.wikicredit.repository;

import interview.wikicredit.entity.WikipediaData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikipediaDataRepository extends JpaRepository<WikipediaData, Integer> {
    Optional<WikipediaData> findByCompanyId(int companyId);
}
