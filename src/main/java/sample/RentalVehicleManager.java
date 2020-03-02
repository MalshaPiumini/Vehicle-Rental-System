package sample;

public interface RentalVehicleManager {
    public abstract void addCar(Car c);     //add a car
    public abstract void addMotorBike(MotorBike m); //add a bike
    public abstract void deleteVehicle(String a); //delete a vehicle
    public abstract void printListOfVehicles(); //get the list of vehicles
    public abstract void edit(String PN); //edit the vehicle
    public abstract void bookVehicle(); //book a vehicle

}
