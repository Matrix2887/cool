package cn.gandan.domain;

public class Manger {
    private String user;
    private String password;

    public Manger() {
    }

    public Manger(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Manger{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
