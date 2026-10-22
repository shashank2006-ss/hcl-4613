package com.abes.hrms.model;

import com.abes.hrms.repository.EmpRepImp;
import com.abes.hrms.repository.EmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class DemoEmployee {
    @Autowired
    private EmpRepImp emprepo;
    public int add() {
        return emprepo.add(20,90);
    }
    public double squareRoot(){
        return emprepo.squareroot(49);

    }
    public int gtn(){
        return emprepo.gtn(10,30);
    }

}
