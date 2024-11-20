package mitigia.backend.mitigia_task.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mitigia.backend.mitigia_task.ProjectRepository;
import mitigia.backend.mitigia_task.Model.DisplayableObject;
import mitigia.backend.mitigia_task.Model.Project;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public List<DisplayableObject> findProjectsWithQuerry(){
        List<DisplayableObject> displayableObjects = new ArrayList<>();
        List<Object[]> resultList = projectRepository.findProjectsWithQuerry();
        for (Object[] row : resultList) {
            DisplayableObject displayableObject = new DisplayableObject();
            displayableObject.setprj_id((Integer) row[0]);
            displayableObject.setlicence_plate((String) row[1]);
            displayableObject.setv_manufacturer((String) row[2]);
            displayableObject.setv_model((String) row[3]);
            displayableObject.setv_year((Integer) row[4]);
            displayableObject.setv_type((String) row[5]);
            displayableObjects.add(displayableObject);
        }

        return displayableObjects;
    }
}
