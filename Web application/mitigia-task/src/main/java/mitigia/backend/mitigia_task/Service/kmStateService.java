package mitigia.backend.mitigia_task.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mitigia.backend.mitigia_task.kmStateRepository;
import mitigia.backend.mitigia_task.Model.DisplayableObject;
import mitigia.backend.mitigia_task.Model.Project;
import mitigia.backend.mitigia_task.Model.kmState;

import java.time.LocalDate;
import java.util.List;

@Service
public class kmStateService {

    @Autowired
    private kmStateRepository kmStateRepository;

    @Autowired
    private ProjectService projectService;

    public kmStateService(){
        projectService = new ProjectService();
    }


    public List<DisplayableObject> getProjectsForAngular(){
        List<DisplayableObject> extendedList = projectService.findProjectsWithQuerry();
        for(int i = 0; i < extendedList.size(); i++){
            extendedList.get(i).setkm_state(LastKm(extendedList.get(i).getlicence_plate()));
            extendedList.get(i).setsave_date(getLastStateDate(extendedList.get(i).getlicence_plate()));
        }
        return extendedList;
    }



    public List<kmState> getAllkmStates(){
        return kmStateRepository.findAll();
    }

    public String addKmState(String license_plate, Integer km_state){
        LocalDate currentDate = LocalDate.now();
        List<Project> projList = projectService.getAllProjects();
        boolean containesLicense = false;
        for (Project proj : projList) {
            if(proj.getlicence_plate().equals(license_plate)){
                containesLicense = true;
                break;
            }
        }
        if(!containesLicense){
            return "Unkown licence plate";
        }

        LocalDate lastSave = getLastStateDate(license_plate);

        if(lastSave != null){
            if(currentDate.isBefore(lastSave)){
                return "Bad date";
            }
            if(km_state <= LastKm(license_plate)){
                return "km can't be smaller than the last one";
            }

        }




        kmState newKmState = new kmState(license_plate, km_state, currentDate);
        try{
            kmStateRepository.saveAndFlush(newKmState);
        }
        catch(Exception x){
            return x.getMessage();
        }
        return "Success";

    }

    private LocalDate getLastStateDate(String licensePlate){
        List<kmState> kmStateList = kmStateRepository.findAll();
        LocalDate lastState = null;
        for (kmState kmState : kmStateList) {
            if(kmState.getlicence_plate().equals(licensePlate)){
                lastState = kmState.getsave_date();
                break;
            }
        }

        return lastState;
    }

    private Integer LastKm(String licensePlate){
        List<kmState> kmStateList = kmStateRepository.findAll();
        Integer greatestKm = 0;
        for (kmState kmState : kmStateList) {
            if(kmState.getlicence_plate().equals(licensePlate) && kmState.getkm_state() > greatestKm){
                greatestKm = kmState.getkm_state();
            }
        }
        return greatestKm;
    }
    


}
