package org.example;

import org.example.sec06.TransferService;
import org.example.sec07.FlowControlService;
import org.example.sec12.BankService;
import org.example.sec12.interceptors.ApiKeyValidationInterceptor;

public class Demo {
    public static void main(String[] args) {
       /* GrpcServer.create(new BankService(),new TransferService(),new FlowControlService())
                .start()
                .await();
*/
         GrpcServer.create(6060,builder->{
            builder.addService(new BankService())
                    .intercept(new ApiKeyValidationInterceptor());
        })
                 .start()
                 .await();
    }
}
