package com.epam.courses.java.final_project.dao.entity;

/**
 * The {@code User} class represent corresponding entity from database.
 * User entity can have only one role, which define abilities of this account.
 * <p>
 *     ~ {@code Customer} role - base role of any {@code User}
 * <p>
 *     ~ {@code Manager} role gives ability to accept {@code Request}'s
 * <p>
 *     ~ {@code Admin} can give manager privileges to user
 * <p>
 * Some fields are have to be unique in db: id, login, phoneNumber, email
 *
 * @author Kostiantyn Kolchenko
 * */
public class User {
    
    long id;  // value ether equal to corresponding db value, or 0
    String login;
    String password;
    String phoneNumber;
    String email;
    Role role;

    public User() {
        role = Role.Customer;
    }

    public User(long id, String login, String password, String phoneNumber, String email, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    public User(long id, String login, String password, String phoneNumber, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        role = Role.Customer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(int value) {
        this.role = Role.getRole(value);
    }

    public enum Role{
        Customer(1), Manager(2), Admin(3);

        int dbValue;

        Role(int num) {
            dbValue = num;
        }

        public int getValue() {
            return dbValue;
        }

        public static Role getRole(int num){
            if (num == 3)
                return Admin;
            else if (num == 2)
                return Manager;
            return Customer;
        }
    }

}
