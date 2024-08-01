package org.example.sec12;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;

public class UserTokenInterCeptor implements ServerInterceptor {
    public static final Set<String> PRIME_SET = Set.of("user-token-1","user-token-2");
    public static final Set<String> STANDARTD_SET = Set.of("user-token-3","user-token-4");

    private static final Logger log = LoggerFactory.getLogger(UserTokenInterCeptor.class);
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        var token = extractToken(metadata.get(Constants.USER_TOKEN_KEY));
        log.info("{}"+token);
        if(!isvalid(token)){
            return close(serverCall,metadata,Status.UNAUTHENTICATED.withDescription("token is either null or invalid"));

        }
        var isOneMessage = serverCall.getMethodDescriptor().getType().serverSendsOneMessage();
        if(isOneMessage || PRIME_SET.contains(token)){
            return serverCallHandler.startCall(serverCall,metadata);
        }
        return close(serverCall,metadata,Status.PERMISSION_DENIED.withDescription("user is not allowed to do this operations"));
    }
    private String extractToken(String value){
        return Objects.nonNull(value) && value.startsWith(Constants.BEARER)?
                value.substring(Constants.BEARER.length()).trim():null;
    }

    private boolean isvalid(String token){
        return Objects.nonNull(token) && (PRIME_SET.contains(token)|| STANDARTD_SET.contains(token));
    }
    private <ReqT,RespT> ServerCall.Listener<ReqT> close(ServerCall<ReqT,RespT> serverCall, Metadata metadata, Status status){
        serverCall.close(status,metadata);
        return new ServerCall.Listener<ReqT>() {
        };
    }
}
