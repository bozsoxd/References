package mitigia.backend.mitigia_task.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import mitigia.backend.mitigia_task.Model.Vehicle;
import mitigia.backend.mitigia_task.Service.VehicleService;

@RestController
public class VehicleController {
    private VehicleService vehicleService;

   @Autowired 
    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles")
    public List<Vehicle> getAllVehicles(){
        return vehicleService.getAllVehicles();
    }
}
