package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookinData {
    private  StringProperty plateNumber;
    private  StringProperty vehicle_type;


    public BookinData(String plateNumber, String vehicle_type) {
        this.plateNumber =  new SimpleStringProperty(plateNumber);
        this.vehicle_type =  new SimpleStringProperty(vehicle_type);
    }
//getters and setters
    public String getVehicle_type() {
        return vehicle_type.get();
    }

    public StringProperty vehicle_typeProperty() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type.set(vehicle_type);
    }

    public String getPlateNumber() {
        return plateNumber.get();
    }

    public StringProperty plateNumberProperty() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber.set(plateNumber);
    }


}
