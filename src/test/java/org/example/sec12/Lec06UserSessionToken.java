package org.example.sec12;

import io.grpc.CallCredentials;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.example.GrpcServer;
import org.example.common.ResponseObserver;
import org.example.models.sec12.BalanceCheckRequest;
import org.example.models.sec12.Money;
import org.example.models.sec12.WithdrawRequest;
import org.example.sec12.interceptors.ApiKeyValidationInterceptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class Lec06UserSessionToken extends AbstractInterceptorTest {
    public static final Logger log = LoggerFactory.getLogger(Lec06UserSessionToken.class);

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {

        return Collections.emptyList();

    }

    @Override
    protected GrpcServer createServer(){
        return GrpcServer.create(6060,builder->{
            builder.addService(new BankService())
                    .intercept(new UserTokenInterCeptor());
        });
    }
    @Test
    public void unaryUserCredentialsDemo(){
       for (int i =1 ; i<=5;i++){
           var request = BalanceCheckRequest.newBuilder()
                   .setAccountNumber(1)
                   .build();
           var response = this.bankBlockingStub
                   .withCallCredentials(new UserSessionToken("user-token-"+i))
                   .getAccountBalance(request);
           log.info("{}",response);
       }
    }

    @Test
    public void streamingCredentialsDemo(){
        for (int i =1 ; i<=5;i++){
            var observer = ResponseObserver.<Money>create();
            var request = WithdrawRequest.newBuilder()
                    .setAccountNumber(i)
                    .setAmount(30)
                    .build();
            this.bankStub
                    .withCallCredentials(new UserSessionToken("user-token-"+i))
                    .withdraw(request,observer);


            observer.await();
        }
    }

    private static class UserSessionToken extends CallCredentials{
        public static final String TOKEN_FORMAT = "%s %s";
        public UserSessionToken(String jwt) {
            this.jwt = jwt;
        }

        private final String jwt;

        @Override
        public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
            executor.execute(()-> {
                        var metadata = new Metadata();
                        metadata.put(Constants.USER_TOKEN_KEY, TOKEN_FORMAT.formatted(Constants.BEARER, jwt));
                        metadataApplier.apply(metadata);
                    }
            );
        }
    }
}
