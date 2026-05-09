package model;

import com.github.javafaker.Faker;

public class Courier {


    private static String login;
    private static String password;
    private static String firstName;

//    public model.Courier(String login, String password, String firstName) {
//        this.login = login;
//        this.password = password;
//        this.firstName = firstName;
//    }

    public Courier() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

//        создаем данные для курьера с помощью Faker
    public void generateRandomCredentials() {
        Faker user = new Faker();
        login = user.name().lastName() + System.currentTimeMillis();
        password = user.regexify("[0-9]{5}");
        firstName = user.name().lastName();
    }
}
