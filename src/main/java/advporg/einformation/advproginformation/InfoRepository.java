package advporg.einformation.advproginformation;

import advporg.einformation.advproginformation.model.Information;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface InfoRepository extends MongoRepository<Information, String> {
    Information findInformationByMonuCode(String monuCode);
    List<Information> findInformationByBuildYear(Date buildYear);
    List<Information> findAllByOrderByScoreAsc();
    List<Information> findAllByOrderByBuildYearDesc();
    List<Information> findAllByOrderByBuildYearAsc();
}
