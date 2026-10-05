public class Client {
    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Client phone cannot be blank.");
        }
        this.phone = phone;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Client name cannot be blank.");
        }
        this.name = name;
    }

    public Client(String name, String phone) {
        setName(name);
        setPhone(phone);
    }

}



