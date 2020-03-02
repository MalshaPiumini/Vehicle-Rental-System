package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
    private final StringProperty plateNumber;
    private final StringProperty vehicle_type;
    private final StringProperty color;
    private final StringProperty availability;
    private final StringProperty make;

    public Product(String plateNumber, String vehicleType, String color, String availability, String make) {
        this.plateNumber = new SimpleStringProperty(plateNumber);
        this.vehicle_type = new SimpleStringProperty(vehicleType);
        this.color = new SimpleStringProperty(color);
        this.availability = new SimpleStringProperty(availability);
        this.make = new SimpleStringProperty(make);
    }


//getters and setters
    public String getPlateNumber() {
        return plateNumber.get();
    }

    public StringProperty plateNumberProperty() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber.set(plateNumber);
    }

    public String getVehicleType() {
        return vehicle_type.get();
    }

    public StringProperty vehicleTypeProperty() {
        return vehicle_type;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicle_type.set(vehicleType);
    }

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getAvailability() {
        return availability.get();
    }

    public StringProperty availabilityProperty() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability.set(availability);
    }

    public String getMake() {
        return make.get();
    }

    public StringProperty makeProperty() {
        return make;
    }

    public void setMake(String make) {
        this.make.set(make);
    }
}
