package org.example.sec12;

import io.grpc.ClientInterceptor;
import org.example.models.sec12.BalanceCheckRequest;
import org.example.sec12.interceptors.DeadlineInterceptor;
import org.example.sec12.interceptors.GzipRequestInterceptor;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

public class Lec04GzipInterceptorsTest extends AbstractInterceptorTest{

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return List.of(new GzipRequestInterceptor(), new DeadlineInterceptor(Duration.ofSeconds(2)));

    }
    @Test
    public  void gzipDemo(){
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var response = this.bankBlockingStub.getAccountBalance(request);
    }

}
