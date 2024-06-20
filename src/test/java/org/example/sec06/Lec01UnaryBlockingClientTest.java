package org.example.sec06;


import com.google.protobuf.Empty;
import org.example.models.sec06.BalanceCheckRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01UnaryBlockingClientTest extends AbstractTest{
    private static final Logger log = LoggerFactory.getLogger(Lec01UnaryBlockingClientTest.class);

    @Test
    public void getBalanceTest(){
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var balance = this.blockingStub.getAccountBalance(request);

        log.info("Unary balance received: {}",balance);
        Assertions.assertEquals(100,balance.getBalance());
    }
   @Test
    public void getAllAccount()
    {
        var allAccount = this.blockingStub.getAllAccount(Empty.getDefaultInstance());
        log.info("Unary balance received: {}",allAccount);
        Assertions.assertEquals(10,allAccount.getAccountsCount());

    }

}
