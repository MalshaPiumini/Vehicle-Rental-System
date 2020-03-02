package sample;

import View.FirstView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Scanner;

public class Test  extends Application {
    static Scanner input_1 = new Scanner(System.in);


    //validating the integer inputs
    public static void validateInteger() {
        while (!input_1.hasNextInt()) {
            System.out.println("Invalid input, re-enter an Integer");
            input_1.next();
        }
    }

    public static void main(String[] args) {
        WestminsterRentalVehicleManager wrm = new WestminsterRentalVehicleManager();
        int sentinel = 1;
        while (sentinel > 0) {
            System.out.println("           \n Welcome to Car rental System \n" +
                    "choose your option :\n" +
                    "01. Add a vehicle \n" +
                    "02. Delete a vehicle\n" +
                    "03. Edit details of Vehicles\n" +
                    "04. Print the list of vehicles\n" +
                    "05. GUI\n" +
                    "06. Book a vehicle\n" +
                    "07. Generate Report\n" +
                    "08. Quit");

            System.out.print(">>>");
            validateInteger();
            int option_01 = input_1.nextInt();

            switch (option_01) {
                //Add new vehicle to the system
                case 1:
                    //show remaining spaces in the database
                    wrm.showFreeSpacesLeft();
                    wrm.addVehicle();
                    break;

                case 2:
                    //delete a car using the plate number
                    wrm.printListOfVehicles();
                    System.out.println("\nEnter the plate number of the vehicle you want to delete:\n");
                    Scanner sc = new Scanner(System.in);
                    String toDelete = sc.next();
                    wrm.deleteVehicle(toDelete);
                    wrm.showFreeSpacesLeft();
                    break;

                case 3:
                    System.out.println("Enter the plate number of the Vehicle to Edit");
                    wrm.printListOfVehicles();
                    String pn = input_1.next();
                    wrm.edit(pn);
                    break;

                case 4:
                    wrm.printListOfVehicles();
                    break;

                case 5:
                    //GUI
                    launch(args);
                    break;
                case 6:
                    //book a vehicle
                    wrm.bookVehicle();
                    break;

                case 7:
                    wrm.generateReport();
                    break;

                case 8:
                    System.out.println("Do you want to exit? \n01. Enter 01 to Quiet\n02. Enter 02 Cancel ");
                    validateInteger();
                    int exitOption = input_1.nextInt();
                    if (exitOption == 1) {
                        System.exit(0);
                    } else if (exitOption == 2) {
                        continue;
                    } else {
                        System.out.println("Wrong Input");
                        continue;
                    }

                default:
                    System.out.println("Wrong Input");
                    continue;
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        FirstView f1 =new FirstView();
        f1.MainFx(stage);
    }
}