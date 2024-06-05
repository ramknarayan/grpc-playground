package org.example.sec03;

import org.example.models.sec03.Address;
import org.example.models.sec03.School;
import org.example.models.sec03.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec04Composition {
    private static final Logger log = LoggerFactory.getLogger((Lec03Performance.class));
    public static void main(String[] args){

        var address = Address.newBuilder()
                .setStreet("12 main St")
                .setCity("Wesley Chapel")
                .setSate("FL")
                .build();
        var student = Student.newBuilder()
                .setName("same")
                .setAddress(address)
                .build();
        var school = School.newBuilder()
                .setId(10)
                .setName("Middle School")
                .setAddress(address.toBuilder().setStreet("2342 mainST").build())
                .build();

        log.info("School : {}",school);
        log.info("Student: {}",student.getAddress().getStreet());
    }
}
