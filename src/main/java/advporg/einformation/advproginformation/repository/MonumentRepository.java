package advporg.einformation.advproginformation.repository;

import advporg.einformation.advproginformation.model.Monument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface MonumentRepository extends MongoRepository<Monument, String> {
    Monument findMonumentByMonuCode(String monuCode);
    List<Monument> findMonumentByBuildYear(Date buildYear);
    List<Monument> findAllByOrderByScoreDesc();
    List<Monument> findAllByOrderByBuildYearDesc();
    List<Monument> findAllByOrderByBuildYearAsc();
}
