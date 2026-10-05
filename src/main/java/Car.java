import java.util.HashSet;
import java.util.Set;

public class Car {
    private static final Set<String> USED_VIN_CODES = new HashSet<>();

    private String  brand;
    private String model;
    private int year;
    private String vin_code;
    private Client  owner;

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

    public boolean isRegistered() {
        return vin_code != null && USED_VIN_CODES.contains(vin_code);
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public void setVin_code(String vin_code) {
        String validVin = vin_code == null ? null : vin_code.trim();

        if (validVin == null || validVin.isEmpty()) {
            throw new IllegalArgumentException("VIN code cannot be empty");
        }

        if (validVin.equals(this.vin_code)) {
            return;
        }

        if (USED_VIN_CODES.contains(validVin)) {
            throw new IllegalArgumentException("VIN code must be unique within the service");
        }

        if (this.vin_code != null) {
            USED_VIN_CODES.remove(this.vin_code);
        }

        this.vin_code = validVin;
        USED_VIN_CODES.add(validVin);
    }

    public void setYear(int year) {
        this.year = year;
    }


}
