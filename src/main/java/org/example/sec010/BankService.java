package org.example.sec010;

import com.google.common.util.concurrent.Uninterruptibles;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.example.models.sec10.*;
import org.example.sec010.repository.AccountRepository;
import org.example.sec010.validator.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class BankService extends BankServiceGrpc.BankServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BankService.class);
    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        RequestValidator.validateAccount(request.getAccountNumber())

                .ifPresentOrElse(
                        responseObserver::onError,
                        ()->sendAccountBalance(request,responseObserver)
                );

    }
    public void sendAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver){
        var accountNumber = request.getAccountNumber();
        var balance = AccountRepository.getBalance(accountNumber);
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(balance)
                .build();
        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        RequestValidator.validateAccount(request.getAccountNumber())
                .or(()-> RequestValidator.isAmountDivisibleBy10(request.getAmount()))
                .or(()-> RequestValidator.hasSufficientBalance(request.getAmount(), AccountRepository.getBalance(request.getAccountNumber())))
                  .ifPresentOrElse(
                        responseObserver::onError,
                        ()->sendMoney( request,  responseObserver)
                );

    }
    public void sendMoney(WithdrawRequest request, StreamObserver<Money> responseObserver){
        var accountNumber = request.getAccountNumber();
        var requestedAmount = request.getAmount();
        var accountBalance = AccountRepository.getBalance(accountNumber);

        if (requestedAmount>accountBalance){
            responseObserver.onCompleted();
            return;
        }

        for(int i =0;i<(requestedAmount/10);i++){
            var money = Money.newBuilder().setAmount(10).build();
            responseObserver.onNext(money);
            log.info("money sent{}",money);
            AccountRepository.deductAmount(accountNumber,10);
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        }
        responseObserver.onCompleted();
    }

}
