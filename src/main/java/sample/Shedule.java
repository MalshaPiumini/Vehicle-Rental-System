package sample;

import java.util.Calendar;
import java.util.Scanner;

public class Shedule {
    private int day;
    private int month;
    private int year;
    static Scanner in= new Scanner(System.in);

    public Shedule() {

    }
//getters and setters
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        Calendar c = Calendar.getInstance();
        int year_ = c.get(Calendar.YEAR);
        int month_ = c.get(Calendar.MONTH);
        int day_ = c.get(Calendar.DATE);

        if(year==year_& month==month_){
            if(day<day_){
                System.out.println("Current date is : "+year_+"-"+month_+"-"+day_ +"\nEnter a valid day");
                while (day<day_ & day<0) {
                    System.out.println("Current date is : " + year_ + "-" + month_ + "-" + day_ + "\nEnter a valid day");
                    validateInteger();
                    day = in.nextInt();
                }

                this.day=day;
            }
        }else {
            if(day >0 & day< 31){
                if(month==2){
                    while (day>29 | day < 0){
                        System.out.println("Not a valid day. Enter day between 1 and 29");
                        validateInteger();
                        day=in.nextInt();
                    }
                        this.day=day;
                }else if (month==9 || month==4 || month==7 || month == 11) {
                    while (day > 30 || day < 0) {
                        System.out.println("Not a valid day, Enter a day below 30");
                        validateInteger();
                        day = in.nextInt();
                    }
                    this.day = day;

                }if(month==1 || month ==3 || month==5 || month==6|| month==8 || month==10||month==12) {
                    while (day > 31 || day < 0) {
                        System.out.println("Not a valid day, Enter a day below 31");
                        validateInteger();
                        day = in.nextInt();
                    }
                    this.day = day;
                }
            }else{
                    while (day <0 || day >31){
                        System.out.println("Enter proper date between 1 and 12");
                        validateInteger();
                        day = in.nextInt();
                }
                this.day=day;
            }
        }
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        Calendar c = Calendar.getInstance();
        int year_ = c.get(Calendar.YEAR);
        int month_ = c.get(Calendar.MONTH);
        int day_ = c.get(Calendar.DATE);

        if (year==year_) {
            while (month <= month_ ||month <1) {
                System.out.println("Enter a valid month,Shedule is already : "+year_+"-"+month_+"-"+day_);
                validateInteger();
                month = in.nextInt();
            }
            this.month = month;
        }else if(year>1 & year>year_){
            while (month<1 | month >13){
                System.out.println("Enter a valid month");
                validateInteger();
                month = in.nextInt();
            }
            this.month = month;
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if(year>=2019){
            this.year = year;
        }else {
            while (year<2019 ) {
                System.out.println("Enter year Valid year");
                validateInteger();
                year = in.nextInt();
            }
            this.year = year;
        }

    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void validateInteger() {
        while (!in.hasNextInt()) {
            System.out.println("Invalid input, re-enter an Integer");
            in.next();
        }
    }
    public String getDate(){
        if (day<10 & month>10){
            return "Shedule : 0"+getDay()+"-"+getMonth()+"-"+getYear();
        }else if(day>10 & month <10){
            return "Shedule : "+getDay()+"-0"+getMonth()+"-"+getYear();
        }else if(day<10 & month<10){
            return "Shedule : 0"+getDay()+"-0"+getMonth()+"-"+getYear();
        }else {
            return "Shedule : "+getDay()+"-"+getMonth()+"-"+getYear();
        }
    }
}
