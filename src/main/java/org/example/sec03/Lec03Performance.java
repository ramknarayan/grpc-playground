package org.example.sec03;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import org.example.models.sec03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec03Performance {
    private static final Logger log = LoggerFactory.getLogger((Lec03Performance.class));
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args){

        var protoPerson = Person.newBuilder()
                .setLastName("Sam")
                .setAge(12)
                .setEmail("same@gmail.com")
                .setEmployed(true)
                .setSalary(1000.2345)
                .setBankAccountNumber(1212131311)
                .setBalance(-10000)
                .build();
        var jsonPerson = new JSonPerson("Sam",12,"sam@gamil.com",
                true,1000.2345,1212131311,-10000);
        for(int i =0;i<5;i++){
            runTest("json",()->json(jsonPerson));
            runTest("proto",()->proto(protoPerson));
        }

    }

    private static void proto(Person person){
        try{
            var bytes = person.toByteArray();
            //log.info("proto bytes length : {}",bytes.length);
            Person.parseFrom(bytes);
        }catch(InvalidProtocolBufferException e){
            throw new RuntimeException((e));
        }
    }

    private static void json(JSonPerson person){
        try{
            var bytes = mapper.writeValueAsBytes(person);
            //log.info("proto bytes length : {}",bytes.length);
           mapper.readValue(bytes,JSonPerson.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private static void runTest(String testName,Runnable runnable){
        var start = System.currentTimeMillis();
        for (int i=0;i<1000000;i++){
            runnable.run();
        }
        var end = System.currentTimeMillis();
        log.info("time taken for {} - {} ms",testName,(end-start));
    }

}
