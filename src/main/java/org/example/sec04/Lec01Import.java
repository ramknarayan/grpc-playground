package org.example.sec04;


import org.example.models.common.Address;
import org.example.models.common.BodyStyle;
import org.example.models.common.Car;
import org.example.models.sec04.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01Import {
    private static final Logger log = LoggerFactory.getLogger((Lec01Import.class));

    public static void main(String[] args) {

        var address = Address.newBuilder().setCity("Atlanta").build();
        var car = Car.newBuilder().setYear(2020).setBodyStyle(BodyStyle.COUPE).build();
        var person = Person.newBuilder()
                .setName("Sam")
                .setAge(12)
                .setCar(car)
                .setAddress(address)
                .build();

        log.info("Person {}",person);
    }
}

