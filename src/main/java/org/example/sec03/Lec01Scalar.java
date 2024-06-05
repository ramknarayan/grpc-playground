package org.example.sec03;

import org.example.models.sec03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Lec01Scalar {

    private static final Logger log = LoggerFactory.getLogger(Lec01Scalar.class);

    public static void main(String[] args){
        var person = Person.newBuilder()
                .setLastName("Sam")
                .setAge(12)
                .build();
        log.info(" {}",person);
    }
}
