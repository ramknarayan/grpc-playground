package org.example.sec03;

import org.example.models.sec03.Crendentials;
import org.example.models.sec03.Email;
import org.example.models.sec03.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec08OneOf {
    private static final Logger log = LoggerFactory.getLogger((Lec08OneOf.class));

    public static void main(String[] args){
        var email = Email.newBuilder().setAddress("sam@gmail.com").setPassword("admin").build();
        var phone = Phone.newBuilder().setNumber(123131).build();
        login(Crendentials.newBuilder().setEmail(email).build());
        login(Crendentials.newBuilder().setPhone(phone).build());
        login(Crendentials.newBuilder().setPhone(phone).setEmail(email).build());

    }
    private static  void login(Crendentials crendentials){
        switch (crendentials.getLoginTypeCase()){
            case EMAIL -> log.info("email -->{}",crendentials.getEmail());
            case PHONE -> log.info("phone -->{}",crendentials.getPhone());
        }
    }
}
