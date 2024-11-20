package mitigia.backend.mitigia_task.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long v_id;
    private Integer v_year;
    private String v_manufacturer;
    private String v_model;
    private String v_type;
    private String fuel_type;
    private Integer wltp_energy_consumption;
    private Integer nedc_energy_consumption;

    public Vehicle() { }

    public Vehicle(Long v_id, String v_manifactured, String v_model, String v_type, String fuel_type, Integer wltp_energy_consumption, Integer nedc_energy_consumption){
        this.v_id = v_id;
        this.v_manufacturer =v_manifactured;
        this.v_model = v_model;
        this.v_type = v_type;
        this.fuel_type = fuel_type;
        this.wltp_energy_consumption = wltp_energy_consumption;
        this.nedc_energy_consumption = nedc_energy_consumption;
    }

    public Long getv_id() {
        return v_id;
    }
    
    public String getv_manufacturer() {
        return v_manufacturer;
    }
    
    public Integer getv_year() {
        return v_year;
    }

    public String getv_model() {
        return v_model;
    }
    
    public String getv_type() {
        return v_type;
    }
    
    public String getfuel_type() {
        return fuel_type;
    }
    
    public Integer getwltp_energy_consumption() {
        return wltp_energy_consumption;
    }
    
    public Integer getnedc_energy_consumption() {
        return nedc_energy_consumption;
    }


}