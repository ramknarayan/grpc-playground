package org.example.sec12;

import org.example.common.ResponseObserver;
import org.example.models.sec12.Money;
import org.example.models.sec12.WithdrawRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class Lec02ExecutorCallOptions extends AbstractTest{
    private static final Logger log = LoggerFactory.getLogger(Lec01GZipCallOptionsTest.class);
    @Test
    public void executorDemo(){
        var observer = ResponseObserver.<Money>create();
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(30)
                .build();
        this.bankStub
                .withExecutor(Executors.newSingleThreadExecutor())
                .withdraw(request,observer);
        observer.await();


    }
}
