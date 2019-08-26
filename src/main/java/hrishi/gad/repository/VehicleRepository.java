package hrishi.gad.repository;

import hrishi.gad.entity.Vehicles;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicles, String> {
}
