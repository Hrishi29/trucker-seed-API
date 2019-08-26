package hrishi.gad.controller;

import hrishi.gad.entity.Readings;
import hrishi.gad.entity.Vehicles;
import hrishi.gad.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://mocker.ennate.academy")
public class EntityController {

    @Autowired
    private EntityService entityService;

    @PutMapping("vehicles")
    public Vehicles[] update(@RequestBody Vehicles[] vehicle){
        return entityService.update(vehicle);
    }

    @PostMapping("readings")
    public Readings create(@RequestBody Readings reading){
        return entityService.create(reading);
    }
}
