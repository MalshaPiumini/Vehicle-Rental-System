package sample;

public class Car extends Vehicle {
    private int noOfSeats;
    private int noOfDoors;
    private String carModel; // model of the car


    public Car(String color, String make, String vacant, String plateNumber, int noOfSeats, int noOfDoors, String carModel) {
        super(color, make, vacant, plateNumber);
        this.noOfSeats = noOfSeats;
        this.noOfDoors = noOfDoors;
        this.carModel = carModel;
    }

    //getter and setter methods

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getNoOfDoors() {
        return noOfDoors;
    }

    public void setNoOfDoors(int noOfDoors) {
        this.noOfDoors = noOfDoors;
    }
}
