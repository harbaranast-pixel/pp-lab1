import java.util.HashSet;
import java.util.Set;

public class Car {

    private String brand;
    private String model;
    private int year;
    private String vinCode;
    private Client owner;

    public Car(String vinCode, String brand, String model, int year, Client owner) {
        if (vinCode == null || vinCode.isBlank()) {
            throw new IllegalArgumentException("VIN code cannot be empty.");
        }
        this.vinCode = vinCode;
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

    // Виправлено: getVin_code -> getVinCode
    public String getVinCode() {
        return vinCode;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.isBlank())
            throw new IllegalArgumentException("Brand cannot be blank.");
        this.brand = brand;
    }

    public void setModel(String model) {
        if (model == null || model.isBlank())
            throw new IllegalArgumentException("Model cannot be blank.");
        this.model = model;
    }

    public void setYear(int year) {
        if (year < Constants.MIN_MANUFACTURING_YEAR)
            throw new IllegalArgumentException("Year is invalid.");
        this.year = year;
    }

    public void setOwner(Client owner) {
        if (owner == null)
            throw new IllegalArgumentException("Car must have an owner.");
        this.owner = owner;
    }
}