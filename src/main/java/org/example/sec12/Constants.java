package org.example.sec12;

import io.grpc.Metadata;

public class Constants {
    public static final Metadata.Key<String> API_KEY = Metadata.Key.of("api-key",Metadata.ASCII_STRING_MARSHALLER);

    public static final Metadata.Key<String> USER_TOKEN_KEY = Metadata.Key.of("Autohorization",Metadata.ASCII_STRING_MARSHALLER);

    public static final String BEARER = "Bearer";
}
