package mitigia.backend.mitigia_task.Model;

import java.time.LocalDate;

import io.micrometer.common.lang.Nullable;

public class DisplayableObject {
    private Integer prj_id;
    private String licence_plate;
    private String v_manufacturer;
    private String v_model;
    private Integer v_year;
    private String v_type;

    @Nullable
    private Integer km_state;
    @Nullable
    private LocalDate save_date;

    public DisplayableObject(Integer prj_id, String licence_plate, Integer v_year, String v_manufacturer, String v_model, String v_type, Integer km_state, LocalDate save_date) {
        this.prj_id = prj_id;
        this.licence_plate = licence_plate;
        this.v_year = v_year;
        this.v_manufacturer = v_manufacturer;
        this.v_model = v_model;
        this.v_type = v_type;
        this.km_state = km_state;
        this.save_date = save_date;
    }

    public DisplayableObject(){}

    public Integer getprj_id() {
        return prj_id;
    }
    public void setprj_id(Integer value) {
        prj_id = value;
    }

    public String getlicence_plate() {
        return licence_plate;
    }
    public void setlicence_plate(String value) {
        licence_plate = value;
    }

    public Integer getv_year() {
        return v_year;
    }
    public void setv_year(Integer value) {
        v_year = value;
    }

    public String getv_manufacturer() {
        return v_manufacturer;
    }
    public void setv_manufacturer(String value) {
        v_manufacturer = value;
    }

    public String getv_model() {
        return v_model;
    }
    public void setv_model(String value) {
        v_model = value;
    }

    public String getv_type() {
        return v_type;
    }
    public void setv_type(String value) {
        v_type = value;
    }
    
    public Integer getkm_state() {
        return km_state;
    }
    public void setkm_state(Integer value) {
        km_state = value;
    }

    public LocalDate getsave_date() {
        return save_date;
    }
    public void setsave_date(LocalDate value) {
        save_date = value;
    }

}
