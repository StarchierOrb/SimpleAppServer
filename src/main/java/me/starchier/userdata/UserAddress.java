package me.starchier.userdata;

public class UserAddress {
    private final String province;
    private final String city;
    private final String town;
    private final String detailedAddress;
    private final String phoneNumber;
    private final String name;

    public UserAddress(String province, String city, String town, String detailedAddress, String phoneNumber, String name) {
        this.province = province;
        this.city = city;
        this.town = town;
        this.detailedAddress = detailedAddress;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getTown() {
        return town;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }
}
