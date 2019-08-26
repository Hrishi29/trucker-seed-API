package hrishi.gad.service;

import hrishi.gad.entity.Alerts;
import hrishi.gad.entity.Priority;
import hrishi.gad.entity.Readings;
import hrishi.gad.entity.Vehicles;
import hrishi.gad.exception.VehicleNotFoundException;
import hrishi.gad.repository.AlertsRepository;
import hrishi.gad.repository.ReadingsRepository;
import hrishi.gad.repository.TiresRepository;
import hrishi.gad.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EntityServiceImplementation implements EntityService{

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ReadingsRepository readingsRepository;

    @Autowired
    private TiresRepository tiresRepository;

    @Autowired
    private AlertsRepository alertsRepository;

    @Override
    @Transactional
    public Vehicles[] update(Vehicles[] vehicles) {
        for(Vehicles vehicle: vehicles) {
            vehicleRepository.save(vehicle);
        }
        return vehicles;
    }

    @Override
    @Transactional
    public Readings create(Readings readings) {
        Optional<Vehicles> existing = vehicleRepository.findById(readings.getVin());
        if (existing.isPresent()) {
            Optional<Readings> existing1 = readingsRepository.findById(readings.getVin());
            existing1.ifPresent(value -> tiresRepository.delete(value.getTires()));
            tiresRepository.save(readings.getTires());
            readingsRepository.save(readings);
            if(createAlert(readings));
                return readings;
        }
        throw new VehicleNotFoundException("Cannot input Readings. Vehicle with id: " + readings.getVin() + " not found");


    }

    @Override
    @Transactional
    public boolean createAlert(Readings readings) {
        List<Alerts> existing = alertsRepository.findByVin(readings.getVin());
        if(!existing.isEmpty())
        {
            for(Alerts alert : existing){
                alertsRepository.delete(alert);
            }
        }
        if(readings.getEngineRpm() > vehicleRepository.findById(readings.getVin()).get().getRedlineRpm()){
            Alerts alert = new Alerts();
            alert.setVin(readings.getVin());
            alert.setPriority(Priority.HIGH);
            alert.setMessage("Engine RPM over Readline RPM");
            alertsRepository.save(alert);
        }
        if(readings.getFuelVolume() < 0.1*(vehicleRepository.findById(readings.getVin()).get().getMaxFuelVolume())){
            Alerts alert = new Alerts();
            alert.setVin(readings.getVin());
            alert.setPriority(Priority.MEDIUM);
            alert.setMessage("Fuel Volume is less than 10% of Maximum Fuel Volume");
            alertsRepository.save(alert);
        }
        if(readings.isEngineCoolantLow() || readings.isCheckEngineLightOn()){
            Alerts alert = new Alerts();
            alert.setVin(readings.getVin());
            alert.setPriority(Priority.LOW);
            alert.setMessage("Engine Coolant Low or Engine Light On");
            alertsRepository.save(alert);
        }
        if((readings.getTires().getFrontLeft() < 32 || readings.getTires().getFrontRight() < 32 || readings.getTires().getRearLeft() < 32 || readings.getTires().getRearRight() < 32) || (readings.getTires().getFrontLeft() > 36 || readings.getTires().getFrontRight() > 36 || readings.getTires().getRearLeft() > 36 || readings.getTires().getRearRight() > 36)){
            Alerts alert = new Alerts();
            alert.setVin(readings.getVin());
            alert.setPriority(Priority.LOW);
            alert.setMessage("Check Tire Pressure");
            alertsRepository.save(alert);
        }
        return true;
    }
}
