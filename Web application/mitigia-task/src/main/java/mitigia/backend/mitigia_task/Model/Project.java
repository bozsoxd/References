package mitigia.backend.mitigia_task.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prj_id;
    private String licence_plate;
    private Long v_id;
    private LocalDate start_date;
    private LocalDate end_date;
    private Integer start_odo;
    private Integer last_odo;

    public Project(Integer prj_id, String licence_plate, Long v_id, LocalDate start_date, LocalDate end_date, Integer start_odo, Integer last_odo) {
        this.prj_id = prj_id;
        this.licence_plate = licence_plate;
        this.v_id = v_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.start_odo = start_odo;
        this.last_odo = last_odo;
    }

    public Project(){};

    public Integer getprj_id() {
        return prj_id;
    }

    public String getlicence_plate() {
        return licence_plate;
    }

    public Long getv_id() {
        return v_id;
    }

    public LocalDate getstart_date() {
        return start_date;
    }

    public LocalDate getend_date() {
        return end_date;
    }

    public Integer getstart_odo() {
        return start_odo;
    }

    public Integer getlast_odo() {
        return last_odo;
    }
}
