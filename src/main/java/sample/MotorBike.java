package sample;

public class MotorBike extends Vehicle {
    private String hasSideCar;
    private String Biketype;

    public MotorBike(String color, String make, String vacant, String plateNumber, String hasSideCar, String type) {
        super(color, make, vacant, plateNumber);
        this.hasSideCar = hasSideCar;
        this.Biketype = type;
    }

    //getters and setters
    public String getHasSideCar() {
        return hasSideCar;
    }

    public void setHasSideCar(String hasSideCar) {
        this.hasSideCar = hasSideCar;
    }

    public String getBiketype() {
        return Biketype;
    }

    public void setBiketype(String biketype) {
        Biketype = biketype;
    }
}
