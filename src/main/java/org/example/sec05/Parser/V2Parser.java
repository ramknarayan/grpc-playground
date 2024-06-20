package org.example.sec05.Parser;


import com.google.protobuf.InvalidProtocolBufferException;
import org.example.models.sec05.v2.Television;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class V2Parser {
    private static final Logger log = LoggerFactory.getLogger((V2Parser.class));

    public  static void parser(byte[] bytes) throws InvalidProtocolBufferException {
        var tv = Television.parseFrom(bytes);

        log.info("Brand:{}", tv.getBrand());
        log.info("year:{}", tv.getYear());
        log.info("Type:{}", tv.getType());

    }

}
