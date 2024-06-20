package org.example.sec05;

import com.google.protobuf.InvalidProtocolBufferException;
import org.example.models.sec05.v1.Television;
import org.example.sec04.Lec01Import;
import org.example.sec05.Parser.V1Parser;
import org.example.sec05.Parser.V2Parser;
import org.example.sec05.Parser.V3Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V1VersionCompatibility {

    private static final Logger log = LoggerFactory.getLogger((Lec01Import.class));

    public static void main(String[] args) throws InvalidProtocolBufferException {
        var tv = Television.newBuilder()
                .setBrand("Samsung")
                .setYear(2019)
                .build();
        V1Parser.parser(tv.toByteArray());
        V2Parser.parser(tv.toByteArray());
        V3Parser.parser(tv.toByteArray());
    }
}
