package org.example.sec06;

import com.google.common.util.concurrent.Uninterruptibles;
import org.example.common.ResponseObserver;
import org.example.models.sec06.AccountBalance;
import org.example.models.sec06.DepositRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class Lec04ClientStreamingTest extends AbstractTest {

    @Test
    public void depositTest(){
        var responseObserver = ResponseObserver.<AccountBalance>create();
        var requestObserver = this.bankStub.deposit(responseObserver);

        requestObserver.onNext(DepositRequest.newBuilder().setAccountNumber(5).build());


        requestObserver.onError(new RuntimeException());
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
  /*      IntStream.rangeClosed(1,10)
                .mapToObj(i -> Money.newBuilder().setAmount(10).build())
                .map(m->DepositRequest.newBuilder().setMoney(m).build())
                .forEach(requestObserver::onNext);

        //notify the server that we are done
        requestObserver.onCompleted();

        responseObserver.await();*/

        //Assertions.assertEquals(1,responseObserver.getItems().size());
        //Assertions.assertEquals(200,responseObserver.getItems().get(0).getBalance());
        //Assertions.assertNull(responseObserver.getThrowable());
        System.out.println("Error " + responseObserver.getThrowable());
        Assertions.assertNotNull(responseObserver.getThrowable());
    }
}
