package org.example.sec10;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
import org.example.GrpcServer;
import org.example.common.AbstractChannelTest;
import org.example.models.sec10.BankServiceGrpc;
import org.example.models.sec10.ErrorMessage;
import org.example.models.sec10.ValidationCode;
import org.example.sec11.DeadlineBankService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.engine.TestExecutionResult;

import java.util.Optional;

public class AbstractTest extends AbstractChannelTest {

    private final GrpcServer grpcServer =  GrpcServer.create(new DeadlineBankService());
    private static final Metadata.Key<ErrorMessage> ERROR_MESSAGE_KEY = ProtoUtils.keyForProto(ErrorMessage.getDefaultInstance());
    protected BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;
    protected BankServiceGrpc.BankServiceStub bankStub;


    @BeforeAll
    public void setup(){
        this.grpcServer.start();
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.bankStub = BankServiceGrpc.newStub(channel);
    }
    @AfterAll
    public void stop(){
        this.grpcServer.stop();
    }

}
