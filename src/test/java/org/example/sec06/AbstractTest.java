package org.example.sec06;

import org.example.GrpcServer;
import org.example.common.AbstractChannelTest;
import org.example.models.sec06.BankServiceGrpc;
import org.example.models.sec06.TransferServiceGrpc;
import org.example.sec06.BankService;
import org.example.sec06.TransferService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class AbstractTest extends AbstractChannelTest {

    private final GrpcServer grpcServer =  GrpcServer.create(new BankService(),new TransferService());

    protected BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;
    protected BankServiceGrpc.BankServiceStub bankStub;

    protected TransferServiceGrpc.TransferServiceStub transferStub;
    @BeforeAll
    public void setup(){
        this.grpcServer.start();
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.bankStub = BankServiceGrpc.newStub(channel);
        this.transferStub = TransferServiceGrpc.newStub(channel);
    }
    @AfterAll
    public void stop(){
        this.grpcServer.stop();
    }

}
