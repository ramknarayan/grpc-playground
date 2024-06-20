package org.example.sec06;

import io.grpc.stub.AbstractStub;
import org.example.GrpcServer;
import org.example.common.AbbstractChannelTest;
import org.example.models.sec06.BankServiceGrpc;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class AbstractTest extends AbbstractChannelTest {

    private final GrpcServer grpcServer =  GrpcServer.create(new BankService());

    protected BankServiceGrpc.BankServiceBlockingStub blockingStub;
    protected BankServiceGrpc.BankServiceStub stub;
    @BeforeAll
    public void setup(){
        this.grpcServer.start();
        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.stub = BankServiceGrpc.newStub(channel);
    }
    @AfterAll
    public void stop(){
        this.grpcServer.stop();
    }

}
