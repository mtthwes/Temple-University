class Car {
    private String make;
    private String model;
    private int year;
    private double mpg;
    private double milesDriven;
    private double fuelCapacity;
    private double fuelRemaining;

    public Car(String make, String model, int year, double mpg, double milesDriven, double fuelCapacity, double fuelRemaining) {
        if (make == null || model == null || year <= 0 || mpg <= 0 || milesDriven < 0 || fuelCapacity <= 0 || fuelRemaining < 0) {
            throw new IllegalArgumentException("Invalid input for car fields");
        }
        this.make = make;
        this.model = model;
        this.year = year;
        this.mpg = mpg;
        this.milesDriven = milesDriven;
        this.fuelCapacity = fuelCapacity;
        this.fuelRemaining = Math.min(fuelCapacity, fuelRemaining);
    }

    public void fillTank(double g) {
        if (g < 0) {
            throw new IllegalArgumentException("Fuel amount cannot be negative");
        }
        fuelRemaining = Math.min(fuelRemaining + g, fuelCapacity);
    }

    public void drive(double m) {
        if (m < 0) {
            throw new IllegalArgumentException("Miles driven cannot be negative");
        }
        double fuelNeeded = m / mpg;
        if (fuelNeeded <= fuelRemaining) {
            milesDriven += m;
            fuelRemaining -= fuelNeeded;
        } else {
            System.out.println("Not enough fuel to drive that far.");
        }
    }

    public double getFuelRemaining() {
        return this.fuelRemaining;
    }

    @Override
    public String toString() {
        return "Car Details: Make = " + make + ", Model = " + model + ", Year = " + year + ", MPG = " + mpg + ", Miles Driven = " + milesDriven + ", Fuel Capacity = " + fuelCapacity + " gallons" ;
    }
}