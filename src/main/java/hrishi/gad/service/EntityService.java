package hrishi.gad.service;

import hrishi.gad.entity.Readings;
import hrishi.gad.entity.Vehicles;

public interface EntityService {
    Vehicles[] update(Vehicles[] vehicles);
    Readings create(Readings readings);
    boolean createAlert(Readings readings);
}
