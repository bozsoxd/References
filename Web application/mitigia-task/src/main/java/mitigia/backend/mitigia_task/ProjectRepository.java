package mitigia.backend.mitigia_task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mitigia.backend.mitigia_task.Model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    @Query("SELECT p.prj_id, p.licence_plate , v.v_manufacturer, v.v_model, v.v_year, v.v_type FROM Project p INNER JOIN Vehicle v ON p.v_id = v.v_id")
    List<Object[]> findProjectsWithQuerry();


}
