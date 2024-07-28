package org.example.sec12.interceptors;

import io.grpc.*;
import org.example.sec12.Constants;

import java.util.Objects;

public class ApiKeyValidationInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        var apikey = metadata.get(Constants.API_KEY);
        if (isValid(apikey)) {

            return serverCallHandler.startCall(serverCall,metadata);
        }
        serverCall.close(
                Status.UNAUTHENTICATED.withDescription("Client must provide valid api key"),metadata
        );
        return new ServerCall.Listener<ReqT>() {
        };
    }
    private boolean isValid(String apiKey){
        return Objects.nonNull(apiKey) && apiKey.equals("bank-client-secret");
    }

}
