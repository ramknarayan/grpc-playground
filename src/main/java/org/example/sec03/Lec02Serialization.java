package org.example.sec03;

import org.example.models.sec03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Lec02Serialization {

    private static final Logger log = LoggerFactory.getLogger(Lec02Serialization.class);
    private static final Path PATH = Path.of("person.out");
    public static void main(String[] args) throws IOException {
        var person = Person.newBuilder()
                .setLastName("Sam")
                .setAge(12)
                .build();
        log.info(" {}",person);
        serialize(person);

        log.info("{}",deserialize());
        log.info("equals:{}",person.equals(deserialize()));

    }
    public static void serialize(Person person) throws IOException {
        try(var stream = Files.newOutputStream(PATH)){
            person.writeTo(stream);
        }
    }


    public static Person deserialize() throws IOException{
        try(var stream = Files.newInputStream(PATH)){
            return  Person.parseFrom(stream);
        }

    }
}
