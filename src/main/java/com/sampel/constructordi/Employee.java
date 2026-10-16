package com.sampel.constructordi;

import org.springframework.stereotype.Component;

public class Employee {
    private Address address;
    public Employee(Address address){
        this.address=address;
    }
    public void getEmpAddress(){
        address.getAddress();
    }
    private double basicSalary = 30000;

        public double calculateDA() {
            return basicSalary * 0.10;  // 10% DA
        }

        public double calculateHRA() {
            return basicSalary * 0.20;  // 20% HRA
        }

        public double calculateGrossSalary() {
            return basicSalary + calculateDA() + calculateHRA();
        }
        public void display() {
            System.out.println("Basic Salary: " + basicSalary);
            System.out.println("DA: " + calculateDA());
            System.out.println("HRA: " + calculateHRA());
            System.out.println("Gross Salary: " + calculateGrossSalary());
        }

}
