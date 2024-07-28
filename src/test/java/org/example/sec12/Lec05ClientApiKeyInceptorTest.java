package org.example.sec12;

import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.example.GrpcServer;
import org.example.models.sec12.BalanceCheckRequest;
import org.example.sec12.interceptors.ApiKeyValidationInterceptor;
import org.example.sec12.interceptors.GzipResponseInterceptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Lec05ClientApiKeyInceptorTest extends AbstractInterceptorTest {
    public static final Logger log = LoggerFactory.getLogger(Lec05ClientApiKeyInceptorTest.class);

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {

        return List.of(MetadataUtils.newAttachHeadersInterceptor(getApiKey()));

    }
    private Metadata getApiKey(){
        var metadata = new Metadata();
        metadata.put(Constants.API_KEY,"bank-client-secret");
        return metadata;
    }
    @Override
    protected GrpcServer createServer(){
        return GrpcServer.create(6060,builder->{
            builder.addService(new BankService())
                    .intercept(new ApiKeyValidationInterceptor());
        });
    }
    @Test
    public void clientApiKeyDemo(){
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var response = this.bankBlockingStub.getAccountBalance(request);
        log.info("{}",response);
    }
}
