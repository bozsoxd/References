package mitigia.backend.mitigia_task.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import mitigia.backend.mitigia_task.Model.Project;
import mitigia.backend.mitigia_task.Service.ProjectService;

@RestController
public class ProjectController {
    private ProjectService projectService;

    @Autowired
    public ProjectController (ProjectService projectService){
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

}
