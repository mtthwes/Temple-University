public class CarsMain {
    public static void main(String[] args) {
       
        Car[] cars = new Car[7];
        cars[0] = new Car("Chevrolet", "Impala", 1967, 15.0, 100000, 18, 10);
        cars[1] = new Car("Toyota", "Corolla", 2015, 30.0, 50000, 13, 7);
        cars[2] = new Car("Honda", "Civic", 2020, 35.0, 20000, 12, 6);
        cars[3] = new Car("Mazda", "3", 2018, 24.0, 67600, 32, 18);
        cars[4] = new Car("Ford", "Fusion", 2018, 25.0, 30000, 15, 8); // creates a new Car object
        cars[5] = new Car("BMW", "X5", 2021, 20.0, 10000, 18, 9); // creates a new Car object
        cars[6] = new Car("Nissan", "Altima", 2019, 28.0, 40000, 14, 7); // creates a new Car object


       
            cars[0].drive(10);
            cars[0].fillTank(3);
            System.out.println(cars[0]); 
            System.out.println("Fuel Remaining = " + cars[0].getFuelRemaining());
            System.out.println();
            
            cars[1].drive(50); 
            cars[1].fillTank(2); 
            System.out.println(cars[1]);
            System.out.println("Fuel Remaining = " + cars[1].getFuelRemaining());
            System.out.println();

            cars[2].drive(30); 
            cars[2].fillTank(4); 
            System.out.println(cars[2]);
            System.out.println("Fuel Remaining = " + cars[2].getFuelRemaining());
            System.out.println();
            
            // makes the rest the same drive and fill
            for (int i = 3; i <= 5; i++) {
                cars[i].drive(20); 
                cars[i].fillTank(3); 
                System.out.println(cars[i]);
                System.out.println("Fuel Remaining = " + cars[i].getFuelRemaining()); 
                System.out.println();
            }
            
            // test if it's negative
            cars[6].drive(-5); 
            cars[6].fillTank(4); 
            System.out.println(cars[6]);
            System.out.println("Fuel Remaining = " + cars[6].getFuelRemaining());
            System.out.println();

            }

        }
    
