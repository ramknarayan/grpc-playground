package org.example;

import org.example.sec010.BankService;
import org.example.sec06.TransferService;
import org.example.sec07.FlowControlService;

public class Demo {
    public static void main(String[] args) {
        GrpcServer.create(new BankService(),new TransferService(),new FlowControlService())
                .start()
                .await();

    }
}
