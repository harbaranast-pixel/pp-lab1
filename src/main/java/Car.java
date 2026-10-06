import java.util.HashSet;
import java.util.Set;

public class Car {

    private String  brand;
    private String model;
    private int year;
    private String vin_code;
    private Client  owner;

    public Car(String vin_code, String brand, String model, int year, Client owner) {
        if (vin_code == null || vin_code.isBlank()) {
            throw new IllegalArgumentException("VIN code cannot be empty.");
        }
        this.vin_code = vin_code;
        setBrand(brand);
        setModel(model);
        setYear(year);
        setOwner(owner);
    }

    public int getYear() {
        return year;
    }

    public Client getOwner() {
        return owner;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getVin_code() {
        return vin_code;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.isBlank()) throw new IllegalArgumentException("Brand cannot be blank.");
        this.brand = brand;
    }

    public void setModel(String model) {
        if (model == null || model.isBlank()) throw new IllegalArgumentException("Model cannot be blank.");
        this.model = model;
    }

    public void setYear(int year) {
        if (year < 1886) throw new IllegalArgumentException("Year is invalid.");
        this.year = year;
    }

    public void setOwner(Client owner) {
        if (owner == null) throw new IllegalArgumentException("Car must have an owner.");
        this.owner = owner;
    }


}
