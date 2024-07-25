package org.example.sec12;

import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.GrpcServer;
import org.example.models.sec12.BankServiceGrpc;
import org.example.sec12.interceptors.GzipResponseInterceptor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractInterceptorTest {

    private GrpcServer grpcServer;
    protected ManagedChannel channel;
    protected BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;
    protected BankServiceGrpc.BankServiceStub bankStub;

    protected abstract List<ClientInterceptor> getClientInterceptors();
    protected GrpcServer createServer(){
        return GrpcServer.create(6060,builder->{
           builder.addService(new BankService())
                   .intercept(new GzipResponseInterceptor());
        });
    }
    @BeforeAll
    public void setup(){
        this.grpcServer = createServer();
        this.grpcServer.start();
        this.channel = ManagedChannelBuilder.forAddress("localhost",6060)
                .usePlaintext()
                .intercept(getClientInterceptors())
                .build();
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.bankStub = BankServiceGrpc.newStub(channel);
    }
    @AfterAll
    public void stop(){

        this.grpcServer.stop();
        this.channel.shutdown();
    }
}
