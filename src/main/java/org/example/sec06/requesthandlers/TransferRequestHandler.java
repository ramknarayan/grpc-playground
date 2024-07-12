package org.example.sec06.requesthandlers;

import io.grpc.stub.StreamObserver;
import org.example.models.sec06.AccountBalance;
import org.example.models.sec06.TransferRequest;
import org.example.models.sec06.TransferResponse;
import org.example.models.sec06.TransferStatus;
import org.example.sec06.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferRequestHandler implements StreamObserver<TransferRequest> {
    private static final Logger log = LoggerFactory.getLogger(TransferRequestHandler.class);
    private final StreamObserver<TransferResponse> responseObserver;

    public TransferRequestHandler(StreamObserver<TransferResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(TransferRequest transferRequest) {
        var status = this.transfer(transferRequest);
        //if(TransferStatus.COMPLETED.equals(status)) {
            var response = TransferResponse.newBuilder()
                    .setFromAccount(this.toAccountBalance(transferRequest.getFromAccount()))
                    .setToAccount(this.toAccountBalance(transferRequest.getToAccount()))
                    .setStatus(status)
                    .build();
            this.responseObserver.onNext(response);
        //}
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        log.info("Transfer request stream completed");
        this.responseObserver.onCompleted();
    }

    private TransferStatus transfer(TransferRequest request){
        var amount = request.getAmount();
        var fromAccount = request.getFromAccount();
        var toAccount = request.getToAccount();
        var status = TransferStatus.REJECTED;
        if(AccountRepository.getBalance(fromAccount)>=amount && (fromAccount != toAccount)){
            AccountRepository.deductAmount(fromAccount,amount);
            AccountRepository.addAmount(toAccount,amount);
            status =TransferStatus.COMPLETED;
        }
        return  status;
    }
    private AccountBalance toAccountBalance(int accountNumber) {
        return AccountBalance.newBuilder().setAccountNumber(accountNumber)
                .setBalance(AccountRepository.getBalance(accountNumber))
                .build();
    }
}
