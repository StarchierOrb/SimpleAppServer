package me.starchier.userdata;

import java.util.List;

public class UserInformation {
    private final String name;
    private final String sex;
    private final String phoneNumber;
    private final String mailAddress;
    private final int age;
    private final List<UserAddress> addresses;
    private final UserDetails userDetails;

    public UserInformation(String name, String sex, String phoneNumber, String mailAddress, int age, List<UserAddress> addresses, UserDetails userDetails) {
        this.name = name;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.mailAddress = mailAddress;
        this.age = age;
        this.addresses = addresses;
        this.userDetails = userDetails;
    }

    public UserInformation() {
        this.name = null;
        this.sex = null;
        this.phoneNumber = null;
        this.mailAddress = null;
        this.age = -1;
        this.addresses = null;
        this.userDetails = null;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public int getAge() {
        return age;
    }

    public List<UserAddress> getAddresses() {
        return addresses;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
