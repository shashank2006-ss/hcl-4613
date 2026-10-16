package Interface;
import java.util.*;
public class maps {
    static void main(String[] args) {
        Map<Integer,String> mp=new HashMap<>();
        mp.put(1,"sumit");
        mp.put(2,"sujit");
        mp.put(3,"shashank");
        mp.put(4,"abhis");
        if(mp.containsValue("abhis")){
            System.out.println("YEs");

        }
        else{
            System.out.println("no");
        }
        System.out.println(mp);
        Map<String,Integer> ma=new HashMap<>();
        ma.put("mohit",20);
        ma.put("shika",30);
        ma.put("kkiii",40);
        System.out.println(ma);
        System.out.println(ma.get("mohit"));
        ma.forEach((k,v)->System.out.println(k+":"+v));

    }
}
