package org.example.sec03;

import org.example.models.sec03.BodyStyle;
import org.example.models.sec03.Car;
import org.example.models.sec03.Dealer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec06Map {
    private static final Logger log = LoggerFactory.getLogger((Lec03Performance.class));

    public static void main(String[] args){

        var car1 = Car.newBuilder()
                .setMake("Honda")
                .setModel("Civic")
                .setYear(2000)
                .setBodyStyle(BodyStyle.COUPE)
                .build();
        var car2 = Car.newBuilder()
                .setMake("Honda")
                .setModel("accord")
                .setYear(2002)
                .setBodyStyle(BodyStyle.SEDAN)
                .build();
        var dealer = Dealer.newBuilder()
                .putInventary(car1.getYear(),car1)
                .putInventary(car2.getYear(),car2)
                .build();

        log.info("{}",dealer);

        log.info("2002 ? : {}",dealer.containsInventary(2002));
        log.info("2003 ? : {}",dealer.containsInventary(2003));

        log.info("2002 model: {}",dealer.getInventaryOrThrow(2002).getModel());
        log.info("2002 model: {}",dealer.getInventaryOrThrow(2002).getBodyStyle());

    }
}
