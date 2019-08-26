package hrishi.gad.repository;

import hrishi.gad.entity.Alerts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlertsRepository extends CrudRepository<Alerts, String> {
    List<Alerts> findByVin(String vin);
}
