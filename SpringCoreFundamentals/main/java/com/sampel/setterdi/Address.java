package com.sampel.setterdi;

public class Address {
    private Address address;

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void displauAddress() {
        System.out.println(getAddress());
    }
}
