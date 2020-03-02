package sample;

public class Vehicle {
    protected String color;         // colour of the vehicle
    protected String make;          // make of the vehicle
    protected String vacant;          // if the car is not booked that means that is vacant so you have to enter y only
    protected String plateNumber;   //plate number of the vehicle

    public Vehicle(String color, String make, String vacant, String plateNumber) {
        this.color = color;
        this.make = make;
        this.vacant = vacant;
        this.plateNumber = plateNumber;
    }

    //getter and setter methods

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getVacant() {
        return vacant;
    }

    public void setVacant(String vacant) {
        this.vacant = vacant;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}
