package Switchcase;

import java.util.Scanner;
import java.util.*;
public class Switchcaseexpression {
    static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int day=sc.nextInt();
        String res=switch (day){
            case 1->"monday";
            case 2->"tue";
            case 3->"wed";
            case 4->"thu";
            case 5->"fri";
            case 6->"sat";
            case 7->"sun";
            default -> "Invalid day";
        };
        System.out.println(res);
    }
}
