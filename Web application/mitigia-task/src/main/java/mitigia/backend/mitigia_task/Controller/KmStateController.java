package mitigia.backend.mitigia_task.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mitigia.backend.mitigia_task.Model.DisplayableObject;
import mitigia.backend.mitigia_task.Model.kmState;
import mitigia.backend.mitigia_task.Service.kmStateService;

@RestController
public class KmStateController {
    private kmStateService kmStateService;

    @Autowired
    public KmStateController(kmStateService kmStateService){
        this.kmStateService = kmStateService;
    }

    @PostMapping("/km")
    public String insertData(@RequestBody kmState entity) {
        System.out.println(entity.getlicence_plate());
        System.out.println(entity.getkm_state());
       String response = kmStateService.addKmState(entity.getlicence_plate(), entity.getkm_state());
        return response;
    }

    @GetMapping("/kmStates")
    public List<kmState> getAllkmState(){
        return kmStateService.getAllkmStates();
    }

    @GetMapping("/display")
    public List<DisplayableObject> getDisplay(){
        return kmStateService.getProjectsForAngular();
    }

}
