package mitigia.backend.mitigia_task.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mitigia.backend.mitigia_task.VehicleRepository;
import mitigia.backend.mitigia_task.Model.Vehicle;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository VehicleRepository;

    public List<Vehicle> getAllVehicles(){
        return VehicleRepository.findAll();
    }
}
