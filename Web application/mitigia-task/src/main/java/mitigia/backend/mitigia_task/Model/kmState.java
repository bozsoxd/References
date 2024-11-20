package mitigia.backend.mitigia_task.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "kmState")
public class kmState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer km_id;
    private String licence_plate;
    private Integer km_state;
    private LocalDate save_date;

    public kmState(){};

    public kmState(String licence_plate, Integer km_state, LocalDate save_date) {
        this.licence_plate = licence_plate;
        this.km_state = km_state;
        this.save_date = save_date;
    }

    public Integer getkm_id(){
        return km_id;
    }

    public String getlicence_plate(){
        return licence_plate;
    }

    public Integer getkm_state(){
        return km_state;
    }

    public LocalDate getsave_date(){
        return save_date;
    }

}