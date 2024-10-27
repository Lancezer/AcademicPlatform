package User;

public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(Identity identity, String id, String name, String password) {
        this.identity = identity;
        this.id = id;
        this.name = name;
        this.password = password;
    }
}