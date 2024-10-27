package User;

public class User {
    public enum Identity {
        NO_IDENTITY, STUDENT, TEACHER, ADMIN
    }

    public static final String[] IDENTITY = {
            "Student", "Teacher", "Administrator"
    };

    protected Identity identity;
    protected String id;
    protected String name;
    protected String password;

    public User() {
        this.identity = Identity.NO_IDENTITY;
        this.id = "000000";
        this.name = "Default";
        this.password = "000000";
    }

    public User(Identity identity, String id, String name, String password) {
        this.identity = identity;
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Identity getIdentity() {
        return this.identity;
    }

    public String getIdentityString() {
        return switch (this.getIdentity()) {
            case STUDENT -> User.IDENTITY[0];
            case TEACHER -> User.IDENTITY[1];
            case ADMIN -> User.IDENTITY[2];
            default -> "";
        };
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}