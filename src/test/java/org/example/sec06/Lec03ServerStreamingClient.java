package org.example.sec06;

import org.example.common.ResponseObserver;
import org.example.models.sec06.Money;
import org.example.models.sec06.WithdrawRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec03ServerStreamingClient extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(Lec03ServerStreamingClient.class);

    @Test
    public void blockingClientWithdrawTest(){
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(2)
                .setAmount(20)
                .build();
        var iterator = this.bankBlockingStub.withdraw(request);
        int count =0;
        while(iterator.hasNext()){
            log.info("Received money:{}",iterator.next());
            count++;
        }
        Assertions.assertEquals(2,count);
    }

    @Test
    public void asyncClientWithdrawTest(){
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(2)
                .setAmount(20)
                .build();
        var observer = ResponseObserver.<Money>create();
        this.bankStub.withdraw(request,observer);
        observer.await();
        Assertions.assertEquals(2,observer.getItems().size());
        Assertions.assertEquals(10,observer.getItems().get(0).getAmount());
        Assertions.assertNull(observer.getThrowable());

    }

}
